package ass.keyboard.action.type.file.impl;

import java.io.File;
import java.util.ArrayList;

import ass.keyboard.action.editable.EditableProperty;
import ass.keyboard.action.type.file.FileAction;

public class RenameAction extends FileAction {

	@Override
	public String toString() {
		return "Rename";
	}

	private String TAG = "RenameAction";

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
	public void perform(File p) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getDescription() {
		return "Renames the selected file";
	}

	@Override
	public int getPolicy() {
		return FileAction.PERFORMED_ON_ONE_OR_MANY_FILES_ONLY_POLICY;
	}

}
