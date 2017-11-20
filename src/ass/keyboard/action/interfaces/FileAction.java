package ass.keyboard.action.interfaces;

import java.io.File;

public abstract class FileAction implements Action, Cloneable {

	public abstract void perform(File p);

	@Override
	public FileAction clone() throws CloneNotSupportedException {
		return (FileAction) super.clone();
	}

}
