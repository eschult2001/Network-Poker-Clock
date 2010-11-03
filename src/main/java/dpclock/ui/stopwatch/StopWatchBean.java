/**
 * (c)2010 Eric Schult
 * All Rights Reserved
 * 
 */
package dpclock.ui.stopwatch;

import java.beans.PropertyChangeListener;

public interface StopWatchBean {

	static final int STATE_STOPPED = 0;
	static final int STATE_RUNNING = 1;
	static final int STATE_PAUSED = 2;
	static final int STATE_ALARM = 3;
	
	static final String PROP_TIMELEFT = "timeRemaining";
	static final String PROP_STATE = "state";

	//Query
	int getState();
	int getTimeLeft();
	boolean isRunning();
	boolean isPaused();
	boolean isStopped();

	//Actions
	void stop();
	void start();
	void pause();
	int togglePause();
	
	//Property change support
	void addPropertyChangeListener(PropertyChangeListener listener);
	void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener);
	
	void removePropertyChangeListener(PropertyChangeListener listener);
	void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener);

}