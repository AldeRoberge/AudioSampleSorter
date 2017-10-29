package samplerSorter.action.type.sound;

import samplerSorter.GUI.soundpanel.SoundPanel;
import samplerSorter.action.type.Action;

public abstract class SoundAction implements Action {

	//

	public abstract void perform(SoundPanel p);

	public abstract void undo();

}
