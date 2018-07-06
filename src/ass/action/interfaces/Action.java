package ass.action.interfaces;

import java.io.Serializable;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ass.action.editeable.EditableProperty;

/**
 * Action is a single Action (rename, move, etc) to be performed on a SoundPanel
 * (Sound).
 * 
 * @author 4LDE
 * 
 *         Don't forget to hardcode them inside ActionManager
 *
 */
public interface Action extends Serializable {

	Logger log = LoggerFactory.getLogger(Action.class);

	boolean hasBeenPerformed = false;

	public default boolean hasEditeableProperties() {
		return !(getEditableProperties() == null);
	}

	public default ArrayList<EditableProperty> getEditableProperties() {
		return null;
	}

	public abstract String toString(); // used by ComboBox inside MacorEditorUI, implemented by FileAction and UIAction

	public abstract String getDescription();

	/**	used by MacroActionEditPanel (Editbutton) to know if there is
		editableProperties inside the action
	 */

	public default boolean isEditable() {
		return (getEditableProperties() != null);
	}

	//File policy (if it can be performed on the current amount of selected files in FileManager (ex : Play cant be performed on more than 1 file))

	public static final int PERFORMED_ON_ZERO_FILES_ONLY_POLICY = -2; //0 selected files
	public static final int PERFORMED_ON_ONE_FILE_ONLY_POLICY = -1; //1 selected file
	public static final int PERFORMED_ON_ZERO_TO_MANY_FILES_POLICY = 0; //0 to infinity selected files
	public static final int PERFORMED_ON_ONE_OR_MANY_FILES_ONLY_POLICY = 1; //1 to infinity selected files
	public static final int PERFORMED_ON_MANY_FILES_ONLY_POLICY = 2; //2 to infinity selected files

	//Must return one of the above
	public abstract int getPolicy();

	public default boolean canBePerformedOnFiles(int numberOfFiles) {
		switch (getPolicy()) {
		case PERFORMED_ON_ZERO_FILES_ONLY_POLICY:
			return numberOfFiles == 0;
		case PERFORMED_ON_ONE_FILE_ONLY_POLICY:
			return numberOfFiles == 1;
		case PERFORMED_ON_ZERO_TO_MANY_FILES_POLICY:
			return numberOfFiles >= 0;
		case PERFORMED_ON_ONE_OR_MANY_FILES_ONLY_POLICY:
			return numberOfFiles >= 1;
		case PERFORMED_ON_MANY_FILES_ONLY_POLICY:
			return numberOfFiles >= 2;

		default:
			log.error("FileAction", "Invalid performed on files policy : " + getPolicy());
			return false;
		}
	}

	public static String getPolicyAsString(int highestPolicy) {
		switch (highestPolicy) {
		case PERFORMED_ON_ZERO_FILES_ONLY_POLICY:
			return "no files";
		case PERFORMED_ON_ONE_FILE_ONLY_POLICY:
			return "one file";
		case PERFORMED_ON_ZERO_TO_MANY_FILES_POLICY:
			return "any number of files";
		case PERFORMED_ON_ONE_OR_MANY_FILES_ONLY_POLICY:
			return "one or more files";
		case PERFORMED_ON_MANY_FILES_ONLY_POLICY:
			return "more than two files";

		default:
			log.error("FileAction", "Invalid getPolicyAsString policy : " + highestPolicy);
			return "invalid policy " + highestPolicy;
		}
	}

}
