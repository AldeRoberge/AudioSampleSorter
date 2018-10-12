package ass.action.ui;

import java.util.ArrayList;

import ass.action.editeable.EditableProperty;
import ass.action.file.FileAmountPolicy;
import ass.action.interfaces.UIAction;

public class SimpleUIAction extends UIAction {

	public String TAG = "UIAction"; //real action name is defined bellow

	//

	public static ArrayList<SimpleUIAction> UIActions = new ArrayList<>();

	private static FileAmountPolicy NO_POLICY = FileAmountPolicy.PERFORMED_ON_ZERO_TO_MANY_FILES_POLICY;

	public static final SimpleUIAction SHOW_CREDITS = new SimpleUIAction(0, "Show Credits", "Shows the credits UI.",
			NO_POLICY);
	public static final SimpleUIAction SHOW_MACRO = new SimpleUIAction(1, "Edit Macros", "Shows the macro UI.",
			NO_POLICY);
	public static final SimpleUIAction SHOW_SETTINGS = new SimpleUIAction(2, "Edit Settings", "Shows the settings UI.",
			NO_POLICY);
	public static final SimpleUIAction SHOW_LOGGER = new SimpleUIAction(3, "Show Logger", "Shows the debug log.",
			NO_POLICY);

	public static void init() {
		UIActions.add(SHOW_CREDITS);
		UIActions.add(SHOW_MACRO);
		UIActions.add(SHOW_SETTINGS);
		UIActions.add(SHOW_LOGGER);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private int id;
	private String name; //Used in toString (populating combobox)
	private FileAmountPolicy policy;
	private String description;

	private SimpleUIAction(int id, String name, String description, FileAmountPolicy policy) {
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
	public FileAmountPolicy getPolicy() {
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
			ASS.showCredits();
		} else if (id == SHOW_MACRO.getId()) {
			ASS.showEditMacros();
		} else if (id == SHOW_SETTINGS.getId()) {
			ASS.showSettings();
		} else if (id == SHOW_LOGGER.getId()) {
			ASS.showLogger();
		} else {
			log.error(name, "Invalid object for perform SimpleUIAction");
		}

	}

	@Override
	public void unperform() {
		// TODO Auto-generated method stub
		
	}

}
