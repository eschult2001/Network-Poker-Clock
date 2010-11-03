package dpclock.actions;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import dpclock.service.TournamentController;

public abstract class AbstractTournamentAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	protected TournamentController tournamentController;
	
	public AbstractTournamentAction() {
		super();
	}

	public AbstractTournamentAction(String name) {
		super(name);
	}

	public AbstractTournamentAction(String name, Icon icon) {
		super(name, icon);
	}

	public void setTournamentController(TournamentController tournamentControllerImpl) {
		this.tournamentController = tournamentControllerImpl;
	}

}