package dpclock.service.remote;

import java.beans.PropertyChangeListener;

import dpclock.schema.LevelBean;
import dpclock.service.TournamentController;
import dpclock.service.TournamentControllerImpl;

	
public class TournamentRemoteProxy implements TournamentController {

	private TournamentControllerImpl controller;

	public void setController(TournamentControllerImpl controller) {
		this.controller = controller;
	}

	public LevelBean getLevel() {
		return controller.getLevel();
	}

	public void setLevel(LevelBean level) {
		controller.setLevel(level);
		controller.syncStates();
	}

	public void nextLevel() {
		controller.nextLevel();
		controller.syncStates();
	}

	public void prevLevel() {
		controller.prevLevel();
		controller.syncStates();
	}

	public int getState() {
		return controller.getState();
	}

	public int getTimeLeft() {
		return controller.getTimeLeft();
	}
	
	@Override
	public void setTime(int newtime) {
		controller.setTime(newtime);
		controller.syncStates();
	}

	public boolean isRunning() {
		return controller.isRunning();
	}

	public boolean isPaused() {
		return controller.isPaused();
	}

	public boolean isStopped() {
		return controller.isStopped();
	}

	public void stop() {
		controller.stop();
		controller.syncStates();
	}

	public void start() {
		controller.start();
		controller.syncStates();
	}

	public void pause() {
		controller.pause();
		controller.syncStates();
	}

	public int togglePause() {
		int ret = controller.togglePause();
		controller.syncStates();
		return ret;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		controller.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		controller.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		controller.removePropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		controller.removePropertyChangeListener(propertyName, listener);
	}

	
}
