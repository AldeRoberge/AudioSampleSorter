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
	public static final StaticIcon DEFAULT_ICON = new StaticIcon("missing.png");

	public static final StaticIcon SOFTWARE_ICON = new StaticIcon("music-waves.png");
	public static final StaticIcon PLAY = new StaticIcon("play-sign.png");
	public static final StaticIcon ABOUT = new StaticIcon("question-sign.png");
	public static final StaticIcon MACROS = new StaticIcon("keyboard.png");
	public static final StaticIcon SETTINGS = new StaticIcon("cog-wheel-silhouette.png");
	public static final StaticIcon CONSOLE = new StaticIcon("terminal.png");
	public static final StaticIcon IMPORT = new StaticIcon("folder-plus.png");
	public static final StaticIcon PENCIL = new StaticIcon("edit-interface-sign.png"); //used by IconChooser

	//TODO
	public static final StaticIcon EXIT = new StaticIcon("exit.png"); //used by IconChooser

	//TODO
	public static final StaticIcon ICON_CHOOSER = new StaticIcon("cogs.png"); //used by IconChooser

	//TODO
	public static final StaticIcon LOADING_BAR = new StaticIcon("spinner-of-dots.png"); //used by IconChooser

	//TODO
	public static final StaticIcon CROSS = new StaticIcon("download.png"); //used by IconChooser

	//

	static ImageIcon createImageIcon(String path) {
		Logger.logInfo(TAG, "Getting icon " + path + ".");

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

	/**
	public static ImageIcon getIcon(String value) {
		ImageIcon key = DEFAULT_ICON.getImageIcon();
	
		if (value == null || value.equals("")) {
			Logger.logError(TAG, "getIconFromKey value is incorrect!" + value);
		} else {
			for (StaticIcon s : images) {
			
			
			for (Entry<ImageIcon, String> map : images.entrySet()) {
				if (map.getValue().equals(value)) {
					key = map.getKey();
				}
			}
		}
		return key;
	}
	
	public static ImageIcon getIconFromPath(String value) {
		ImageIcon key = DEFAULT_ICON.getImageIcon();
	
		if (value == null || value.equals("")) {
			Logger.logError(TAG, "getIconFromKey value is incorrect!" + value);
		} else {
			if (new File(value).exists()) {
				return new ImageIcon(value);
			}
		}
		return key;
	}
	
	
	public static String getIconPath(String value) {
		if (value == null || value.equals("")) {
			Logger.logError(TAG, "getIconPath value is incorrect!" + value);
		} else {
			for (Entry<ImageIcon, String> map : pathAndImage.entrySet()) {
				if (map.getValue().equals(value)) {
					return map.getValue();
				}
			}
		}
		return null;
	}*/

}
