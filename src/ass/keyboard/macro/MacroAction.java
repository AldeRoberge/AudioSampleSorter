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

	private String name;
	private UserIcon icon;

	// Keys required to be pressed to trigger the event
	public ArrayList<Key> keys = new ArrayList<>();

	public ArrayList<Action> actionsToPerform = new ArrayList<>();

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
		keys = new ArrayList<>();
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

		StringBuilder build = new StringBuilder("<html>");

		build.append("<p><strong>").append(getName()).append("</strong></p>");

		for (Action a : actionsToPerform) {
			build.append("<p>").append(a.getDescription()).append("</p>");
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
			build.append("<p>Shortcut : ").append(getKeysAsString()).append("</p>");
		}

		if (highestPolicy != -100) { //If there is no actions, policy will stay at -100

			String policyString = "<small>Requires " + Action.getPolicyAsString(highestPolicy)
					+ " to be selected</small>";

			if (!isEnabled) {
				build.append("<p><font color=\"red\">").append(policyString).append("</font></p>");
			} /*else {
				build += "<p>" + policyString + "</p>";
				}*/

		}

		return build + "</html>";

	}

	public String getKeysAsString() {

		StringBuilder displayKeyNames = new StringBuilder();

		for (int i = 0; i < keys.size(); i++) {
			if (i != 0) {
				displayKeyNames.append(" + ");
			}
			displayKeyNames.append(keys.get(i).keyName);
		}

		return displayKeyNames.toString();
	}

}
