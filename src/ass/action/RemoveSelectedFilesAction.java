package ass.action;

import java.io.File;
import java.util.ArrayList;

import ass.action.interfaces.UIAction;

public class RemoveSelectedFilesAction extends UIAction {

	@Override
	public int getPolicy() {
		return UIAction.PERFORMED_ON_ONE_OR_MANY_FILES_ONLY_POLICY;
	}

	@Override
	public String getDescription() {
		return "Removes the files *(Does not delete from disk)";
	}
	
	@Override
	public String toString() {
		return "Remove Selected Files";
	}

	private ArrayList<File> filesRemoved = new ArrayList<>();

	@Override
	public void perform() {

		filesRemoved = ASS.fileBro.selectedFiles;
		ASS.fileBro.removeFiles(filesRemoved);

	}

	@Override
	public void unperform() {
		ASS.fileBro.addFiles(filesRemoved);
		
	}

}
