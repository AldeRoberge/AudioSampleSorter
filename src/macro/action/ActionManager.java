package macro.action;

import macro.action.type.file.impl.PlayAction;
import macro.action.type.file.impl.RenameAction;
import macro.action.type.file.impl.TestAction;
import macro.action.type.ui.impl.ShowUIAction;

import java.util.ArrayList;

import ass.SorterUI;

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
