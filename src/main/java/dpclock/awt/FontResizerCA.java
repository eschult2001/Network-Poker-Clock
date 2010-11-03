/**
 * (c)2010 Eric Schult
 * All Rights Reserved
 * 
 */
package dpclock.awt;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

import org.springframework.stereotype.Component;

/**
 * @author a488585
 * 
 */
@Component
public class FontResizerCA extends ComponentAdapter implements PropertyChangeListener {


	private String clockWidthMaxString;
	private double fontFitPercent = 0.99;
	private int maxFontSize = 500;
	private int minFontSize = 8;
	private int pad = 2;
	
	public FontResizerCA(String maxString) {
		this.clockWidthMaxString = maxString;
	}

	
	@Override
	public final void componentResized(ComponentEvent evt) {
		doResize((JComponent)evt.getComponent());
	}
	
	@Override
	public final void componentShown(ComponentEvent evt) {
		doResize((JComponent)evt.getComponent());
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("text")) {
			Object source = evt.getSource();
			if (source instanceof JComponent) {
				doResize((JComponent)source);
			}
		}
	}
	
	public final void doResize(JComponent c) {
		
		int width = c.getWidth() - pad;
		int height = c.getHeight() - pad;

		if (width<1 || height<1) {
			return;
		}
		
		Insets i = c.getInsets();
		width = width - i.left - i.right;
		height = height - i.top - i.bottom;

		String text = clockWidthMaxString;
		if (c instanceof AbstractButton) {
			Insets margins = ((AbstractButton)c).getMargin();
			height = height - margins.top - margins.bottom;
			width = width - margins.left - margins.right;
			text = ((AbstractButton)c).getText();
		}
		if (c instanceof JLabel) {
			text = ((JLabel)c).getText();
		}
		doResize(c,width,height,text);
	}
	
	public void doResize(JComponent c, int windowWidthMax, int windowHeightMax, String text) {

		if (!c.isShowing()) {
			return;
		}
		
		Font currentFont = c.getFont();
		int windowHeightLow = (int) (windowHeightMax * fontFitPercent);
		int windowWidthLow = (int) (windowWidthMax * fontFitPercent);

		float newFontSize = ((maxFontSize - minFontSize) / 2 ) + minFontSize;
		float delta = newFontSize / 2;

		Font newFont = currentFont;  //for now
		while (delta > 0.1) {

			newFont = currentFont.deriveFont((float) newFontSize);
			FontMetrics fm = c.getGraphics().getFontMetrics(newFont);

			int stringWidth = Math.max(fm.stringWidth(clockWidthMaxString),fm.stringWidth(text));
			int stringHeight = fm.getHeight();

			// Check if both width and height fit, AND one or the other is close...
			if (stringWidth < windowWidthMax && stringHeight < windowHeightMax
					&& (stringWidth >= windowWidthLow || stringHeight >= windowHeightLow)) {
				// close enough
				break;
			}

			if (stringWidth <= windowWidthMax && stringHeight < windowHeightMax) {
				// too small
				newFontSize = Math.min(newFontSize + delta,maxFontSize);
			} else {
				// too large
				newFontSize = Math.max(newFontSize - delta,minFontSize);
			}
			delta /= 2;
		}
		c.setFont(newFont);
		//System.out.println("New Font: "+newFont);
	}

	public final String getClockWidthMaxString() {
		return clockWidthMaxString;
	}

	public final void setClockWidthMaxString(String clockWidthMaxString) {
		this.clockWidthMaxString = clockWidthMaxString;
	}

	public final double getFontFitPercent() {
		return fontFitPercent;
	}

	public final void setFontFitPercent(double fontFitPercent) {
		this.fontFitPercent = fontFitPercent;
	}
	
	public final int getMaxFontSize() {
		return maxFontSize;
	}
	
	public final void setMaxFontSize(int maxFontSize) {
		this.maxFontSize = maxFontSize;
	}
	
	public final int getMinFontSize() {
		return minFontSize;
	}
	
	public final void setMinFontSize(int minFontSize) {
		this.minFontSize = minFontSize;
	}

	public final int getPad() {
		return pad;
	}
	public final void setPad(int pad) {
		this.pad = pad;
	}
}

