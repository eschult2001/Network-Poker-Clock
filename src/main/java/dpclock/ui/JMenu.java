package dpclock.ui;

import java.awt.Component;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;

public class JMenu extends javax.swing.JMenu implements InitializingBean {

	private static final long serialVersionUID = -3547314616946623366L;
	private List<Component> panelComponents;
	
	public void setPanelComponents(List<Component> panelComponents) {
		this.panelComponents = panelComponents;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		for(Component comp : panelComponents)
			add(comp);
	}

}
