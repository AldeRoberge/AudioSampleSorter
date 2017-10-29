package samplerSorter.action.type.sound.impl;

import java.io.File;
import java.util.ArrayList;

import samplerSorter.GUI.Sorter;
import samplerSorter.action.editable.EditeableProperty;
import samplerSorter.action.type.Action;
import samplerSorter.logger.Logger;

public class RenameAction implements Action {

	@Override
	public String toString() {
		return "Rename";
	}

	private String TAG = "RenameAction";

	Sorter sorter;

	private EditeableProperty<Boolean> promptOnRename = new EditeableProperty<Boolean>(true, "Prompt on rename");
	private EditeableProperty<String> newName = new EditeableProperty<String>("default", "New name");

	public void init(File f) {

	}


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

}
