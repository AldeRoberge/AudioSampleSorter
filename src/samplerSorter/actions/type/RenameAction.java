package samplerSorter.actions.type;

import java.io.File;
import java.util.ArrayList;

import samplerSorter.UI.Sorter;
import samplerSorter.actions.Action;
import samplerSorter.actions.editeable.EditeableProperty;
import samplerSorter.logger.Logger;

public class RenameAction extends Action {

	public String TAG = "RenameAction";

	Sorter sorter;

	public EditeableProperty<Boolean> promptOnRename = new EditeableProperty<Boolean>(true, "Prompt on rename");
	public EditeableProperty<String> newName = new EditeableProperty<String>("default", "New name");

	public void init(File f) {

	}

	@Override
	public void perform() {
		Logger.logInfo(TAG, "Renamed new name : " + newName);
	}

	@Override
	public void undo() {
		Logger.logInfo(TAG, "Undone");
	}

	@Override
	public String toString() {
		return "Rename";
	}

	@Override
	public boolean isEditeable() {
		return true;
	}

	@Override
	public ArrayList<EditeableProperty> getEditeableProperties() {
		ArrayList<EditeableProperty> editeableProperties = new ArrayList<EditeableProperty>();
		editeableProperties.add(promptOnRename);
		editeableProperties.add(newName);
		return editeableProperties;
	}

}
