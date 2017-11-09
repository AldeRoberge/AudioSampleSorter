package action.type.ui;

import action.type.Action;

/**
 * Action is a single Action (rename, move, etc) to be performed on a SoundPanel (Sound).
 * @author 4LDE
 * 
 * Don,t forget to hardcode them inside ActionManager
 *
 */
public abstract class UIAction implements Action, Cloneable {

	public abstract void perform(); //UI is called statically

	public abstract void undo();

	@Override
	public UIAction clone() throws CloneNotSupportedException {
		return (UIAction) super.clone();

		/** equivalent of
		 * UIAction cloneA = (UIAction) super.clone();
		 return cloneA;
		 */
	}

}
