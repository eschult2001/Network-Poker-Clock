/**
 * (c)2010 Eric Schult
 * All Rights Reserved
 * 
 */
package dpclock.service;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.PostConstruct;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.MutableComboBoxModel;
import javax.swing.SwingUtilities;
import javax.swing.event.SwingPropertyChangeSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgroups.blocks.GroupRequest;
import org.jgroups.blocks.MethodCall;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.blocks.RpcDispatcher;
import org.jgroups.util.RspList;
import org.springframework.beans.MethodInvocationException;
import org.springframework.beans.factory.InitializingBean;

import dpclock.schema.LevelBean;
import dpclock.schema.Tournament;
import dpclock.schema.TournamentLevelBean;
import dpclock.ui.stopwatch.StopWatchBean;
import dpclock.ui.stopwatch.StopWatchBeanImpl;


public class TournamentControllerImpl implements InternalStateBean, StopWatchBean, PropertyChangeListener, TournamentController {

	/**
	 * 
	 */
	private static final String METHOD_RESTORE_INTERNAL_STATE = "restoreInternalState";

	private static final Log log = LogFactory.getLog(TournamentControllerImpl.class);

	private StopWatchBeanImpl stopWatchBean = new StopWatchBeanImpl(); 
	private DefaultComboBoxModel levels = new DefaultComboBoxModel(new TournamentLevelBean[]{
			new TournamentLevelBean()
	});
	private Tournament settings = null; 
	private RpcDispatcher rpcDispatcher;
	private SwingPropertyChangeSupport pcs = new SwingPropertyChangeSupport(this,true);
	private String tournamentName; 
	
	private static final Method restoreInternalStateMethod;
	static {
		try {
			restoreInternalStateMethod = TournamentControllerImpl.class.getMethod(METHOD_RESTORE_INTERNAL_STATE, Serializable.class);
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("Could not locate the '"+METHOD_RESTORE_INTERNAL_STATE+"(Serializable)' method on the TournamentControllerImpl class",e);
		}
	}

	/**
	 * RpcDispatcher property get
	 * @return
	 */
	public final RpcDispatcher getRpcDispatcher() {
		return rpcDispatcher;
	}
	public final void setRpcDispatcher(RpcDispatcher rpcDispatcher) {
		this.rpcDispatcher = rpcDispatcher;
	}
	

	/**
	 * TournamentNameProperty
	 * 
	 * @return
	 */
	public final String getTournamentName() {
		return tournamentName;
	}
	public final void setTournamentName(String tournamentName) {
		pcs.firePropertyChange("tournamentName", this.tournamentName, this.tournamentName = tournamentName);
	}
	
	
	/**
	 * StopWatchBean property
	 * 
	 * @return
	 */
	public final StopWatchBean getStopWatchBean() {
		return stopWatchBean;
	}
	

	/**
	 * Levels property
	 * 
	 * @return
	 */
	public final MutableComboBoxModel getLevels() {
		return levels;
	}
	
	
	/**
	 * return the current <code>settings</code>.  This is only really used on initialization.
	 * 
	 * @return
	 */
	public final Tournament getSettings() {
		return settings;
	}

	/**
	 * Sets the current settings for the tournament.  This is only used during bean configuration
	 * 
	 * @param settings  {@link Tournament} object
	 */
	public final void setSettings(Tournament settings) {
		this.settings = settings;
	}

	

	/**
	 * Returns the current <code>level<code>
	 * 
	 * @return {@link LevelBean} representing current level.  If <code>null</code> then something is wrong
	 */
	@Override
	public final LevelBean getLevel() {
		return (LevelBean) levels.getSelectedItem();
	}	
	/**
	 * Sets the current <code>level</code>
	 * 
	 * @param {{@link LevelBean} new level
	 * @param level
	 */
	public final void setLevel(LevelBean level) {
		levels.setSelectedItem(level);
		setTime(level.getTime());
	}
	
	/////////////////////////////////////////////////////
	
	/**
	 * Handles events from child components.
	 * 
	 * Message from stopWatchBean are handled thusly: 
	 * 
	 * Rule: 
	 * 		If the current event is from the timer
	 * 			If the clock is RUNNING and reaches 0 and the current level is autoNext, 
	 * 				call nextLevel and restart.  Consume the current event.
	 *			otherwise			
	 * 				forward the event 
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		log.debug(evt.getSource() + "[" + evt.getPropertyName() + "] "
				+ evt.getOldValue() + "->" + evt.getNewValue());

		boolean handled = false;
		if (StopWatchBean.PROP_TIMELEFT.equals(evt.getPropertyName())
				&& ((Integer) evt.getNewValue()).intValue() == 0
				&& stopWatchBean.isRunning()) {

			pcs.firePropertyChange(evt.getPropertyName(), evt.getOldValue(),
					evt.getNewValue());

			nextLevel();
			handled = true;
		}
		if (!handled) {
			pcs.firePropertyChange(evt.getPropertyName(), evt.getOldValue(),
					evt.getNewValue());
		}
	}
	
	/**
	 * Returns the current level bean.  If no current level exists, returns the first item in the list.
	 * 
	 * The list should never be empty.
	 * 
	 * @return
	 */
	private final LevelBean safeCurrentLevel() {
		LevelBean curr = (LevelBean) levels.getSelectedItem();
		return  curr != null ? curr : (LevelBean)levels.getElementAt(0);
	}
	
	@Override
	public void nextLevel() {
		LevelBean nextLevelBean = safeCurrentLevel().getNext();
		if (nextLevelBean != null) {
			if (nextLevelBean.getTime() <= 0 )
				pause();
			setLevel(nextLevelBean);
		}
	}

	@Override
	public final void prevLevel() {
		LevelBean prevLevelBean = safeCurrentLevel().getPrev();
		if (prevLevelBean != null) {
			setLevel(prevLevelBean);
		}
	}

	@PostConstruct
	public final void init() throws Exception {
		stopWatchBean.addPropertyChangeListener(this);
		if (settings!=null) {
			loadSettings(settings);
		}
	};

	
	
	
	

	public final void doResetClock() {
		stopWatchBean.setTimeLeft(getLevel().getTime());
	}
	

	public static final class InternalState implements Serializable {
		private static final long serialVersionUID = 1L;
		public int level = 0;
		public Serializable stopWatchState;
	}
	
	public final void loadSettings(final Tournament t) {
		
		log.info(">> loadSettings()");
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				setTournamentName(t.getTournamentName());
				
				levels.removeAllElements();

				LevelBean prev = null;
				for(LevelBean l : t.getLevels()) {
					if (prev!=null) {
						l.setPrev(prev);
						prev.setNext(l);
					}
					prev = l;
					levels.addElement(l);
				}
				levels.setSelectedItem(t.getCurrentLevel());
				stop();
				doResetClock();
			}
		});
	}
	
	public final void syncStates() {
		log.info(">> syncStates()");
		Serializable state = saveInternalState();
		MethodCall mc = new MethodCall(restoreInternalStateMethod, new Object[]{state});
		rpcDispatcher.callRemoteMethods(null, mc, RequestOptions.ASYNC);
	}
	
	@Override
	public final Serializable saveInternalState() {
		log.info(">> saveInternalState()");
		final InternalState is = new InternalState();
		is.level = levels.getIndexOf(levels.getSelectedItem());
		is.stopWatchState = stopWatchBean.saveInternalState();
		return is;
	}

	@Override
	public final void restoreInternalState(Serializable s) {
		log.info(">> restoreInternalState()");
		if (s instanceof InternalState) {
			final InternalState is = (InternalState)s;
			levels.setSelectedItem(levels.getElementAt(is.level));
			stopWatchBean.restoreInternalState(is.stopWatchState);
		}
	}
	
	
	
	// StopWatchBean.java
	
	public final int getState() {
		return stopWatchBean.getState();
	}
	public final int getTimeLeft() {
		return stopWatchBean.getTimeLeft();
	}
	public final boolean isRunning() {
		return stopWatchBean.isRunning();
	}
	public final boolean isPaused() {
		return stopWatchBean.isPaused();
	}
	public final boolean isStopped() {
		return stopWatchBean.isStopped();
	}
	public final void stop() {
		stopWatchBean.stop();
	}
	public final void start() {
		stopWatchBean.start();
	}
	public final void pause() {
		stopWatchBean.pause();
	}
	public final int togglePause() {
		if (isPaused() && getTimeLeft()==0)
			nextLevel();
		return stopWatchBean.togglePause();
	}
	
	
	
	
	// Property Change Support
	public final void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	public final void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(propertyName, listener);
	}
	public final void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}
	public final void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(propertyName, listener);
	}
	
	@Override
	public final void setTime(final int newtime) {
		stopWatchBean.setTimeLeft(newtime);
		
	}
	
	/* (non-Javadoc)
	 * @see dpclock.service.TournamentController#doExit()
	 */
	@Override
	public final void doExit() {
		stopWatchBean.stop();
		rpcDispatcher.getChannel().close();		
	}
}
