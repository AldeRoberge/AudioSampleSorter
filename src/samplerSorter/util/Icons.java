package samplerSorter.util;

import java.awt.Image;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import samplerSorter.logger.Logger;

public class Icons {

	private static final String TAG = "Icons";

	private static final int W = 16;
	private static final int H = 16;

	//

	private static final String LOCATION = new File(".").getAbsolutePath() + "/res/icons/";

	public static final ImageIcon SETTINGS;
	public static final ImageIcon IMPORT;
	public static final ImageIcon ABOUT;
	public static final ImageIcon EXIT;
	public static final ImageIcon CONSOLE;
	public static final ImageIcon MACROS;

	static {
		SETTINGS = createImageIcon("cog.png");
		IMPORT = createImageIcon("folder-upload.png");
		ABOUT = createImageIcon("info.png");
		EXIT = createImageIcon("exit.png");
		CONSOLE = createImageIcon("menu.png");
		MACROS = createImageIcon("keyboard.png");
	}

	protected static ImageIcon createImageIcon(String path) {
		Logger.logInfo(TAG, "Getting icon " + LOCATION + path + ".");
		return scaleImage(new ImageIcon(LOCATION + path));
	}

	protected static ImageIcon scaleImage(ImageIcon icon) {
		int nw = icon.getIconWidth();
		int nh = icon.getIconHeight();

		if (icon.getIconWidth() > W) {
			nw = W;
			nh = (nw * icon.getIconHeight()) / icon.getIconWidth();
		}

		if (nh > H) {
			nh = H;
			nw = (icon.getIconWidth() * nh) / icon.getIconHeight();
		}

		return new ImageIcon(icon.getImage().getScaledInstance(nw, nh, Image.SCALE_SMOOTH));
	}

}
