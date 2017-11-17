package ass.macro.ui;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import ass.ui.ToolBar;
import logger.Logger;
import ass.macro.MacroLoader;
import ass.macro.ui.edit.MacroEditorUI;
import ass.macro.ui.list.MacroListUI;

/**
 * This is the head of the Macro movement
 * It holds reference to the MacroLoader, MacroListUI and MacroEditorUI
 * 
 * @author 4LDE
 *
 */

public class MacroEditor extends JFrame {

	private static final String TAG = "MacroEditor";

	public MacroListUI macroListUI = new MacroListUI(this);
	public MacroEditorUI macroEditPanel = new MacroEditorUI(this);

	public MacroLoader macroLoader = new MacroLoader(this);

	private Image macroEditIcon = Toolkit.getDefaultToolkit().getImage(MacroEditor.class.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Indent-Black@2x.png"));
	private Image macroListIcon = Toolkit.getDefaultToolkit().getImage(MacroEditor.class.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Justify-Black.png"));

	private ToolBar toolBar = new ToolBar(this);

	/**
	 * Used by KeyBindUI press 'Add' -> MacroEditor change view to show -> NewMacroUI
	 */
	public void showMacroListUI(MacroAction keyBindToEdit) {

		if (keyBindToEdit != null) {
			changeTitle("Edit Macro");
		} else {
			changeTitle("New Macro");
		}

		setIconImage(macroEditIcon);

		setContentPane(macroEditPanel);

		macroListUI.onHide();

		macroEditPanel.onShow();
		macroEditPanel.clearActionEditPanels(); //Clear all 'action edit panels' before adding the new ones
		macroEditPanel.changeKeyBindToEdit(keyBindToEdit);
	}

	/**
	 * Used by RunSS -> new MacroEditor() -> initialize()
	 */
	public void showMacroEditUI() {

		changeTitle("List of Macros");

		setContentPane(macroListUI);

		macroListUI.onShow();
		macroEditPanel.onHide();

		setIconImage(macroListIcon);
	}

	/**
	 * Used by RunSS -> new MacroEditor() -> initialize()
	 */
	void changeTitle(String newTitle) {
		setTitle(newTitle);
	}

	/**
	 * Create the application.
	 */
	public MacroEditor() {
		initialize();
	}

	/**
	 * Initialize the contents of the 
	 */
	private void initialize() {
		Logger.logInfo(TAG, "Launching...");

		setResizable(false);
		setBounds(100, 100, 360, 350);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null); //middle of the screen

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});

		showMacroEditUI();

	}

	public ToolBar getToolBar() {
		return toolBar;
	}

}
