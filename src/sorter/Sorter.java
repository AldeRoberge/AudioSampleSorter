package sorter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import audio.AudioPlayer;
import key.GlobalKeyListener;
import logger.Logger;
import property.Properties;
import sorter.soundPanel.Sound;
import sorter.soundPanel.SoundPanel;
import sorter.soundPanel.sorter.SoundPanelSorter;

public class Sorter extends JPanel {

	private static final String TAG = "Sorter";

	public AudioPlayer audioPlayer = new AudioPlayer();

	public ArrayList<SoundPanel> soundPanels = new ArrayList<SoundPanel>();
	public ArrayList<SoundPanel> selectedSoundPanels = new ArrayList<SoundPanel>();

	private static JPanel columnpanel;
	private static JPanel borderlayoutpanel;
	public JScrollPane scrollPane;

	public SoundPanelSorter soundPanelSorter = new SoundPanelSorter(this);

	public GlobalKeyListener globalKeyListener = GlobalKeyListener.get();

	/**
	 * Create the panel.
	 */
	public Sorter() {
		globalKeyListener.init(this);

		setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 600, 231);
		scrollPane.setAutoscrolls(true);
		add(scrollPane, BorderLayout.CENTER);

		borderlayoutpanel = new JPanel();
		scrollPane.setViewportView(borderlayoutpanel);

		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		//I hardcoded this here because I couldnt find a link between the height 
		//of the soundpanels and the number needed to scroll 1 height of a soundpanel

		borderlayoutpanel.setLayout(new BorderLayout(0, 0));

		columnpanel = new JPanel();
		columnpanel.setLayout(new GridLayout(0, 1, 0, 1));
		columnpanel.setBackground(Color.gray);
		borderlayoutpanel.add(columnpanel, BorderLayout.NORTH);

		borderlayoutpanel.setFocusable(true);
		borderlayoutpanel.requestFocusInWindow();

	}

	void addSound(Sound sound) {

		SoundPanel soundPanel = new SoundPanel(sound, this);

		columnpanel.add(soundPanel);
		soundPanels.add(soundPanel);

		refreshInfoPanels();
	}

	public void refreshInfoPanels() {

		for (SoundPanel logPanel : soundPanels) {
			logPanel.updateFilenameLabel();
		}

		for (SoundPanel logPanel : soundPanels) {

			logPanel.validate();
			logPanel.repaint();

		}

		columnpanel.validate();
		columnpanel.repaint();

		borderlayoutpanel.validate();
		borderlayoutpanel.repaint();

		scrollPane.validate();
		scrollPane.repaint();

	}

	public void soundPanelIsLeftClicked(SoundPanel me) {

		if (globalKeyListener.shiftIsPressed() && selectedSoundPanels.size() >= 1) { //SHIFT + CLICK

			int targetPoint = soundPanels.indexOf(me);

			Logger.logInfo(TAG, "Target : " + targetPoint);

			int anchorPoint = -1;

			for (SoundPanel p : soundPanels) {

				if (selectedSoundPanels.contains(p)) {
					anchorPoint = soundPanels.indexOf(p);
					break;
				}
			}

			if (anchorPoint == -1) {
				Logger.logError(TAG + "soundPanelIsLeftClicked", "Invalid anchorPoint -1");
				return;
			}

			Logger.logInfo(TAG, "targetPoint , anchorPoint : " + (targetPoint + "," + anchorPoint));

			if (targetPoint > anchorPoint) { //"Target point is higher than anchor"

				for (int i = anchorPoint; i <= targetPoint; i++) {
					selectSoundPanel(soundPanels.get(i), false);
				}

			} else if (targetPoint < anchorPoint) { //"Target point is smaller than anchor"

				for (int i = anchorPoint; i >= targetPoint; i--) {
					selectSoundPanel(soundPanels.get(i), false);
				}

			}

		} else {

			if (globalKeyListener.ctrlIsPressed()) { //CTRL + CLICK

				if (me.selected == true) {
					unselectSoundPanel(me);
				} else if (me.selected == false) {
					selectSoundPanel(me, Properties.PLAY_ON_CLICK.getValueAsBoolean());
				}

			} else { //DEFAULT "Shift is not pressed OR less than 1 item is already selectioned"

				unselectAllSoundPanels();

				selectedSoundPanels.add(me);
				me.updateIsSelected(true, Properties.PLAY_ON_CLICK.getValueAsBoolean());
			}

		}

	}

	private void selectSoundPanel(SoundPanel soundPanel, boolean shouldPlay) {
		soundPanel.updateIsSelected(true, shouldPlay);
		selectedSoundPanels.add(soundPanel);
	}

	private void unselectSoundPanel(SoundPanel soundPanel) {
		if (selectedSoundPanels.contains(soundPanel)) {
			selectedSoundPanels.remove(soundPanel);
			soundPanel.updateIsSelected(false, false);
		} else {
			Logger.logError(TAG, "Sound panel is not in selectedSoundPanels!");
		}
	}

	public void performSelectAll() { //selectOrDeselectAll
		if (selectedSoundPanels.size() == soundPanels.size()) {
			unselectAllSoundPanels();
		} else {
			selectAllSoundPanels();
		}
	}

	private void selectAllSoundPanels() {
		unselectAllSoundPanels(); //avoid selecting all sound panels twice

		try {
			for (SoundPanel soundPanel : soundPanels) {
				selectSoundPanel(soundPanel, false);
			}
		} catch (Exception e1) {
			Logger.logError(TAG, "Error in removeInfoPanel", e1);
			e1.printStackTrace();
		}
	}

	private void unselectAllSoundPanels() {
		try {
			for (Iterator<SoundPanel> iterator = selectedSoundPanels.iterator(); iterator.hasNext();) {
				SoundPanel soundPanel = iterator.next();
				iterator.remove();
				soundPanel.updateIsSelected(false, false);
			}
		} catch (Exception e1) {
			Logger.logError(TAG, "Error in removeInfoPanel", e1);
			e1.printStackTrace();
		}
	}

	public void scroll(boolean scrollUp) {
		if (selectedSoundPanels.size() == 1) {
			int anchor = soundPanels.indexOf(selectedSoundPanels.get(0));

			SoundPanel panelToSelect = null;

			if (scrollUp) {
				if (anchor - 1 >= 0) {
					panelToSelect = soundPanels.get(anchor - 1);
				}

			} else { //down...!

				if (soundPanels.size() > anchor + 1) {
					panelToSelect = soundPanels.get(anchor + 1);
				}

			}

			if (panelToSelect != null) {

				scrollPane.scrollRectToVisible(panelToSelect.getBounds(null));

				unselectSoundPanel(soundPanels.get(anchor)); //same as unselectAllSoundPanels but more efficient
				selectSoundPanel(panelToSelect, Properties.PLAY_ON_CLICK.getValueAsBoolean());
			}

		}
	}

	/**
	 * Files imported this way should be sound files
	 * See FileImporter
	 */
	public void importNewFiles(ArrayList<File> filesToImport) {
		for (File f : filesToImport) {

			boolean alreadyImported = false;

			for (SoundPanel p : soundPanels) {
				if (p.getFile().getAbsolutePath().equals(f.getAbsolutePath())) {
					alreadyImported = true;
					break;
				}
			}

			if (!alreadyImported) {
				addSound(new Sound(f, audioPlayer));
			} else {
				Logger.logInfo(TAG, "Trying to import already imported files");
			}

		}
	}

	public void clearPanelsFromUI() {

		for (SoundPanel sp : soundPanels) {
			columnpanel.remove(sp);
		}

		refreshInfoPanels();

	}

	public void clearAndRepopulatePanels() {
		clearPanelsFromUI();

		for (SoundPanel soundPanel : soundPanels) {
			columnpanel.add(soundPanel);
			refreshInfoPanels();
		}
	}

}
