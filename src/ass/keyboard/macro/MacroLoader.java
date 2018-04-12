package ass.keyboard.macro;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import alde.commons.util.file.ObjectSerializer;
import ass.action.DeleteAction;
import ass.action.OpenContainingFolderAction;
import ass.action.RemoveSelectedFilesAction;
import ass.action.RenameAction;
import ass.action.SimpleUIAction;
import ass.action.interfaces.Action;
import ass.file.ListenForSelectedFilesChanges;
import ass.keyboard.key.Key;
import constants.icons.Icons;
import constants.library.LibraryManager;

public class MacroLoader implements ListenForSelectedFilesChanges {

	private static final String TAG = "MacroLoader";

	/**
	 * macroActions is the shortcuts currently in the program
	 * 
	 * (formelly 'actions')
	 */
	public ArrayList<MacroAction> macroActions = new ArrayList<MacroAction>();

	private MacroEditor macroEditor;

	private ObjectSerializer<ArrayList<MacroAction>> macroSerializer = new ObjectSerializer<ArrayList<MacroAction>>(LibraryManager.getMacroFile());

	public MacroLoader(MacroEditor macroEditor) {
		this.macroEditor = macroEditor;

		//restore macros from file

		if (!macroSerializer.isNull()) {

			if (macroSerializer.get() != null) { //null might be caused if we remove all the macroactions
				for (MacroAction tmp : macroSerializer.get()) {
					addNewMacro(tmp);
				}
			}

		} else {
			//Default macro actions

			populateDefaultMacros();

		}

	}

	private void populateDefaultMacros() {
		addNewMacro(new MacroAction("Rename", Icons.PENCIL, new Key(KeyEvent.VK_R), new RenameAction(), true, true));
		addNewMacro(new MacroAction("Remove", Icons.FOLDER_MINUS, new Key(KeyEvent.VK_BACK_SPACE), new RemoveSelectedFilesAction(), true, true));
		addNewMacro(new MacroAction("Delete", Icons.TRASH, new Key(KeyEvent.VK_DELETE), new DeleteAction(), true, true));
		addNewMacro(new MacroAction("Open containing folder", Icons.OPEN_FOLDER, new Key(KeyEvent.VK_ENTER), new OpenContainingFolderAction(), true, true));

		addNewMacro(new MacroAction("Show Credits", Icons.ABOUT, new Key(KeyEvent.VK_F1), SimpleUIAction.SHOW_CREDITS, false, false));
		addNewMacro(new MacroAction("Edit Macros", Icons.MACROS, new Key(KeyEvent.VK_F2), SimpleUIAction.SHOW_MACRO, false, false));
		addNewMacro(new MacroAction("Edit Settings", Icons.SETTINGS, new Key(KeyEvent.VK_F3), SimpleUIAction.SHOW_SETTINGS, false, false));
		addNewMacro(new MacroAction("Show Logger", Icons.LOGGER, new Key(KeyEvent.VK_F4), SimpleUIAction.SHOW_LOGGER, false, false));
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

	private ArrayList<ListenForMacroChanges> waitingForChanges = new ArrayList<>();

	/**
	 * @param an object that wants to know when Macros change (toolbar and menu in manager)
	 */
	public void registerWaitingForMacroChanges(ListenForMacroChanges l) {
		waitingForChanges.add(l);

	}

	public void tellMacroChanged() {
		for (ListenForMacroChanges l : waitingForChanges) {
			l.macroChanged(macroActions);
		}
	}

	public void serialise() {
		macroSerializer.set(macroActions);
	}

	@Override
	public void filesChanged(int amountOfSelectedFiles) {

		for (MacroAction ma : macroActions) {

			boolean canBePerformed = true;

			for (Action a : ma.actionsToPerform) {
				if (!a.canBePerformedOnFiles(amountOfSelectedFiles)) {
					canBePerformed = false;
					break;
				}
			}

			ma.isEnabled = canBePerformed;

		}

		tellMacroChanged();
	}

}
