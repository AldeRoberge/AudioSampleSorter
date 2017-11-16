package ass.icons;

import ui.FloatingLoadingBar;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class Icons {

	//

	private static final String TAG = "Icons";

	//

	public static final String LOCATION_OF_ICONS = new File(".").getAbsolutePath() + "/res/icons/";

	public static final ArrayList<StaticIcon> images = new ArrayList<StaticIcon>();

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

	public static final ImageIcon SOFTWARE_ICON = new ImageIcon("res/icon.png");
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

	static ImageIcon createImageIcon(String path, int height, int width) {
		return scaleImage(new ImageIcon(path), height, width);
	}

	private static ImageIcon scaleImage(ImageIcon icon, int height, int width) {
		int nw = icon.getIconWidth();
		int nh = icon.getIconHeight();

		if (icon.getIconWidth() > width) {
			nw = width;
			nh = (nw * icon.getIconHeight()) / icon.getIconWidth();
		}

		if (nh > height) {
			nh = height;
			nw = (icon.getIconWidth() * nh) / icon.getIconHeight();
		}

		return new ImageIcon(icon.getImage().getScaledInstance(nw, nh, Image.SCALE_SMOOTH));

	}

}
