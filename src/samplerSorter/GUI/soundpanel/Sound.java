package samplerSorter.GUI.soundpanel;

import java.io.File;

import samplerSorter.audio.AudioPlayer;
import samplerSorter.util.file.FileNameUtil;

public class Sound {

	private AudioPlayer audioPlayer;

	public File file;

	boolean isPlaying = false;

	public Sound(File path, AudioPlayer audioPlayer) {
		this.file = path;
		this.audioPlayer = audioPlayer;
	}

	public String getName(boolean withSuffix) {
		if (withSuffix) {
			return file.getName();
		} else {
			return FileNameUtil.removeExtension(file.getName());
		}
	}

	/**public String getPath() {
		return file.getAbsolutePath();
	}*/

	public void play() {
		audioPlayer.playNewSoundOrResume(this);
		isPlaying = true;
	}

	/**
	 * Will stop if the sound isPlaying
	 */
	public void stop() {
		if (isPlaying) {
			audioPlayer.pause();
			isPlaying = false;
		}
	}

}
