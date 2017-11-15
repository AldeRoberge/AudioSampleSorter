package macro.action.type.file.impl;

import java.io.File;

import macro.action.type.file.FileAction;

public class PlayAction extends FileAction {

	@Override
	public String toString() {
		return "Play";
	}

	public String TAG = "PlayAction";

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

	@Override
	public void unperform() {
	}

}
