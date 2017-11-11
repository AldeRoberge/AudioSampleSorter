package history;

import java.util.ArrayList;
import java.util.List;

import action.type.Action;
import action.type.sound.FileAction;
import action.type.ui.UIAction;

public class EventManager {

	public EventFrame eventFrame = new EventFrame(this);

	public static List<Event> events = new ArrayList<Event>();

	private static final String TAG = "EventManager";

	public static void performEvent(Event event) {

		Action action = event.action;

	}

}
