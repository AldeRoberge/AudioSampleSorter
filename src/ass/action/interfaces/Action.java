package ass.action.interfaces;

import java.io.Serializable;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ass.action.editeable.EditableProperty;
import ass.action.file.FileAmountPolicy;

/**
 * Action is a single Action (rename, move, etc) to be performed on a SoundPanel
 * (Sound).
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

	public abstract FileAmountPolicy getPolicy();

}
