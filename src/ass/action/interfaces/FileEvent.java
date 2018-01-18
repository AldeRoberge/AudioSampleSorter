package ass.action.interfaces;

import java.io.File;

public class FileEvent {

	public File oldFile;
	public File newFile;

	public FileEvent(File oldFile, File newFile) {
		this.oldFile = oldFile;
		this.newFile = newFile;
	}

}
