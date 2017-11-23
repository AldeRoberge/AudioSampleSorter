package ass;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
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

import ass.file.FileManager;
import ass.keyboard.action.interfaces.UIAction;
import ass.keyboard.macro.MacroEditor;
import ass.ui.CreditsUI;
import ass.ui.SettingsUI;
import constants.Constants;
import constants.icons.Icons;
import constants.property.Properties;
import logger.LogUI;
import logger.Logger;
import ui.BasicContainer;
import ui.MiddleOfTheScreen;

public class ASS extends JFrame {

	private static final String TAG = Constants.SOFTWARE_NAME;

	public FileManager fMan = new FileManager();

	private MacroEditor macroEditor;

	private BasicContainer logger = new BasicContainer("Log", Icons.LOGGER.getImage(), new LogUI(), true);
	private BasicContainer settings  = new BasicContainer("Settings", Icons.SETTINGS.getImage(), new SettingsUI(fMan), false);
	private BasicContainer credits = new BasicContainer("Credits", Icons.ABOUT.getImage(), new CreditsUI(), true);

	/**
	 * Create the frame.
	 */
	public ASS() {

		FileActionManager.fMan = fMan;

		UIAction.ASS = this;

		macroEditor = new MacroEditor();

		macroEditor.macroLoader.registerWaitingForMacroChanges(fMan);

		//Manually trigger it to populate fMan and toolBar
		macroEditor.macroLoader.tellMacroChanged();

		//

		System.setProperty("sun.awt.noerasebackground", "true"); //Suposed to reduce flicker on manual window resize

		setResizable(true);
		setBackground(Color.WHITE);
		setTitle(Constants.SOFTWARE_NAME);
		setBounds(100, 100, 655, 493);
		setSize(new Dimension(605, 500));
		setIconImage(Icons.SOFTWARE_ICON.getImage());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocation(MiddleOfTheScreen.getMiddleOfScreenLocationFor(this));

		try {

			UIManager.getLookAndFeelDefaults().put("defaultFont", new Font("Times New Roman", Font.BOLD, 14));
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
					int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?", "Exit?", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
							Icons.ABOUT.getImageIcon(), ObjButtons, ObjButtons[1]);
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
				fMan.showFileImporter(true, true);
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

		JMenuItem mntmConsole = new JMenuItem(new AbstractAction("Show Log") {
			public void actionPerformed(ActionEvent e) {
				showLogger(true, true);
			}
		});
		mntmConsole.setIcon(Icons.LOGGER.getImageIcon());
		mnHelp.add(mntmConsole);

		// Help : About

		JMenuItem mntmAbout = new JMenuItem(new AbstractAction("About") {
			public void actionPerformed(ActionEvent e) {
				showCredits(true, true);
			}
		});
		mntmAbout.setIcon(Icons.ABOUT.getImageIcon());
		mnHelp.add(mntmAbout);

		getContentPane().add(fMan.fileVisualiser, BorderLayout.SOUTH);

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

		JPanel container = new JPanel();

		splitPane.setBottomComponent(container);
		container.setLayout(new BorderLayout(0, 0));

		container.add(fMan.fileVisualiser.getPlayer(), BorderLayout.CENTER);

		getContentPane().add(splitPane, BorderLayout.CENTER);

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
				setIsListenningForInputs(true);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				setIsListenningForInputs(false);
			}

			@Override
			public void windowIconified(WindowEvent e) {
				setIsListenningForInputs(false);
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				setIsListenningForInputs(true);
			}

			@Override
			public void windowActivated(WindowEvent e) {
				setIsListenningForInputs(true);
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				setIsListenningForInputs(false);
			}

			@Override
			public void windowGainedFocus(WindowEvent e) {
				setIsListenningForInputs(true);
			}

			@Override
			public void windowLostFocus(WindowEvent e) {
				setIsListenningForInputs(false);

			}

			private void setIsListenningForInputs(boolean b) {
				macroEditor.globalKeyListener.isListenningForInputs = b;
			}
		});

		if (Properties.FIRST_LAUNCH.getValueAsBoolean()) {
			Properties.FIRST_LAUNCH.setNewValue(false);
			showCredits(true, true);
		}
	}

	public boolean showFileImporter(boolean forceState, boolean newState) {
		return fMan.showFileImporter(forceState, newState);
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

	public boolean showLogger(boolean forceState, boolean newState) {
		return toggleVisibility(logger, forceState, newState);
	}

	/**
	 * @param c Component to toggleVisibility on
	 * @param forceState If we should force the new visiblity state
	 * @param newState New state (true == set to visible)
	 * @return returns the new state of the component (true == now visible)
	 */
	boolean toggleVisibility(Component c, boolean forceState, boolean newState) {

		if (forceState) {
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

	/**
	 * @return true if its paused
	 */
	public boolean resumeOrPauseSound() {
		return fMan.fileVisualiser.getAudioPlayer().resumeOrPause();
	}

}
