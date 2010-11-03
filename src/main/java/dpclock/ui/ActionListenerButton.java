/**
 * (c)2010 Eric Schult
 * All Rights Reserved
 * 
 */
package dpclock.ui;

import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;

import org.springframework.beans.factory.InitializingBean;

public class ActionListenerButton extends JButton implements PropertyChangeListener,InitializingBean  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6471686011699626119L;
	private ActionListener actionListener;
	private List<ComponentListener> componentListeners;
	private JComponent propertySource;
	private String propertyName;
	public void setActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
	}

	public void setComponentListeners(List<ComponentListener> componentListeners) {
		this.componentListeners = componentListeners;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(propertyName)) {
			setText(evt.getNewValue().toString());
		}
	}

	@Override
	public void afterPropertiesSet() {
		this.addActionListener(actionListener);
		if (componentListeners != null) {
			for (ComponentListener listener : this.componentListeners) {
				this.addComponentListener(listener);
			}
		}
		if (propertySource!=null && propertyName != null) {
			propertySource.addPropertyChangeListener(propertyName,this);
		}
	}

	public JComponent getPropertySource() {
		return propertySource;
	}

	public void setPropertySource(JComponent propertySource) {
		this.propertySource = propertySource;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	
	
}
