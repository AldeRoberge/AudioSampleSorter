package ass.keyboard.macro;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ass.keyboard.key.GlobalKeyListener;
import ass.keyboard.macro.edit.MacroEditorUI;
import ass.keyboard.macro.list.MacroListUI;

/**
 * MacroEditor is the head of the Macro movement
 * It holds reference to the MacroLoader, MacroListUI and MacroEditorUI
 * 
 * @author 4LDE
 *
 */

public class MacroEditor extends JFrame {

	static Logger log = LoggerFactory.getLogger(MacroEditor.class);

	public MacroListUI macroListUI = new MacroListUI(this);
	public MacroEditorUI macroEditorUI = new MacroEditorUI(this);

	public MacroLoader macroLoader = new MacroLoader(this);

	//@formatter:off
	private Image macroEditIcon = Toolkit.getDefaultToolkit().getImage(MacroEditor.class.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Indent-Black@2x.png"));
	private Image macroListIcon = Toolkit.getDefaultToolkit().getImage(MacroEditor.class.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Justify-Black.png"));
	//@formatter:on

	/**
	 * Used by KeyBindUI press 'Add' -> MacroEditor change view to show -> NewMacroUI
	 */
	public void showMacroListUI(MacroAction keyBindToEdit) {

		if (keyBindToEdit == null) {
			setTitle("New Macro");
			keyBindToEdit = new MacroAction("Title");
		} else {
			setTitle("Edit Macro");
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

		setTitle("List of Macros");

		setContentPane(macroListUI);

		macroListUI.onShow();
		macroEditorUI.onHide();

		setIconImage(macroListIcon);
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
		log.info("Launching...");

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
