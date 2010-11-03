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

import org.springframework.stereotype.Controller;

import dpclock.ui.stopwatch.StopWatchBean;

public class SoundManager implements PropertyChangeListener {

	private Map<String, Object> events = new HashMap<String, Object>();

	private StopWatchBean stopWatchBean;

	public void setEvents(Map<String, Object> events) {
		this.events = events;
	}

	public void setStopWatchBean(StopWatchBean stopWatchBean) {
		this.stopWatchBean = stopWatchBean;
	}

	@PostConstruct
	public void afterPropertiesSet() throws Exception {
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
	public void propertyChange(PropertyChangeEvent evt) {

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
						System.out.println("Loaded Clip: " + clip.getFormat());
						events.put(key, clip);
						o = clip;
					} catch (LineUnavailableException e) {
						e.printStackTrace();
					} catch (UnsupportedAudioFileException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				((Clip) o).setFramePosition(0);
				((Clip) o).start();

				System.out.println("Playing: " + o);
			}

	}

}
