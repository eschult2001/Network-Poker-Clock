/**
 * (c)2010 Eric Schult
 * All Rights Reserved
 * 
 */
package dpclock.actions;
import java.awt.event.ActionEvent;

import org.springframework.stereotype.Component;


@Component
public class NextLevelAction extends AbstractTournamentAction {

	private static final long serialVersionUID = 1L;

	public NextLevelAction() {
		super("Next Level");
	}

	@Override
	public final void actionPerformed(ActionEvent e) {
		tournamentController.stop();
		tournamentController.nextLevel();
	}	
}
