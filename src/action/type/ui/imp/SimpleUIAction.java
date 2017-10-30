package action.type.ui.imp;

import java.util.ArrayList;

import GUI.SorterUI;
import GUI.Sorter;
import action.editable.EditeableProperty;
import action.type.Action;
import logger.Logger;

public class SimpleUIAction implements Action {

	public String TAG = "SimpleUIAction"; //name is defined bellow

	//

	public static ArrayList<SimpleUIAction> UIActions = new ArrayList<SimpleUIAction>();

	private static final int SCROLL_UP_ID = 0;
	private static final int SCROLL_DOWN_ID = 1;
	private static final int SHOW_CREDITS_UI_ID = 2;
	private static final int SHOW_MACRO_UI_ID = 3;
	private static final int SHOW_SETTINGS_UI_ID = 4;
	private static final int SHOW_CONSOLE_ID = 5;
	private static final int SELECT_ALL_ID = 6;

	private static final SimpleUIAction SCROLL_UP = new SimpleUIAction(SCROLL_UP_ID, "Scroll Up");
	private static final SimpleUIAction SCROLL_DOWN = new SimpleUIAction(SCROLL_DOWN_ID, "Scroll Down");
	private static final SimpleUIAction SHOW_CREDITS_UI = new SimpleUIAction(SHOW_MACRO_UI_ID, "Show Credits");
	private static final SimpleUIAction SHOW_MACRO_UI = new SimpleUIAction(SHOW_MACRO_UI_ID, "Edit Macros");
	private static final SimpleUIAction SHOW_SETTINGS_UI = new SimpleUIAction(SHOW_SETTINGS_UI_ID, "Edit Settings");
	private static final SimpleUIAction SHOW_CONSOLE = new SimpleUIAction(SHOW_CONSOLE_ID, "Show Console");
	private static final SimpleUIAction SELECT_ALL = new SimpleUIAction(SELECT_ALL_ID, "Select all");

	public static void init() {
		UIActions.add(SCROLL_UP);
		UIActions.add(SCROLL_DOWN);
		UIActions.add(SHOW_CREDITS_UI);
		UIActions.add(SHOW_MACRO_UI);
		UIActions.add(SHOW_SETTINGS_UI);
		UIActions.add(SHOW_CONSOLE);
		UIActions.add(SELECT_ALL);
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

	private SorterUI samplerSorter;
	private Sorter sorter;

	public void init(SorterUI samplerSorter) {
		this.samplerSorter = samplerSorter;
		this.sorter = samplerSorter.sorter;
	}

	public void perform() {

		switch (ID) {

		case SCROLL_UP_ID:
			sorter.scrollUp();
			break;
		case SCROLL_DOWN_ID:
			sorter.scrollDown();
			break;
		case SHOW_CREDITS_UI_ID:
			break;
		case SHOW_MACRO_UI_ID:
			samplerSorter.showEditMacrosUI();
			break;
		case SHOW_SETTINGS_UI_ID:
			samplerSorter.showSettings();
			break;
		case SHOW_CONSOLE_ID:
			break;
		case SELECT_ALL_ID:
			sorter.selectAll();
			break;
		default:
			Logger.logError(name, "Invalid ID " + ID + " for SimpleUIAction");
			break;

		}

	}

	@Override
	public void undo() {
	}

	@Override
	public ArrayList<EditeableProperty> getEditeableProperties() {
		return null;
	}

}
