package ass.keyboard.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import ass.keyboard.action.editable.EditableProperty;
import ass.keyboard.action.interfaces.FileAction;
import ass.keyboard.action.interfaces.FileEvent;
import constants.library.LibraryManager;
import logger.Logger;

public class DeleteAction extends FileAction {

	@Override
	public String toString() {
		return "Delete";
	}

	@Override
	public ArrayList<EditableProperty> getEditableProperties() {
		return null;
	}

	@Override
	public String getDescription() {
		return "Sends the selected files to the trash.";
	}

	@Override
	public int getPolicy() {
		return FileAction.PERFORMED_ON_ONE_OR_MANY_FILES_ONLY_POLICY;
	}

	////////////////////////////////////////////////////////////////

	private File fileAffected;

	private String previousPath;
	private String newPath;

	@Override
	public FileEvent perform(File oldFile) {

		fileAffected = oldFile;

		previousPath = oldFile.getAbsolutePath();
		newPath = LibraryManager.getTrashFolder().getAbsolutePath() + "\\" + oldFile.getName();

		return moveFileTo(oldFile, newPath);
	}

	@Override
	public void unperform() {
		moveFileTo(fileAffected, previousPath);
	}

}
