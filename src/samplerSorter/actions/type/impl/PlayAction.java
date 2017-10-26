package samplerSorter.actions.type.impl;

import samplerSorter.SamplerSorter;
import samplerSorter.UI.Sorter;
import samplerSorter.UI.SoundPanel;
import samplerSorter.actions.Action;
import samplerSorter.logger.Logger;

public class PlayAction extends Action {

	Sorter sorter;

	public String TAG = "PlayAction";
	
	@Override
	public void perform() {
		Logger.logInfo(TAG, "Played.");

		for (SoundPanel p : sorter.selectedSoundPanels) {
			p.play();
		}

	}

	@Override
	public void undo() {
		Logger.logInfo(TAG, "Undone");
	}

	@Override
	public String toString() {
		return "Play";
	}

}
