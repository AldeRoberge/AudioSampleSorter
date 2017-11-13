package keybinds.action.type.file.impl;

import java.io.File;
import java.util.ArrayList;

import keybinds.action.editeable.EditeableProperty;
import keybinds.action.type.file.FileAction;

public class RenameAction extends FileAction {

	@Override
	public String toString() {
		return "Rename";
	}

	private String TAG = "RenameAction";

	private EditeableProperty<Boolean> promptOnRename = new EditeableProperty<Boolean>(true, "Prompt on rename");
	private EditeableProperty<String> newName = new EditeableProperty<String>("NewFileName", "New name");

	@Override
	public ArrayList<EditeableProperty> getEditeableProperties() {
		ArrayList<EditeableProperty> editeableProperties = new ArrayList<EditeableProperty>();
		editeableProperties.add(promptOnRename);
		editeableProperties.add(newName);
		return editeableProperties;
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub

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
	public boolean canBePerformedOnMultipleFiles() {
		return false;
	}

}
