package samplerSorter.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.io.FilenameUtils;

import samplerSorter.GUI.soundpanel.Sound;
import samplerSorter.constants.Icons;
import samplerSorter.logger.Logger;
import samplerSorter.property.Properties;
import samplerSorter.util.file.ExtensionFilter;
import samplerSorter.util.ui.RelativeLocationManager;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.Action;
import javax.swing.BoxLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class FileImporter extends JFrame {

	protected static final String TAG = "FileImporter";
	private JPanel contentPane;
	private Sorter sorter;

	ExtensionFilter filesToAllow = new ExtensionFilter("Audio Files (*.aiff, *.au, *.mp3, *.ogg, *.mp4, *.wav)",
			new String[] { ".aiff", ".au", ".mp3", ".ogg", ".mp4", ".wav" });

	private ArrayList<File> filesToImport = new ArrayList<File>();
	private DropPane dropPanel;

	public void addFileToImport(File f) {
		if (!filesToImport.contains(f) && filesToAllow.accept(f)) {
			filesToImport.add(f);

			dropPanel.updateMessage();
		}
	}

	public int getTotalFilesToImport() {
		return filesToImport.size();
	}

	/**
	 * Only used to test
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileImporter frame = new FileImporter(new Sorter());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FileImporter(Sorter s) {

		this.sorter = s;

		setIconImage(Icons.IMPORT.getImage());
		setTitle("Import");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});

		setBounds(100, 100, 649, 363);
		contentPane = new JPanel();
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

				chooser.setFileFilter(filesToAllow);

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

					for (File file : files) { //we do this because the user might choose more than 1 folder
						if (file.isDirectory()) {

							int totalResultFiles = getAllFiles(file.getAbsolutePath(),
									Properties.INCLUDE_SUBFOLDERS.isTrue(), 0);

							System.out.println("We got " + totalResultFiles + " files!");

						} else {
							addFileToImport(file);
						}
					}

					//
				} else {
					Logger.logInfo(TAG + " (Folder Selector)", "No selection");
				}

			}
		});
		panel.add(btnOpenFileBrowser);

		JButton btnNewButton = new JButton("Import");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sorter.importNewFiles(filesToImport);
				filesToImport.clear();
				setVisible(false);
				dropPanel.updateMessage();
			}
		});
		panel.add(btnNewButton);

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

				addFileToImport(file);

			} else if (file.isDirectory() && includeSubfolders) {
				getAllFiles(file.getAbsolutePath(), includeSubfolders, totalOfFiles);
			}
		}
		return totalOfFiles;

	}

}
