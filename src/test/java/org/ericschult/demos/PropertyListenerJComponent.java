package org.ericschult.demos;

import javax.swing.JButton;
import javax.swing.JFrame;

public class PropertyListenerJComponent {

	public static void main(String[] a) {

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton bn = new JButton();

		bn.addPropertyChangeListener(new ActionChangedListener(bn));

		frame.add(bn);
		bn.setEnabled(false);

		frame.setSize(300, 300);
		frame.setVisible(true);

		frame.add(bn);
	}

}