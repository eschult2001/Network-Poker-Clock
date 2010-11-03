package dpclock.awt;

import java.awt.Insets;

public class GridBagConstraints extends java.awt.GridBagConstraints {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GridBagConstraints() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GridBagConstraints(int gridx, int gridy, int gridwidth,
			int gridheight, double weightx, double weighty, int anchor,
			int fill, Insets insets, int ipadx, int ipady) {
		super(gridx, gridy, gridwidth, gridheight, weightx, weighty, anchor, fill,
				insets, ipadx, ipady);
		// TODO Auto-generated constructor stub
	}

	public int getGridx() { return gridx; }
	public void setGridx(int gridx) { this.gridx = gridx; }
	
	public int getGridy() { return gridy; }
	public void setGridy(int gridy) { this.gridy = gridy; }
	
	public int getGridwidth() { return gridwidth; }
	public void setGridwidth(int gridwidth) { this.gridwidth = gridwidth; }
	
	public int getGridheight() { return gridheight; }
	public void setGridheight(int gridheight) { this.gridheight = gridheight; }
	
	public double getWeightx() { return weightx; }
	public void setWeightx(double weightx) { this.weightx = weightx; }
	
	public double getWeighty() { return weighty; }
	public void setWeighty(double weighty) { this.weighty = weighty; }

	public int getAnchor() { return anchor; }
	public void setAnchor(int anchor) { this.anchor = anchor; }

	public int getFill() { return fill; }
	public void setFill(int fill) { this.fill = fill; }

	public Insets getInsets() { return insets; }
	public void setInsets(Insets insets) { this.insets = insets; }

	public int getIpadx() { return ipadx; }
	public void setIpadx(int ipadx) { this.ipadx = ipadx; }
	
	public int getIpady() { return ipady; }
	public void setIpady(int ipady) { this.ipady = ipady; }
}
