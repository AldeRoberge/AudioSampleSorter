package samplerSorter.actions;

import java.io.Serializable;

import samplerSorter.SamplerSorter;
import samplerSorter.UI.Sorter;
import samplerSorter.UI.SoundPanel;

/**
 * Action is a single Action (rename, move, etc) performed on a SoundPanel (Sound).
 * Once performed, its set in a locked and can only be undone.
 * @author 4LDE
 *
 */
public abstract class Action implements Serializable {

	public static final String TAG = "Action";
	
	public abstract void perform();

	public abstract void undo();

	public abstract String toString();
	
}
