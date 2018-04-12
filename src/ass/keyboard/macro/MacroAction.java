package ass.keyboard.macro;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.Icon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ass.action.interfaces.Action;
import ass.action.interfaces.FileAction;
import ass.action.interfaces.UIAction;
import ass.keyboard.key.Key;
import constants.icons.UserIcon;


/**
 * MacroAction is key(s) to action(s)
 */

public class MacroAction implements Serializable {

	static Logger log = LoggerFactory.getLogger(MacroAction.class);
	
	// UI information

	private static final String TAG = "MacroAction";
	private String name;
	private UserIcon icon;

	// Keys required to be pressed to trigger the event
	public ArrayList<Key> keys = new ArrayList<Key>();

	public ArrayList<Action> actionsToPerform = new ArrayList<Action>();

	public boolean showInMenu = true;
	public boolean showInToolbar = true;

	public boolean isEnabled;

	//Used by MacroLoader to instantiate basic actions
	public MacroAction(String name, UserIcon icon, Key key, Action action, boolean showInMenu, boolean showInToolbar) {
		this.name = name;
		this.setIcon(icon);
		if (key != null) {
			this.keys.add(key);
		}
		this.actionsToPerform.add(action);
		this.showInMenu = showInMenu;
		this.showInToolbar = showInToolbar;

	}

	public MacroAction(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return keys.toString() + " " + actionsToPerform.toString();
	}

	// Used by MacroEditorUI when the edit audiosamplesorter JTextPanel is clicked to reset
	// the keys
	public void clearKeys() {
		keys = new ArrayList<Key>();
	}

	public void perform() {
		for (Action action : actionsToPerform) {

			if (action instanceof UIAction) {

				log.info("This action is an instanceof UIAction");

				UIAction act = (UIAction) action;
				UIAction clonedAction = null;

				try {
					clonedAction = act.clone();
				} catch (CloneNotSupportedException e1) {
					e1.printStackTrace();
				}

				clonedAction.perform();

			} else if (action instanceof FileAction) {

				log.info("This action is an instanceof FileAction");

				FileAction act = (FileAction) action;
				FileAction clonedAction = null;
				try {
					clonedAction = act.clone();
				} catch (CloneNotSupportedException e2) {
					e2.printStackTrace();
				}

				//perform

				clonedAction.ready();

			} else {
				log.error("Invalid type of action!");
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

	public void setIcon(UserIcon icon) {
		this.icon = icon;
	}

	public String getToolTip() {

		String build = "<html>";

		build += "<p><strong>" + getName() + "</strong></p>";

		for (Action a : actionsToPerform) {
			build += "<p>" + a.getDescription() + "</p>";
		}

		//Get highest policy as string

		int highestPolicy = -100;

		for (Action a : actionsToPerform) {
			if (a.getPolicy() > highestPolicy) {
				highestPolicy = a.getPolicy();
			}
		}

		//

		if (keys.size() > 0) {
			build += "<p>Shortcut : " + getKeysAsString() + "</p>";
		}

		if (highestPolicy != -100) { //If there is no actions, policy will stay at -100

			String policyString = "<small>Requires " + Action.getPolicyAsString(highestPolicy) + " to be selected</small>";

			if (!isEnabled) {
				build += "<p><font color=\"red\">" + policyString + "</font></p>";
			} /*else {
				build += "<p>" + policyString + "</p>";
				}*/

		}

		return build + "</html>";

	}

	public String getKeysAsString() {

		String displayKeyNames = "";

		for (int i = 0; i < keys.size(); i++) {
			if (i != 0) {
				displayKeyNames += " + ";
			}
			displayKeyNames += keys.get(i).keyName;
		}

		return displayKeyNames;
	}

}
