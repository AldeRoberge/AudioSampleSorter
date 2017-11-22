package ass.keyboard.action;

import java.io.File;
import java.util.ArrayList;

import ass.keyboard.action.editable.EditableProperty;
import ass.keyboard.action.interfaces.FileAction;
import ass.keyboard.action.interfaces.FileEvent;

public class RenameAction extends FileAction {

	private String TAG = "Rename";

	@Override
	public String toString() {
		return TAG;
	}

	private EditableProperty<Boolean> promptOnRename = new EditableProperty<Boolean>(true, "Prompt on rename");
	private EditableProperty<String> newName = new EditableProperty<String>("NewFileName", "New name");

	@Override
	public ArrayList<EditableProperty> getEditableProperties() {
		ArrayList<EditableProperty> editableProperties = new ArrayList<EditableProperty>();
		editableProperties.add(promptOnRename);
		editableProperties.add(newName);
		return editableProperties;
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
		// TODO Auto-generated method stub
		return null;
	}

}
