package samplerSorter.actions;

import java.util.ArrayList;

import samplerSorter.actions.type.PlayAction;
import samplerSorter.actions.type.RenameAction;
import samplerSorter.actions.type.SimpleUIAction;
import samplerSorter.actions.type.TestAction;

public class ActionManager {

	/**
	 * Used by MacroEditorUI to populate the 'add action' combobox
	 */
	public static ArrayList<Action> actions = new ArrayList<Action>();

	public static void init() {

		//Simple UI Actions
		
		SimpleUIAction.init();

		for (SimpleUIAction sUIa : SimpleUIAction.UIActions) {
			addAction(sUIa);
		}
		
		//Other Actions

		addAction(new PlayAction());
		addAction(new RenameAction());
		addAction(new TestAction());

	}

	public static void addAction(Action a) {
		actions.add(a);
	}

}
