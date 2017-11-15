package macro.action.type.ui;

import macro.action.Action;

public abstract class UIAction implements Action, Cloneable {

	public abstract void perform();

	@Override
	public UIAction clone() throws CloneNotSupportedException {
		return (UIAction) super.clone();
	}

}
