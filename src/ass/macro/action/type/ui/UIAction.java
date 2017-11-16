package ass.macro.action.type.ui;

import ass.macro.action.Action;

public abstract class UIAction implements Action, Cloneable {

	public abstract void perform();

	@Override
	public UIAction clone() throws CloneNotSupportedException {
		return (UIAction) super.clone();
	}

}
