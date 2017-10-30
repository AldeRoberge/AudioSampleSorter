package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import GUI.soundPanelSorter.SoundPanelsSorter;
import GUI.soundpanel.Sound;
import GUI.soundpanel.SoundPanel;
import audio.AudioPlayer;
import logger.Logger;
import macro.GlobalMacroKeyListener;
import macro.GodManager;
import property.Properties;

public class Sorter extends JPanel {

	private static final String TAG = "Sorter";

	public AudioPlayer audioPlayer = new AudioPlayer();

	public ArrayList<SoundPanel> soundPanels = new ArrayList<SoundPanel>();
	public ArrayList<SoundPanel> selectedSoundPanels = new ArrayList<SoundPanel>();

	private static JPanel columnpanel;
	private static JPanel borderlaoutpanel;
	public JScrollPane scrollPane;

	public GodManager god = new GodManager(this);

	public SoundPanelsSorter soundPanelSorter = new SoundPanelsSorter(this);

	/**
	 * Create the panel.
	 */
	public Sorter() {

		setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 600, 231);
		// scrollPane.setAutoscrolls(true);
		// scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane, BorderLayout.CENTER);

		borderlaoutpanel = new JPanel();
		scrollPane.setViewportView(borderlaoutpanel);
		borderlaoutpanel.setLayout(new BorderLayout(0, 0));

		columnpanel = new JPanel();
		columnpanel.setLayout(new GridLayout(0, 1, 0, 1));
		columnpanel.setBackground(Color.gray);
		borderlaoutpanel.add(columnpanel, BorderLayout.NORTH);

		borderlaoutpanel.setFocusable(true);
		borderlaoutpanel.requestFocusInWindow();

		//TODO MAKE THIS A SEPARATE CLASS

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

		borderlaoutpanel.validate();
		borderlaoutpanel.repaint();

		scrollPane.validate();
		scrollPane.repaint();

	}

	public void soundPanelIsLeftClicked(SoundPanel me) {

		if (god.shiftIsPressed() && selectedSoundPanels.size() >= 1) {

			int targetPoint = soundPanels.indexOf(me);

			Logger.logInfo(TAG, "Target : " + targetPoint);

			int anchorPoint = 0;

			for (SoundPanel p : soundPanels) {

				if (selectedSoundPanels.contains(p)) {

					anchorPoint = soundPanels.indexOf(p);

					Logger.logInfo(TAG, "selectedSoundPanels.contains(p), index is " + anchorPoint);

					break;
				}
			}

			Logger.logInfo(TAG, "targetPoint , anchorPoint : " + (targetPoint + "," + anchorPoint));

			if (targetPoint > anchorPoint) {

				System.out.println("Target point is higher than anchor");

				for (int i = anchorPoint; i <= targetPoint; i++) {
					System.out.println("Selecting " + i);

					selectPanel(soundPanels.get(i), false);
				}

			} else if (targetPoint < anchorPoint) {

				System.out.println("Target point is smaller than anchor");

				for (int i = anchorPoint; i >= targetPoint; i--) {
					System.out.println("Selecting " + i);

					selectPanel(soundPanels.get(i), false);
				}

			}

		} else {

			System.out.println("Shift is not pressed OR less than 1 item is already selectioned");

			unselectAllSoundPanels();

			System.out.println("Is not selected, selecting!!!" + selectedSoundPanels.size());

			selectedSoundPanels.add(me);
			me.setSelected(true, Properties.PLAY_ON_CLICK.getValueAsBoolean());
		}

	}

	private void selectPanel(SoundPanel soundPanel, boolean shouldPlay) {
		soundPanel.setSelected(true, shouldPlay);
		selectedSoundPanels.add(soundPanel);
	}

	public void selectOrUnselectAllSoundPanels() {
		if (selectedSoundPanels.size() == soundPanels.size()) {
			System.out.println("All panels are selected, unselecting all");

			unselectAllSoundPanels();
		} else {

			System.out.println("Not all panels are selected, selecting all");

			System.out.println("selectedSoundPanels.size() : " + selectedSoundPanels.size() + " " + soundPanels.size());

			selectAllSoundPanels();
		}
	}

	private void unselectAllSoundPanels() {
		try {
			for (Iterator<SoundPanel> iterator = selectedSoundPanels.iterator(); iterator.hasNext();) {
				SoundPanel soundPanel = iterator.next();
				iterator.remove();
				soundPanel.setSelected(false, false);
			}
		} catch (Exception e1) {
			Logger.logError(TAG, "Error in removeInfoPanel", e1);
			e1.printStackTrace();
		}
	}

	
	private void unselectSoundPanel(SoundPanel soundPanel) {
		if (selectedSoundPanels.contains(soundPanel)) {
			selectedSoundPanels.remove(soundPanel);
			soundPanel.setSelected(false, false);
		} else {
			Logger.logError(TAG, "Sound panel is not in selectedSoundPanels!");
		}
	}
	
	private void selectAllSoundPanels() {
		unselectAllSoundPanels(); //avoid selecting all sound panels twice

		try {
			for (SoundPanel soundPanel : soundPanels) {
				selectPanel(soundPanel, false);
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

				if (soundPanels.size() >= anchor + 1) {
					panelToSelect = soundPanels.get(anchor + 1);
				}

			}

			if (panelToSelect != null) {
				unselectSoundPanel(soundPanels.get(anchor)); //same as unselectAllSoundPanels but more efficient
				selectPanel(panelToSelect, Properties.PLAY_ON_CLICK.getValueAsBoolean());
			}

		}
	}

	public void selectAll() {
		// TODO Auto-generated method stub

	}

	public void soundPanelIsRightClicked(SoundPanel me) {
		// TODO Auto-generated method stub

	}

	public Object removeSoundPanel(SoundPanel soundPanel) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Files imported this way should be sound files
	 * See FileImporter
	 */
	public void importNewFiles(ArrayList<File> filesToImport) {
		for (File f : filesToImport) {
			addSound(new Sound(f, audioPlayer));
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
