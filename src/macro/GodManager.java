package macro;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import GUI.Sorter;
import GUI.SorterUI;
import GUI.soundpanel.SoundPanel;
import action.type.Action;
import action.type.sound.SoundAction;
import action.type.ui.UIAction;

public class GodManager {

	public Sorter sorter;

	public GlobalMacroKeyListener globalKeyListener = GlobalMacroKeyListener.get();

	public GodManager(Sorter sorter) {
		this.sorter = sorter;
		globalKeyListener.init(this);
	}

	public boolean shiftIsPressed() {
		return globalKeyListener.keyIsPressed(KeyEvent.VK_SHIFT);
	}

	public boolean ctrlIsPressed() {
		return globalKeyListener.keyIsPressed(KeyEvent.VK_CONTROL);
	}

	public void performActions(ArrayList<Action> actionsToPerform) {

		System.out.println("Action(s) to perform : " + actionsToPerform.size());
		
		for (Action action : actionsToPerform) {

			if (action instanceof UIAction) {

				System.out.println("Action is instanceof UIAction");
				
				UIAction act = (UIAction) action;

				UIAction clonedAction = null;
				try {
					clonedAction = act.clone();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}

				clonedAction.perform();

			} else if (action instanceof SoundAction) {
				
				System.out.println("Action is instanceof SoundAction");

				SoundAction act = (SoundAction) action;

				SoundAction clonedAction = null;
				try {
					clonedAction = act.clone();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}

				for (SoundPanel sp : sorter.soundPanels) {
					clonedAction.perform(sp);
				}

			}
		}
	}

}
