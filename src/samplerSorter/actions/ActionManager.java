package samplerSorter.actions;

import java.util.ArrayList;

import samplerSorter.SamplerSorter;
import samplerSorter.actions.type.impl.PlayAction;

public class ActionManager {

	/**
	 * Used by MacroEditorUI to populate the 'add action' combobox
	 */
	public static ArrayList<Action> actions = new ArrayList<Action>();

	public static void init(SamplerSorter s) {
		actions.add(new PlayAction());
	}

}
