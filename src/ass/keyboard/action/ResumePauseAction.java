package ass.keyboard.action;

import ass.keyboard.action.interfaces.UIAction;

public class ResumePauseAction extends UIAction {

	@Override
	public String toString() {
		if (isPlaying()) {
			return "Pause";
		} else {
			return "Resume";
		}
	}

	@Override
	public int getPolicy() {
		if (isPlaying()) {
			return UIAction.PERFORMED_ON_ZERO_TO_MANY_FILES_POLICY; //PAUSE POLICY
		} else {
			return UIAction.PERFORMED_ON_ONE_FILE_ONLY_POLICY; //RESUME POLICY
		}
	}

	@Override
	public String getDescription() {
		return "Play or pauses the sound.";
	}

	@Override
	public void perform() {
		ASS.resumeOrPauseSound();
	}

	public boolean isPlaying() {
		System.out.println(ASS);
		System.out.println(ASS.fMan);
		System.out.println(ASS.fMan.fileVisualiser);
		System.out.println(ASS.fMan.fileVisualiser.getAudioPlayer());
		
		return ASS.fMan.fileVisualiser.getAudioPlayer().isPlaying;
	}
	
	@Override
	public void unperform() {
		ASS.resumeOrPauseSound();
	}

}
