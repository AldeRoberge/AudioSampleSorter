package samplerSorter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import samplerSorter.macro.MacroEditor;
import samplerSorter.logger.LogUI;
import samplerSorter.logger.Logger;
import samplerSorter.properties.Properties;
import samplerSorter.util.ExtensionFilter;

public class RunSS {

	String TAG = "RunSS";

	private JFrame frmSamplerSorter;
	private JButton btnSelectFolder;
	private JCheckBox chckbxIncludeSubfoldersInSearch;
	private LogUI panel;
	private JButton btnEditMacros;

	private MacroEditor macroEditor = new MacroEditor();

	static JPanel columnpanel = new JPanel();
	static JPanel borderlaoutpanel;
	public static JScrollPane scrollPane;

	private static ArrayList<SoundPanel> allSoundPanels = new ArrayList<SoundPanel>();
	public ArrayList<SoundPanel> selectedPanels = new ArrayList<SoundPanel>();

	static AudioPlayer audioPlayer = new AudioPlayer();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RunSS window = new RunSS();
					window.frmSamplerSorter.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RunSS() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmSamplerSorter = new JFrame();
		frmSamplerSorter.setResizable(false);

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look and feel.
		}

		frmSamplerSorter.setTitle("Sampler Sorter | Ultimate Edition");
		frmSamplerSorter.setBounds(100, 100, 633, 387);
		frmSamplerSorter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSamplerSorter.getContentPane().setLayout(null);

		new Thread("Image loader") {

			public void run() {

				try {
					frmSamplerSorter.setIconImage(
							new ImageIcon(ImageIO.read(new URL("https://i.imgur.com/ioE0Nrf.png"))).getImage());
				} catch (IOException e2) {
					Logger.logError(TAG, "Error with getting the icon", e2);
					e2.printStackTrace();
				}

				Logger.logInfo(TAG, "Loaded external icon");

			}
		}.start();

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 629, 353);
		frmSamplerSorter.getContentPane().add(tabbedPane);

		JPanel mainPanel = new JPanel();
		tabbedPane.addTab("UI", null, mainPanel, null);
		mainPanel.setLayout(null);

		btnSelectFolder = new JButton("Import samples");
		btnSelectFolder.setToolTipText("Select folder containing samples or samples");
		btnSelectFolder.setBounds(12, 13, 150, 25);
		btnSelectFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Open JFileChoose to select a directory (folder)

				Logger.logInfo(TAG, "Selecting a folder...");

				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File(Properties.LAST_OPENNED_LOCATION.getValue())); //open where last time it was
				chooser.setDialogTitle("Import folders and samples");
				chooser.setApproveButtonText("Choose");

				//Format supported by AudioPlayer :
				//WAV, AU, AIFF ,MP3 and Ogg Vorbis files

				ExtensionFilter type2 = new ExtensionFilter("Audio Files (*.aiff, *.au, *.mp3, *.ogg, *.mp4, *.wav)",
						new String[] { ".aiff", ".au", ".mp3", ".ogg", ".mp4", ".wav" });
				chooser.setFileFilter(type2);

				chooser.setMultiSelectionEnabled(true);

				//chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
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
							getAllFiles(file.getAbsolutePath(), chckbxIncludeSubfoldersInSearch.isSelected());
						} else {
							addLogInfoPanel(new Sound(file));
						}
					}

					//
				} else {
					Logger.logInfo(TAG + " (Folder Selector)", "No selection");
				}

			}
		});
		mainPanel.add(btnSelectFolder);

		chckbxIncludeSubfoldersInSearch = new JCheckBox("Include subfolders");
		chckbxIncludeSubfoldersInSearch.setToolTipText("Include folders inside the selected folders");
		chckbxIncludeSubfoldersInSearch.setBounds(170, 13, 133, 25);
		chckbxIncludeSubfoldersInSearch.setSelected(Properties.INCLUDE_SUBFOLDERS.isTrue());
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				Properties.INCLUDE_SUBFOLDERS.setNewValue(chckbxIncludeSubfoldersInSearch.isSelected());
			}
		};
		chckbxIncludeSubfoldersInSearch.addActionListener(actionListener);
		mainPanel.add(chckbxIncludeSubfoldersInSearch);

		//

		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 47, 600, 231);
		//scrollPane.setAutoscrolls(true);
		//scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		mainPanel.add(scrollPane);

		borderlaoutpanel = new JPanel();
		scrollPane.setViewportView(borderlaoutpanel);
		borderlaoutpanel.setLayout(new BorderLayout(0, 0));

		columnpanel = new JPanel();
		columnpanel.setLayout(new GridLayout(0, 1, 0, 1));
		columnpanel.setBackground(Color.gray);
		borderlaoutpanel.add(columnpanel, BorderLayout.NORTH);

		//

		btnEditMacros = new JButton("Edit Macros");
		btnEditMacros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				macroEditor.setVisible(true);
			}
		});
		btnEditMacros.setBounds(12, 285, 150, 25);
		mainPanel.add(btnEditMacros);

		JPanel consolePanel = new JPanel();
		tabbedPane.addTab("Console", null, consolePanel, null);
		consolePanel.setLayout(null);

		panel = new LogUI();
		panel.setBounds(0, 0, 624, 323);
		Logger.init(panel);
		consolePanel.add(panel);

		JPanel settingsPanel = new SettingsPanel();
		tabbedPane.addTab("Settings", null, settingsPanel, null);

		Logger.logInfo(TAG, "RunSS proccess started...");

		try {

			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			Logger.logError(TAG, "Could not set look and feel", e1);
			e1.printStackTrace();
		}

	}

	private void getAllFiles(String directoryName, boolean includeSubfolders) {

		System.out.println(
				"Getting all files for directory : " + directoryName + ", including subfolders : " + includeSubfolders);

		File directory = new File(directoryName);

		// get all the files from a directory
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile()) {
				addLogInfoPanel(new Sound(file));
			} else if (file.isDirectory() && includeSubfolders) {
				getAllFiles(file.getAbsolutePath(), includeSubfolders);
			}
		}

	}

	// SoundPanel UI

	public void addLogInfoPanel(Sound sound) {

		SoundPanel infoPanel = new SoundPanel(sound);

		columnpanel.add(infoPanel);
		allSoundPanels.add(infoPanel);

		refreshInfoPanels();
	}

	public void refreshInfoPanels() {

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

	public void clearLog() {
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

	public static void refreshSoundPanelAfterSuffixChange() {
		for (SoundPanel logPanel : allSoundPanels) {

			logPanel.updateFilenameLabel();

		}
	}

}
