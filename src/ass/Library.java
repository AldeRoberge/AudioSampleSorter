package ass;

import java.io.File;
import java.io.IOException;

import logger.Logger;

public class Library {

	private static final String TAG = "Library";
	private File libraryFolder;

	public Library(File folder) {
		this.libraryFolder = folder;
	}

	@Override
	public String toString() {
		return "Library [" + libraryFolder + "]";
	}

	public File getFileInLibrary(String file, boolean createIfDoesntExist) {
		File f = new File(libraryFolder.getAbsolutePath() + "\\" + file);

		if (createIfDoesntExist && !f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				Logger.logError(TAG, "Library error with file " + file + ".");
				e.printStackTrace();
			}
		}

		return f;
	}

	public File getFolderInLibrary(String folder, boolean createIfDoesntExist) {
		File f = new File(libraryFolder.getAbsolutePath() + "\\" + folder);

		if (createIfDoesntExist && !f.exists()) {
			f.mkdir();
		}

		return f;
	}

}
