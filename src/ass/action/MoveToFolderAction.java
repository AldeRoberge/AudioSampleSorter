package ass.action;

import java.io.File;
import java.util.ArrayList;

import ass.action.editeable.EditableProperty;
import ass.action.interfaces.FileAction;
import ass.action.interfaces.FileEvent;


public class MoveToFolderAction extends FileAction {

	public static String TAG = "Move to folder";

	@Override
	public String toString() {
		return "Move to folder";
	}

	private EditableProperty<File> fileTest = new EditableProperty<>(new File("."), "Select folder");

	@Override
	public ArrayList<EditableProperty> getEditableProperties() {
		ArrayList<EditableProperty> editableProperties = new ArrayList<>();
		editableProperties.add(fileTest);
		return editableProperties;
	}

	@Override
	public String getDescription() {
		return "Moves the selected files to a folder.";
	}

	@Override
	public int getPolicy() {
		return FileAction.PERFORMED_ON_ONE_OR_MANY_FILES_ONLY_POLICY;
	}

	////////////////////////////////////////////////////////////////

	private File fileAffected;

	private String previousPath;

	@Override
	public FileEvent perform(File oldFile) {

		File folderToMoveTo = fileTest.getValue();

		if (fileTest.isDefaultValue() || !folderToMoveTo.exists() || !folderToMoveTo.isDirectory()) {
			log.info("Error with folder to move to!");

			if (fileTest.isDefaultValue()) {
				log.info("Folder is default value!");
			} else if (!folderToMoveTo.exists()) {
				log.info("Folder does not exist!");
			} else if (!folderToMoveTo.isDirectory()) {
				log.info("Folder is not a directory!");
			} else {
				log.info("Unknown error");
			}

			return null;
		} else {
			fileAffected = oldFile;
			previousPath = oldFile.getAbsolutePath();

			String newPath = fileTest.getValue().getAbsolutePath() + "\\" + oldFile.getName();

			return moveFileTo(oldFile, newPath);
		}

	}

	@Override
	public void unperform() {
		moveFileTo(fileAffected, previousPath);
	}

}
