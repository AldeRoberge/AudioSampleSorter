package samplerSorter.action.type.sound.impl;

import samplerSorter.GUI.soundpanel.SoundPanel;
import samplerSorter.action.type.sound.SoundAction;
import samplerSorter.logger.Logger;

public class PlayAction extends SoundAction {

	@Override
	public String toString() {
		return "Play";
	}

	private String TAG = "PlayAction";

	@Override
	public void undo() {
	}

	@Override
	public void perform(SoundPanel p) {
		Logger.logInfo(TAG, "Played.");

		p.playOrPause();
	}

}
