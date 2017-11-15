package ass;

import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JPanel;

import file.FileTypes;
import constants.Constants;
import logger.Logger;
import audioPlayer.AudioPlayer;

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
			audPlayer.playNewSoundOrResume(file);
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

	public void setFilesSelected(ArrayList<File> files) {

		setFilesDetails(files);

		//Do not play the sound

	}

	public AudioPlayer getAudioPlayer() {
		return audPlayer;
	}

	public JPanel getPlayer() {
		return playerContainer;
	}

}
