package action.type.sound;

import java.io.File;

import action.type.Action;

public abstract class FileAction implements Action, Cloneable {

	public abstract void perform(File p);

	public abstract boolean canBePerformedOnMultipleFiles();

	@Override
	public FileAction clone() throws CloneNotSupportedException {
		return (FileAction) super.clone();
	}

}
