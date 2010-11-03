/**
 * (c)2010 Eric Schult
 * All Rights Reserved
 * 
 */
package dpclock.ui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.springframework.beans.factory.InitializingBean;

public class MainFrame extends JFrame implements InitializingBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2935275007686424199L;
	
	public MainFrame() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(600, 400));
		setState(JFrame.NORMAL);
	}
	
	@Override
	public void afterPropertiesSet() {
		setVisible(true);
	}
}
