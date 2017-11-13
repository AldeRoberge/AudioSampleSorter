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

	boolean hasBeenPerformed = false;

	public default ArrayList<EditableProperty> getEditableProperties() {
		return null;
	}

	/**
	 * perform() is implemented by FileAction and UIAction (classes implementing Action)
	 * Example : 
	 * FileAction = perform(File f);
	 * UIAction = perform();
	 */

	public abstract void unperform(); // "undo"

	/**
	 * Example of unperform() : 
	 * 
	 * public void unperform() {
	 *     audioPlayer.stop(file);
	 * }
	 */

	public abstract String toString(); // used by ComboBox inside MacorEditorUI

	public abstract String getDescription();

	public default boolean isEditable() { // used by MacroActionEditPanel (Edit
												// button) to know if there is
											// editableProperties inside the
											// action
		return (getEditableProperties() != null);
	}

}
