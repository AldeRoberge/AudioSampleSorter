package ass.keyboard.macro;

import ass.keyboard.key.GlobalKeyListener;
import ass.keyboard.macro.edit.MacroEditorUI;
import ass.keyboard.macro.list.MacroListUI;
import logger.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * MacroEditor is the head of the Macro movement
 * It holds reference to the MacroLoader, MacroListUI and MacroEditorUI
 * 
 * @author 4LDE
 *
 */

public class MacroEditor extends JFrame {

	private static final String TAG = "MacroEditor";

	public MacroListUI macroListUI = new MacroListUI(this);
	public MacroEditorUI macroEditorUI = new MacroEditorUI(this);

	public MacroLoader macroLoader = new MacroLoader(this);

	private Image macroEditIcon = Toolkit.getDefaultToolkit().getImage(MacroEditor.class.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Indent-Black@2x.png"));
	private Image macroListIcon = Toolkit.getDefaultToolkit().getImage(MacroEditor.class.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Justify-Black.png"));

	public GlobalKeyListener globalKeyListener;

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

		setContentPane(macroEditorUI);

		macroListUI.onHide();

		macroEditorUI.onShow();
		macroEditorUI.clearActionEditPanels(); //Clear all 'action edit panels' before adding the new ones
		macroEditorUI.changeKeyBindToEdit(keyBindToEdit);
	}

	/**
	 * Used by RunSS -> new MacroEditor() -> initialize()
	 */
	public void showMacroEditUI() {

		changeTitle("List of Macros");

		setContentPane(macroListUI);

		macroListUI.onShow();
		macroEditorUI.onHide();

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

		globalKeyListener = GlobalKeyListener.get();
		globalKeyListener.init(this);

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

	public ArrayList<MacroAction> getAllMacroActions() {
		return macroLoader.macroActions;
	}

}
