package action.type.sound.imp;

import java.io.File;
import java.util.ArrayList;

import GUI.Sorter;
import GUI.soundpanel.SoundPanel;
import action.editable.EditeableProperty;
import action.type.Action;
import action.type.sound.SoundAction;

public class TestAction extends SoundAction {

	@Override
	public String toString() {
		return "TestAction";
	}

	private String TAG = "RenameAction";

	Sorter sorter;

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
	public void perform(SoundPanel p) {
		System.out.println("Hey hey hey!");
	}

}
