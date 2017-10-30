package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Window;
import java.awt.event.ActionEvent;
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
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

import GUI.other.Container;
import GUI.other.CreditsPanel;
import GUI.soundPanelSorter.Sort;
import GUI.soundPanelSorter.SoundPanelsSorter;
import action.ActionManager;
import constants.Icons;
import logger.LogUI;
import logger.Logger;
import macro.MacroEditor;
import property.Properties;
import property.SettingsUI;
import util.ui.MiddleOfTheScreen;

public class SorterUI extends JFrame {

	private static final String TAG = "SamplerSorter";
	public Sorter sorter = new Sorter(); //Formelly known as SSUI

	private static MacroEditor macroEditor;

	private Container console = new Container("Console", Icons.CONSOLE.getImage(), new LogUI(), true);
	private Container settings = new Container("Settings", Icons.SETTINGS.getImage(), new SettingsUI(sorter), false);
	private Container credits = new Container("Credits", Icons.ABOUT.getImage(), new CreditsPanel(), true);

	public static Container fileImporterIcon = new Container("This should be used by fileChoosers to pass the icon",
			Icons.IMPORT.getImage(), null, false);

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
		ActionManager.init(this); //init actions
		macroEditor = new MacroEditor(); //we must do this after initialising actions because it uses Actions in a combobox

		BorderLayout borderLayout = (BorderLayout) getContentPane().getLayout();
		borderLayout.setVgap(5);
		borderLayout.setHgap(5);
		setResizable(false);

		setBackground(Color.WHITE);

		setTitle("SamplerSorter | Ultimate");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 655, 493);

		//DEAD SMACK IN THE MIDDLE OF THE SCREEEN

		setLocation(MiddleOfTheScreen.getLocationFor(this));

		//

		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			if (info.getName().equals("Nimbus")) {
				System.out.println("Updating theme...");

				try {
					UIManager.setLookAndFeel(info.getClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					Logger.logError(TAG, "Error while setting new theme");
					e.printStackTrace();
				}

				// Refresh the UI
				sorter.refreshInfoPanels();

				//This is experimental TODO
				for (Window w : SorterUI.getOwnerlessWindows()) {
					SwingUtilities.updateComponentTreeUI(w);
				}

			}
		}

		//

		setSize(new Dimension(605, 500));
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

		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);

		JMenu mnSortBy = new JMenu("Sort by");
		mnView.add(mnSortBy);

		for (Sort s : SoundPanelsSorter.sortByTypes) {

			JMenuItem sortByMenuItem = new JMenuItem(new AbstractAction(s.name) {
				public void actionPerformed(ActionEvent e) {
					sorter.soundPanelSorter.sortBy(s);
				}
			});

			mnSortBy.add(sortByMenuItem);
		}

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
				sorter.god.globalKeyListener.isListenningForGlobalInputs = true;
			}

			@Override
			public void windowClosed(WindowEvent e) {
				sorter.god.globalKeyListener.isListenningForGlobalInputs = false;
			}

			@Override
			public void windowIconified(WindowEvent e) {
				sorter.god.globalKeyListener.isListenningForGlobalInputs = false;
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				sorter.god.globalKeyListener.isListenningForGlobalInputs = true;
			}

			@Override
			public void windowActivated(WindowEvent e) {
				sorter.god.globalKeyListener.isListenningForGlobalInputs = true;
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				sorter.god.globalKeyListener.isListenningForGlobalInputs = false;
			}

			@Override
			public void windowGainedFocus(WindowEvent e) {
				sorter.god.globalKeyListener.isListenningForGlobalInputs = true;
			}

			@Override
			public void windowLostFocus(WindowEvent e) {
				sorter.god.globalKeyListener.isListenningForGlobalInputs = false;
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
