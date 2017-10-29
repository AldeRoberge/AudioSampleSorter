package samplerSorter.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;

import samplerSorter.GUI.other.Container;
import samplerSorter.GUI.other.CreditsPanel;
import samplerSorter.action.ActionManager;
import samplerSorter.constants.Icons;
import samplerSorter.logger.LogUI;
import samplerSorter.logger.Logger;
import samplerSorter.macro.MacroEditor;
import samplerSorter.property.Properties;
import samplerSorter.property.SettingsUI;

public class SorterUI extends JFrame {

	private static final String TAG = "SamplerSorter";
	public Sorter sorter = new Sorter(); //Formelly known as SSUI

	private static MacroEditor macroEditor = new MacroEditor();

	private Container console = new Container("Console", Icons.CONSOLE.getImage(), new LogUI(), true);
	private Container settings = new Container("Settings", Icons.SETTINGS.getImage(), new SettingsUI(sorter), false);
	private Container credits = new Container("Credits", Icons.ABOUT.getImage(), new CreditsPanel(), true);

	private FileImporter fileImporter = new FileImporter(sorter);

	/** 
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SorterUI frame = new SorterUI();
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
	private SorterUI() {
		ActionManager.init();

		setBackground(Color.WHITE);

		setTitle("SamplerSorter | Ultimate");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 655, 493);

		Dimension defaultDimensions = new Dimension(620, 300);

		setMinimumSize(defaultDimensions);
		setPreferredSize(defaultDimensions);

		Dimension maximumDimensions = new Dimension(700, 400);

		setMaximumSize(maximumDimensions);
		setSize(maximumDimensions);
		revalidate();

		setIconImage(Icons.SOFTWARE_ICON.getImage());

		/** Menu bar */

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		/** File */

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		// File : Import

		JMenuItem mntmImport = new JMenuItem(new AbstractAction("Import...") {
			public void actionPerformed(ActionEvent e) {
				Logger.logInfo(TAG, "Importing...");
				fileImporter.setVisible(true);
			}
		});

		mntmImport.setIcon(Icons.IMPORT);
		mnFile.add(mntmImport);

		// File : Separator

		mnFile.addSeparator();

		// File : Exit

		JMenuItem mnExit = new JMenuItem(new AbstractAction("Exit") {
			public void actionPerformed(ActionEvent e) {
				Logger.logInfo(TAG, "Exiting...");
				System.exit(0);
			}
		});
		mnExit.setIcon(Icons.EXIT);
		mnFile.add(mnExit);

		/** Edit */

		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);

		JMenuItem mntmMacros = new JMenuItem(new AbstractAction("Edit Macros") {
			public void actionPerformed(ActionEvent e) {
				showEditMacrosUI();
			}

		});
		mntmMacros.setIcon(Icons.MACROS);
		mnEdit.add(mntmMacros);

		// Edit : Settings

		JMenuItem mntmSettings = new JMenuItem(new AbstractAction("Settings") {
			public void actionPerformed(ActionEvent e) {
				showSettings();
			}

		});
		mntmSettings.setIcon(Icons.SETTINGS);
		mnEdit.add(mntmSettings);

		/** Help */

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmConsole = new JMenuItem(new AbstractAction("Console") {
			public void actionPerformed(ActionEvent e) {
				showConsoleUI();
			}
		});
		mntmConsole.setIcon(Icons.CONSOLE);
		mnHelp.add(mntmConsole);

		// Help : About

		JMenuItem mntmAbout = new JMenuItem(new AbstractAction("About") {
			public void actionPerformed(ActionEvent e) {
				showCreditsUI();
			}

		});
		mntmAbout.setIcon(Icons.ABOUT);
		mnHelp.add(mntmAbout);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerLocation(Properties.SPLITPANE_DIVIDERLOCATION.getValueAsInt());

		splitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent pce) {
				Properties.SPLITPANE_DIVIDERLOCATION.setNewValue((((Integer) pce.getNewValue()).intValue()) + "");
			}
		});

		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		getContentPane().add(splitPane, BorderLayout.CENTER);

		splitPane.setLeftComponent(sorter);

		splitPane.setRightComponent(sorter.audioPlayer.getVisualizer().analyzer);

		/** End of menus */

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
				sorter.globalKeyListener.isListenningForGlobalInputs = true;
			}

			@Override
			public void windowClosed(WindowEvent e) {
				sorter.globalKeyListener.isListenningForGlobalInputs = false;
			}

			@Override
			public void windowIconified(WindowEvent e) {
				sorter.globalKeyListener.isListenningForGlobalInputs = false;
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				sorter.globalKeyListener.isListenningForGlobalInputs = true;
			}

			@Override
			public void windowActivated(WindowEvent e) {
				sorter.globalKeyListener.isListenningForGlobalInputs = true;
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				sorter.globalKeyListener.isListenningForGlobalInputs = false;
			}

			@Override
			public void windowGainedFocus(WindowEvent e) {
				sorter.globalKeyListener.isListenningForGlobalInputs = true;
			}

			@Override
			public void windowLostFocus(WindowEvent e) {
				sorter.globalKeyListener.isListenningForGlobalInputs = false;
			}

		});

		sorter.scrollPane.setBounds(0, 0, 637, 271);

		/**getContentPane().add(sorter);
		//setContentPane(sorter);
		sorter.setLayout(null);*/

	}

	void showCreditsUI() {
		credits.setVisible(true);
	}

	public void showEditMacrosUI() {
		macroEditor.setVisible(true);
	}

	public void showSettings() {
		settings.setVisible(true);
	}

	void showConsoleUI() {
		console.setVisible(true);
	}
}
