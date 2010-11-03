package dpclock.ui.stopwatch;

import javax.swing.AbstractAction;
import javax.swing.Icon;

abstract class StopWatchAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	protected StopWatchBean stopWatchBean;
	
	
	public StopWatchAction(StopWatchBean stopWatchBean) {
		super();
		this.stopWatchBean=stopWatchBean;
	}


	public StopWatchAction(StopWatchBean stopWatchBean, String name, Icon icon) {
		super(name, icon);
		this.stopWatchBean=stopWatchBean;
	}


	public StopWatchAction(StopWatchBean stopWatchBean, String name) {
		super(name);
		this.stopWatchBean=stopWatchBean;
	}


}
