package dpclock.actions;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import org.springframework.stereotype.Component;

@Component
public class SetTimeAction extends AbstractTournamentAction {

	private static final long serialVersionUID = 1L;
	private java.awt.Component parentComponent;

	public SetTimeAction() {
		super("Set Time Left");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		tournamentController.stop();
		String newTime = JOptionPane.showInputDialog(parentComponent,"Specify time to set on clock (MM:SS)");
		tournamentController.setTime(parseTime(newTime));
	}
	
	public void setParentComponent(java.awt.Component parentComponent) {
		this.parentComponent = parentComponent;
		
	}
	
	public static final int HOURS = 1;
	public static final int MINUTES = 2; 
	public static final int SECONDS = 4;
	public static final int parseTime( String time) {
		return parseTime(time,MINUTES|SECONDS);
	}
	public static final int parseTime( String time, int fields) {
		String args[] = time.split(":");
		int last = args.length;
		int result = 0;
		if ((fields & SECONDS) > 0 && last > 0) {
			last--;
			result =+ Integer.parseInt(args[last]);
		}
		if ((fields & MINUTES) > 0 && last > 0) {
			last--;
			result += 60 * Integer.parseInt(args[last]);
		}
		if ((fields & MINUTES) > 0 && last > 0) {
			last--;
			result += 60 * 60 * Integer.parseInt(args[last]);
		}
		return result;
	};
}
