package ass.keyboard.macro;

import icons.Icons;
import ass.keyboard.action.type.file.impl.PlayAction;
import ass.keyboard.action.type.file.impl.RenameAction;
import ass.keyboard.action.type.ui.impl.ShowUIAction;
import ass.keyboard.key.Key;
import ass.keyboard.macro.list.MacroInfoPanel;
import file.ObjectSerializer;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class MacroLoader {

	private static final String TAG = "MacroLoader";

	/**
	 * macroActions is the shortcuts currently in the program
	 * 
	 * (formelly 'actions')
	 */
	public ArrayList<MacroAction> macroActions = new ArrayList<MacroAction>();

	private MacroEditor macroEditor;

	public ObjectSerializer<ArrayList<MacroAction>> macroSerialiser = new ObjectSerializer<ArrayList<MacroAction>>("macro.ser");


	
	public MacroLoader(MacroEditor macroEditor) {
		this.macroEditor = macroEditor;

		//restore macros from file

		if (!macroSerialiser.isNull()) {

			if (macroSerialiser.get() != null) { //null might be caused if we remove all the macroactions
				for (MacroAction tmp : macroSerialiser.get()) {
					addNewMacro(tmp);
				}
			}

		} else {
			addNewMacro(new MacroAction("Play", Icons.PLAY, new Key(KeyEvent.VK_SPACE), new PlayAction(), true));

			addNewMacro(new MacroAction("Show Credits", Icons.ABOUT, new Key(KeyEvent.VK_F1), ShowUIAction.SHOW_CREDITS, false));
			addNewMacro(new MacroAction("Edit Macros", Icons.MACROS, new Key(KeyEvent.VK_F2), ShowUIAction.SHOW_MACRO, false));
			addNewMacro(new MacroAction("Edit Settings", Icons.SETTINGS, new Key(KeyEvent.VK_F3), ShowUIAction.SHOW_SETTINGS, false));
			addNewMacro(new MacroAction("Show Logger", Icons.LOGGER, new Key(KeyEvent.VK_F4), ShowUIAction.SHOW_LOGGER, false));

			addNewMacro(new MacroAction("Rename", Icons.PENCIL, new Key(KeyEvent.VK_R), new RenameAction(), false));

		}

	}

	public void addNewMacro(MacroAction tmp) {
		macroActions.add(tmp);
		macroEditor.macroListUI.addKeyBindInfoPanel(tmp);

		serialise();

		tellMacroChanged();
	}

	public void removeMacro(MacroAction keyBind, MacroInfoPanel me) {
		macroActions.remove(keyBind);
		macroEditor.macroListUI.removePanel(me);

		serialise();
		
		tellMacroChanged();
	}


	public static ArrayList<ListenForMacroChanges> waitingForChanges = new ArrayList<>();

	public static void registerWaitingForMacroChanges(ListenForMacroChanges l) {
		waitingForChanges.add(l);
	}

	public void tellMacroChanged() {
		for (ListenForMacroChanges l : waitingForChanges) {
			l.macroChanged(macroActions);
		}
	}

	public void serialise() {
		macroSerialiser.set(macroActions);
		macroSerialiser.serialise();
	}

}
