package global.icons;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.ImageIcon;

import global.logger.Logger;
import keybinds.event.EventFrame;
import ui.FloatingLoadingBar;

public class Icons {

	//

	private static final String TAG = "Icons";

	//

	public static final String LOCATION_OF_ICONS = new File(".").getAbsolutePath() + "/res/icons/";

	public static final ArrayList<StaticIcon> images = new ArrayList<StaticIcon>();

	public static final ImageDimension defaultDimensions = new ImageDimension(16, 16);

	static { //We do this to be able to pick an image with IconChooser

		FloatingLoadingBar loadingBar = new FloatingLoadingBar();

		File iconFolder = new File(LOCATION_OF_ICONS);
		File[] allIcons = iconFolder.listFiles();

		for (int i = 0; i < allIcons.length; i++) {
			loadingBar.setCurrentProgress(i, allIcons.length);

			images.add(new StaticIcon(allIcons[i].getAbsolutePath()));
		}

		loadingBar.end();
	}

	//

	//Paths

	//Returned in case everything fails;
	public static final StaticIcon DEFAULT_ICON = new StaticIcon(LOCATION_OF_ICONS + "missing.png");

	public static final StaticIcon SOFTWARE_ICON = new StaticIcon(LOCATION_OF_ICONS + "music-waves.png");
	public static final StaticIcon PLAY = new StaticIcon(LOCATION_OF_ICONS + "play-sign.png");
	public static final StaticIcon ABOUT = new StaticIcon(LOCATION_OF_ICONS + "question-sign.png");
	public static final StaticIcon MACROS = new StaticIcon(LOCATION_OF_ICONS + "keyboard.png");
	public static final StaticIcon SETTINGS = new StaticIcon(LOCATION_OF_ICONS + "cog-wheel-silhouette.png");
	public static final StaticIcon CONSOLE = new StaticIcon(LOCATION_OF_ICONS + "terminal.png");
	public static final StaticIcon IMPORT = new StaticIcon(LOCATION_OF_ICONS + "folder-plus.png");
	public static final StaticIcon PENCIL = new StaticIcon(LOCATION_OF_ICONS + "edit-interface-sign.png");
	public static final StaticIcon EXIT = new StaticIcon(LOCATION_OF_ICONS + "exit.png");
	public static final StaticIcon ICON_CHOOSER = new StaticIcon(LOCATION_OF_ICONS + "cogs.png");
	public static final StaticIcon LOADING_BAR = new StaticIcon(LOCATION_OF_ICONS + "spinner-of-dots.png");
	public static final StaticIcon CROSS = new StaticIcon(LOCATION_OF_ICONS + "download.png");

	//

	static ImageIcon createImageIcon(String path) {
		return scaleImage(new ImageIcon(path), defaultDimensions);
	}

	static ImageIcon createImageIcon(String path, ImageDimension d) {
		Logger.logInfo(TAG, "Getting icon " + path + ".");

		return scaleImage(new ImageIcon(path), d);
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
