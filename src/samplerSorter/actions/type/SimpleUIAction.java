package samplerSorter.actions.type;

import java.util.ArrayList;
import java.util.function.Function;

import samplerSorter.SamplerSorter;
import samplerSorter.UI.Sorter;
import samplerSorter.UI.SoundPanel;
import samplerSorter.actions.Action;
import samplerSorter.actions.editeable.EditeableProperty;
import samplerSorter.logger.Logger;

public class SimpleUIAction extends Action {

	public String TAG = "SimpleUIAction"; //name is defined bellow

	//

	public static ArrayList<SimpleUIAction> UIActions = new ArrayList<SimpleUIAction>();

	public static final int SCROLL_UP_ID = 0;
	public static final int SCROLL_DOWN_ID = 1;
	public static final int SHOW_CREDITS_UI_ID = 2;
	public static final int SHOW_MACRO_UI_ID = 3;
	public static final int SHOW_SETTINGS_UI_ID = 4;
	public static final int SHOW_CONSOLE_ID = 5;
	public static final int SELECT_ALL_ID = 6;

	public static final SimpleUIAction SCROLL_UP = new SimpleUIAction(SCROLL_UP_ID, "Scroll Up");
	public static final SimpleUIAction SCROLL_DOWN = new SimpleUIAction(SCROLL_DOWN_ID, "Scroll Down");
	public static final SimpleUIAction SHOW_CREDITS_UI = new SimpleUIAction(SHOW_MACRO_UI_ID, "Show Credits");
	public static final SimpleUIAction SHOW_MACRO_UI = new SimpleUIAction(SHOW_MACRO_UI_ID, "Edit Macros");
	public static final SimpleUIAction SHOW_SETTINGS_UI = new SimpleUIAction(SHOW_SETTINGS_UI_ID, "Edit Settings");
	public static final SimpleUIAction SHOW_CONSOLE = new SimpleUIAction(SHOW_CONSOLE_ID, "Show Console");
	public static final SimpleUIAction SELECT_ALL = new SimpleUIAction(SELECT_ALL_ID, "Select all");

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

	public int ID;
	public String name; //Used in toString (populating combobox)

	public SimpleUIAction(int ID, String name) {
		this.ID = ID;
		this.name = name;
	}

	//

	SamplerSorter samplerSorter;
	Sorter sorter;

	public void init(SamplerSorter samplerSorter) {
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
	public String toString() {
		return name;
	}

	@Override
	public boolean isEditeable() {
		return false;
	}

	@Override
	public ArrayList<EditeableProperty> getEditeableProperties() {
		return null;
	}

}
