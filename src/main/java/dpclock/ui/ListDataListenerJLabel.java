package dpclock.ui;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.InitializingBean;

public class ListDataListenerJLabel extends JLabel implements ListDataListener,InitializingBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5169400143921922036L;

	private ComboBoxModel propertySource;
	private String  propertyName;
	
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public void setPropertySource(ComboBoxModel propertySource) {
		this.propertySource = propertySource;
	}

	@Override
	public void contentsChanged(ListDataEvent evt) {
		updateText();
	}
	
	private void updateText() {
		Object src = propertySource.getSelectedItem();
		if (src!=null) {
			try {
				setText(BeanUtils.getNestedProperty(src, propertyName));
			} catch (IllegalArgumentException e) {
				// no joy
				setText("");
			} catch (Exception e) {
				e.printStackTrace();
				setText("");
			}
		} else {
			setText("");
		}
	}
	
	@Override
	public void intervalAdded(ListDataEvent e) { }
	
	@Override
	public void intervalRemoved(ListDataEvent e) { }

	@Override
	public void afterPropertiesSet() {
		super.init();
		updateText();
		propertySource.addListDataListener(this);
	}
}
