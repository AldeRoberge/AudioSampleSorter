package sorter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import action.ActionManager;
import constants.Constants;
import constants.Icons;
import logger.LogUI;
import logger.Logger;
import macro.MacroEditor;
import property.Properties;
import property.SettingsUI;
import sorter.fileImport.FileImporter;
import sorter.other.Container;
import sorter.other.CreditsPanel;
import util.ui.MiddleOfTheScreen;

public class SorterUI extends JFrame {

	private static final String TAG = "SampleSorter";
	public Sorter sorter = new Sorter(); //Formelly known as SSUI

	private static MacroEditor macroEditor;

	private Container console = new Container("Console", Icons.CONSOLE.getImage(), new LogUI(), true);
	private Container settings = new Container("Settings", Icons.SETTINGS.getImage(), new SettingsUI(sorter), false);
	private Container credits = new Container("Credits", Icons.ABOUT.getImage(), new CreditsPanel(), true);

	private FileImporter fileImporter = new FileImporter(sorter);

	JMenuItem ascending;
	JMenuItem descending;

	/**
	 * Create the frame.
	 */
	public SorterUI() {

		ActionManager.init(this); //init actions
		macroEditor = new MacroEditor(); //we must do this after initialising actions because it uses Actions in a combobox

		BorderLayout borderLayout = (BorderLayout) getContentPane().getLayout();
		borderLayout.setVgap(5);
		borderLayout.setHgap(5);
		setResizable(false);

		setBackground(Color.WHITE);

		setTitle("SampleSorter | Ultimate");

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				String ObjButtons[] = { "Yes", "No" };
				int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?", "Exit",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, Icons.QUESTION, ObjButtons,
						ObjButtons[1]);
				if (PromptResult == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});

		setBounds(100, 100, 655, 493);
		setLocation(MiddleOfTheScreen.getLocationFor(this));

		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			if (info.getName().equals("Nimbus")) {
				Logger.logInfo(TAG, "Updating theme...");

				try {
					UIManager.setLookAndFeel(info.getClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					Logger.logError(TAG, "Error while setting new theme");
					e.printStackTrace();
				}

				// Refresh the UI

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

		// File, Import

		JMenuItem mntmImport = new JMenuItem(new AbstractAction("Import audio files...") {
			public void actionPerformed(ActionEvent e) {
				Logger.logInfo(TAG, "Importing...");
				fileImporter.setVisible(true);
			}
		});

		mntmImport.setIcon(Icons.IMPORT);
		mnFile.add(mntmImport);

		// File (Separator)

		mnFile.addSeparator();

		//File, Exit

		JMenuItem mnExit = new JMenuItem(new AbstractAction("Exit") {
			public void actionPerformed(ActionEvent e) {
				Logger.logInfo(TAG, "Exiting...");
				System.exit(0);
			}
		});
		mnExit.setIcon(Icons.EXIT);
		mnFile.add(mnExit);

		//Edit

		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);

		JMenuItem mntmMacros = new JMenuItem(new AbstractAction("Edit Macros") {
			public void actionPerformed(ActionEvent e) {
				showEditMacros(true);
			}

		});
		mntmMacros.setIcon(Icons.MACROS);
		mnEdit.add(mntmMacros);

		//Edit, Settings

		JMenuItem mntmSettings = new JMenuItem(new AbstractAction("Settings") {
			public void actionPerformed(ActionEvent e) {
				showSettings(true);
			}

		});
		mntmSettings.setIcon(Icons.SETTINGS);
		mnEdit.add(mntmSettings);

		//View

		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);

		//Help

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmConsole = new JMenuItem(new AbstractAction("Debugger Console") {
			public void actionPerformed(ActionEvent e) {
				showConsole(true);
			}
		});
		mntmConsole.setIcon(Icons.CONSOLE);
		mnHelp.add(mntmConsole);

		// Help : About

		JMenuItem mntmAbout = new JMenuItem(new AbstractAction("About") {
			public void actionPerformed(ActionEvent e) {
				showCredits(true);
			}

		});
		mntmAbout.setIcon(Icons.ABOUT);
		mnHelp.add(mntmAbout);

		/** End of menus */

		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerLocation(Properties.HORIZONTAL_SPLITPANE_DIVIDERLOCATION.getValueAsInt());

		splitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent pce) {
				Properties.HORIZONTAL_SPLITPANE_DIVIDERLOCATION
						.setNewValue((((Integer) pce.getNewValue()).intValue()) + "");
			}
		});

		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		getContentPane().add(splitPane, BorderLayout.CENTER);

		splitPane.setLeftComponent(sorter);

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
				sorter.globalKeyListener.isListenningForInputs = true;
			}

			@Override
			public void windowClosed(WindowEvent e) {
				sorter.globalKeyListener.isListenningForInputs = false;
			}

			@Override
			public void windowIconified(WindowEvent e) {
				sorter.globalKeyListener.isListenningForInputs = false;
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				sorter.globalKeyListener.isListenningForInputs = true;
			}

			@Override
			public void windowActivated(WindowEvent e) {
				sorter.globalKeyListener.isListenningForInputs = true;
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				sorter.globalKeyListener.isListenningForInputs = false;
			}

			@Override
			public void windowGainedFocus(WindowEvent e) {
				sorter.globalKeyListener.isListenningForInputs = true;
			}

			@Override
			public void windowLostFocus(WindowEvent e) {
				sorter.globalKeyListener.isListenningForInputs = false;
			}

		});

		JPanel panel = new JPanel();
		panel.setBackground(Constants.SICK_PURPLE);
		splitPane.setRightComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));

		panel.add(sorter.audioPlayer.getVisualizer().analyzer, BorderLayout.CENTER);

		if (Properties.FIRST_LAUNCH.getValueAsBoolean()) {
			Properties.FIRST_LAUNCH.setNewValue(false);
			showCredits(true);
		}

	}

	void showCredits(boolean show) {
		credits.setVisible(show);
	}

	public void showEditMacros(boolean show) {
		macroEditor.setVisible(show);
	}

	public void showSettings(boolean show) {
		settings.setVisible(show);
	}

	void showConsole(boolean show) {
		console.setVisible(show);
	}
}
