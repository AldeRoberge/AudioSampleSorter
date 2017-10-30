package action.type.sound.imp;

import GUI.soundpanel.SoundPanel;
import action.type.sound.SoundAction;
import logger.Logger;

public class PlayAction extends SoundAction {

	@Override
	public String toString() {
		return "Play";
	}

	@Override
	public void undo() {
	}

	@Override
	public void perform(SoundPanel p) {
		p.sound.play();
	}

}
