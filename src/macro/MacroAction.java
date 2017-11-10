package macro;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.Icon;

import action.type.Action;
import action.type.sound.FileAction;
import action.type.ui.UIAction;
import key.Key;

/**
 * MacroAction
 * is key(s) to action(s)
 */

public class MacroAction implements Serializable {

	//UI information

	private static final String TAG = "MacroAction";
	public String name;
	public String iconPath;

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

	public void perform() {

		for (Action action : actionsToPerform) {

			if (action instanceof UIAction) {

				logger.Logger.logInfo(TAG, "Action is instanceof UIAction");

				UIAction act = (UIAction) action;
				UIAction clonedAction = null;

				try {
					clonedAction = act.clone();
				} catch (CloneNotSupportedException e1) {
					e1.printStackTrace();
				}

				clonedAction.perform();

			} else if (action instanceof FileAction) {

				logger.Logger.logInfo(TAG, "Action is instanceof SoundAction");

				FileAction act = (FileAction) action;
				FileAction clonedAction = null;
				try {
					clonedAction = act.clone();
				} catch (CloneNotSupportedException e2) {
					e2.printStackTrace();
				}

				/**for (SoundPanel sp : sorter.selectedSoundPanels) {
					clonedAction.perform(sp);
				}*/

			} else {
				logger.Logger.logError(TAG, "Invalid type of action!");
			}
		}
	}
}
