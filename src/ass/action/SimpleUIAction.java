package ass.action;

import java.util.ArrayList;

import ass.action.editeable.EditableProperty;
import ass.action.interfaces.UIAction;


public class SimpleUIAction extends UIAction {

	public String TAG = "UIAction"; //real action name is defined bellow

	//

	public static ArrayList<SimpleUIAction> UIActions = new ArrayList<SimpleUIAction>();

	private static int NO_POLICY = UIAction.PERFORMED_ON_ZERO_TO_MANY_FILES_POLICY;

	public static final SimpleUIAction SHOW_CREDITS = new SimpleUIAction(0, "Show Credits", "Shows the credits UI.", NO_POLICY);
	public static final SimpleUIAction SHOW_MACRO = new SimpleUIAction(1, "Edit Macros", "Shows the macro UI.", NO_POLICY);
	public static final SimpleUIAction SHOW_SETTINGS = new SimpleUIAction(2, "Edit Settings", "Shows the settings UI.", NO_POLICY);
	public static final SimpleUIAction SHOW_LOGGER = new SimpleUIAction(3, "Show Logger", "Shows the debug log.", NO_POLICY);

	public static void init() {
		UIActions.add(SHOW_CREDITS);
		UIActions.add(SHOW_MACRO);
		UIActions.add(SHOW_SETTINGS);
		UIActions.add(SHOW_LOGGER);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean currentState = false; //used to unperform

	private int id;
	private String name; //Used in toString (populating combobox)
	private int policy;
	private String description;

	private SimpleUIAction(int id, String name, String description, int policy) {
		this.id = id;
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

	int getId() {
		return id;
	}

	public void perform() {

		/**
		 * 
		 * perform() -> currentState = switch state (!current state)
		 * unperform() -> currentState = reverts (!currentState)
		 */

		if (id == SHOW_CREDITS.getId()) {
			currentState = ASS.showCredits(false, false);
		} else if (id == SHOW_MACRO.getId()) {
			currentState = ASS.showEditMacros(false, false);
		} else if (id == SHOW_SETTINGS.getId()) {
			currentState = ASS.showSettings(false, false);
		} else if (id == SHOW_LOGGER.getId()) {
			currentState = ASS.showLogger(false, false);
		} else {
			log.error(name, "Invalid object for perform SimpleUIAction");
		}

	}

	@Override
	public void unperform() {
		boolean switchState = !currentState;

		if (id == SHOW_CREDITS.getId()) {
			currentState = ASS.showCredits(true, switchState);
		} else if (id == SHOW_MACRO.getId()) {
			currentState = ASS.showEditMacros(true, switchState);
		} else if (id == SHOW_SETTINGS.getId()) {
			currentState = ASS.showSettings(true, switchState);
		} else if (id == SHOW_LOGGER.getId()) {
			currentState = ASS.showLogger(true, switchState);
		} else {
			log.error(name, "Invalid object for perform SimpleUIAction");
		}

	}

}
