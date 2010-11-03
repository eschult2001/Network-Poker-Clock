package org.ericschult.demos;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
 
public class JButtonBorder extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6455954770470779453L;
	@SuppressWarnings("unused")
	private JButton b1, b2;
	private JPanel p;
 
   public JButtonBorder() {
      super("JButtonBorder");
      p = new JPanel();
      p.setLayout(new BorderLayout());
      b1 = new JButton("button with border");
      b2 = new JButton("button without border");
      b2.setFont(new Font("Default",Font.PLAIN,50));
	
      b2.setBorderPainted(false);
      b2.setMargin(new Insets(1,1,1,1));
      
      setContentPane(p);
      //p.add(b1,BorderLayout.CENTER);
      p.add(b2,BorderLayout.CENTER);
 	
      addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent event) {
            dispose();
            System.exit(0);
         }
      });
 
      pack();
      setVisible(true);
   }
 
   public static void main(String args[]) {
      @SuppressWarnings("unused")
	JButtonBorder j = new JButtonBorder();
   }
}
