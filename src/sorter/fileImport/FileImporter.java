package sorter.fileImport;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import constants.icons.Icons;
import fileTypes.FileTypes;
import logger.Logger;
import property.Properties;
import sorter.other.Container;
import util.FileManager;
import util.ui.MiddleOfTheScreen;

public class FileImporter extends JFrame {

	protected static final String TAG = "FileImporter";

	private ArrayList<File> filesToImport = new ArrayList<File>();
	private DropPane dropPanel;
	private JButton btnImport;

	public static Container fileImporterParent = new Container("This is used to pass the icon to the fileChooser",
			Icons.IMPORT.getImage(), null, false);

	/**
	 * Create the frame.
	 */
	public FileImporter(FileManager fMan) {

		FileManager fMan1 = fMan;

		setIconImage(Icons.IMPORT.getImage());
		setTitle("Import");

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				filesToImport.clear();
				dropPanel.updateMessage();
				setVisible(false);
			}
		});

		setBounds(100, 100, 553, 262);

		setLocation(MiddleOfTheScreen.getLocationFor(this));

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel container = new JPanel();
		contentPane.add(container, BorderLayout.CENTER);
		container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

		dropPanel = new DropPane(this);
		container.add(dropPanel);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);

		JButton btnOpenFileBrowser = new JButton("Open file browser");
		btnOpenFileBrowser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Logger.logInfo(TAG, "Selecting a folder...");

				JFileChooser chooser = new JFileChooser();

				// set location on middle of screen

				chooser.setLocation(MiddleOfTheScreen.getLocationFor(chooser));

				chooser.setCurrentDirectory(new File(Properties.LAST_OPENNED_LOCATION.getValue()));
				chooser.setDialogTitle("Import folders and samples");
				chooser.setApproveButtonText("Choose");

				// from https://stackoverflow.com/questions/16292502/how-can-i-start-the-jfilechooser-in-the-details-view
				Action details = chooser.getActionMap().get("viewTypeDetails"); // show details view
				details.actionPerformed(null);

				// Format supported by AudioPlayer :
				// WAV, AU, AIFF ,MP3 and Ogg Vorbis files

				chooser.setFileFilter(FileTypes.AUDIO_FILES);

				chooser.setMultiSelectionEnabled(true); // shift + click to select multiple files
				chooser.setPreferredSize(new Dimension(800, 600));
				chooser.setAcceptAllFileFilterUsed(false);
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

				if (chooser.showOpenDialog(fileImporterParent) == JFileChooser.APPROVE_OPTION) {
					btnImport.requestFocus();

					String directory = chooser.getCurrentDirectory().toString();

					Properties.LAST_OPENNED_LOCATION.setNewValue(directory);

					File[] files = chooser.getSelectedFiles();

					importAll(Arrays.asList(files));

					//
				} else {
					Logger.logInfo(TAG + " (Folder Selector)", "No selection");
				}

			}

		});
		panel.add(btnOpenFileBrowser);

		btnImport = new JButton("Import");
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (filesToImport.size() > 0) {

					fMan.importFiles(filesToImport);
					filesToImport.clear();
					setVisible(false);
					dropPanel.updateMessage();

				}
			}
		});
		panel.add(btnImport);

	}

	void importAll(List<File> transferData) {

		for (File file : transferData) { //we do this because the user might choose more than 1 folder
			if (file.isDirectory()) {

				int totalResultFiles = getAllFiles(file.getAbsolutePath(),
						Properties.INCLUDE_SUBFOLDERS.getValueAsBoolean(), 0);

			} else {
				addFileToImport(file);
			}
		}

	}

	int getAllFiles(String directoryName, boolean includeSubfolders, int totalOfFiles) {

		System.out.println(
				"Getting all files for directory : " + directoryName + ", including subfolders : " + includeSubfolders);

		File directory = new File(directoryName);

		// get all the files from a directory
		File[] fList = directory.listFiles();
		for (File file : fList) {

			if (file.isFile()) {
				totalOfFiles += 1;

				addFileToImport(file);

			} else if (file.isDirectory() && includeSubfolders) {
				getAllFiles(file.getAbsolutePath(), includeSubfolders, totalOfFiles);
			}
		}
		return totalOfFiles;

	}

	public void addFileToImport(File f) {
		if (!filesToImport.contains(f) && FileTypes.AUDIO_FILES.accept(f)) {
			filesToImport.add(f);

			dropPanel.updateMessage();
		}
	}

	public int getTotalFilesToImport() {
		return filesToImport.size();
	}

}
