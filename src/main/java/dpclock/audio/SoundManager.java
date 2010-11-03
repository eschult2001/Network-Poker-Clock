/**
 * (c)2010 Eric Schult
 * All Rights Reserved
 * 
 */
package dpclock.audio;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import dpclock.ui.stopwatch.StopWatchBean;

public class SoundManager implements PropertyChangeListener {

	private static final Log log = LogFactory.getLog(SoundManager.class);

	private Map<String, Object> events = new HashMap<String, Object>();

	private StopWatchBean stopWatchBean;

	public final void setEvents(Map<String, Object> events) {
		this.events = events;
	}

	public final void setStopWatchBean(StopWatchBean stopWatchBean) {
		this.stopWatchBean = stopWatchBean;
	}

	@PostConstruct
	public final void afterPropertiesSet() {
		stopWatchBean.addPropertyChangeListener(this);
	}

	/**
	 * Conditions for an alarm:
	 * - the clock's timeleft changes AND
	 * - the event's old and new values are not null AND
	 * - the event's old and new values are of type Integer AND
	 * - the event's new value is the (old value - 1)
	 * - the clock is still running.
	 */
	@Override
	public final void propertyChange(PropertyChangeEvent evt) {

			String key = evt.getPropertyName() + "." + evt.getOldValue() + "." + evt.getNewValue();
			if (!events.containsKey(key)) {
				key = evt.getPropertyName() + "." + evt.getNewValue();
			}
			if (events.containsKey(key))
			{
				Object o = events.get(key);

				if (!(o instanceof Clip)) {
					Clip clip;
					try {
						clip = AudioSystem.getClip();
						AudioInputStream inputStream = AudioSystem
								.getAudioInputStream(SoundManager.class
										.getResourceAsStream(o.toString()));
						clip.open(inputStream);
						log.debug("Loaded Clip: " + clip.getFormat());
						events.put(key, clip);
						o = clip;
					} catch (LineUnavailableException e) {
						log.warn("Line Unavailable",e);
					} catch (UnsupportedAudioFileException e) {
						log.warn("Unsupported file format",e);
					} catch (IOException e) {
						log.warn("Problem reading/writing file",e);
					}
				}
				
				((Clip) o).setFramePosition(0);
				((Clip) o).start();

				log.debug("Playing: " + o);
			}

	}

}
