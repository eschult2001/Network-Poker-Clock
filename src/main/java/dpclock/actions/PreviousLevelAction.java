/**
 * (c)2010 Eric Schult
 * All Rights Reserved
 * 
 */
package dpclock.actions;
import java.awt.event.ActionEvent;

import org.springframework.stereotype.Component;

@Component
public class PreviousLevelAction extends AbstractTournamentAction {

	private static final long serialVersionUID = 1L;

	public PreviousLevelAction() {
		super("Previous Level");
	}

	@Override
	public final void actionPerformed(ActionEvent e) {
		tournamentController.prevLevel();
	}
	
}
