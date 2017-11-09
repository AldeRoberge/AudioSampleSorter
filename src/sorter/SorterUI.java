package sorter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.UIManager;

import action.ActionManager;
import constants.Constants;
import constants.icons.IconLoader;
import constants.icons.Icons;
import key.GlobalKeyListener;
import logger.LogUI;
import logger.Logger;
import macro.MacroEditor;
import property.Properties;
import property.SettingsUI;
import sorter.fileImport.FileImporter;
import sorter.other.Container;
import sorter.other.CreditsPanel;
import util.FileManager;
import util.FileVisualiser;
import util.ToolBar;
import util.ui.MiddleOfTheScreen;

public class SorterUI extends JFrame {

	private static final String TAG = Constants.SOFTWARE_NAME;

	public MacroEditor macroEditor;

	public FileVisualiser openFileManager = new FileVisualiser();

	private Container console = new Container("Console", Icons.CONSOLE.getImage(), new LogUI(), true);
	private Container settings = new Container("Settings", Icons.SETTINGS.getImage(),
			new SettingsUI(openFileManager.getAudioPlayer()), false);
	private Container credits = new Container("Credits", Icons.ABOUT.getImage(), new CreditsPanel(), true);

	public FileManager fMan = new FileManager(openFileManager);

	private FileImporter fileImporter = new FileImporter(fMan);

	public GlobalKeyListener globalKeyListener;

	//Actual UI

	public ToolBar toolBar = new ToolBar();

	/**
	 * Create the frame.
	 */
	public SorterUI() {

		System.setProperty("sun.awt.noerasebackground", "true"); //Suposed to reduce flicker on manual window resize

		IconLoader.init();

		ActionManager.init(this); //init actions

		macroEditor = new MacroEditor(); //we must do this after initialising actions because it uses Actions in a combobox

		//Init children components
		globalKeyListener = GlobalKeyListener.get();
		globalKeyListener.init(this);

		//

		setResizable(true);
		setBackground(Color.WHITE);
		setTitle(Constants.SOFTWARE_NAME);
		setBounds(100, 100, 655, 493);
		setSize(new Dimension(605, 500));
		setIconImage(Icons.SOFTWARE_ICON.getImage());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocation(MiddleOfTheScreen.getLocationFor(this));

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception weTried) {
			weTried.printStackTrace();
		}

		//

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

		getContentPane().add(openFileManager, BorderLayout.SOUTH);

		/** End of menus */

		//

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setResizeWeight(0.90);
		splitPane.setDividerLocation(Properties.HORIZONTAL_SPLITPANE_DIVIDERLOCATION.getValueAsInt());
		splitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent pce) {
				Properties.HORIZONTAL_SPLITPANE_DIVIDERLOCATION
						.setNewValue((((Integer) pce.getNewValue()).intValue()) + "");
			}
		});
		BorderLayout borderLayout = (BorderLayout) fMan.getLayout();
		borderLayout.setVgap(1);
		borderLayout.setHgap(1);

		//fMan
		splitPane.setTopComponent(fMan);

		//

		JPanel conta = new JPanel();

		splitPane.setBottomComponent(conta);
		conta.setLayout(new BorderLayout(0, 0));

		conta.add(toolBar, BorderLayout.SOUTH);

		conta.add(openFileManager.getPlayer(), BorderLayout.CENTER);

		getContentPane().add(splitPane, BorderLayout.CENTER);

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {

				globalKeyListener.isListenningForInputs = true;
			}

			@Override
			public void windowClosed(WindowEvent e) {
				globalKeyListener.isListenningForInputs = false;
			}

			@Override
			public void windowIconified(WindowEvent e) {
				globalKeyListener.isListenningForInputs = false;
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				globalKeyListener.isListenningForInputs = true;
			}

			@Override
			public void windowActivated(WindowEvent e) {
				globalKeyListener.isListenningForInputs = true;
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				globalKeyListener.isListenningForInputs = false;
			}

			@Override
			public void windowGainedFocus(WindowEvent e) {
				globalKeyListener.isListenningForInputs = true;
			}

			@Override
			public void windowLostFocus(WindowEvent e) {
				globalKeyListener.isListenningForInputs = false;
			}
		});

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
