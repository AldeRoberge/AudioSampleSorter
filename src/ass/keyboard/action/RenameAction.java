package ass.keyboard.action;

import java.io.File;
import java.util.ArrayList;

import org.omg.Messaging.SyncScopeHelper;

import ass.keyboard.action.editable.EditableProperty;
import ass.keyboard.action.interfaces.FileAction;
import ass.keyboard.action.interfaces.FileEvent;
import file.FileNameUtil;

public class RenameAction extends FileAction {

	private String TAG = "Rename";

	@Override
	public String toString() {
		return TAG;
	}

	@Override
	public ArrayList<EditableProperty> getEditableProperties() {
		return null;
	}

	@Override
	public String getDescription() {
		return "Renames the selected file";
	}

	@Override
	public int getPolicy() {
		return FileAction.PERFORMED_ON_ONE_OR_MANY_FILES_ONLY_POLICY;
	}

	@Override
	public void unperform() {
		// TODO Auto-generated method stub
	}

	@Override
	public FileEvent perform(File fileAffected) {
		return rename(fileAffected, "newName");
	}

	public FileEvent rename(File file, String newName) {
		String basePath = file.getParent();

		String oldFileName = file.getName();
		String newFileName = newName + FileNameUtil.getExtension(file.getName());

		System.out.println("File name : " + file.getName());

		String newPath = basePath + "\\" + newFileName;

		System.out.println("Old : " + basePath + file.getName());
		System.out.println("New : " + newPath);

		File oldFile = file;
		File newFile = new File(newPath);

		if (file.renameTo(newFile)) {
			System.out.println("Success...");
			return new FileEvent(oldFile, newFile);
		} else {
			System.err.println("NOT Success...");
			return new FileEvent(oldFile, oldFile);
		}

	}

}
