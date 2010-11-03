/**
 * (c)2010 Eric Schult
 * All Rights Reserved
 * 
 */
package dpclock.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;

public class TextValuePropertyChangeListener implements PropertyChangeListener {

	private JButton dest;
	private String propertyName;
	
	@Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        String property = propertyChangeEvent.getPropertyName();
        if (propertyName.equals(property)) {
        	dest.setText(propertyChangeEvent.getNewValue().toString());
        }
      }

}
