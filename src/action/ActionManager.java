package action;

import java.util.ArrayList;

import GUI.SorterUI;
import action.type.Action;
import action.type.sound.imp.PlayAction;
import action.type.sound.imp.RenameAction;
import action.type.sound.imp.TestAction;
import action.type.ui.imp.SimpleUIAction;

public class ActionManager {

	/**
	 * Used by MacroEditorUI to populate the 'add action' combobox
	 * Default actions are handled in MacroLoader
	 */
	public static ArrayList<Action> actions = new ArrayList<Action>();

	public static void init(SorterUI s) {

		//Simple UI Actions

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
