package ass.keyboard.event;

import java.util.ArrayList;
import java.util.List;

import ass.keyboard.action.Action;

public class EventManager {

	public EventFrame eventFrame = new EventFrame(this);

	public static List<Action> events = new ArrayList<Action>();

	private static final String TAG = "EventManager";

	public static void performEvent(Action event) {

	}

}
