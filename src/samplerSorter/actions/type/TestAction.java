package samplerSorter.actions.type;

import java.io.File;
import java.util.ArrayList;

import samplerSorter.UI.Sorter;
import samplerSorter.actions.Action;
import samplerSorter.actions.editeable.EditeableProperty;
import samplerSorter.logger.Logger;

public class TestAction extends Action {

	public String TAG = "RenameAction";

	Sorter sorter;

	public EditeableProperty<Boolean> booleanTest = new EditeableProperty<>(true, "Prompt on rename");
	public EditeableProperty<String> stringTest = new EditeableProperty<>("Hey", "New name");
	public EditeableProperty<Integer> integerTest = new EditeableProperty<>(1, "Value");
	public EditeableProperty<File> fileTest = new EditeableProperty<>(new File("."), "File");

	public void init(File f) {

	}

	@Override
	public void perform() {
		Logger.logInfo(TAG, "Renamed new name : ");
	}

	@Override
	public void undo() {
		Logger.logInfo(TAG, "Undone");
	}

	@Override
	public String toString() {
		return "TestAction";
	}

	@Override
	public boolean isEditeable() {
		return true;
	}

	@Override
	public ArrayList<EditeableProperty> getEditeableProperties() {
		ArrayList<EditeableProperty> editeableProperties = new ArrayList<EditeableProperty>();
		editeableProperties.add(booleanTest);
		editeableProperties.add(stringTest);
		editeableProperties.add(integerTest);
		editeableProperties.add(fileTest);

		return editeableProperties;
	}

}
