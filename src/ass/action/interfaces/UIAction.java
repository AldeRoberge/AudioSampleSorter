package ass.action.interfaces;

import ass.ASS;

public abstract class UIAction implements Action, Cloneable {

	//If ASS is null, it might be because you forgot to call init() in MacroLoader

	public static ASS ASS;

	public abstract void perform();

	public abstract void unperform();

	@Override
	public UIAction clone() throws CloneNotSupportedException {
		return (UIAction) super.clone();
	}

}
