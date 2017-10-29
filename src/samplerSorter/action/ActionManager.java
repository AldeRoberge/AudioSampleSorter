package samplerSorter.action;

import java.util.ArrayList;

import samplerSorter.action.type.Action;
import samplerSorter.action.type.sound.impl.PlayAction;
import samplerSorter.action.type.sound.impl.RenameAction;
import samplerSorter.action.type.sound.impl.TestAction;
import samplerSorter.action.type.ui.SimpleUIAction;

public class ActionManager {

	/**
	 * Used by MacroEditorUI to populate the 'add action' combobox
	 */
	public static ArrayList<Action> actions = new ArrayList<Action>();

	public static void init() {

		//Simple UI Actions

		SimpleUIAction.init();

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
