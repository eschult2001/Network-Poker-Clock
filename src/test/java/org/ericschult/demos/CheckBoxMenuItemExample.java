package org.ericschult.demos;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;

public class CheckBoxMenuItemExample extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JTextPane pane;
    public JMenuBar menuBar;
    public JToolBar toolBar;

    public CheckBoxMenuItemExample(  ) {
        
    	menuBar = new JMenuBar(  );
        JMenu justifyMenu = new JMenu("Justify");

        ActionListener actionPrinter = new ActionListener(  ) {
            public void actionPerformed(ActionEvent e) {
                try {
                	pane.getStyledDocument(  ).insertString(0 ,
                      "Action ["+e.getActionCommand(  )+"] performed!\n", null);
                } catch (Exception ex) { ex.printStackTrace(  ); }
            }
        };

        
        JCheckBoxMenuItem leftJustify = new
               JCheckBoxMenuItem("Left", new ImageIcon("left.gif"));
        leftJustify.setHorizontalTextPosition(JMenuItem.RIGHT);
        leftJustify.setAccelerator(KeyStroke.getKeyStroke('L',
                        Toolkit.getDefaultToolkit(  ).getMenuShortcutKeyMask(  )));
        leftJustify.addActionListener(actionPrinter);
        
        JCheckBoxMenuItem rightJustify = new
               JCheckBoxMenuItem("Right", new ImageIcon("right.gif"));
        rightJustify.setHorizontalTextPosition(JMenuItem.RIGHT);
        rightJustify.setAccelerator(KeyStroke.getKeyStroke('R',
                        Toolkit.getDefaultToolkit(  ).getMenuShortcutKeyMask(  )));
        rightJustify.addActionListener(actionPrinter);

        JCheckBoxMenuItem centerJustify = new
               JCheckBoxMenuItem("Center", new ImageIcon("center.gif"));
        centerJustify.setHorizontalTextPosition(JMenuItem.RIGHT);
        centerJustify.setAccelerator(KeyStroke.getKeyStroke('M',
                        Toolkit.getDefaultToolkit(  ).getMenuShortcutKeyMask(  )));
        centerJustify.addActionListener(actionPrinter);

        JCheckBoxMenuItem fullJustify = new
               JCheckBoxMenuItem("Full", new ImageIcon("full.gif"));
        fullJustify.setHorizontalTextPosition(JMenuItem.RIGHT);
        fullJustify.setAccelerator(KeyStroke.getKeyStroke('F',
                        Toolkit.getDefaultToolkit(  ).getMenuShortcutKeyMask(  )));
        fullJustify.addActionListener(actionPrinter);

        justifyMenu.add(leftJustify);
        justifyMenu.add(rightJustify);
        justifyMenu.add(centerJustify);
        justifyMenu.add(fullJustify);
        
        ItemListener itemPrinter = new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                try {
                	@SuppressWarnings("unused")
					JCheckBoxMenuItem source = (JCheckBoxMenuItem) e.getSource();
                	pane.getStyledDocument(  ).insertString(0 ,
                      "Item ["+e.paramString()+"] changed! State=" + (((JCheckBoxMenuItem)e.getSource()).isSelected()?"on":"off")+"\n", null);
                } catch (Exception ex) { ex.printStackTrace(  ); }
			}
		};
		leftJustify.addItemListener(itemPrinter);
		rightJustify.addItemListener(itemPrinter);
		centerJustify.addItemListener(itemPrinter);
		fullJustify.addItemListener(itemPrinter);


        menuBar.add(justifyMenu);
        menuBar.setBorder(new BevelBorder(BevelBorder.RAISED));

    }
    public static void main(String s[ ]) {
        CheckBoxMenuItemExample example = new CheckBoxMenuItemExample(  );
        example.pane = new JTextPane(  );
        example.pane.setPreferredSize(new Dimension(250, 250));
        example.pane.setBorder(new BevelBorder(BevelBorder.LOWERED));

        JFrame frame = new JFrame("Menu Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(example.menuBar);
        frame.getContentPane(  ).add(example.pane, BorderLayout.CENTER);
        frame.pack(  );
        frame.setVisible(true);
    }
}