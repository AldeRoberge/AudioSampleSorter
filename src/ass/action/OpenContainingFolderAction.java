package ass.action;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.omg.Messaging.SyncScopeHelper;

import ass.action.editeable.EditableProperty;
import ass.action.interfaces.FileAction;
import ass.action.interfaces.FileEvent;
import ass.action.interfaces.UIAction;
import file.FileNameUtil;
import logger.Logger;

public class OpenContainingFolderAction extends UIAction {

	private static final String TAG = null;

	@Override
	public int getPolicy() {
		return UIAction.PERFORMED_ON_ONE_FILE_ONLY_POLICY;
	}

	@Override
	public String getDescription() {
		return "Opens the location of the file";
	}

	@Override
	public String toString() {
		return "Open Location in Explorer";
	}

	@Override
	public void perform() {

		if (ASS.fileBro.selectedFiles.isEmpty()) {
			Logger.logError(TAG, "No selected files");
		} else {
			try {
				Desktop.getDesktop().open(ASS.fileBro.selectedFiles.get(0).getParentFile());
			} catch (IOException e) {
				Logger.logError(TAG, "Could not open file location");
				e.printStackTrace();
			}
		}

	}

	@Override
	public void unperform() {
	}

}
