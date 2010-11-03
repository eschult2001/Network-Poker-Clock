package org.ericschult.demos;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Timer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;


/**
 * Simple Demo of 1.3 java.util.Timer and TimerTask classes.  When
 * the "Fade" button is pressed we pan an image into view from the
 * left and right with alternating scan lines coming from opposite
 * sides of the frame.
 */
public class FadeDemo 
{
    /**
     * This version of TimerTask just ensures that the the task is 
     * executed on the event dispatching thread.  Subclasses must
     * override doRun() instead of run().
     */
    private static abstract class SwingTimerTask extends java.util.TimerTask {
	public abstract void doRun();
	public void run() {
	    if (!EventQueue.isDispatchThread()) {
		EventQueue.invokeLater(this);
	    } else {
		doRun();
	    }
	}
    }


    /** 
     * Displays an image and supports the fade operation that we use to 
     * demo java.util.Timer/TimerTask.
     */
    private static class ImagePanel extends JPanel 
    {
	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	private final Timer timer = new Timer();
	private final Image image;
	private final int imageWidth;
	private final int imageHeight;
	private int[] scanLineOffsets;        
	private long startTime = -1;
	private long totalTime = -1;
	private long frameRate = 0;

	/** 
	 * Create an ImagePanel that will display the specified image.
	 */
	ImagePanel(String file) {
	    super(null);  // null layout manager
	    this.image = createImageIcon(file,"description").getImage();
	    imageWidth = image.getWidth(this);
	    imageHeight = image.getHeight(this);
	    scanLineOffsets = new int[imageWidth];
	    setBackground(Color.black);
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected ImageIcon createImageIcon(String path,
	                                           String description) {
	    java.net.URL imgURL = getClass().getResource(path);
	    if (imgURL != null) {
	        return new ImageIcon(imgURL, description);
	    } else {
	        System.err.println("Couldn't find file: " + path);
	        return null;
	    }
	}

	/**
	 * Return the image size.
	 */
	public Dimension getPreferredSize() {
	    Insets insets = getInsets();
	    int width = imageWidth + insets.left + insets.right;
	    int height = imageHeight + insets.top + insets.bottom;
	    return new Dimension(width, height);
	}

	/**
	 * Draw the image one scan line at a time.  Each scan-line is drawn
	 * at offset[y] - the pan method takes care of setting the offsets
	 * before calling repaint().
	 */
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);

	    /* If we're in the middle of a fade, compute what percentage 
	     * of the overall image width should be displayed now, update 
	     * the offsets appropriately.
	     */
	    if ((startTime > 0) && (totalTime > 0)) {
		long dt = System.currentTimeMillis() - startTime;
		int dw = (int)((double)imageWidth * ((double)dt / (double)totalTime));
		for(int r = 0; r < scanLineOffsets.length; r += 2) {
		    scanLineOffsets[r] = Math.min(0, dw - imageWidth);
		    if ((r + 1) < scanLineOffsets.length) {
			scanLineOffsets[r + 1] = Math.max(0, -scanLineOffsets[r]);
		    }
		}
	    }

	    /* Paint the image, one scan line at a time.
	     */
	    Insets insets = getInsets();
	    for(int r = 0; r < imageHeight; r++) {
		int dx1 = insets.left + scanLineOffsets[r];
		int dy1 = insets.top + r;
		int dx2 = dx1 + imageWidth;
		int dy2 = dy1 + 1;
		int sx1 = 0;
		int sy1 = r;
		int sx2 = sx1 + imageWidth;
		int sy2 = sy1 + 1;
		g.drawImage(image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, this);
	    }
	}


	/** 
	 * The frame rate is based on an estimate of the time it takes to paint the 
	 * frame. Our estimate is based on the average elapsed time required to 
	 * paint the frame to an offscreen image plus 10%.  The extra 10% is 
	 * to try and guard against making the frame rate so fast that 
	 * slight variations in the performance of paintComponent will make the 
	 * TimerTask fall behind.  
	 */
	public long computeFrameRate() {
	    Graphics g = createImage(imageWidth, imageHeight).getGraphics();
	    long dt = 0;
	    paintComponent(g);
	    for(int i = 0; i < 10; i++) {
		long startTime = System.currentTimeMillis();
		paintComponent(g);
		dt += System.currentTimeMillis() - startTime;
	    }
	    return (long)((float)(dt / 10) * 1.1f);
	}


	/**
	 * Fade the image in over <code>totalFadeTime</code> milliseconds.
	 * <p>
	 * Compute the frame rate, if it hasn't been computed already, and then 
	 * schedule a TimerTask that will call repaint() at that rate.
	 * Each time the TimerTask fires the paintComponent method figures out what 
	 * percentage of the total time has elapsed and then adjust the 
	 * scan line offsets to match.  When we're out of time, the TimerTask 
	 * is cancelled.
	 */
	public void startFade(long totalFadeTime) {

	    SwingTimerTask updatePanTask = new SwingTimerTask() {
		public void doRun() {
		    /* If we've used up the available time then cancel
		     * the timer.
		     */
		    if ((System.currentTimeMillis() - startTime) >= totalTime) {
			endFade();
			cancel();
		    }
		    repaint();
		}
	    };

	    totalTime = totalFadeTime;
	    startTime = System.currentTimeMillis();

	    if (frameRate == 0) {
		frameRate = computeFrameRate();
	    }

	    timer.schedule(updatePanTask, 0, frameRate);
	}


	/**
	 * Clear the internal state associated with a fade.
	 */
	public void endFade() {
	    totalTime = startTime = -1;
	    for(int r = 0; r < scanLineOffsets.length; r++) {
		scanLineOffsets[r] = 0;
	    }
	}
    }


    public static void main(String[] args) 
    {
	final ImagePanel imagePanel = new ImagePanel("images/duck.jpg");

	final JSlider totalTimeSlider = new JSlider(500, 5000, 2000);
	totalTimeSlider.setMajorTickSpacing(1000);
	totalTimeSlider.setMinorTickSpacing(100);
	totalTimeSlider.setPaintTicks(true);
	totalTimeSlider.setPaintLabels(true);

	Action panAction = new AbstractAction("Fade") {
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
		imagePanel.startFade((long)(totalTimeSlider.getValue()));
	    }
	};

	JToolBar toolbar = new JToolBar();
	toolbar.add(panAction);
	toolbar.addSeparator();
	toolbar.add(new JLabel("Total Fade Time (ms): "));
	toolbar.add(totalTimeSlider);


	/* Application boilerplate.
	 */

	JFrame f = new JFrame("Timer/TimerTask Demo");

	WindowListener l = new WindowAdapter() {
	    public void windowClosing(WindowEvent e) {
		System.exit(0);
	    }
	};
	f.addWindowListener(l); 

	Container contentPane = f.getContentPane();
	contentPane.add(imagePanel, BorderLayout.CENTER);
	contentPane.add(toolbar, BorderLayout.NORTH);
	f.pack();
	f.setVisible(true);
    }
}
