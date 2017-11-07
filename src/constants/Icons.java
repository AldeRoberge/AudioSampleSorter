package constants;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import logger.Logger;
import util.icon.ImageDimension;

public class Icons {

	private static final String TAG = "Icons";

	private static final String LOCATION = new File(".").getAbsolutePath() + "/res/icons/";

	public static final ImageIcon SOFTWARE_ICON;

	public static final ImageIcon SETTINGS;
	public static final ImageIcon IMPORT;
	public static final ImageIcon ABOUT;
	public static final ImageIcon EXIT;
	public static final ImageIcon CONSOLE;
	public static final ImageIcon MACROS;
	public static final ImageIcon DOT;
	public static final ImageIcon QUESTION;

	public static ImageDimension cursorDimension = new ImageDimension(20, 20);
	public static ImageDimension defaultDimensions = new ImageDimension(16, 16);
	public static ImageDimension softwareIconDimensions = new ImageDimension(64, 64);

	static {
		SETTINGS = createImageIcon("cog.png");
		IMPORT = createImageIcon("folder-upload.png");
		ABOUT = createImageIcon("info.png");
		EXIT = createImageIcon("exit.png");
		CONSOLE = createImageIcon("menu.png");
		MACROS = createImageIcon("keyboard.png");
		SOFTWARE_ICON = createImageIcon("software_icon.png", softwareIconDimensions);
		DOT = createImageIcon("dot.png", new ImageDimension(10, 10));
		QUESTION = createImageIcon("question_mark.png");
	}

	private static ImageIcon createImageIcon(String path) {
		Logger.logInfo(TAG, "Getting icon " + LOCATION + path + ".");

		return scaleImage(new ImageIcon(LOCATION + path), defaultDimensions);
	}

	private static ImageIcon createImageIcon(String path, ImageDimension d) {
		Logger.logInfo(TAG, "Getting icon " + LOCATION + path + ".");

		return scaleImage(new ImageIcon(LOCATION + path), d);
	}

	private static ImageIcon scaleImage(ImageIcon icon, ImageDimension d) {
		int nw = icon.getIconWidth();
		int nh = icon.getIconHeight();

		if (icon.getIconWidth() > d.getWidth()) {
			nw = d.getWidth();
			nh = (nw * icon.getIconHeight()) / icon.getIconWidth();
		}

		if (nh > d.getHeight()) {
			nh = d.getHeight();
			nw = (icon.getIconWidth() * nh) / icon.getIconHeight();
		}

		return new ImageIcon(icon.getImage().getScaledInstance(nw, nh, Image.SCALE_SMOOTH));
	}

}
