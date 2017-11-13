package keybinds.action.type.ui.impl;

import java.util.ArrayList;

import global.logger.Logger;
import keybinds.action.editeable.EditeableProperty;
import keybinds.action.type.ui.UIAction;
import sorter.SorterUI;

public class SimpleUIAction extends UIAction {

	public String TAG = "SimpleUIAction"; //name is defined bellow

	//

	public static ArrayList<SimpleUIAction> UIActions = new ArrayList<SimpleUIAction>();

	private static final int SHOW_CREDITS_UI_ID = 0;
	private static final int SHOW_MACRO_UI_ID = 1;
	private static final int SHOW_SETTINGS_UI_ID = 2;
	private static final int SHOW_CONSOLE_ID = 3;

	public static final SimpleUIAction SHOW_CREDITS_UI = new SimpleUIAction(SHOW_MACRO_UI_ID, "Show Credits");
	public static final SimpleUIAction SHOW_MACRO_UI = new SimpleUIAction(SHOW_MACRO_UI_ID, "Edit Macros");
	public static final SimpleUIAction SHOW_SETTINGS_UI = new SimpleUIAction(SHOW_SETTINGS_UI_ID, "Edit Settings");
	public static final SimpleUIAction SHOW_CONSOLE = new SimpleUIAction(SHOW_CONSOLE_ID, "Show Console");
	private static SorterUI sampleSorter;

	public static void init(SorterUI s) {
		sampleSorter = s;

		UIActions.add(SHOW_CREDITS_UI);
		UIActions.add(SHOW_MACRO_UI);
		UIActions.add(SHOW_SETTINGS_UI);
		UIActions.add(SHOW_CONSOLE);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private int ID;
	private String name; //Used in toString (populating combobox)

	private SimpleUIAction(int ID, String name) {
		this.ID = ID;
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	//

	public void perform() {

		switch (ID) {

		case SHOW_CREDITS_UI_ID:
			sampleSorter.showCredits();
			break;
		case SHOW_MACRO_UI_ID:
			sampleSorter.showEditMacros();
			break;
		case SHOW_SETTINGS_UI_ID:
			sampleSorter.showSettings();
			break;
		case SHOW_CONSOLE_ID:
			sampleSorter.showConsole();
			break;
		default:
			Logger.logError(name, "Invalid ID " + ID + " for SimpleUIAction");
			break;

		}
	}

	@Override
	public void undo() {
		perform();
	}

	@Override
	public ArrayList<EditeableProperty> getEditeableProperties() {
		return null;
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

}
