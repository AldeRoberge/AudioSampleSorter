package sorter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;

import global.Constants;
import global.icons.Icons;
import global.logger.LogUI;
import global.logger.Logger;
import keybinds.action.ActionManager;
import keybinds.key.GlobalKeyListener;
import keybinds.macro.MacroEditor;
import property.Properties;
import property.SettingsUI;
import sorter.ui.Container;
import sorter.ui.CreditsPanel;
import sorter.ui.FileManager;
import sorter.ui.FileVisualiser;
import sorter.ui.ToolBar;
import sorter.ui.fileImporter.FileImporter;
import ui.MiddleOfTheScreen;

public class SorterUI extends JFrame {

	private static final String TAG = Constants.SOFTWARE_NAME;

	public MacroEditor macroEditor;

	public FileVisualiser openFileManager = new FileVisualiser();

	private Container console = new Container("Console", Icons.CONSOLE.getImage(), new LogUI(), true);
	private Container settings = new Container("Settings", Icons.SETTINGS.getImage(), new SettingsUI(openFileManager.getAudioPlayer()), false);
	private Container credits = new Container("Credits", Icons.ABOUT.getImage(), new CreditsPanel(), true);

	public FileManager fMan = new FileManager(openFileManager);

	private FileImporter fileImporter = new FileImporter(fMan);

	public GlobalKeyListener globalKeyListener;

	//Actual UI

	public ToolBar toolBar;

	/**
	 * Create the frame.
	 */
	public SorterUI() {

		ActionManager.init(this); //init actions

		macroEditor = new MacroEditor(); //We do this after initialising ActionManager because it uses its static Actions in a combobox (macroEditUI)

		toolBar = macroEditor.getToolBar();

		//we do this after initialising macroEditor because we use its macroLoader to populate it

		globalKeyListener = GlobalKeyListener.get();
		globalKeyListener.init(this);

		//

		System.setProperty("sun.awt.noerasebackground", "true"); //Suposed to reduce flicker on manual window resize

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

				if (Properties.PROMPT_ON_EXIT.getValueAsBoolean()) {
					String ObjButtons[] = { "Yes", "No" };
					int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?", "Exit?", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, Icons.ABOUT.getImageIcon(), ObjButtons,
							ObjButtons[1]);
					if (PromptResult == JOptionPane.YES_OPTION) {
						System.exit(0);
					}
				} else {
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

		mntmImport.setIcon(Icons.IMPORT.getImageIcon());
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
		mnExit.setIcon(Icons.EXIT.getImageIcon());
		mnFile.add(mnExit);

		//Edit

		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);

		JMenuItem mntmMacros = new JMenuItem(new AbstractAction("Edit Macros") {
			public void actionPerformed(ActionEvent e) {
				showEditMacros(true, true);
			}

		});
		mntmMacros.setIcon(Icons.MACROS.getImageIcon());
		mnEdit.add(mntmMacros);

		//Edit, Settings

		JMenuItem mntmSettings = new JMenuItem(new AbstractAction("Settings") {
			public void actionPerformed(ActionEvent e) {
				showSettings(true, true);
			}

		});
		mntmSettings.setIcon(Icons.SETTINGS.getImageIcon());
		mnEdit.add(mntmSettings);

		//Help

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmConsole = new JMenuItem(new AbstractAction("Debugger Console") {
			public void actionPerformed(ActionEvent e) {
				showConsole(true, true);
			}
		});
		mntmConsole.setIcon(Icons.CONSOLE.getImageIcon());
		mnHelp.add(mntmConsole);

		// Help : About

		JMenuItem mntmAbout = new JMenuItem(new AbstractAction("About") {
			public void actionPerformed(ActionEvent e) {
				showCredits(true, true);
			}
		});
		mntmAbout.setIcon(Icons.ABOUT.getImageIcon());
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
				Properties.HORIZONTAL_SPLITPANE_DIVIDERLOCATION.setNewValue((((Integer) pce.getNewValue()).intValue()) + "");
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

		JScrollPane toolBarContainer = new JScrollPane();
		conta.add(toolBarContainer, BorderLayout.SOUTH);
		toolBarContainer.setViewportView(toolBar);

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

		toolBar.repopulate();

		if (Properties.FIRST_LAUNCH.getValueAsBoolean()) {
			Properties.FIRST_LAUNCH.setNewValue(false);
			showCredits(true, true);
		}
	}

	/**
	 * @param forceState enforce new visibility state (if is set to false, component will switch visibility)
	 * @param newState new state of visiblity (if forceState set to false, newState is invalidated)
	 * @return
	 */
	public boolean showCredits(boolean forceState, boolean newState) {
		return toggleVisibility(credits, forceState, newState);
	}

	public boolean showEditMacros(boolean forceState, boolean newState) {
		return toggleVisibility(macroEditor, forceState, newState);
	}

	public boolean showSettings(boolean forceState, boolean newState) {
		return toggleVisibility(settings, forceState, newState);
	}

	public boolean showConsole(boolean forceState, boolean newState) {
		return toggleVisibility(console, forceState, newState);
	}

	/**
	 * @param c Component to toggleVisibility on
	 * @param forceState If we should force the new visiblity state
	 * @param newState New state (true == set to visible)
	 * @return returns the new state of the component (true == now visible)
	 */
	public boolean toggleVisibility(Component c, boolean forceState, boolean newState) {

		if (forceState == true) {
			c.setVisible(newState);
			return newState;
		} else {
			if (c.isVisible()) {
				c.setVisible(false);
				return false;
			} else {
				c.setVisible(true);
				return true;
			}
		}
	}
}
