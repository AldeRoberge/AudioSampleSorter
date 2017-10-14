package samplerSorter.actions.type;

import samplerSorter.logger.Logger;

public class MoveAction implements Action {

	public String TAG = "MoveAction";

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
		return "Move file";
	}

}
