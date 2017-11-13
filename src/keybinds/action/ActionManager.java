package keybinds.action;

import java.util.ArrayList;

import keybinds.action.type.file.impl.PlayAction;
import keybinds.action.type.file.impl.RenameAction;
import keybinds.action.type.file.impl.TestAction;
import keybinds.action.type.ui.impl.SimpleUIAction;
import sorter.SorterUI;

public class ActionManager {

	/**
	 * Used by MacroEditorUI to populate the 'add action' combobox
	 * Default actions are handled in MacroLoader
	 */
	public static ArrayList<Action> actions = new ArrayList<Action>();

	public static void init(SorterUI s) {

		// Simple UI Actions

		SimpleUIAction.init(s);

		for (Action sUIa : SimpleUIAction.UIActions) {
			addAction(sUIa);
		}

		//Other Actions

		addAction(new PlayAction());
		addAction(new RenameAction());
		addAction(new TestAction());

	}

	private static void addAction(Action a) {
		actions.add(a);
	}

}
