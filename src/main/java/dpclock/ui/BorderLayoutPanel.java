package dpclock.ui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;

import org.springframework.beans.factory.InitializingBean;

public class BorderLayoutPanel extends JPanel implements InitializingBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2178227602161534115L;

	private Component north;
	private Component south;
	private Component east;
	private Component west;
	private Component center;

	private int hgap = 0;
	private int vgap = 0;

	public void setHgap(int hgap) {
		this.hgap = hgap;
	}

	public void setVgap(int vgap) {
		this.vgap = vgap;
	}

	public void setNorth(Component north) {
		this.north = north;
	}

	public void setSouth(Component south) {
		this.south = south;
	}

	public void setEast(Component east) {
		this.east = east;
	}

	public void setWest(Component west) {
		this.west = west;
	}

	public void setCenter(Component center) {
		this.center = center;
	}

	@Override
	public void afterPropertiesSet() {
		setLayout(new BorderLayout(hgap, vgap));

		if (north!=null) {
			add(north, BorderLayout.NORTH);
		}
		if (south!=null) {
			add(south, BorderLayout.SOUTH);
		}
		if (east!=null) {
			add(east, BorderLayout.EAST);
		}
		if (west!=null) {
			add(west, BorderLayout.WEST);
		}
		if (center!=null) {
			add(center, BorderLayout.CENTER);
		}
	}
}
