package action.type.sound.imp;

import java.io.File;

import action.type.sound.FileAction;

public class PlayAction extends FileAction {

	@Override
	public String toString() {
		return "Play";
	}

	@Override
	public void undo() {
	}

	@Override
	public void perform(File p) {
		//p.sound.play();
	}

}
