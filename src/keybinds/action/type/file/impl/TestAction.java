package keybinds.action.type.file.impl;

import java.io.File;
import java.util.ArrayList;

import keybinds.action.editeable.EditeableProperty;
import keybinds.action.type.file.FileAction;

public class TestAction extends FileAction {

	@Override
	public String toString() {
		return "Test";
	}

	private String TAG = "RenameAction";

	private EditeableProperty<Boolean> booleanTest = new EditeableProperty<>(true, "Prompt on rename");
	private EditeableProperty<String> stringTest = new EditeableProperty<>("Hey", "New name");
	private EditeableProperty<Integer> integerTest = new EditeableProperty<>(1, "Value");
	private EditeableProperty<File> fileTest = new EditeableProperty<>(new File("."), "File");

	@Override
	public ArrayList<EditeableProperty> getEditeableProperties() {
		ArrayList<EditeableProperty> editeableProperties = new ArrayList<EditeableProperty>();
		editeableProperties.add(booleanTest);
		editeableProperties.add(stringTest);
		editeableProperties.add(integerTest);
		editeableProperties.add(fileTest);

		return editeableProperties;
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}

	@Override
	public void perform(File p) {
	}

	@Override
	public String getDescription() {
		return "Test action used to test features";
	}

	@Override
	public boolean canBePerformedOnMultipleFiles() {
		return false;
	}

}
