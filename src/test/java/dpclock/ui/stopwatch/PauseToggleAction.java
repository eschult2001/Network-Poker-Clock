package dpclock.ui.stopwatch;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

public class PauseToggleAction extends StopWatchAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public PauseToggleAction(StopWatchBean swb) {
		super(swb,"Pause");
	}
	public PauseToggleAction(StopWatchBean swb, Icon icon) {
		super(swb,"Pause",icon);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		stopWatchBean.togglePause();
	}

}
