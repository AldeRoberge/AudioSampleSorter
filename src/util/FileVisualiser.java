package util;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JPanel;

import audio.AudioPlayer;
import constants.Constants;
import fileTypes.FileTypes;
import logger.Logger;

public class FileVisualiser extends FileInformation {

	private static final String TAG = "FileVisualiser";

	public JPanel playerContainer;

	private AudioPlayer audPlayer = new AudioPlayer();

	public FileVisualiser() {
		super();

		playerContainer = new JPanel();
		playerContainer.setBackground(Constants.SICK_PURPLE);
		playerContainer.setLayout(new BorderLayout(0, 0));
		playerContainer.add(audPlayer.getVisualizer().analyzer, BorderLayout.CENTER);
	}

	public void setFileSelected(File file) {

		setFileDetails(file);

		if (FileTypes.AUDIO_FILES.accept(file)) {
			System.out.println("File is a sound file!");
			audPlayer.playNewSoundOrResume(file);
			//TODO change playerContainer to contain audPlayer
		} else if (FileTypes.VIDEO_FILES.accept(file)) {
			Logger.logError(TAG, "Video files are not currently supported!");
		} else if (FileTypes.PICTURE_FILES.accept(file)) {
			Logger.logError(TAG, "Picture files are not currently supported!");
		} else if (FileTypes.TEXT_FILES.accept(file)) {
			Logger.logError(TAG, "Text files are not currently supported!");
		} else {
			Logger.logError(TAG, "Unknown file Type " + file.getName());
		}

	}

	public AudioPlayer getAudioPlayer() {
		return audPlayer;
	}

	public JPanel getPlayer() {
		return playerContainer;
	}

}
