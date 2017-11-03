package action.type.sound.imp;

import action.type.sound.SoundAction;
import logger.Logger;
import sorter.soundPanel.SoundPanel;

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
