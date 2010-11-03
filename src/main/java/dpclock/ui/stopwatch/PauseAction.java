package dpclock.ui.stopwatch;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class PauseAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5019400868511820899L;

	
	public PauseAction() {
		super("Pause");
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		((StopWatchBean)e.getSource()).togglePause();
	}

	
}
