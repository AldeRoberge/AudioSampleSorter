package ass.keyboard.action.type.file.impl;

import java.io.File;

import ass.keyboard.action.type.file.FileAction;

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
	public int getPolicy() {
		return FileAction.PERFORMED_ON_ONE_FILE_ONLY_POLICY;
	}

}
