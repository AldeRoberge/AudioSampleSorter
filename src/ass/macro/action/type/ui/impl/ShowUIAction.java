package ass.macro.action.type.ui.impl;

import java.util.ArrayList;

import ass.ui.SorterUI;
import logger.Logger;
import ass.macro.action.editable.EditableProperty;
import ass.macro.action.type.ui.UIAction;

public class ShowUIAction extends UIAction {

	public String TAG = "SimpleUIAction"; //name is defined bellow

	//

	public static ArrayList<ShowUIAction> UIActions = new ArrayList<ShowUIAction>();

	private static final int SHOW_CREDITS_ID = 0;
	private static final int SHOW_MACRO_ID = 1;
	private static final int SHOW_SETTINGS_ID = 2;
	private static final int SHOW_LOGGER_ID = 3;

	public static final ShowUIAction SHOW_CREDITS = new ShowUIAction(SHOW_CREDITS_ID, "Show Credits");
	public static final ShowUIAction SHOW_MACRO = new ShowUIAction(SHOW_MACRO_ID, "Edit Macros");
	public static final ShowUIAction SHOW_SETTINGS = new ShowUIAction(SHOW_SETTINGS_ID, "Edit Settings");
	public static final ShowUIAction SHOW_LOGGER = new ShowUIAction(SHOW_LOGGER_ID, "Show Logger");
	private static SorterUI sorterUI;

	public static void init(SorterUI s) {
		sorterUI = s;

		UIActions.add(SHOW_CREDITS);
		UIActions.add(SHOW_MACRO);
		UIActions.add(SHOW_SETTINGS);
		UIActions.add(SHOW_LOGGER);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean currentVisibilityState = false; //used to unperform

	private int ID;
	private String name; //Used in toString (populating combobox)

	private ShowUIAction(int ID, String name) {
		this.ID = ID;
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	//

	public void perform() {

		/**
		 * on perform() = currentVisibilityState = component.setVisibility(!component.isVisible)
		 * 
		 * on UNperform() = component.setVisibility(!currentVisibilityState)
		 */

		switch (ID) {

		case SHOW_CREDITS_ID:
			currentVisibilityState = sorterUI.showCredits(false, false);
			break;
		case SHOW_MACRO_ID:
			currentVisibilityState = sorterUI.showEditMacros(false, false);
			break;
		case SHOW_SETTINGS_ID:
			currentVisibilityState = sorterUI.showSettings(false, false);
			break;
		case SHOW_LOGGER_ID:
			currentVisibilityState = sorterUI.showLogger(false, false);
			break;
		default:
			Logger.logError(name, "Invalid ID " + ID + " for SimpleUIAction");
			break;

		}
	}

	@Override
	public void unperform() {

		boolean switchState = !currentVisibilityState;

		switch (ID) {

		case SHOW_CREDITS_ID:
			currentVisibilityState = sorterUI.showCredits(true, switchState);
			break;
		case SHOW_MACRO_ID:
			currentVisibilityState = sorterUI.showEditMacros(true, switchState);
			break;
		case SHOW_SETTINGS_ID:
			currentVisibilityState = sorterUI.showSettings(true, switchState);
			break;
		case SHOW_LOGGER_ID:
			currentVisibilityState = sorterUI.showLogger(true, switchState);
			break;
		default:
			Logger.logError(name, "Invalid ID " + ID + " for SimpleUIAction");
			break;

		}
	}

	@Override
	public String getDescription() {

		String answer = "Toggles visibility of ";

		switch (ID) {
		case SHOW_CREDITS_ID:
			answer += "credits";
			break;
		case SHOW_MACRO_ID:
			answer += "macros";
			break;
		case SHOW_SETTINGS_ID:
			answer += "settings";
			break;
		case SHOW_LOGGER_ID:
			answer += "logger";
			break;
		default:
			Logger.logError(name, "Invalid ID " + ID + " for SimpleUIAction");
			answer = "Invalid SimpleUIAction";
			break;
		}

		answer += ".";

		return answer;
	}

	@Override
	public ArrayList<EditableProperty> getEditableProperties() {
		return null;
	}

}
