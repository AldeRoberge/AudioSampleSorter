package constants.icons;

import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class IconLoader {

	private static final String FOLDER_LOCATION = new File(".").getAbsolutePath() + "/res/default_icons/";

	private static final ArrayList<ImageIcon> pathAndImage = new ArrayList<ImageIcon>();

	private static final ImageDimension defaultDimension = new ImageDimension(16, 16);

	public static void init() {

		File folder = new File(FOLDER_LOCATION);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			//pathAndImage.add(Icons.createImageIcon(listOfFiles[i].getAbsolutePath(), defaultDimension));
		}

	}

}
