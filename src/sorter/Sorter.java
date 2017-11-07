package sorter;

import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JPanel;

import audio.AudioPlayer;
import key.GlobalKeyListener;
import logger.Logger;
import sorter.soundPanel.Sound;
import util.FileManager;

public class Sorter extends JPanel {

	private static final String TAG = "Sorter";

	public AudioPlayer audioPlayer = new AudioPlayer();

	public GlobalKeyListener globalKeyListener = GlobalKeyListener.get();

	public FileManager fMan = new FileManager();

	/**
	 * Create the panel.
	 */
	public Sorter() {
		globalKeyListener.init(this);

		setLayout(new BorderLayout(0, 0));

	}

	void addSound(Sound sound) {

	}

	public void soundPanelIsLeftClicked(Sound me) {
		//selectSoundPanel(me, Properties.PLAY_ON_CLICK.getValueAsBoolean());
		//me.setSelec
	}

	/**
	 * Files imported this way should be sound files
	 * See FileImporter
	 */
	public void importNewFiles(ArrayList<File> filesToImport) {
		for (File f : filesToImport) {

			boolean alreadyImported = false;

			/**for (SoundPanel p : soundPanels) {
				if (p.getFile().getAbsolutePath().equals(f.getAbsolutePath())) {
					alreadyImported = true;
					break;
				}
			}*/

			if (!alreadyImported) {
				addSound(new Sound(f, audioPlayer));
			} else {
				Logger.logInfo(TAG, "Trying to import already imported files");
			}

		}
	}

}
