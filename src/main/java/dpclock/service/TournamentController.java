package dpclock.service;

import dpclock.schema.LevelBean;
import dpclock.ui.stopwatch.StopWatchBean;

public interface TournamentController extends StopWatchBean {

	
	public LevelBean getLevel();
	public void setLevel(LevelBean level);
	public void setTime(int newtime);

	public void nextLevel();
	public void prevLevel();


}