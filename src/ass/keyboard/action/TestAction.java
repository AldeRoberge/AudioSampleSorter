package ass.keyboard.action;

import java.io.File;
import java.util.ArrayList;

import ass.keyboard.action.editable.EditableProperty;
import ass.keyboard.action.interfaces.FileAction;

public class TestAction extends FileAction {

	private String TAG = "TEST";

	@Override
	public String toString() {
		return TAG;
	}

	private EditableProperty<Boolean> booleanTest = new EditableProperty<>(true, "Prompt on rename");
	private EditableProperty<String> stringTest = new EditableProperty<>("Hey", "New name");
	private EditableProperty<Integer> integerTest = new EditableProperty<>(1, "Value");
	private EditableProperty<File> fileTest = new EditableProperty<>(new File("."), "File");

	@Override
	public ArrayList<EditableProperty> getEditableProperties() {
		ArrayList<EditableProperty> editableProperties = new ArrayList<EditableProperty>();
		editableProperties.add(booleanTest);
		editableProperties.add(stringTest);
		editableProperties.add(integerTest);
		editableProperties.add(fileTest);

		return editableProperties;
	}

	@Override
	public void perform() {
	}

	@Override
	public String getDescription() {
		return "Test action used to test features";
	}

	@Override
	public int getPolicy() {
		return FileAction.PERFORMED_ON_ONE_OR_MANY_FILES_ONLY_POLICY;
	}

	@Override
	public void unperform() {
		// TODO Auto-generated method stub
		
	}

}
