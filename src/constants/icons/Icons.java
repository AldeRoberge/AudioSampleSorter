package constants.icons;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import ui.FloatingLoadingBar;

public class Icons {

	//

	private static final String TAG = "Icons";

	//

	public static final String LOCATION_OF_ICONS = new File(".").getAbsolutePath() + "\\res\\icons\\";

	public static final String LOCATION_OF_SOFTWARE_ICONS = new File(".").getAbsolutePath() + "\\res\\";

	public static final ArrayList<UserIcon> images = new ArrayList<UserIcon>();

	static {

		FloatingLoadingBar loadingBar = new FloatingLoadingBar();

		File iconFolder = new File(LOCATION_OF_ICONS);
		File[] allIcons = iconFolder.listFiles();

		for (int i = 0; i < allIcons.length; i++) {
			loadingBar.setCurrentProgress(i, allIcons.length);

			images.add(new UserIcon(allIcons[i].getAbsolutePath()));
		}

		loadingBar.end();
	}

	//Returned in case everything fails;
	public static final UserIcon DEFAULT_ICON = new UserIcon(LOCATION_OF_ICONS + "missing.png");

	public static final UserIcon PLAY = new UserIcon(LOCATION_OF_ICONS + "play-sign.png");
	public static final UserIcon ABOUT = new UserIcon(LOCATION_OF_ICONS + "information-button.png");
	public static final UserIcon MACROS = new UserIcon(LOCATION_OF_ICONS + "keyboard.png");
	public static final UserIcon SETTINGS = new UserIcon(LOCATION_OF_ICONS + "cog-wheel-silhouette.png");
	public static final UserIcon LOGGER = new UserIcon(LOCATION_OF_ICONS + "terminal.png");
	public static final UserIcon IMPORT = new UserIcon(LOCATION_OF_ICONS + "folder-plus.png");
	public static final UserIcon PENCIL = new UserIcon(LOCATION_OF_ICONS + "edit-interface-sign.png");
	public static final UserIcon EXIT = new UserIcon(LOCATION_OF_ICONS + "exit.png");
	public static final UserIcon ICON_CHOOSER = new UserIcon(LOCATION_OF_ICONS + "cogs.png");
	public static final UserIcon LOADING_BAR = new UserIcon(LOCATION_OF_ICONS + "spinner-of-dots.png");
	public static final UserIcon CROSS = new UserIcon(LOCATION_OF_ICONS + "download.png");
	public static final UserIcon TRASH = new UserIcon(LOCATION_OF_ICONS + "trash.png");
	public static final UserIcon FOLDER_MINUS = new UserIcon(LOCATION_OF_ICONS + "folder-minus.png");
	public static final UserIcon OPEN_FOLDER = new UserIcon(LOCATION_OF_ICONS + "folder-open.png");

	public static final UserIcon BIG_ICON = new UserIcon(LOCATION_OF_SOFTWARE_ICONS + "icon_huge.png");
	public static final UserIcon SMALL_ICON = new UserIcon(LOCATION_OF_SOFTWARE_ICONS + "icon_small.png");

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
