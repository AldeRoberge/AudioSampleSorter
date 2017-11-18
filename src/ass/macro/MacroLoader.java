package ass.macro;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import ass.ui.SorterUI;
import ass.icons.Icons;
import ass.macro.action.type.file.impl.PlayAction;
import ass.macro.action.type.file.impl.RenameAction;
import ass.macro.action.type.ui.impl.ShowUIAction;
import ass.macro.key.Key;
import ass.macro.ui.MacroAction;
import ass.macro.ui.MacroEditor;
import ass.macro.ui.list.MacroInfoPanel;
import file.ObjectSerializer;

public class MacroLoader {

	private static final String TAG = "MacroLoader";

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
			addNewMacro(new MacroAction("Play", Icons.PLAY, new Key(KeyEvent.VK_SPACE), new PlayAction()));

			addNewMacro(new MacroAction("Show Credits", Icons.ABOUT, new Key(KeyEvent.VK_F1), ShowUIAction.SHOW_CREDITS));
			addNewMacro(new MacroAction("Edit Macros", Icons.MACROS, new Key(KeyEvent.VK_F2), ShowUIAction.SHOW_MACRO));
			addNewMacro(new MacroAction("Edit Settings", Icons.SETTINGS, new Key(KeyEvent.VK_F3), ShowUIAction.SHOW_SETTINGS));
			addNewMacro(new MacroAction("Show Logger", Icons.LOGGER, new Key(KeyEvent.VK_F4), ShowUIAction.SHOW_LOGGER));

			addNewMacro(new MacroAction("Rename", Icons.PENCIL, new Key(KeyEvent.VK_R), new RenameAction()));

		}

	}

	public void addNewMacro(MacroAction tmp) {
		macroActions.add(tmp);
		macroEditor.macroListUI.addKeyBindInfoPanel(tmp);

		serialise();
	}

	public void removeMacro(MacroAction keyBind, MacroInfoPanel me) {
		macroActions.remove(keyBind);
		macroEditor.macroListUI.removePanel(me);

		serialise();

		SorterUI.toolBar.repopulate();
	}

	public void serialise() {
		macroSerialiser.set(macroActions);
		macroSerialiser.serialise();
	}

}
