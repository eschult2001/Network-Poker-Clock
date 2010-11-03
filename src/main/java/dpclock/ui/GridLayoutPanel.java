/**
 * (c)2010 Eric Schult
 * All Rights Reserved
 * 
 */
package dpclock.ui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;

public class GridLayoutPanel extends JPanel {

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

	
	
	public int getHgap() {
		return hgap;
	}



	public void setHgap(int hgap) {
		this.hgap = hgap;
	}



	public int getVgap() {
		return vgap;
	}



	public void setVgap(int vgap) {
		this.vgap = vgap;
	}



	public Component getNorth() {
		return north;
	}



	public void setNorth(Component north) {
		this.north = north;
	}



	public Component getSouth() {
		return south;
	}



	public void setSouth(Component south) {
		this.south = south;
	}



	public Component getEast() {
		return east;
	}



	public void setEast(Component east) {
		this.east = east;
	}



	public Component getWest() {
		return west;
	}



	public void setWest(Component west) {
		this.west = west;
	}



	public Component getCenter() {
		return center;
	}



	public void setCenter(Component center) {
		this.center = center;
	}



	public void init() {
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
