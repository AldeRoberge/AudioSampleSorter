package constants.library;

import java.io.File;
import java.io.IOException;

import constants.property.Properties;
import logger.Logger;

public class LibraryManager {

	private static final String TAG = "LibraryManager";

	private static Library library;

	private static Library getLibrary() {

		if (library == null) {
			File LIBRARY_FOLDER = new File(Properties.LIBRARY_LOCATION.getValue());

			if (Properties.LIBRARY_LOCATION.isDefaultValue()) {

				LIBRARY_FOLDER = new File(new File(".").getAbsolutePath() + "/library/");

				Logger.logInfo(TAG, "Library is at " + LIBRARY_FOLDER.getPath() + "...");

				if (!LIBRARY_FOLDER.exists()) {
					Logger.logInfo(TAG, "Creating new folder...");

					LIBRARY_FOLDER.mkdir();
				}

				Properties.LIBRARY_LOCATION.setNewValue(LIBRARY_FOLDER.getAbsolutePath());

			}

			library = new Library(LIBRARY_FOLDER);
		}

		return library;

	}

	/**
	 * return the file used by MacroLoader to serialise macros to
	 */
	public static File getMacroFile() {
		return getLibrary().getFileInLibrary("macros.ser", true);
	}

	/**
	 * return the file used by FileManager to serialise imported files to
	 */
	public static File getFileFile() {
		return getLibrary().getFileInLibrary("files.ser", true);
	}

	/**
	 * return the folder used to store deleted files in
	 */
	public static File getTrashFolder() {
		return getLibrary().getFolderInLibrary("trash", true);
	}

}

class Library {

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
				Logger.logError(TAG, "Could not create file " + file + ".");
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
