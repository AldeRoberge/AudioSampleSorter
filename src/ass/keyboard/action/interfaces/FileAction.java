package ass.keyboard.action.interfaces;

import java.io.File;

public abstract class FileAction implements Action, Cloneable {

	/**
	 * Do not use this method, use perform() instead
	 */
	public abstract FileEvent perform(File fileAffected);

	public abstract void unperform();

	@Override
	public FileAction clone() throws CloneNotSupportedException {
		return (FileAction) super.clone();
	}

}
