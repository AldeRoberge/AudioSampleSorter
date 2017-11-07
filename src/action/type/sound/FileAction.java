package action.type.sound;

import java.io.File;

import action.type.Action;

public abstract class FileAction implements Action, Cloneable {

	public abstract void perform(File p);

	public abstract void undo();

	@Override
	public FileAction clone() throws CloneNotSupportedException {
		FileAction cloneA = (FileAction) super.clone();
		return cloneA;
	}

}
