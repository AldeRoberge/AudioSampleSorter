package util.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

public class MiddleOfTheScreen {

	static final Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();

	public static Point getLocationFor(int width, int height) {
		return new Point(screenDimension.width / 2 - width / 2, screenDimension.height / 2 - height / 2);
	}

	//Note : component.setLocation should be called after component.setBounds
	
	public static Point getLocationFor(Component e) {
		return new Point(screenDimension.width / 2 - e.getWidth() / 2, screenDimension.height / 2 - e.getHeight() / 2);
	}

}
