package ass.keyboard.macro;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.function.Consumer;

import alde.commons.util.file.ObjectSerializer;
import ass.action.file.DeleteAction;
import ass.action.file.RenameAction;
import ass.action.interfaces.Action;
import ass.action.ui.OpenContainingFolderAction;
import ass.action.ui.RemoveSelectedFilesAction;
import ass.action.ui.SimpleUIAction;
import ass.keyboard.key.Key;
import constants.icons.iconChooser.Icons;
import constants.library.ASSLibraryManager;

public class MacroLoader {

	/**
	 * Macro actions
	 */
	public ArrayList<MacroAction> macroActions = new ArrayList<>();

	/**
	 * Macro editor UI (edit and create custom macros)
	 */
	private MacroEditor macroEditor;

	/**
	 * Object serializer to store list of MacroActions
	 */
	private ObjectSerializer<ArrayList<MacroAction>> macroSerializer = new ObjectSerializer<ArrayList<MacroAction>>(
			ASSLibraryManager.getMacroFile());

	public void serialise() {
		macroSerializer.set(macroActions);
	}

	public MacroLoader(MacroEditor macroEditor) {
		this.macroEditor = macroEditor;


		if (!macroSerializer.isNull()) { // Restore macros from file

			if (macroSerializer.get() != null) { //null might be caused if we remove all the macroactions
				for (MacroAction tmp : macroSerializer.get()) {
					addNewMacro(tmp);
				}
			}

		} else { //Create default macros
			populateDefaultMacros();
		}

	}

	private void populateDefaultMacros() {
		addNewMacro(new MacroAction("Rename", Icons.PENCIL, new Key(KeyEvent.VK_R), new RenameAction(), true, true));
		addNewMacro(new MacroAction("Remove", Icons.FOLDER_MINUS, new Key(KeyEvent.VK_BACK_SPACE),
				new RemoveSelectedFilesAction(), true, true));
		addNewMacro(
				new MacroAction("Delete", Icons.TRASH, new Key(KeyEvent.VK_DELETE), new DeleteAction(), true, true));
		addNewMacro(new MacroAction("Opens the containing folder", Icons.OPEN_FOLDER, new Key(KeyEvent.VK_ENTER),
				new OpenContainingFolderAction(), true, true));

		addNewMacro(new MacroAction("Show Credits", Icons.ABOUT, new Key(KeyEvent.VK_F1), SimpleUIAction.SHOW_CREDITS,
				false, false));
		addNewMacro(new MacroAction("Edit Macros", Icons.MACROS, new Key(KeyEvent.VK_F2), SimpleUIAction.SHOW_MACRO,
				false, false));
		addNewMacro(new MacroAction("Edit Settings", Icons.SETTINGS, new Key(KeyEvent.VK_F3),
				SimpleUIAction.SHOW_SETTINGS, false, false));
		addNewMacro(new MacroAction("Show Logger", Icons.LOGGER, new Key(KeyEvent.VK_F4), SimpleUIAction.SHOW_LOGGER,
				false, false));
	}

	public void addNewMacro(MacroAction tmp) {
		macroActions.add(tmp);
		macroEditor.macroListUI.addKeyBindInfoPanel(tmp);

		serialise();

		tellMacroChanged();
	}

	public void restoreDefaultMacros() {
		macroActions.clear();
		macroEditor.removeAll();
		macroEditor.macroListUI.removeAllPanels();

		populateDefaultMacros();
	}

	public void removeMacro(MacroAction keyBind) {
		macroActions.remove(keyBind);

		serialise();

		tellMacroChanged();
	}


	private ArrayList<Consumer<ArrayList<MacroAction>>> listeningForMacroChanges = new ArrayList<>();

	public void registerListeningForMacroChanges(Consumer<ArrayList<MacroAction>> l) {
		listeningForMacroChanges.add(l);

	}

	public void tellMacroChanged() {
		for (Consumer<ArrayList<MacroAction>> l : listeningForMacroChanges) {
			l.accept(macroActions);
		}
	}

	public void filesChanged(int amountOfSelectedFiles) {

		for (MacroAction ma : macroActions) {

			boolean canBePerformed = true;

			for (Action a : ma.actionsToPerform) {
				if (!a.getPolicy().canBePerformedOnFiles(amountOfSelectedFiles)) {
					canBePerformed = false;
					break;
				}
			}

			ma.isEnabled = canBePerformed;

		}

		tellMacroChanged();
	}

}
