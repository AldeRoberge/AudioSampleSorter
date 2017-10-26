package samplerSorter.properties;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

public class RelativeLocationManager {

	public static Point getRelativeLocationToScreenProperty() {
		int savedLocX = Properties.LOCATION_ON_SCREEN_X.getValueAsInt();
		int savedLocY = Properties.LOCATION_ON_SCREEN_Y.getValueAsInt();

		if ((savedLocX == 0) && (savedLocY == 0)) { // location is not set

			// create new location (of the middle of the screen) based on screen
			// size

			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int newLocX = screenSize.width / 2;
			int newLocY = screenSize.height / 2;

			Properties.LOCATION_ON_SCREEN_X.setNewValue(newLocX + "");
			Properties.LOCATION_ON_SCREEN_Y.setNewValue(newLocY + "");

			return new Point(newLocX, newLocY);

		}
		return new Point(savedLocX, savedLocY);

	}

}
