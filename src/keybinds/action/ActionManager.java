package keybinds.action;

import keybinds.action.type.file.impl.PlayAction;
import keybinds.action.type.file.impl.RenameAction;
import keybinds.action.type.file.impl.TestAction;
import keybinds.action.type.ui.impl.ShowUIAction;
import sorter.SorterUI;

import java.util.ArrayList;

public class ActionManager {

	/**
	 * Used by MacroEditorUI to populate the 'add action' comboBox
	 * Default actions are handled in MacroLoader
	 */
	public static ArrayList<Action> actions = new ArrayList<Action>();

	public static void init(SorterUI s) {

		// Simple UI Actions

		ShowUIAction.init(s);

		for (Action sUIa : ShowUIAction.UIActions) {
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
