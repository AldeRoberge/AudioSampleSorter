package samplerSorter;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import javax.swing.border.EmptyBorder;

import samplerSorter.audioplayer.AudioPlayer;
import samplerSorter.globalkeylistenner.GlobalKeyListener;
import samplerSorter.logger.LogUI;
import samplerSorter.logger.Logger;
import samplerSorter.macro.MacroEditor;
import samplerSorter.properties.Properties;
import samplerSorter.util.ExtensionFilter;
import samplerSorter.util.ToastMessage;

public class SSUI extends JFrame {

	static String TAG = "RunSS";

	private JPanel contentPane;

	private JButton btnSelectFolder;
	private JCheckBox chckbxIncludeSubfoldersInSearch;
	private LogUI panel;
	private JButton btnEditMacros;

	private MacroEditor macroEditor = new MacroEditor();

	static JPanel columnpanel = new JPanel();
	static JPanel borderlaoutpanel;
	public static JScrollPane scrollPane;

	private static ArrayList<SoundPanel> allSoundPanels = new ArrayList<SoundPanel>();
	public static ArrayList<SoundPanel> selectedSoundPanels = new ArrayList<SoundPanel>();

	static AudioPlayer audioPlayer = new AudioPlayer();

	public static GlobalKeyListener globalKeyListenner = new GlobalKeyListener();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SSUI frame = new SSUI();
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
	public SSUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		setResizable(false);

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			Logger.logError(TAG, "Could not set look and feel", e);
		}

		setLocationRelativeTo(null); //middle of the screen
		setTitle("Sampler Sorter | Ultimate Edition");
		setBounds(100, 100, 633, 387);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		new Thread("Image loader") {
			public void run() {
				try {
					setIconImage(new ImageIcon(ImageIO.read(new URL("https://i.imgur.com/ioE0Nrf.png"))).getImage());
				} catch (IOException e2) {
					Logger.logError(TAG, "Error with getting the icon", e2);
					e2.printStackTrace();
				}

				Logger.logInfo(TAG, "Loaded external icon");

			}
		}.start();

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 629, 353);
		getContentPane().add(tabbedPane);

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

				ToastMessage.makeToast(borderlaoutpanel, "MHM MHM!", 3000, false);

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

		borderlaoutpanel.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println("Pressed " + e.getKeyChar());
			}
		});

		borderlaoutpanel.setFocusable(true);
		borderlaoutpanel.requestFocusInWindow();

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

		globalKeyListenner.init();

	}

	private boolean isListenningForKeyInputs() {
		System.out.println(macroEditor.isVisible);

		if (!macroEditor.isVisible) {
			return true;
		} else {
			return false;
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

	public static void soundPanelIsClicked(SoundPanel me) {

		String cTAG = TAG + ", soundPanelIsClicked";

		System.out.println("                .                 ");

		if (globalKeyListenner.shiftIsPressed()) { //multi selectionA
			Logger.logInfo(cTAG, "Shift is pressed");

			if (selectedSoundPanels.size() > 1) {

				Logger.logInfo(cTAG, "selectedSoundPanels.size() > 1");

				int anchorPoint = 0; //the least high in the list selected (used to -> from select)

				for (SoundPanel p : allSoundPanels) {
					if (selectedSoundPanels.contains(p)) {
						Logger.logInfo(cTAG, "selectedSoundPanels.contains(p)");

						anchorPoint = allSoundPanels.indexOf(p);
						break;
					}
				}

				int targetPoint = allSoundPanels.indexOf(me);

				Logger.logInfo(cTAG, "targetPoint > anchorPoint : " + (targetPoint > anchorPoint));

				if (targetPoint > anchorPoint) {

					for (int i = 0; i <= targetPoint; i++) {
						allSoundPanels.get(i).setSelected(true);
					}

				} else if (targetPoint < anchorPoint) {

					for (int i = 0; i >= targetPoint; i++) {
						allSoundPanels.get(i).setSelected(true);
					}

				}

			} else { //its equal or smaller than 1

				basicSelect(cTAG, me);

			}

		} else {

			basicSelect(cTAG, me);
		}

	}

	private static void basicSelect(String cTAG, SoundPanel me) {
		Logger.logInfo(cTAG, "Shift is not clicked");

		if (selectedSoundPanels.contains(me)) {
			Logger.logInfo(cTAG, "selectedSoundPanels contains me");

			selectedSoundPanels.remove(me);
			me.setSelected(false);
		} else {
			Logger.logInfo(cTAG, "selectedSoundPanels does not contain me");

			for (SoundPanel s : selectedSoundPanels) {
				s.setSelected(false);
			}

			selectedSoundPanels.clear(); //select only 1 at a time

			//

			selectedSoundPanels.add(me);
			me.setSelected(true);
		}
	}

}
