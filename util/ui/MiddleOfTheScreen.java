package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

public class MiddleOfTheScreen {

	static final Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();

	/**
	 * Keep in mind that setLocation should be called AFTER setBounds!
	 */
	public static Point getMiddleOfScreenLocationFor(Component e) {
		return getMiddleOfScreenLocationFor(e.getWidth(), e.getHeight());
	}

	public static Point getMiddleOfScreenLocationFor(int width, int height) {
		return new Point(screenDimension.width / 2 - width / 2, screenDimension.height / 2 - height / 2);
	}

}
