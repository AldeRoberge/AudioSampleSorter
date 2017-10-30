package GUI.soundpanel;

import java.io.File;

import audio.AudioPlayer;
import util.file.FileNameUtil;

public class Sound {

	private AudioPlayer audioPlayer;

	public String fromRootDirectory; //used to display Folder\music.mp3
	public File file;

	public boolean isPlaying = false;

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
		System.out.println("Play!!!!!!!!!!");
		
		audioPlayer.playNewSoundOrResume(this);
		isPlaying = true;
	}

	/**
	 * Will stop if the sound isPlaying
	 */
	public void pause() {
		if (isPlaying) {
			audioPlayer.pause();
			isPlaying = false;
		}
	}

	//Should only be used by Actions, use setSelected(true) instead for selection
	public void playOrPause() {
		if (isPlaying) {
			pause();
		} else {
			play();
		}
	}

}
