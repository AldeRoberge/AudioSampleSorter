package ass.keyboard.action.interfaces;

import ass.ASS;

public abstract class UIAction implements Action, Cloneable {

	protected ASS ASS;

	public void init(ASS s) {
		this.ASS = s;
	}

	public abstract void perform();

	public abstract void unperform();

	@Override
	public UIAction clone() throws CloneNotSupportedException {
		return (UIAction) super.clone();
	}

}
