package samplerSorter.actions.type;

import java.util.ArrayList;

import samplerSorter.SamplerSorter;
import samplerSorter.UI.Sorter;
import samplerSorter.UI.SoundPanel;
import samplerSorter.actions.Action;
import samplerSorter.actions.editeable.EditeableProperty;
import samplerSorter.logger.Logger;

public class PlayAction extends Action {

	public String TAG = "PlayAction";

	Sorter sorter;

	public void init(SoundPanel p) {

	}

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

	@Override
	public boolean isEditeable() {
		return false;
	}

	@Override
	public ArrayList<EditeableProperty> getEditeableProperties() {
		return null;
	}

}
