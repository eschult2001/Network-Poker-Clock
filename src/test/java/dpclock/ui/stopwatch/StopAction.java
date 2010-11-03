package dpclock.ui.stopwatch;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

public class StopAction extends StopWatchAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public StopAction(StopWatchBean swb) {
		super(swb, "Stop");
	}
	public StopAction(StopWatchBean swb, Icon icon) {
		super(swb, "Stop", icon);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		stopWatchBean.stop();
	}

}
