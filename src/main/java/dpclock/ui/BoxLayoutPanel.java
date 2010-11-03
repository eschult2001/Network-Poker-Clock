/**
 * (c)2010 Eric Schult
 * All Rights Reserved
 * 
 */
package dpclock.ui;

import java.awt.Component;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.springframework.beans.factory.InitializingBean;

public class BoxLayoutPanel extends JPanel implements InitializingBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5841207079940762015L;
	
	/**
	 * We can't use "components" as the property name, because it conflicts with
	 * an existing property on the Component superclass.
	 */
	private List<Component> panelComponents;
	private int axis = BoxLayout.Y_AXIS;

	public void setPanelComponents(List<Component> panelComponents) {
		this.panelComponents = panelComponents;
	}
	public void setAxis(int axis) {
		this.axis = axis;
	}
	@Override
	public void afterPropertiesSet() {
		this.setLayout(new BoxLayout(this, axis));
		
		for (Component component : panelComponents) {
			add(component);
		}
	}
}