package samplerSorter.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import samplerSorter.SamplerSorter;
import samplerSorter.actions.ActionManager;
import samplerSorter.audioplayer.AudioPlayer;
import samplerSorter.logger.Logger;
import samplerSorter.macro.GlobalMacroKeyListener;
import samplerSorter.properties.Properties;
import samplerSorter.properties.RelativeLocationManager;
import samplerSorter.util.ExtensionFilter;

public class Sorter extends JPanel {

	private static final String TAG = "Sorter";

	public AudioPlayer audioPlayer = new AudioPlayer();

	private ArrayList<SoundPanel> allSoundPanels = new ArrayList<SoundPanel>();
	public ArrayList<SoundPanel> selectedSoundPanels = new ArrayList<SoundPanel>();

	static JPanel columnpanel;
	static JPanel borderlaoutpanel;
	public JScrollPane scrollPane;

	public GlobalMacroKeyListener globalKeyListener = GlobalMacroKeyListener.get();

	/**
	 * Create the panel.
	 */
	public Sorter() {

		globalKeyListener.init();

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

	public void addLogInfoPanel(Sound sound) {

		SoundPanel infoPanel = new SoundPanel(sound, this);

		columnpanel.add(infoPanel);
		allSoundPanels.add(infoPanel);

		refreshInfoPanels();
	}

	public void refreshInfoPanels() {

		for (SoundPanel logPanel : allSoundPanels) {
			logPanel.updateFilenameLabel();
		}

		for (SoundPanel logPanel : allSoundPanels) {

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

	public void removeAllSoundPanels() {
		try {
			for (Iterator<SoundPanel> iterator = allSoundPanels.iterator(); iterator.hasNext();) {
				SoundPanel infoP = iterator.next();

				iterator.remove();
				columnpanel.remove(infoP);
			}

			refreshInfoPanels();
		} catch (Exception e) {
			Logger.logError(TAG, "Error in removeInfoPanel", e);
			e.printStackTrace();
		}
	}

	public void soundPanelIsClicked(SoundPanel me) {

		if (globalKeyListener.shiftIsPressed()) {

			Logger.logInfo(TAG, "Shift IS clicked");

			if (selectedSoundPanels.size() >= 1) {

				Logger.logInfo(TAG, "selectedSoundPanels.size() > 1");

				// the lowest in the list selected (used to -> from select)
				int anchorPoint = 0;

				for (SoundPanel p : allSoundPanels) {
					if (selectedSoundPanels.contains(p)) {
						Logger.logInfo(TAG, "selectedSoundPanels.contains(p)");

						anchorPoint = allSoundPanels.indexOf(p);
						break;
					}
				}

				int targetPoint = allSoundPanels.indexOf(me);

				Logger.logInfo(TAG, "targetPoint > anchorPoint : " + (targetPoint > anchorPoint));

				if (targetPoint > anchorPoint) {

					for (int i = 0; i <= targetPoint; i++) {
						allSoundPanels.get(i).setSelected(true);
					}

				} else if (targetPoint < anchorPoint) {

					for (int i = 0; i >= targetPoint; i++) {
						allSoundPanels.get(i).setSelected(true);
					}

				}

			} else { // its equal or smaller than 1

				Logger.logInfo(TAG, "Normal selection");

				basicSelect(me);

			}

		} else {

			basicSelect(me);
		}

	}

	private void basicSelect(SoundPanel me) {

		if (selectedSoundPanels.contains(me)) { // is selected (unselect)
			Logger.logInfo(TAG, "selectedSoundPanels contains me");

			selectedSoundPanels.remove(me);
			me.setSelected(false);
		} else { // is not selected (select)
			Logger.logInfo(TAG, "selectedSoundPanels does not contain me");

			for (SoundPanel s : selectedSoundPanels) {
				s.setSelected(false);
			}

			selectedSoundPanels.clear(); // select only 1 at a time

			//

			selectedSoundPanels.add(me);
			me.setSelected(true);
		}
	}

	public void openImportDialog() { // Open JFileChoose to select a directory (folder)

		Logger.logInfo(TAG, "Selecting a folder...");

		JFileChooser chooser = new JFileChooser();

		// set location on middle of screen

		Point newLoc = RelativeLocationManager.getRelativeLocationToScreenProperty();
		chooser.setLocation(newLoc);
		chooser.setCurrentDirectory(new File(Properties.LAST_OPENNED_LOCATION.getValue()));
		chooser.setDialogTitle("Import folders and samples");
		chooser.setApproveButtonText("Choose");

		// from https://stackoverflow.com/questions/16292502/how-can-i-start-the-jfilechooser-in-the-details-view
		Action details = chooser.getActionMap().get("viewTypeDetails"); // show details view
		details.actionPerformed(null);

		// Format supported by AudioPlayer :
		// WAV, AU, AIFF ,MP3 and Ogg Vorbis files

		ExtensionFilter type2 = new ExtensionFilter("Audio Files (*.aiff, *.au, *.mp3, *.ogg, *.mp4, *.wav)",
				new String[] { ".aiff", ".au", ".mp3", ".ogg", ".mp4", ".wav" });
		chooser.setFileFilter(type2);

		chooser.setMultiSelectionEnabled(true); // shift + click to select multiple files
		chooser.setPreferredSize(new Dimension(800, 600));
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
			System.out.println("getSelectedFile() : " + chooser.getSelectedFile());

			String directory = chooser.getCurrentDirectory().toString();

			Properties.LAST_OPENNED_LOCATION.setNewValue(directory);

			File[] files = chooser.getSelectedFiles();

			System.out.println(files.length);

			for (File file : files) {
				if (file.isDirectory()) {

					int totalResultFiles = getAllFiles(file.getAbsolutePath(), Properties.INCLUDE_SUBFOLDERS.isTrue(),
							0);

					System.out.println("We got " + totalResultFiles + " files!");

				} else {
					addLogInfoPanel(new Sound(file));
				}
			}

			//
		} else {
			Logger.logInfo(TAG + " (Folder Selector)", "No selection");
		}

	}

	private int getAllFiles(String directoryName, boolean includeSubfolders, int totalOfFiles) {

		System.out.println(
				"Getting all files for directory : " + directoryName + ", including subfolders : " + includeSubfolders);

		File directory = new File(directoryName);

		// get all the files from a directory
		File[] fList = directory.listFiles();
		for (File file : fList) {

			if (file.isFile()) {
				totalOfFiles += 1;

				addLogInfoPanel(new Sound(file));
			} else if (file.isDirectory() && includeSubfolders) {
				getAllFiles(file.getAbsolutePath(), includeSubfolders, totalOfFiles);
			}
		}
		return totalOfFiles;

	}

	public void scrollUp() {
		// TODO Auto-generated method stub

	}

	public void scrollDown() {
		// TODO Auto-generated method stub

	}

	public void changeGlobalKeyListenerState(boolean b) {
		globalKeyListener.changeListeningState(b);
	}

	public void selectAll() {
		// TODO Auto-generated method stub

	}

}
