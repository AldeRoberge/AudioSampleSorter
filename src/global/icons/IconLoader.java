package global.icons;

import java.io.File;
import java.text.Bidi;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.ImageIcon;

import logger.Logger;
import util.ui.FloatingLoadingBar;

public class IconLoader {

	public static final String FOLDER_LOCATION = new File(".").getAbsolutePath() + "/res/default_icons/";

	public static final HashMap<ImageIcon, String> pathAndImage = new HashMap<ImageIcon, String>();

	private static final ImageDimension defaultDimension = new ImageDimension(16, 16);

	private static final String TAG = "IconLoader";

	static {

		FloatingLoadingBar loadingBar = new FloatingLoadingBar();

		
		
		File folder = new File(FOLDER_LOCATION);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			loadingBar.setCurrentProgress(i, listOfFiles.length);

			ImageIcon hey = Icons.createImageIcon(listOfFiles[i].getAbsolutePath(), defaultDimension);
			hey.setDescription(listOfFiles[i].getName());

			pathAndImage.put(hey, listOfFiles[i].getAbsolutePath());
		}

		loadingBar.end();
	}

	public static ImageIcon getIconFromKey(String value) {
		ImageIcon key = Icons.EXIT; //default image

		if (value == null || value.equals("")) {
			Logger.logError(TAG, "getIconFromKey value is incorrect!" + value);
		} else {
			for (Entry<ImageIcon, String> map : pathAndImage.entrySet()) {
				if (map.getValue().toString().equals(value)) {
					key = map.getKey();
				}
			}
		}

		return key;
	}

}
