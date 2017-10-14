package samplerSorter.macro;

import java.io.Serializable;
import java.util.ArrayList;

import samplerSorter.actions.type.Action;

/**
 * MacroAction
 */

public class MacroAction implements Serializable {

	public ArrayList<Key> keys = new ArrayList<Key>();
	public ArrayList<Action> actionsToPerform = new ArrayList<Action>();

	@Override
	public String toString() {
		return keys.toString() + " " + actionsToPerform.toString();
	}

}
