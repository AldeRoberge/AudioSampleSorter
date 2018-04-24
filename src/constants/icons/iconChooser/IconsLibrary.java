package constants.icons.iconChooser;

import java.io.File;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ass.keyboard.macro.MacroAction;
import ui.FloatingLoadingBar;

public class IconsLibrary {

	static Logger log = LoggerFactory.getLogger(IconsLibrary.class);

	public static final String LOCATION_OF_ICONS = new File(".").getAbsolutePath() + "\\res\\icons\\";

	public static final ArrayList<UserIcon> userIcons = new ArrayList<>();

	static {
		loadAllIcons();
	}

	public static void loadAllIcons() {
		FloatingLoadingBar loadingBar = new FloatingLoadingBar();

		File iconFolder = new File(LOCATION_OF_ICONS);
		File[] allIcons = iconFolder.listFiles();

		for (int i = 0; i < allIcons.length; i++) {
			loadingBar.setCurrentProgress(i, allIcons.length);

			userIcons.add(new UserIcon(allIcons[i].getAbsolutePath()));
		}

		loadingBar.end();
	}

	public static UserIcon getImageIcon(String keyword) {
		for (UserIcon u : userIcons) {
			if (u.containsString(keyword, false)) {
				return u;
			}
		}

		log.error("No image in library with keyword : '" + keyword + "'.");

		return null;
	}

}
