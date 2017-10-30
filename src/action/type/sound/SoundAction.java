package action.type.sound;

import GUI.soundpanel.SoundPanel;
import action.type.Action;

public abstract class SoundAction implements Action {

	//

	public abstract void perform(SoundPanel p);

	public abstract void undo();

}
