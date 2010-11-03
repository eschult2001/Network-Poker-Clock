package dpclock.ui;

import java.awt.event.ComponentListener;
import java.beans.PropertyChangeListener;

public class JLabel extends javax.swing.JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5169400143921922036L;

	private PropertyChangeListener propertyChangeListener;

	protected ComponentListener componentListener;

	public void setPropertyChangeListener(
			PropertyChangeListener propertyChangeListener) {
		this.propertyChangeListener = propertyChangeListener;
	}
	public void setComponentListener(ComponentListener componentListener) {
		this.componentListener = componentListener;
	}
	
	public void init() {
		if (propertyChangeListener!=null) {
			addPropertyChangeListener(propertyChangeListener);
		}
		if (componentListener != null) {
			addComponentListener(componentListener);
		}
	}
}
