package samplerSorter.action.type;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JPanel;

import samplerSorter.action.editable.EditeableProperty;
import samplerSorter.logger.Logger;

/**
 * Action is a single Action (rename, move, etc) to be performed on a SoundPanel (Sound).
 * @author 4LDE
 * 
 * Don,t forget to hardcode them inside ActionManager
 *
 */
public interface Action extends Serializable {

	public default ArrayList<EditeableProperty> getEditeableProperties() {
		return null;
	}

	public abstract void undo();

	public abstract String toString(); //used by ComboBox inside MacorEditorUI

	public default boolean isEditeable() { //used by MacroActionEditPanel (Edit button) to know if there is editeableProperties inside the action
		return (getEditeableProperties() != null);
	}

}
