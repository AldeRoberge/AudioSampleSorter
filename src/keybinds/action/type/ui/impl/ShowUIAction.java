package keybinds.action.type.ui.impl;

import java.util.ArrayList;

import global.logger.Logger;
import keybinds.action.editable.EditableProperty;
import keybinds.action.type.ui.UIAction;
import sorter.SorterUI;

public class ShowUIAction extends UIAction {

	public String TAG = "SimpleUIAction"; //name is defined bellow

	//

	public static ArrayList<ShowUIAction> UIActions = new ArrayList<ShowUIAction>();

	private static final int SHOW_CREDITS_UI_ID = 0;
	private static final int SHOW_MACRO_UI_ID = 1;
	private static final int SHOW_SETTINGS_UI_ID = 2;
	private static final int SHOW_CONSOLE_ID = 3;

	public static final ShowUIAction SHOW_CREDITS_UI = new ShowUIAction(SHOW_MACRO_UI_ID, "Show Credits");
	public static final ShowUIAction SHOW_MACRO_UI = new ShowUIAction(SHOW_MACRO_UI_ID, "Edit Macros");
	public static final ShowUIAction SHOW_SETTINGS_UI = new ShowUIAction(SHOW_SETTINGS_UI_ID, "Edit Settings");
	public static final ShowUIAction SHOW_CONSOLE = new ShowUIAction(SHOW_CONSOLE_ID, "Show Console");
	private static SorterUI sampleSorter;

	public static void init(SorterUI s) {
		sampleSorter = s;

		UIActions.add(SHOW_CREDITS_UI);
		UIActions.add(SHOW_MACRO_UI);
		UIActions.add(SHOW_SETTINGS_UI);
		UIActions.add(SHOW_CONSOLE);
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

		case SHOW_CREDITS_UI_ID:
			currentVisibilityState = sampleSorter.showCredits(false, false);
			break;
		case SHOW_MACRO_UI_ID:
			currentVisibilityState = sampleSorter.showEditMacros(false, false);
			break;
		case SHOW_SETTINGS_UI_ID:
			currentVisibilityState = sampleSorter.showSettings(false, false);
			break;
		case SHOW_CONSOLE_ID:
			currentVisibilityState = sampleSorter.showConsole(false, false);
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

		case SHOW_CREDITS_UI_ID:
			currentVisibilityState = sampleSorter.showCredits(true, switchState);
			break;
		case SHOW_MACRO_UI_ID:
			currentVisibilityState = sampleSorter.showEditMacros(true, switchState);
			break;
		case SHOW_SETTINGS_UI_ID:
			currentVisibilityState = sampleSorter.showSettings(true, switchState);
			break;
		case SHOW_CONSOLE_ID:
			currentVisibilityState = sampleSorter.showConsole(true, switchState);
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
		case SHOW_CREDITS_UI_ID:
			answer += "credits";
			break;
		case SHOW_MACRO_UI_ID:
			answer += "macros";
			break;
		case SHOW_SETTINGS_UI_ID:
			answer += "settings";
			break;
		case SHOW_CONSOLE_ID:
			answer += "debugger console";
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
