package keybinds.action.type.ui;

import keybinds.action.Action;

public abstract class UIAction implements Action, Cloneable {

	public abstract void perform();

	@Override
	public UIAction clone() throws CloneNotSupportedException {
		return (UIAction) super.clone();
	}

}
