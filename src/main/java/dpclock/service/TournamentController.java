/**
 * (c)2010 Eric Schult
 * All Rights Reserved
 * 
 */
package dpclock.service;

import dpclock.schema.LevelBean;
import dpclock.ui.stopwatch.StopWatchBean;

public interface TournamentController extends StopWatchBean {

	
	LevelBean getLevel();
	void setLevel(LevelBean level);
	void setTime(int newtime);

	void nextLevel();
	void prevLevel();

	void doExit();


}