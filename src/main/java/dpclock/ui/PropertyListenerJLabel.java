package dpclock.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.MethodUtils;
import org.springframework.beans.factory.InitializingBean;

public class PropertyListenerJLabel extends JLabel implements PropertyChangeListener,InitializingBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5169400143921922036L;

	private Object propertySource;
	private String propertyName;
	private String beanPath;
	
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public void setPropertySource(Object propertySource) {
		this.propertySource = propertySource;
	}
	
	public String getBeanPath() {
		return beanPath;
	}
	public void setBeanPath(String beanPath) {
		this.beanPath = beanPath;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(propertyName)) {
			setText(evt.getNewValue().toString());
		}
	}
	
	@Override
	public void afterPropertiesSet() {
		super.init();
		
		try {
			MethodUtils.invokeExactMethod(
					propertySource, 
					"addPropertyChangeListener", 
					new Object[]{this}, 
					new Class[]{PropertyChangeListener.class});
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		try {
			setText(BeanUtils.getNestedProperty(propertySource, propertyName));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
