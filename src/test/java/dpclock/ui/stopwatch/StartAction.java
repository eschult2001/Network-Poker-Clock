package dpclock.ui.stopwatch;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

public class StartAction extends StopWatchAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public StartAction(StopWatchBean swb) {
		super(swb, "Start");
	}
	public StartAction(StopWatchBean swb, Icon icon) {
		super(swb, "Start",icon);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		stopWatchBean.start();
	}

}
