/**
 * (c)2010 Eric Schult
 * All Rights Reserved
 * 
 */
package dpclock.awt;

import java.awt.Insets;

public class GridBagConstraints extends java.awt.GridBagConstraints {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GridBagConstraints() {
		super();
	}

	public GridBagConstraints(int gridx, int gridy, int gridwidth,
			int gridheight, double weightx, double weighty, int anchor,
			int fill, Insets insets, int ipadx, int ipady) {
		super(gridx, gridy, gridwidth, gridheight, weightx, weighty, anchor, fill,
				insets, ipadx, ipady);
	}

	public final int getGridx() { return gridx; }
	public final void setGridx(final int gridx) { this.gridx = gridx; }
	
	public final int getGridy() { return gridy; }
	public final void setGridy(final int gridy) { this.gridy = gridy; }
	
	public final int getGridwidth() { return gridwidth; }
	public final void setGridwidth(final int gridwidth) { this.gridwidth = gridwidth; }
	
	public final int getGridheight() { return gridheight; }
	public final void setGridheight(final int gridheight) { this.gridheight = gridheight; }
	
	public final double getWeightx() { return weightx; }
	public final void setWeightx(final double weightx) { this.weightx = weightx; }
	
	public final double getWeighty() { return weighty; }
	public final void setWeighty(final double weighty) { this.weighty = weighty; }

	public final int getAnchor() { return anchor; }
	public final void setAnchor(final int anchor) { this.anchor = anchor; }

	public final int getFill() { return fill; }
	public final void setFill(final int fill) { this.fill = fill; }

	public final Insets getInsets() { return insets; }
	public final void setInsets(final Insets insets) { this.insets = insets; }

	public final int getIpadx() { return ipadx; }
	public final void setIpadx(final int ipadx) { this.ipadx = ipadx; }
	
	public final int getIpady() { return ipady; }
	public final void setIpady(final int ipady) { this.ipady = ipady; }
}
