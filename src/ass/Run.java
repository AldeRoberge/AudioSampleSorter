package ass;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ass.constants.Constants;
import ass.icons.Icons;
import ass.ui.SorterUI;
import logger.Logger;
import ui.SplashScreen;

public class Run {

	private static final String TAG = "Run";

	public static void main(String[] args) {

		SorterUI sorterUI = new SorterUI();

		final String IMAGE_LOCATION = new File(".").getAbsolutePath() + "/res/splashScreen/";
		try {

			BufferedImage inImage = ImageIO.read(new File(IMAGE_LOCATION + "/BG_BLURRY.png"));
			BufferedImage outImage = ImageIO.read(new File(IMAGE_LOCATION + "/BG.png"));
			BufferedImage textImage = ImageIO.read(new File(IMAGE_LOCATION + "/TITLE.png"));
			Image icon = Icons.LOADING_BAR.getImage();

			new SplashScreen(icon, inImage, outImage, textImage, Constants.SOFTWARE_NAME, sorterUI);

		} catch (IOException e) {
			Logger.logError(TAG, "Error with SplashScreen!");
			e.printStackTrace();
			Logger.logError(TAG, "Starting without splashScreen...");

			sorterUI.setVisible(true);
		}
	}
}
