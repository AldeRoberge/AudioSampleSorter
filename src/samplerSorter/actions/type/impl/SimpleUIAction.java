package samplerSorter.actions.type.impl;

import java.util.function.Function;

import samplerSorter.SamplerSorter;
import samplerSorter.UI.Sorter;
import samplerSorter.UI.SoundPanel;
import samplerSorter.actions.Action;
import samplerSorter.logger.Logger;

public class SimpleUIAction extends Action {

	public String TAG = "SimpleUIAction";

	//

	public static final SimpleUIAction SCROLL_UP = new SimpleUIAction(0, "Scroll Up");
	public static final SimpleUIAction SCROLL_DOWN = new SimpleUIAction(1, "Scroll Down");
	public static final SimpleUIAction SHOW_CREDITS_UI = new SimpleUIAction(2, "");
	public static final SimpleUIAction SHOW_MACRO_UI = new SimpleUIAction(3, "");
	public static final SimpleUIAction SHOW_SETTINGS_UI = new SimpleUIAction(4, "");
	public static final SimpleUIAction SHOW_CONSOLE = new SimpleUIAction(5, "");
	public static final SimpleUIAction SELECT_ALL = new SimpleUIAction(6, "");

	//

	public int ID;

	public SimpleUIAction(int ID, String name) {
		this.ID = ID;
	}

	@Override
	public void perform() {
		switch (ID) {

		case 0:
			sorter.scrollUp();
			break;

		case 1:
			sorter.scrollDown();
			break;

		case 2:
			samplerSorter.showEditMacrosUI();
			break;

		case 3:
			samplerSorter.showSettings();
			break;

		case 4:
			sorter.selectAll();
			break;

		default:
			Logger.logError(TAG, "Invalid ID " + ID + " for SimpleUIAction");
			break;

		}

	}

	@Override
	public void undo() {
		sorter.scrollUp();
	}

	@Override
	public String toString() {
		return "SimpleUIAction";
	}

}
