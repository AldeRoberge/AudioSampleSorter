package ass.action;

import java.awt.Desktop;
import java.io.IOException;

import ass.action.interfaces.UIAction;

public class OpenContainingFolderAction extends UIAction {

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

		if (ASS.fileManager.selectedFiles.isEmpty()) {
			log.error("No selected files");
		} else {
			try {
				Desktop.getDesktop().open(ASS.fileManager.selectedFiles.get(0).getParentFile());
			} catch (IOException e) {
				log.error("Could not open file location");
				e.printStackTrace();
			}
		}

	}

	@Override
	public void unperform() {
	}

}
