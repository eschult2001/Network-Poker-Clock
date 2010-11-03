package dpclock.service;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
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
import org.springframework.beans.factory.InitializingBean;

import dpclock.schema.LevelBean;
import dpclock.schema.Tournament;
import dpclock.schema.TournamentLevelBean;
import dpclock.ui.stopwatch.StopWatchBean;
import dpclock.ui.stopwatch.StopWatchBeanImpl;


public class TournamentControllerImpl implements InternalStateBean, StopWatchBean, PropertyChangeListener, TournamentController {

	private static final Log log = LogFactory.getLog(TournamentControllerImpl.class);

	private StopWatchBeanImpl stopWatchBean = new StopWatchBeanImpl(); 
	private MutableComboBoxModel levels = new DefaultComboBoxModel(new TournamentLevelBean[]{
			new TournamentLevelBean()
	});
	private Tournament settings = null; 
	private RpcDispatcher rpcDispatcher;
	private SwingPropertyChangeSupport pcs = new SwingPropertyChangeSupport(this,true);
	private String tournamentName; 
	
	private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];
	private static final Object[]  EMPTY_OBJECT_ARRAY = new Object[0];
	
	private static final Method restoreInternalStateMethod;
	private static final Method saveInternalStateMethod;
	static {
		try {
			restoreInternalStateMethod = TournamentControllerImpl.class.getMethod("restoreInternalState", Serializable.class);
			saveInternalStateMethod = TournamentControllerImpl.class.getMethod("saveInternalState", EMPTY_CLASS_ARRAY);
		} catch (Exception e) {
			throw new RuntimeException("Could not find method", e);
		}
	}

	/**
	 * RpcDispatcher property get
	 * @return
	 */
	public RpcDispatcher getRpcDispatcher() {
		return rpcDispatcher;
	}
	public void setRpcDispatcher(RpcDispatcher rpcDispatcher) {
		this.rpcDispatcher = rpcDispatcher;
	}
	

	/**
	 * TournamentNameProperty
	 * 
	 * @return
	 */
	public String getTournamentName() {
		return tournamentName;
	}
	public void setTournamentName(String tournamentName) {
		pcs.firePropertyChange("tournamentName", this.tournamentName, this.tournamentName = tournamentName);
	}
	
	
	/**
	 * StopWatchBean property
	 * 
	 * @return
	 */
	public StopWatchBean getStopWatchBean() {
		return stopWatchBean;
	}
	

	/**
	 * Levels property
	 * 
	 * @return
	 */
	public MutableComboBoxModel getLevels() {
		return levels;
	}
	public void setLevels(MutableComboBoxModel levels) {
		this.levels = levels;
		levels.setSelectedItem(levels.getElementAt(0));
	}

	
	
	/**
	 * return the current <code>settings</code>.  This is only really used on initialization.
	 * 
	 * @return
	 */
	public Tournament getSettings() {
		return settings;
	}

	/**
	 * Sets the current settings for the tournament.  This is only used during bean configuration
	 * 
	 * @param settings  {@link Tournament} object
	 */
	public void setSettings(Tournament settings) {
		this.settings = settings;
	}

	

	/**
	 * Returns the current <code>level<code>
	 * 
	 * @return {@link LevelBean} representing current level.  If <code>null</code> then something is wrong
	 */
	@Override
	public LevelBean getLevel() {
		return (LevelBean) levels.getSelectedItem();
	}	
	/**
	 * Sets the current <code>level</code>
	 * 
	 * @param {{@link LevelBean} new level
	 * @param level
	 */
	public void setLevel(LevelBean level) {
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
		System.out.println(evt.getSource() + "[" + evt.getPropertyName() + "] "
				+ evt.getOldValue() + "->" + evt.getNewValue());
		//if (evt.getSource().equals(stopWatchBean)) {
			boolean handled = false;
			if (StopWatchBean.PROP_TIMELEFT.equals(evt.getPropertyName())
					&& ((Integer) evt.getNewValue()).intValue() == 0
					&& stopWatchBean.isRunning()) {

				pcs.firePropertyChange(evt.getPropertyName(),
						evt.getOldValue(), evt.getNewValue());

				nextLevel();
				handled = true;
			}
			if (!handled) {
				pcs.firePropertyChange(evt.getPropertyName(),
						evt.getOldValue(), evt.getNewValue());
			}
		//}
	}
	
	/**
	 * Returns the current level bean.  If no current level exists, returns the first item in the list.
	 * 
	 * The list should never be empty.
	 * 
	 * @return
	 */
	private LevelBean safeCurrentLevel() {
		Object curr = levels.getSelectedItem();
		if (curr==null) {
			curr = levels.getElementAt(0);
		}
		return (LevelBean) curr;
	}
	
	@Override
	public void nextLevel() {
		LevelBean nextLevelBean = safeCurrentLevel().getNext();
		if (nextLevelBean != null) {
			if (nextLevelBean.getTime() <= 0)
				pause();
			setLevel(nextLevelBean);
		}
	}

	@Override
	public void prevLevel() {
		LevelBean prevLevelBean = safeCurrentLevel().getPrev();
		if (prevLevelBean != null) {
			setLevel(prevLevelBean);
		}
	}

	@PostConstruct
	public void afterPropertiesSet() throws Exception {
		stopWatchBean.addPropertyChangeListener(this);
		if (settings!=null) {
			loadSettings(settings);
		}
	};

	
	
	
	

	public void doResetClock() {
		//stopWatchBean.stop();
		stopWatchBean.setTimeLeft(getLevel().getTime());
	}
	

	public static final class InternalState implements Serializable {
		private static final long serialVersionUID = 1L;
		public int level = 0;
		public Serializable stopWatchState;
	}

	private int getIndexOf(Object o) {
		int size = levels.getSize();
		for(int i=0; i<size; i++) {
			if (levels.getElementAt(i).equals(o)) {
				return i;
			}
		}
		return 0;
	}
	
	public void loadSettings(Tournament newt) {
		
		log.info(">> loadSettings()");
		
		final MutableComboBoxModel model = this.levels;
		final Tournament t = newt;
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				setTournamentName(t.getTournamentName());
				
				//fix level prev/next and add them to the comboboxmodel
				while(model.getSize()>0) 
					model.removeElementAt(0);

				LevelBean prev = null;
				for(LevelBean l : t.getLevels()) {
					if (prev!=null) {
						l.setPrev(prev);
						prev.setNext(l);
					}
					prev = l;
					model.addElement(l);
				}
				model.setSelectedItem(t.getCurrentLevel());
				
				doResetClock();
			}
		});
	}
	
	public void syncStates() {
		log.info(">> syncStates()");
		
		Serializable state = saveInternalState();
		MethodCall mc = new MethodCall(restoreInternalStateMethod, new Object[]{state});
		try {
			rpcDispatcher.callRemoteMethods(null, mc, RequestOptions.ASYNC);
		} catch (Throwable e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public Serializable saveInternalState() {
		log.info(">> saveInternalState()");
		InternalState is = new InternalState();
		is.level = getIndexOf(levels.getSelectedItem());
		is.stopWatchState = stopWatchBean.saveInternalState();
		return is;
	}

	@Override
	public void restoreInternalState(Serializable s) {
		log.info(">> restoreInternalState()");
		if (s instanceof InternalState) {
			InternalState is = (InternalState)s;
			levels.setSelectedItem(levels.getElementAt(is.level));
			stopWatchBean.restoreInternalState(is.stopWatchState);
		}
	}
	
	
	
	// StopWatchBean.java
	
	public int getState() {
		return stopWatchBean.getState();
	}
	public int getTimeLeft() {
		return stopWatchBean.getTimeLeft();
	}
	public boolean isRunning() {
		return stopWatchBean.isRunning();
	}
	public boolean isPaused() {
		return stopWatchBean.isPaused();
	}
	public boolean isStopped() {
		return stopWatchBean.isStopped();
	}
	public void stop() {
		stopWatchBean.stop();
	}
	public void start() {
		stopWatchBean.start();
	}
	public void pause() {
		stopWatchBean.pause();
	}
	public int togglePause() {
		if (isPaused() && getTimeLeft()==0)
			nextLevel();
		return stopWatchBean.togglePause();
	}
	
	
	
	
	// Property Change Support
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(propertyName, listener);
	}
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}
	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(propertyName, listener);
	}
	
	@Override
	public void setTime(int newtime) {
		stopWatchBean.setTimeLeft(newtime);
		
	}
	
}
