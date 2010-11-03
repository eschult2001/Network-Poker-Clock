package dpclock.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import org.springframework.stereotype.Component;

@Component
public class ExitAction extends AbstractAction implements Action {

	private static final long serialVersionUID = 3819944170793907572L;

	public ExitAction() {
		this.putValue(NAME,"Exit");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}
}
