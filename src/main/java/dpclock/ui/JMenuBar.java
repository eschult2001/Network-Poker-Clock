/**
 * (c)2010 Eric Schult
 * All Rights Reserved
 * 
 */
package dpclock.ui;

import java.awt.Component;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;

public class JMenuBar extends javax.swing.JMenuBar implements InitializingBean {

	private static final long serialVersionUID = 2429245574742582155L;
	private List<Component> panelComponents;
	
	public void setPanelComponents(List<Component> panelComponents) {
		this.panelComponents = panelComponents;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		for(Component comp: panelComponents)
			this.add(comp);
	}

}
