package ass.action.file;

import java.io.File;
import java.util.ArrayList;

import ass.action.editeable.EditableProperty;
import ass.action.interfaces.FileAction;
import ass.action.interfaces.FileEvent;

/**
 * Test of all editeable Properties (boolean, string, integer, filetest)
 */
public class TestAction extends FileAction {

	@Override
	public String toString() {
		String TAG = "TEST";
		return TAG;
	}

	private EditableProperty<Boolean> booleanTest = new EditableProperty<>(true, "Prompt on rename");
	private EditableProperty<String> stringTest = new EditableProperty<>("Hey", "New name");
	private EditableProperty<Integer> integerTest = new EditableProperty<>(1, "Value");
	private EditableProperty<File> fileTest = new EditableProperty<>(new File("."), "File");

	@Override
	public ArrayList<EditableProperty> getEditableProperties() {
		ArrayList<EditableProperty> editableProperties = new ArrayList<>();
		editableProperties.add(booleanTest);
		editableProperties.add(stringTest);
		editableProperties.add(integerTest);
		editableProperties.add(fileTest);

		return editableProperties;
	}

	@Override
	public String getDescription() {
		return "Test action used to test features";
	}

	@Override
	public FileAmountPolicy getPolicy() {
		return FileAmountPolicy.PERFORMED_ON_ONE_OR_MANY_FILES_ONLY_POLICY;
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
