package ass.macro.ui;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.Icon;

import ass.icons.StaticIcon;
import ass.macro.action.Action;
import ass.macro.action.type.file.FileAction;
import ass.macro.action.type.ui.UIAction;
import ass.macro.event.EventManager;
import ass.macro.key.Key;
import logger.Logger;

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
	
	public boolean showInToolbar;

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

	// Used by MacroEditorUI when the edit ui JTextPanel is clicked to reset
	// the keys
	public void clearKeys() {
		keys = new ArrayList<Key>();
	}

	public void perform() { // this needs to be here rather than in
								// globalKeyListener to allow UI buttons to be
							// pressed
		for (Action action : actionsToPerform) {

			if (action instanceof UIAction) {

				Logger.logInfo(TAG, "This action is an instanceof UIAction");

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

				Logger.logInfo(TAG, "This action is an instanceof FileAction");

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
				Logger.logError(TAG, "Invalid type of action!");
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
		return icon.getImagePath();
	}

	public void setIcon(StaticIcon icon) {
		this.icon = icon;
	}

}
