package samplerSorter.util.file;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import samplerSorter.logger.Logger;

public class FileReader {

	private static final String TAG = "FileReader";

	public static String readFile(String filePath) {
		File file = new File(filePath);
		String string;
		try {
			string = FileUtils.readFileToString(file);

		} catch (IOException e) {
			e.printStackTrace();
			Logger.logError(TAG, "Could not read file " + filePath + ".", e);
			return TAG + " returned null, " + e;
		}

		return string;

	}

}
