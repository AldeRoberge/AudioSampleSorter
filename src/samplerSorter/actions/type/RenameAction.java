package samplerSorter.actions.type;

import samplerSorter.logger.Logger;

public class RenameAction implements Action {

	public String TAG = "RenameAction";

	@Override
	public void perform() {
		Logger.logInfo(TAG, "Performed");
	}

	@Override
	public void undo() {
		Logger.logInfo(TAG, "Undone");
	}

	@Override
	public String toString() {
		return "Rename file";
	}

}
