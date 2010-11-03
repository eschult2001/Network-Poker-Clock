/**
 * (c)2010 Eric Schult 
 * All Rights Reserved
 * 
 */

package dpclock.actions;

import java.awt.Frame;
import java.awt.event.ActionEvent;

import org.springframework.stereotype.Component;

@Component
public class ExitAction extends AbstractTournamentAction {

	private static final long serialVersionUID = 3819944170793907572L;

	public ExitAction() {
		this.putValue(NAME,"Exit");
	}
	
	@Override
	public final void actionPerformed(ActionEvent e) {
		tournamentController.doExit();
		for(Frame frame: Frame.getFrames()) {
			frame.dispose();
		}
	}
}
