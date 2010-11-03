/**
 * (c)2010 Eric Schult
 * All Rights Reserved
 * 
 */
package dpclock.ui.stopwatch;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class StopWatchJButton extends JButton implements PropertyChangeListener,ActionListener {

	private static final long serialVersionUID = -1319007015677173723L;

	private StopWatchBean stopWatchBean;
	private Color runningColor = new Color(0,200,0);
	private Color stoppedColor = new Color(200,0,0);
	private Color savedForeground = null;
	
	private Timer pauseTimer = new Timer(1000,this);
	private long nextFlash = 0;
	
	public StopWatchJButton() {
		setHorizontalAlignment(SwingConstants.CENTER);
		setVerticalTextPosition(SwingConstants.CENTER);
		setForeground(Color.WHITE);
		setBackground(stoppedColor);
		setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		setBorderPainted(false);
		setMargin(new Insets(0,0,0,0));
		setHideActionText(true);
		setOpaque(true);
		setToolTipText("Click to pause");
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src==pauseTimer) {
			if (e.getWhen()>nextFlash) {
				if (savedForeground!=null) {
					setForeground(savedForeground);
					savedForeground=null;
				} else {
					savedForeground=getForeground();
					setForeground(getBackground());
				}
			}
		}
	}

	public void propertyChange(PropertyChangeEvent evt) {
//		if (evt.getSource()==stopWatchBean) {
			if (savedForeground!=null) {
				setForeground(savedForeground);
				savedForeground=null;
			}
			if (StopWatchBean.PROP_TIMELEFT.equals(evt.getPropertyName())) {
				updateText((Integer)evt.getNewValue());
			} else if (StopWatchBean.PROP_STATE.equals(evt.getPropertyName())) {
				int state = (Integer)evt.getNewValue();
				updateBackground(state);
				if (StopWatchBean.STATE_PAUSED == state) {
					pauseTimer.start();
				} else {
					pauseTimer.stop();
				}
				//updateText(stopWatchBean.getTimeLeft());
			}
		//}
	}
	
	private void updateBackground(int status) {
		if (status==StopWatchBean.STATE_RUNNING) {
			setBackground(runningColor);
		} else {
			setBackground(stoppedColor);
		}		
	}
	
	private void updateText(int remaining) {
		int min = remaining / 60;
		int sec = remaining % 60;
		setText(String.format("%02d:%02d", min, sec));
	}
	
	public StopWatchBean getStopWatchBean() {
		return stopWatchBean;
	}
	
	public void setStopWatchBean(StopWatchBean stopWatchBean) {
		if (this.stopWatchBean != null) {
			this.stopWatchBean.removePropertyChangeListener(this);
		}
		this.stopWatchBean = stopWatchBean;
		this.stopWatchBean.addPropertyChangeListener(this);
		updateText(this.stopWatchBean.getTimeLeft());
		updateBackground(this.stopWatchBean.getState());
	}

	public Color getRunningColor() {
		return runningColor;
	}

	public void setRunningColor(Color runningColor) {
		if (this.runningColor==null && runningColor==null) 
			return;
		firePropertyChange("runningColor", this.runningColor, this.runningColor=runningColor);
		if (stopWatchBean != null) 
			updateBackground(stopWatchBean.getState());
	}

	public Color getStoppedColor() {
		return stoppedColor;
	}

	public void setStoppedColor(Color stoppedColor) {
		if (this.stoppedColor==null && stoppedColor==null) 
			return;
		firePropertyChange("stoppedColor", this.stoppedColor, this.stoppedColor=stoppedColor);
		if (stopWatchBean != null) 
			updateBackground(stopWatchBean.getState());
	}	
	
	public void setComponentListener(ComponentListener l) {
		this.addComponentListener(l);
	}
	
}
