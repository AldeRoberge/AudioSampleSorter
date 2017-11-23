package ass.keyboard.action;

import java.io.File;
import java.util.ArrayList;

import org.omg.Messaging.SyncScopeHelper;

import ass.keyboard.action.editable.EditableProperty;
import ass.keyboard.action.interfaces.FileAction;
import ass.keyboard.action.interfaces.FileEvent;
import ass.keyboard.action.interfaces.UIAction;
import file.FileNameUtil;

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

	private ArrayList<File> filesRemoved = new ArrayList<File>();

	@Override
	public void perform() {

		filesRemoved = ASS.fMan.selectedFiles;
		ASS.fMan.removeFiles(filesRemoved);

	}

	@Override
	public void unperform() {

		ASS.fMan.importFiles(filesRemoved);

	}

}
