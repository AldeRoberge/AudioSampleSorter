package samplerSorter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;

import samplerSorter.UI.Sorter;
import samplerSorter.UI.other.Container;
import samplerSorter.UI.other.CreditsPanel;
import samplerSorter.actions.ActionManager;
import samplerSorter.audioplayer.SpectrumTimeAnalyzer;
import samplerSorter.logger.LogUI;
import samplerSorter.logger.Logger;
import samplerSorter.macro.MacroEditor;
import samplerSorter.properties.Properties;
import samplerSorter.properties.SettingsUI;
import samplerSorter.util.Icons;

public class SamplerSorter extends JFrame {

	protected static final String TAG = "SamplerSorter";
	public Sorter sorter = new Sorter(); //Formelly known as SSUI

	private static MacroEditor macroEditor = new MacroEditor();

	public Container console = new Container("Console", Icons.CONSOLE.getImage(), new LogUI(), true);
	public Container settings = new Container("Settings", Icons.SETTINGS.getImage(), new SettingsUI(sorter), false);
	public Container credits = new Container("Credits", Icons.ABOUT.getImage(), new CreditsPanel(), true);

	/** 
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SamplerSorter frame = new SamplerSorter();
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
	public SamplerSorter() {
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

		//Set icon

		new Thread("Online image loader") {
			public void run() {
				try {
					setIconImage(new ImageIcon(ImageIO.read(new URL("https://i.imgur.com/ioE0Nrf.png"))).getImage());
				} catch (IOException e2) {
					Logger.logError(TAG, "Error with getting the external icon", e2);
					e2.printStackTrace();
				}

			}
		}.start();

		//

		//

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
				sorter.openImportDialog();
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

		SpectrumTimeAnalyzer visualiserPanel = sorter.audioPlayer.getVisualizer().analyzer;
		splitPane.setRightComponent(visualiserPanel);

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

	public void showCreditsUI() {
		credits.setVisible(true);
	}

	public void showEditMacrosUI() {
		macroEditor.setVisible(true);
	}

	public void showSettings() {
		settings.setVisible(true);
	}

	public void showConsoleUI() {
		console.setVisible(true);
	}
}
