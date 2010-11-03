package dpclock.actions;

import java.awt.event.ActionEvent;

import org.springframework.stereotype.Component;

@Component
public class StartAction extends AbstractTournamentAction {

	private static final long serialVersionUID = 1L;

	public StartAction() {
		super("Start");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		tournamentController.start();
	}	
}
