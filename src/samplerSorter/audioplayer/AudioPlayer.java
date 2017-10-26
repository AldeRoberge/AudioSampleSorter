/*
 * Immense thanks to http://www.javazoom.net
 *----------------------------------------------------------------------
 */
package samplerSorter.audioplayer;

import java.util.Map;

import javax.sound.sampled.SourceDataLine;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;
import samplerSorter.UI.Sound;
import samplerSorter.logger.Logger;
import samplerSorter.properties.Properties;

public class AudioPlayer implements BasicPlayerListener {

	public static String TAG = "AudioPlayer";

	private BasicPlayer player;
	private BasicController control;

	double currentAudioVolume = Properties.MAIN_VOLUME_SLIDER_VALUE.getValueAsInt(); //stored between 0 and 100 (converted in setVolume())
	double currentAudioPan = Properties.MAIN_PAN_SLIDER_VALUE.getValueAsInt(); //stored between 0 and 100 (converted in setGain())

	public Sound currentSelectedSound;

	private boolean isStopped;
	private boolean isPaused;

	//

	private Map audioInfo = null;

	public AudioVisualizer audioVis;

	public AudioVisualizer getVisualizer() {
		if (audioVis == null) {
			audioVis = new AudioVisualizer();
		}
		return audioVis;
	}

	public void newVisualizerStatus(String newStatus) {
		audioVis.setStatus(newStatus);
	}

	public AudioPlayer() {
		Logger.logInfo(TAG, "Initialising AudioPlayer (this, BasicPlayer and BasicController)");

		// Instantiate BasicPlayer.
		player = new BasicPlayer();

		// BasicPlayer is a BasicController.
		control = (BasicController) player;

		// Register BasicPlayerTest to BasicPlayerListener events.
		// It means that this object will be notified on BasicPlayer
		// events such as : opened(...), progress(...), stateUpdated(...)
		player.addBasicPlayerListener(this);

	}

	public void playNewSound(Sound sound) {
		this.currentSelectedSound = sound;

		try {
			// Open file, or URL or Stream (shoutcast) to play.
			control.open(currentSelectedSound.file);
			// control.open(new URL("http://yourshoutcastserver.com:8000"));

			// Start playback in a thread.
			control.play();

			// Set Volume (0 to 1.0).
			// setGain should be called after control.play().

			setVolume(currentAudioVolume);
			setPan(currentAudioPan);

			// If you want to pause/resume/pause the played file then
			// write a Swing player and just call control.pause(),
			// control.resume() or control.stop().			
			// Use control.seek(bytesToSkip) to seek file
			// (i.e. fast forward and rewind). seek feature will
			// work only if underlying JavaSound SPI implements
			// skip(...). True for MP3SPI (JavaZOOM) and SUN SPI's
			// (WAVE, AU, AIFF).

		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open callback, stream is ready to play.
	 *
	 * properties map includes audio format dependant features such as
	 * bitrate, duration, frequency, channels, number of frames, vbr flag,
	 * id3v2/id3v1 (for MP3 only), comments (for Ogg Vorbis), ... 
	 *
	 * @param stream could be File, URL or InputStream
	 * @param properties audio stream properties.
	 */
	public void opened(Object stream, Map properties) {
		audioInfo = properties;
	}

	/**
	 * A basic copy of javazoom.jlgui.player.amp; PlayerUI -> processProgress
	 */
	public void progress(int bytesread, long microseconds, byte[] pcmdata, Map properties) {
		if (audioInfo.containsKey("basicplayer.sourcedataline")) {
			// Spectrum/time analyzer
			getVisualizer().analyzer.writeDSP(pcmdata);
		}

	}

	/**
	 * Notification callback for basicplayer events such as opened, eom ...
	 *  
	 *  A copy of javazoom.jlgui.player.amp.processStateUpdated(BasicPlayerEvent event)
	 *  
	 * @param event
	 */
	public void stateUpdated(BasicPlayerEvent event) {
		// Notification of BasicPlayer states (opened, playing, end of media, ...)
		System.out.println("stateUpdated : " + event.toString());

		int state = event.getCode();

		if (event.getCode() == BasicPlayerEvent.STOPPED) {
			newVisualizerStatus("Stopped");

			isStopped = true;

			//stop analyzer
			getVisualizer().analyzer.stopDSP();
			getVisualizer().analyzer.repaint();

		} else if (event.getCode() == BasicPlayerEvent.PAUSED) {
			newVisualizerStatus("Paused");

			isPaused = true;

		} else if (event.getCode() == BasicPlayerEvent.PLAYING) {
			newVisualizerStatus("Playing");

			isPaused = false;
			isStopped = false;

			//anaylyzer
			if (audioInfo.containsKey("basicplayer.sourcedataline")) {

				getVisualizer().analyzer.setupDSP((SourceDataLine) audioInfo.get("basicplayer.sourcedataline"));
				getVisualizer().analyzer.startDSP((SourceDataLine) audioInfo.get("basicplayer.sourcedataline"));

			}

		} else if (event.getCode() == BasicPlayerEvent.RESUMED) {
			newVisualizerStatus("Resumed");

			isPaused = false;
			isStopped = false;
		} else if (state == BasicPlayerEvent.OPENING) {
			newVisualizerStatus("Buffering");

			//buffering
		} else if (state == BasicPlayerEvent.EOM) { /*-- End Of Media reached --*/
			newVisualizerStatus("End of media");

			//end of media reached
		}

		//see
		//javazoom.jlgui.player.amp.PlayerUI
		//processStateUpdated(BasicPlayerEvent event)

	}

	@Override
	public void setController(BasicController arg0) {
	}

	//Between 0 and 100, converted to 0 and 1
	public void setVolume(double i) {
		try {
			control.setGain(i / 100);
			currentAudioVolume = i;
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
	}

	// Between -50 and 100, converted to -1 and 1
	public void setPan(double i) {
		// setPan should be called after control.play().
		try {
			double pan = (double) ((i - 50) / 100) * 2;

			control.setPan(pan);
			currentAudioPan = i;
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
	}

	public void pause() {

		try {
			control.pause();
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}

	}

	public void resume() {

		try {
			control.resume();
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}

	}

	public void playNewSoundOrResume(Sound sound) {

		if (currentSelectedSound == null) {
			playNewSound(sound);
		} else {

			if (currentSelectedSound.equals(sound)) {

				if (isStopped) { //stopped (play)

					try {
						control.play();
					} catch (BasicPlayerException e) {
						Logger.logError(TAG, "Could not play stopped sound " + sound);
						e.printStackTrace();
					}

				} else if (isPaused) { //paused (resume)

					try {
						control.resume();
					} catch (BasicPlayerException e) {
						Logger.logError(TAG, "Could not resume paused sound " + sound);
						e.printStackTrace();
					}

				} else { //playing (pause)

					try {
						control.pause();
					} catch (BasicPlayerException e) {
						Logger.logError(TAG, "Could not pause playing sound " + sound);
						e.printStackTrace();
					}

				}
			} else {
				playNewSound(sound);
			}
		}

	}

}
