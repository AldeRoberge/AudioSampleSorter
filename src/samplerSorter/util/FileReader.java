package samplerSorter.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

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
