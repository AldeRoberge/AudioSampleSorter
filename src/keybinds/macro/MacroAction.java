package keybinds.macro;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.Icon;

import global.icons.StaticIcon;
import keybinds.action.Action;
import keybinds.action.type.file.FileAction;
import keybinds.action.type.ui.UIAction;
import keybinds.event.EventManager;
import keybinds.key.Key;

/**
 * MacroAction is key(s) to action(s)
 */

public class MacroAction implements Serializable {

	// UI information

	private static final String TAG = "MacroAction";
	private String name;
	private StaticIcon icon;

	// Keys required to be pressed to trigger the event
	public ArrayList<Key> keys = new ArrayList<Key>();

	public ArrayList<Action> actionsToPerform = new ArrayList<Action>();

	//Used by MacroLoader to instantiate basic actions
	public MacroAction(String name, StaticIcon icon, Key key, Action action) {
		this.name = name;
		this.setIcon(icon);
		keys.add(new Key(KeyEvent.VK_SPACE));
		actionsToPerform.add(action);
	}

	public MacroAction(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return keys.toString() + " " + actionsToPerform.toString();
	}

	// Used by MacroEditorUI when the edit macro JTextPanel is clicked to reset
	// the keys
	public void clearKeys() {
		keys = new ArrayList<Key>();
	}

	public void perform() { // this needs to be here rather than in
								// globalKeyListener to allow UI buttons to be
							// pressed
		for (Action action : actionsToPerform) {

			if (action instanceof UIAction) {

				global.logger.Logger.logInfo(TAG, "This action is an instanceof UIAction");

				UIAction act = (UIAction) action;
				UIAction clonedAction = null;

				try {
					clonedAction = act.clone();
				} catch (CloneNotSupportedException e1) {
					e1.printStackTrace();
				}

				clonedAction.perform();

				EventManager.performEvent(clonedAction);

			} else if (action instanceof FileAction) {

				global.logger.Logger.logInfo(TAG, "This action is an instanceof SoundAction");

				FileAction act = (FileAction) action;
				FileAction clonedAction = null;
				try {
					clonedAction = act.clone();
				} catch (CloneNotSupportedException e2) {
					e2.printStackTrace();
				}

				/**
				 * for (File file : SorterUI.) { EventManager.performEvent(new
				 * Event(clonedAction, file)); }
				 */

			} else {
				global.logger.Logger.logError(TAG, "Invalid type of action!");
			}

			// if

		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Icon getIcon() {
		if (icon == null) {
			return null;
		}

		return icon.getImageIcon();
	}

	public String getIconPath() {
		return icon.getPath();
	}

	public void setIcon(StaticIcon icon) {
		this.icon = icon;
	}

}
