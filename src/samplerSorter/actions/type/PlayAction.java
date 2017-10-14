package samplerSorter.actions.type;

import samplerSorter.logger.Logger;

public class PlayAction implements Action {

	public String TAG = "PlayAction";

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
		return "Play sound";
	}

}
