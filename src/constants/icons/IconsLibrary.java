package constants.icons;

import java.io.File;
import java.util.ArrayList;

import ui.FloatingLoadingBar;

public class IconsLibrary {

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
		return null;
	}

}
