package macro;

import java.io.Serializable;
import java.util.ArrayList;

import GUI.soundpanel.SoundPanel;
import action.type.Action;

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

	//Used by MacroEditorUI when the edit macro JTextPanel is clicked to reset the keys
	public void clearKeys() {
		keys = new ArrayList<Key>();
	}

}
