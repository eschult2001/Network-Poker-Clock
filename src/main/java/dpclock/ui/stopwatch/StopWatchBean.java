package dpclock.ui.stopwatch;

import java.beans.PropertyChangeListener;

public interface StopWatchBean {

	public static final int STATE_STOPPED = 0;
	public static final int STATE_RUNNING = 1;
	public static final int STATE_PAUSED = 2;
	public static final int STATE_ALARM = 3;
	
	public static final String PROP_TIMELEFT = "timeRemaining";
	public static final String PROP_STATE = "state";

	//Query
	public abstract int getState();
	public abstract int getTimeLeft();
	public abstract boolean isRunning();
	public abstract boolean isPaused();
	public abstract boolean isStopped();

	//Actions
	public abstract void stop();
	public abstract void start();
	public abstract void pause();
	public abstract int togglePause();
	
	//Property change support
	public void addPropertyChangeListener(PropertyChangeListener listener);
	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener);
	
	public void removePropertyChangeListener(PropertyChangeListener listener);
	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener);

}