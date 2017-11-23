/*
 * Immense thanks to http://www.javazoom.net
 *----------------------------------------------------------------------
 */
package ass.file.player;

import java.io.File;
import java.util.Map;

import javax.sound.sampled.SourceDataLine;

import constants.property.Properties;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;
import logger.Logger;

public class AudioPlayer implements BasicPlayerListener {

	private static String TAG = "AudioPlayer";

	private BasicController control;

	/**
	 * Volume and pan are stored between 0 and 100 in properties and converted from 0 to 1 in setVolume() and setGain()
	 */
	private double currentAudioVolume = Properties.MAIN_VOLUME_SLIDER_VALUE.getValueAsInt();
	private double currentAudioPan = Properties.MAIN_PAN_SLIDER_VALUE.getValueAsInt();

	private File currentSelectedSound;

	//

	private Map audioInfo = null;

	private AudioVisualizer audioVis;

	private BasicPlayer player;

	//

	public AudioVisualizer getVisualizer() {
		if (audioVis == null) {
			audioVis = new AudioVisualizer();
		}
		return audioVis;
	}

	void newVisualizerStatus(String newStatus) {
		boolean debug = true;
		if (debug) {
			Logger.logInfo(TAG, newStatus);
		}

		audioVis.setStatus(newStatus);
	}

	public AudioPlayer() {
		Logger.logInfo(TAG, "Initialising...");

		// Instantiate BasicPlayer.
		player = new BasicPlayer();

		// BasicPlayer is a BasicController.
		control = player;

		// Register BasicPlayerTest to BasicPlayerListener events.
		// It means that this object will be notified on BasicPlayer
		// events such as : opened(...), progress(...), stateUpdated(...)
		player.addBasicPlayerListener(this);

	}

	/**
	 * Open callback, stream is ready to play.
	 *
	 * properties map includes audio format dependant features such as bitrate,
	 * duration, frequency, channels, number of frames, vbr flag, id3v2/id3v1
	 * (for MP3 only), comments (for Ogg Vorbis), ...
	 *
	 * @param stream
	 *            could be File, URL or InputStream
	 * @param properties
	 *            audio stream properties.
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

	@Override
	public void setController(BasicController arg0) {
	}

	// Between 0 and 100, converted to 0 and 1
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
			double pan = (i - 50) / 100 * 2;

			control.setPan(pan);
			currentAudioPan = i;
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
	}

	/////////////////////////////////////////////////////////////////////////

	private boolean isPlaying = false;
	private boolean isStopped = false;
	private boolean isPaused = false;

	private Thread soundPlayThread;

	/**
	 * Notification callback for basicplayer events such as opened, eom ...
	 * 
	 * A copy of javazoom.jlgui.player.amp.processStateUpdated(BasicPlayerEvent event)
	 * 
	 */
	public void stateUpdated(BasicPlayerEvent event) {
		// Notification of BasicPlayer states (opened, playing, end of media,
		// ...)

		int state = event.getCode();

		if (event.getCode() == BasicPlayerEvent.STOPPED) {
			newVisualizerStatus("Stopped");

			isStopped = true;
			isPlaying = false;
			isPaused = false;

			// stop analyzer
			getVisualizer().analyzer.stopDSP();
			getVisualizer().analyzer.repaint();

		} else if (event.getCode() == BasicPlayerEvent.PAUSED) {
			newVisualizerStatus("Paused");

			isStopped = false;
			isPlaying = false;
			isPaused = true;

		} else if (event.getCode() == BasicPlayerEvent.PLAYING) {

			newVisualizerStatus("Playing");

			isStopped = false;
			isPaused = false;
			isPlaying = true;

			// analyzer
			if (audioInfo.containsKey("basicplayer.sourcedataline")) {

				getVisualizer().analyzer.setupDSP((SourceDataLine) audioInfo.get("basicplayer.sourcedataline"));
				getVisualizer().analyzer.startDSP((SourceDataLine) audioInfo.get("basicplayer.sourcedataline"));

			}

		} else if (event.getCode() == BasicPlayerEvent.RESUMED) {
			newVisualizerStatus("Resumed");

			isStopped = false;
			isPaused = false;
			isPlaying = true;
		} else if (state == BasicPlayerEvent.OPENING) {

			newVisualizerStatus("Buffering");

		} else if (state == BasicPlayerEvent.EOM) { /*-- End Of Media reached --*/
			newVisualizerStatus("End of media");
		}

	}

	void stop() {
		try {
			control.stop();
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Return true if its paused
	 */
	void pause() {
		try {
			control.pause();
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
	}

	void resume() {
		try {
			control.resume();
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
	}

	public void play(File sound) {
		if (sound.exists()) {

			soundPlayThread = new Thread("Sound player") {

				public void run() {

					currentSelectedSound = sound;

					try {
						control.open(currentSelectedSound);
						control.play();

						setVolume(currentAudioVolume);
						setPan(currentAudioPan);
					} catch (BasicPlayerException e) {
						e.printStackTrace();
						System.err.println("Error!");
					}

				}
			};

			soundPlayThread.start();
		}

	}

	/**
	 * Return if its playing now
	 */
	public boolean resumeOrPause() {
		if (isPaused) {
			resume();
			return true;
		} else {
			pause();
			return false;
		}
	}

	public void stopUsing(File f) {
		if (currentSelectedSound != null && currentSelectedSound.getAbsolutePath().equals(f.getAbsolutePath())) {
			Logger.logInfo(TAG, "selectedSound is the file last played by AudioPlayer! Stopping using it...");

			stop();

			try {
				soundPlayThread.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			//EPIC THANKS TO wuppi AT https://stackoverflow.com/a/14123384/3224295
			//WUPPI I LOVE YOU

			System.gc();
			Thread.yield();

		}
	}
}
