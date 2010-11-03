package dpclock.actions;

import java.awt.event.ActionEvent;

import org.springframework.stereotype.Component;

@Component
public class StopAction extends AbstractTournamentAction {

	private static final long serialVersionUID = 1L;

	public StopAction() {
		super("Stop");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		tournamentController.stop();
	}	
}
