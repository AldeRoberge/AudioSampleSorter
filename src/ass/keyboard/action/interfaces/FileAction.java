package ass.keyboard.action.interfaces;

import java.io.File;
import java.util.ArrayList;

import logger.Logger;

public abstract class FileAction implements Action, Cloneable {

	public ArrayList<File> filesAffected = new ArrayList<File>();

	public void perform(ArrayList<File> filesAffected) {

		if (!canBePerformedOnFiles(filesAffected.size())) {
			Logger.logError(this.toString(), "Can't perform action on " + filesAffected.size() + " files because of policy : " + Action.getPolicyAsString(getPolicy()));
		} else {
			this.filesAffected = filesAffected;
			perform();
		}
	}

	public abstract void perform();
	
	public abstract void unperform();

	@Override
	public FileAction clone() throws CloneNotSupportedException {
		return (FileAction) super.clone();
	}

}
