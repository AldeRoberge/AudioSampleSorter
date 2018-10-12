package ass.action.file;

import java.io.File;
import java.util.ArrayList;

import ass.action.editeable.EditableProperty;
import ass.action.interfaces.FileAction;
import ass.action.interfaces.FileEvent;
import constants.library.ASSLibraryManager;


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
	public FileAmountPolicy getPolicy() {
		return FileAmountPolicy.PERFORMED_ON_ONE_OR_MANY_FILES_ONLY_POLICY;
	}

	////////////////////////////////////////////////////////////////

	private File fileAffected;

	private String previousPath;
	private String newPath;

	@Override
	public FileEvent perform(File oldFile) {

		fileAffected = oldFile;

		previousPath = oldFile.getAbsolutePath();
		newPath = ASSLibraryManager.getTrashFolder().getAbsolutePath() + "\\" + oldFile.getName();

		return moveFileTo(oldFile, newPath);
	}

	@Override
	public void unperform() {
		moveFileTo(fileAffected, previousPath);
	}

}
