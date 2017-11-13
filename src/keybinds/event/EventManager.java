package keybinds.event;

import java.util.ArrayList;
import java.util.List;

import keybinds.action.Action;

public class EventManager {

	public EventFrame eventFrame = new EventFrame(this);

	public static List<Action> events = new ArrayList<Action>();

	private static final String TAG = "EventManager";

	public static void performEvent(Action event) {

	}

}
