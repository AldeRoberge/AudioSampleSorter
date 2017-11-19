package ass.keyboard.action.type.file;

import java.io.File;

import ass.keyboard.action.Action;

public abstract class FileAction implements Action, Cloneable {

	public abstract void perform(File p);

	@Override
	public FileAction clone() throws CloneNotSupportedException {
		return (FileAction) super.clone();
	}

}
