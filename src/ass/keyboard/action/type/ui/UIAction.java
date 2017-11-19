package ass.keyboard.action.type.ui;

import ass.keyboard.action.Action;
import ass.ASS;

public abstract class UIAction implements Action, Cloneable {

	public ASS ASS;

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
