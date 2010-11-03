/**
 * (c)2010 Eric Schult
 * All Rights Reserved
 * 
 */
package dpclock.ui.stopwatch;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import dpclock.service.InternalStateBean;

@Component
public class StopWatchBeanImpl implements StopWatchBean, InternalStateBean {

	private static final long serialVersionUID = -7009614255479517894L;
	private static final Log log = LogFactory.getLog(StopWatchBeanImpl.class);
	private transient Timer timer;

	private int timeLeft = 0;
	private int state = STATE_STOPPED;
	private long lastEventTime;

	protected boolean doAlarm = false;

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this); 
	
	//State sync and transfer
	public static final class InternalState implements Serializable {
		private static final long serialVersionUID = 1L;
		public int timeLeft = 0;
		public int state = STATE_STOPPED;
		public long lastEventTime;
	}

	
	public StopWatchBeanImpl() {
		timer = new Timer(100, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (state == STATE_RUNNING) {
					int time = (int) (e.getWhen() - lastEventTime);
					int secElapsed = time / 1000;
					if (secElapsed > 0) {
						lastEventTime += (secElapsed * 1000);
						int newTime = timeLeft - secElapsed; 
						setTimeLeft(newTime);
					}
				}
			}
		});
	}

	
	@Override
	public int getState() {
		return this.state;
	}

	private void setState(int state) {		
		if (state == STATE_RUNNING) {
			if (timeLeft > 0) {
				this.lastEventTime = System.currentTimeMillis();
				this.timer.restart();
				firePropertyChange(PROP_STATE, this.state, this.state = state);
			}
		} else {
			this.timer.stop();
			firePropertyChange(PROP_STATE, this.state, this.state = state);
		}
	}

	@Override
	public int getTimeLeft() {
		return this.timeLeft;
	}

	public void setTimeLeft(int timeLeft) {
		if (timeLeft < 0)
			timeLeft = 0;

		firePropertyChange(PROP_TIMELEFT, this.timeLeft,
				this.timeLeft = timeLeft);
	}

	@Override
	public boolean isRunning() {
		return state == STATE_RUNNING;
	}

	@Override
	public boolean isPaused() {
		return state == STATE_PAUSED;
	}

	@Override
	public boolean isStopped() {
		return state==STATE_STOPPED || state==STATE_ALARM;
	}

	@Override
	public int togglePause() {
		if (state == STATE_RUNNING) {
			setState(STATE_PAUSED);
		} else {
			setState(STATE_RUNNING);
		}
		return state;
	}

	@Override
	public void stop() {
		setState(STATE_STOPPED);
	}
	
	@Override
	public void start() {
		if (!isRunning())
			setState(STATE_RUNNING);
	}
	
	@Override
	public void pause() {
		if (isRunning()) {
			setState(STATE_PAUSED);
		}
	}

	@Override
	public Serializable saveInternalState() {
		log.info(">> saveInternalState()");
		InternalState is = new InternalState();
		is.lastEventTime = this.lastEventTime;
		is.state = this.state;
		is.timeLeft = this.timeLeft;
		return is;
	}
	
	@Override
	public void restoreInternalState(final Serializable s) {
		log.info(">> restoreInternalState()");
		
		if (s instanceof InternalState) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					InternalState is = (InternalState)s;

					final int oldTimeLeft = timeLeft;
					final int oldState = state;
					state = is.state;
					lastEventTime = is.lastEventTime;
					timeLeft = is.timeLeft;
					
					firePropertyChange(PROP_TIMELEFT, oldTimeLeft, timeLeft);
					firePropertyChange(PROP_STATE,    oldState,    state);

					timer.restart();
				}
			});
		}
	}


	///////////////////////////////////
	// BEGIN Property Change Support //
	///////////////////////////////////

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}
	
	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(propertyName, listener);
	}

	public void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
		pcs.firePropertyChange(propertyName, oldValue, newValue);
	}

	public void firePropertyChange(String propertyName, int oldValue,
			int newValue) {
		pcs.firePropertyChange(propertyName, oldValue, newValue);
	}

	public void firePropertyChange(String propertyName, boolean oldValue,
			boolean newValue) {
		pcs.firePropertyChange(propertyName, oldValue, newValue);
	}

	public void firePropertyChange(PropertyChangeEvent evt) {
		pcs.firePropertyChange(evt);
	}

	/////////////////////////////////
	// END Property Change Support //
	/////////////////////////////////
	
}
