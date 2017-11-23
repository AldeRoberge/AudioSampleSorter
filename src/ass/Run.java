package ass;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import constants.Constants;
import constants.icons.Icons;
import logger.Logger;
import ui.SplashScreen;

/**
 * Runs ASS with the SplashScreen
 *
 */
class Run {

	private static final String TAG = "Run";

	public static void main(String[] args) {

		ASS ASS = new ASS();

		final String IMAGE_LOCATION = new File(".").getAbsolutePath() + "/res/splashScreen/";
		try {

			BufferedImage inImage = ImageIO.read(new File(IMAGE_LOCATION + "/BG_BLURRY.png"));
			BufferedImage outImage = ImageIO.read(new File(IMAGE_LOCATION + "/BG.png"));
			BufferedImage textImage = ImageIO.read(new File(IMAGE_LOCATION + "/TITLE.png"));
			Image icon = Icons.LOADING_BAR.getImage();

			new SplashScreen(icon, inImage, outImage, textImage, Constants.SOFTWARE_NAME, ASS);

		} catch (IOException e) {
			Logger.logError(TAG, "Error with SplashScreen!");
			e.printStackTrace();
			Logger.logError(TAG, "Starting without SplashScreen...");

			ASS.setVisible(true);
		}
	}
}
