package samplerSorter.macro;

import java.io.Serializable;
import java.util.ArrayList;

import samplerSorter.GUI.soundpanel.SoundPanel;
import samplerSorter.action.type.Action;

/**
 * MacroAction
 * is key(s) to action(s)
 */

public class MacroAction implements Serializable {

	//Keys required to be pressed to trigger the event
	public ArrayList<Key> keys = new ArrayList<Key>();

	public ArrayList<Action> actionsToPerform = new ArrayList<Action>();

	@Override
	public String toString() {
		return keys.toString() + " " + actionsToPerform.toString();
	}

	//Used by MacroEditorUI when the edit macro Jtextpanel is clicked to reset the keys
	public void clearKeys() {
		keys = new ArrayList<Key>();
	}

	public void performActions(ArrayList<SoundPanel> toPerform) {
		for (SoundPanel p : toPerform) {
			for (Action a : actionsToPerform) {

			}
		}
	}

}
