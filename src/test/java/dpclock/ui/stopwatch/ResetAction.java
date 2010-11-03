package dpclock.ui.stopwatch;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

public class ResetAction extends StopWatchAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int resetTime;
	
	public ResetAction(StopWatchBean swb, int seconds) {
		super(swb, "Reset");
		this.resetTime = seconds;
	}
	public ResetAction(StopWatchBean swb, int seconds,
			Icon icon) {
		super(swb, "Reset",icon);
		this.resetTime = seconds;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		stopWatchBean.stop();
		//TODO stopWatchBean.setTimeLeft(resetTime);
	}

}
