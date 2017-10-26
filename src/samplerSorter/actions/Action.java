package samplerSorter.actions;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JPanel;

import samplerSorter.actions.editeable.EditeableProperty;
import samplerSorter.logger.Logger;

/**
 * Action is a single Action (rename, move, etc) to be performed on a SoundPanel (Sound).
 * @author 4LDE
 *
 */
public abstract class Action implements Serializable {

	public abstract void perform();

	public abstract void undo();

	public abstract String toString();

	public abstract boolean isEditeable();

	public JPanel getEditPanel() {
		Logger.logError("Action", "This method should be implemented if isEditeable() is set to true");
		return null;
	}

	public abstract ArrayList<EditeableProperty> getEditeableProperties();

	/**
	 * Init() is called right before we do the action, we point to the required object (SoundPanel, SamplerSorter)
	 */

}
