package samplerSorter.actions;

import java.util.ArrayList;

import samplerSorter.actions.type.Action;
import samplerSorter.actions.type.MoveAction;
import samplerSorter.actions.type.PlayAction;
import samplerSorter.actions.type.RenameAction;

public class ActionManager {

	public static ArrayList<Action> actions = new ArrayList<Action>();

	static {
		init();
	}

	public static void init() {
		actions.add(new MoveAction());
		actions.add(new PlayAction());
		actions.add(new RenameAction());
	}

}
