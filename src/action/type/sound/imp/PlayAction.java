package action.type.sound.imp;

import java.io.File;

import action.type.sound.FileAction;

public class PlayAction extends FileAction {

	@Override
	public String toString() {
		return "Play";
	}

	public String TAG = "PlayAction";

	@Override
	public void undo() {
	}

	@Override
	public void perform(File p) {
		//p.sound.play();
	}

	@Override
	public String getDescription() {
		return "Plays the selected sound";
	}

	@Override
	public boolean canBePerformedOnMultipleFiles() {
		return false;
	}

}
