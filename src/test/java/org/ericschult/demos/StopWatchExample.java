package org.ericschult.demos;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import dpclock.ui.stopwatch.PauseToggleAction;
import dpclock.ui.stopwatch.ResetAction;
import dpclock.ui.stopwatch.StartAction;
import dpclock.ui.stopwatch.StopAction;
import dpclock.ui.stopwatch.StopWatchBeanImpl;
import dpclock.ui.stopwatch.StopWatchJButton;

public class StopWatchExample {

	private static final int TIME = 30;

	public static void main(String args[]) {

		final StopWatchBeanImpl stopWatch = new StopWatchBeanImpl();
		stopWatch.setTimeLeft(TIME);
		stopWatch.start();
		
		Action startAction = new StartAction(stopWatch, new ImageIcon("/dpclock/res/icons/control_play_blue.png"));
		Action stopAction = new StopAction(stopWatch, createImageIcon("res/icons/control_stop_blue.png",null));
		Action pauseAction = new PauseToggleAction(stopWatch, createImageIcon("res/icons/control_pause_blue.png",null));
		Action resetAction = new ResetAction(stopWatch, TIME, createImageIcon("res/icons/arrow_rotate_anticlockwise.png",null));
		Action exitAction = new AbstractAction("Exit") {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		};

		JFrame frame = new JFrame("Stopwatch");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final StopWatchJButton clock = new StopWatchJButton();
		clock.setStopWatchBean(stopWatch);
		clock.setFont(new Font("Default",Font.BOLD,32));
		frame.add(clock,BorderLayout.CENTER);


		JPanel buttonPanel = new JPanel();
		buttonPanel.add(new JButton(resetAction));
		buttonPanel.add(new JButton(startAction));
		buttonPanel.add(new JButton(stopAction));
		buttonPanel.add(new JButton(pauseAction));
		frame.add(buttonPanel,BorderLayout.SOUTH);
		
		JMenuBar menubar = new JMenuBar();
		JMenu fileMenu = new JMenu("File"); menubar.add(fileMenu);
		fileMenu.add(new JMenuItem(exitAction));
		
		JMenu clockMenu = new JMenu("Edit"); menubar.add(clockMenu);
		clockMenu.add(new JMenuItem(startAction));
		clockMenu.add(new JMenuItem(stopAction));
		clockMenu.add(new JMenuItem(pauseAction));
		clockMenu.add(new JMenuItem(resetAction));
		frame.setJMenuBar(menubar);

		frame.setSize(300, 100);
		frame.setVisible(true);
		frame.setSize(new Dimension(500, 300));
	}
	
	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path,
	                                           String description) {
	    URL imgURL = URL.class.getResource(path);
	    if (imgURL != null) {
	        return new ImageIcon(imgURL, description);
	    } else {
	        System.err.println("Couldn't find file: " + path);
	        return null;
	    }
	}

}
