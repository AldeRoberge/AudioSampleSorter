package ass.keyboard.macro;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import ass.LibraryManager;
import ass.file.ListenForSelectedFilesChanges;
import ass.keyboard.action.RenameAction;
import ass.keyboard.action.SimpleUIAction;
import ass.keyboard.action.interfaces.Action;
import ass.keyboard.key.Key;
import file.ObjectSerializer;
import icons.Icons;

public class MacroLoader implements ListenForSelectedFilesChanges {

	private static final String TAG = "MacroLoader";

	/**
	 * macroActions is the shortcuts currently in the program
	 * 
	 * (formelly 'actions')
	 */
	public ArrayList<MacroAction> macroActions = new ArrayList<MacroAction>();

	private MacroEditor macroEditor;

	private ObjectSerializer<ArrayList<MacroAction>> macroSerializer = new ObjectSerializer<ArrayList<MacroAction>>(LibraryManager.getMacroSerFile());

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
			addNewMacro(new MacroAction("Show Credits", Icons.ABOUT, new Key(KeyEvent.VK_F1), SimpleUIAction.SHOW_CREDITS, false));
			addNewMacro(new MacroAction("Edit Macros", Icons.MACROS, new Key(KeyEvent.VK_F2), SimpleUIAction.SHOW_MACRO, false));
			addNewMacro(new MacroAction("Edit Settings", Icons.SETTINGS, new Key(KeyEvent.VK_F3), SimpleUIAction.SHOW_SETTINGS, false));
			addNewMacro(new MacroAction("Show Logger", Icons.LOGGER, new Key(KeyEvent.VK_F4), SimpleUIAction.SHOW_LOGGER, false));

			addNewMacro(new MacroAction("Rename", Icons.PENCIL, new Key(KeyEvent.VK_R), new RenameAction(), false));

		}

	}

	public void addNewMacro(MacroAction tmp) {
		macroActions.add(tmp);
		macroEditor.macroListUI.addKeyBindInfoPanel(tmp);

		serialise();

		tellMacroChanged();
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
		macroSerializer.serialise();
	}

	@Override
	public void filesChanged(int amountOfSelectedFiles) {
		for (MacroAction ma : macroActions) {

			boolean allActionsCanBePerformedOnThisAmountOfSelectedFiles = true;

			for (Action a : ma.actionsToPerform) {
				if (!a.canBePerformedOnFiles(amountOfSelectedFiles)) {
					allActionsCanBePerformedOnThisAmountOfSelectedFiles = false;
					break;
				}
			}

			if (allActionsCanBePerformedOnThisAmountOfSelectedFiles) {
				ma.isEnabled = true;
			} else {
				ma.isEnabled = false;
			}

		}

		tellMacroChanged();
	}

}
