/**
 * (c)2010 Eric Schult
 * All Rights Reserved
 * 
 */
package dpclock.service.remote;

import java.beans.PropertyChangeListener;

import dpclock.schema.LevelBean;
import dpclock.service.TournamentController;
import dpclock.service.TournamentControllerImpl;

	
public class TournamentRemoteProxy implements TournamentController {

	private TournamentControllerImpl controller;

	public final void setController(final TournamentControllerImpl controller) {
		this.controller = controller;
	}

	public final LevelBean getLevel() {
		return controller.getLevel();
	}

	public final void setLevel(final LevelBean level) {
		controller.setLevel(level);
		controller.syncStates();
	}

	public final void nextLevel() {
		controller.nextLevel();
		controller.syncStates();
	}

	public final void prevLevel() {
		controller.prevLevel();
		controller.syncStates();
	}

	public final int getState() {
		return controller.getState();
	}

	public final int getTimeLeft() {
		return controller.getTimeLeft();
	}
	
	@Override
	public final void setTime(int newtime) {
		controller.setTime(newtime);
		controller.syncStates();
	}

	public final boolean isRunning() {
		return controller.isRunning();
	}

	public final boolean isPaused() {
		return controller.isPaused();
	}

	public final boolean isStopped() {
		return controller.isStopped();
	}

	public final void stop() {
		controller.stop();
		controller.syncStates();
	}

	public final void start() {
		controller.start();
		controller.syncStates();
	}

	public final void pause() {
		controller.pause();
		controller.syncStates();
	}

	public final int togglePause() {
		int ret = controller.togglePause();
		controller.syncStates();
		return ret;
	}

	/* (non-Javadoc)
	 * @see dpclock.service.TournamentController#doExit()
	 */
	@Override
	public final void doExit() {
		controller.doExit();
		
	};

	public final void addPropertyChangeListener(PropertyChangeListener listener) {
		controller.addPropertyChangeListener(listener);
	}

	public final void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		controller.addPropertyChangeListener(propertyName, listener);
	}

	public final void removePropertyChangeListener(PropertyChangeListener listener) {
		controller.removePropertyChangeListener(listener);
	}

	public final void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		controller.removePropertyChangeListener(propertyName, listener);
	}

}
