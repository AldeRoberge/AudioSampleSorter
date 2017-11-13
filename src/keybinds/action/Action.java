package keybinds.action;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import keybinds.action.editable.EditableProperty;

/**
 * Action is a single Action (rename, move, etc) to be performed on a SoundPanel
 * (Sound).
 * 
 * @author 4LDE
 * 
 *         Don,t forget to hardcode them inside ActionManager
 *
 */
public interface Action extends Serializable {

	public static final int STATE_WAITING = 0;
	public static final int STATE_DONE = 1;
	public static final int STATE_UNDONE = 2;

	public default ArrayList<EditableProperty> getEditableProperties() {
		return null;
	}

	public File file = new File("NULL");

	boolean hasBeenUndone = false;

	public abstract void undo();

	public abstract String toString(); // used by ComboBox inside MacorEditorUI

	public abstract String getDescription();

	public default boolean isEditable() { // used by MacroActionEditPanel (Edit
											// button) to know if there is
											// editableProperties inside the
											// action
		return (getEditableProperties() != null);
	}

}
