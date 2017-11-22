package ass.keyboard.action;

import java.util.ArrayList;

import ass.ASS;
import ass.keyboard.action.editable.EditableProperty;
import ass.keyboard.action.interfaces.Action;
import ass.keyboard.action.interfaces.UIAction;
import logger.Logger;

public class SimpleUIAction extends UIAction {

	public String TAG = "UIAction"; //real action name is defined bellow

	//

	public static ArrayList<SimpleUIAction> UIActions = new ArrayList<SimpleUIAction>();

	private static final int SHOW_CREDITS_ID = 0;
	private static final int SHOW_MACRO_ID = 1;
	private static final int SHOW_SETTINGS_ID = 2;
	private static final int SHOW_LOGGER_ID = 3;

	static int NO_POLICY = UIAction.PERFORMED_ON_ZERO_TO_MANY_FILES_POLICY;

	public static final SimpleUIAction SHOW_CREDITS = new SimpleUIAction(SHOW_CREDITS_ID, "Show Credits", "Shows the credits UI.", NO_POLICY);
	public static final SimpleUIAction SHOW_MACRO = new SimpleUIAction(SHOW_MACRO_ID, "Edit Macros", "Shows the macro UI.", NO_POLICY);
	public static final SimpleUIAction SHOW_SETTINGS = new SimpleUIAction(SHOW_SETTINGS_ID, "Edit Settings", "Shows the settings UI.", NO_POLICY);
	public static final SimpleUIAction SHOW_LOGGER = new SimpleUIAction(SHOW_LOGGER_ID, "Show Logger", "Shows the debug log.", NO_POLICY);
	
	public static void init() {
		UIActions.add(SHOW_CREDITS);
		UIActions.add(SHOW_MACRO);
		UIActions.add(SHOW_SETTINGS);
		UIActions.add(SHOW_LOGGER);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean currentState = false; //used to unperform

	private int ID;
	private String name; //Used in toString (populating combobox)
	private int policy;
	private String description;

	private SimpleUIAction(int ID, String name, String description, int policy) {
		this.ID = ID;
		this.name = name;
		this.description = description;
		this.policy = policy;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public ArrayList<EditableProperty> getEditableProperties() {
		return null;
	}

	@Override
	public int getPolicy() {
		return policy;
	}

	public void perform() {

		/**
		 * 
		 * perform() -> currentState = switch state (!current state)
		 * unperform() -> currentState = reverts (!currentState)
		 */

		switch (ID) {

		case SHOW_CREDITS_ID:
			currentState = ASS.showCredits(false, false);
			break;
		case SHOW_MACRO_ID:
			currentState = ASS.showEditMacros(false, false);
			break;
		case SHOW_SETTINGS_ID:
			currentState = ASS.showSettings(false, false);
			break;
		case SHOW_LOGGER_ID:
			currentState = ASS.showLogger(false, false);
			break;
		default:
			Logger.logError(name, "Invalid ID " + ID + " for SimpleUIAction");
			break;

		}
	}

	@Override
	public void unperform() {
		boolean switchState = !currentState;

		switch (ID) {

		case SHOW_CREDITS_ID:
			currentState = ASS.showCredits(true, switchState);
			break;
		case SHOW_MACRO_ID:
			currentState = ASS.showEditMacros(true, switchState);
			break;
		case SHOW_SETTINGS_ID:
			currentState = ASS.showSettings(true, switchState);
			break;
		case SHOW_LOGGER_ID:
			currentState = ASS.showLogger(true, switchState);
			break;
		default:
			Logger.logError(name, "Invalid ID " + ID + " for SimpleUIAction");
			break;

		}
	}

}
