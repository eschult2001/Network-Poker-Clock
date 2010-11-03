/**
 * (c)2010 Eric Schult
 * All Rights Reserved
 * 
 */
package dpclock.ui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JPanel;

import org.springframework.beans.factory.InitializingBean;

public class GridBagLayoutPanel extends JPanel implements InitializingBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2178227602161534115L;
	private int columns = 0;
	private int rows = 0;
	private List<Object> children;

	public static final class GridBagComponent{
		public GridBagComponent() {}
		public GridBagComponent(Component component, GridBagConstraints constraint) {
			this.component = component;
			this.constraint = constraint;
		}
		public Component component;
		public GridBagConstraints constraint = new GridBagConstraints();
	}

	public GridBagLayoutPanel() {
		super(new GridBagLayout());
	}

	public int getColumns() {
		return columns;
	}
	public void setColumns(int hgap) {
		this.columns = hgap;
	}

	public int getRows() {
		return rows;
	}
	public void setRows(int vgap) {
		this.rows = vgap;
	}
	
	public List<Object> getChildren() {
		return children;
	}
	public void setChildren(List<Object> children) {
		this.children = children;
	}
	
	public void afterPropertiesSet() {
		for(Object child: children) {
			if (child instanceof GridBagComponent) {
				GridBagComponent gbc = (GridBagComponent)child;
				add(gbc.component,gbc.constraint);
			} else if (child instanceof Component) {
				add((Component)child);
			}
		}
	}
}
