package constants;

import java.awt.Component;
import java.awt.Cursor;

public class CursorManager {

	public static final Cursor handCursor = new Cursor(Cursor.DEFAULT_CURSOR);
	public static final Cursor waitCursor = new Cursor(Cursor.WAIT_CURSOR);

	public static void changeCursor(Component component, Cursor cursor) {
		component.setCursor(cursor);
	}

}
