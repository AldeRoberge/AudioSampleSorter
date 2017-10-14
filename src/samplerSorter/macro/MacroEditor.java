package samplerSorter.macro;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import samplerSorter.macro.macrolist.MacroListUI;
import samplerSorter.macro.macroedit.MacroEditorUI;
import samplerSorter.logger.Logger;

import java.awt.Toolkit;

/**
 * This is the head of the Macro movement
 * It holds reference to the MacroLoader, MacroListUI and MacroEditorUI
 * 
 * @author 4LDE
 *
 */

public class MacroEditor {

	private static final String TAG = "MacroEditor";

	private JFrame frame;

	public MacroListUI macroListUI = new MacroListUI(this);
	public MacroEditorUI macroEditPanel = new MacroEditorUI(this);

	public MacroLoader macroLoader = new MacroLoader(this);

	Image macroEditIcon = Toolkit.getDefaultToolkit().getImage(
			MacroEditor.class.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Indent-Black@2x.png"));
	Image macroListIcon = Toolkit.getDefaultToolkit().getImage(
			MacroEditor.class.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Justify-Black.png"));

	public static void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MacroEditor window = new MacroEditor();
					//window.frame.setVisible(false); window is off by default
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void setVisible(boolean isVisible) {
		Logger.logInfo(TAG, "MacroEditor set visible to " + isVisible + ".");
		frame.setVisible(isVisible);
	}

	/**
	 * Used by KeyBindUI press 'Add' -> MacroEditor change view to show -> NewMacroUI
	 */
	public void showMacroUI(MacroAction keyBindToEdit) {

		frame.setContentPane(macroEditPanel);

		changeTitle("Macro Editor");

		macroListUI.onHide();

		macroEditPanel.onShow();
		macroEditPanel.clearActionEditPanels(); //Clear all 'action edit panels' before adding the new ones
		macroEditPanel.changeKeyBindToEdit(keyBindToEdit);

		frame.setIconImage(macroEditIcon);
	}

	/**
	 * Used by RunSS -> new MacroEditor() -> initialize()
	 */
	public void showKeyBindPanel() {

		changeTitle("List of Macros");

		frame.setContentPane(macroListUI);

		macroListUI.onShow();
		macroEditPanel.onHide();

		frame.setIconImage(macroListIcon);

	}

	/**
	 * Used by RunSS -> new MacroEditor() -> initialize()
	 */
	public void changeTitle(String newTitle) {
		Logger.logInfo(TAG, "New mode : " + newTitle + ".");
		frame.setTitle(newTitle);
	}

	/**
	 * Create the application.
	 */
	public MacroEditor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Logger.logInfo(TAG, "Launching Macro Editor UI");

		frame = new JFrame();
		frame.setBounds(100, 100, 374, 320);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});

		showKeyBindPanel();
	}

}
