package action.type.ui.imp;

import java.util.ArrayList;

import action.editable.EditeableProperty;
import action.type.ui.UIAction;
import logger.Logger;
import sorter.SorterUI;

public class SimpleUIAction extends UIAction {

	public String TAG = "SimpleUIAction"; //name is defined bellow

	//

	public static ArrayList<SimpleUIAction> UIActions = new ArrayList<SimpleUIAction>();

	private static final int SHOW_CREDITS_UI_ID = 2;
	private static final int SHOW_MACRO_UI_ID = 3;
	private static final int SHOW_SETTINGS_UI_ID = 4;
	private static final int SHOW_CONSOLE_ID = 5;

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
			break;
		case SHOW_MACRO_UI_ID:
			sampleSorter.showEditMacros(true);
			break;
		case SHOW_SETTINGS_UI_ID:
			sampleSorter.showSettings(true);
			break;
		case SHOW_CONSOLE_ID:
			break;
		default:
			Logger.logError(name, "Invalid ID " + ID + " for SimpleUIAction");
			break;

		}
	}

	@Override
	public void undo() {
		switch (ID) {

		case SHOW_CREDITS_UI_ID:
			break;
		case SHOW_MACRO_UI_ID:
			sampleSorter.showEditMacros(false);
			break;
		case SHOW_SETTINGS_UI_ID:
			sampleSorter.showSettings(false);
			break;
		case SHOW_CONSOLE_ID:
			break;
		default:
			Logger.logError(name, "Invalid ID " + ID + " for SimpleUIAction");
			break;

		}
	}

	@Override
	public ArrayList<EditeableProperty> getEditeableProperties() {
		return null;
	}

}
