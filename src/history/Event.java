package history;

import java.io.File;

import action.type.Action;

public class Event {

	boolean hasBeenUndone = false;
	
	public Action action;
	public File file;

	public Event(Action action, File file) { //
		this.action = action;
		this.file = file;
	}
	
	public Event(Action action) { //UI actions have no files
		this.action = action;
	}

}
