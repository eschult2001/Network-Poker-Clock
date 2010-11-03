/**
 * (c)2010 Eric Schult
 * All Rights Reserved
 * 
 */

package dpclock.actions;

import java.awt.event.ActionEvent;

import javax.swing.JComboBox;

import org.springframework.stereotype.Component;

import dpclock.schema.LevelBean;

@Component
public class LevelSelectedAction extends AbstractTournamentAction {

	private static final long serialVersionUID = 1L;

	public LevelSelectedAction() {
		super("Start");
	}

	@Override
	public final void actionPerformed(ActionEvent e) {
		JComboBox cb = (JComboBox)e.getSource();
		LevelBean level = (LevelBean) cb.getSelectedItem();

		tournamentController.setLevel(level);
	}	
}
