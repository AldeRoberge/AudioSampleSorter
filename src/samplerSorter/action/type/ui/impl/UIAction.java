package samplerSorter.action.type.ui.impl;

import samplerSorter.GUI.soundpanel.SoundPanel;
import samplerSorter.action.type.Action;
import samplerSorter.history.Event;

/**
 * Action is a single Action (rename, move, etc) to be performed on a SoundPanel (Sound).
 * @author 4LDE
 * 
 * Don,t forget to hardcode them inside ActionManager
 *
 */
public abstract class UIAction implements Action {

	public abstract void perform(); //UI is called statically

	public abstract void undo();

}
