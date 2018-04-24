package constants.library;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import constants.property.Properties;

public class ASSLibraryManager {

	static Logger log = LoggerFactory.getLogger(ASSLibraryManager.class);

	private static Library library;

	private static Library getLibrary() {

		if (library == null) {
			File LIBRARY_FOLDER = new File(Properties.LIBRARY_LOCATION.getValue());

			if (Properties.LIBRARY_LOCATION.isDefaultValue()) {

				LIBRARY_FOLDER = new File(new File(".").getAbsolutePath() + "/library/");

				log.info("Library is at " + LIBRARY_FOLDER.getPath() + "...");

				if (!LIBRARY_FOLDER.exists()) {
					log.info("Creating new folder...");

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
		return getLibrary().getFileInLibrary("macros.ser");
	}

	/**
	 * return the file used by FileManager to serialise imported files to
	 */
	public static File getFileFile() {
		return getLibrary().getFileInLibrary("files.ser");
	}

	/**
	 * return the folder used to store deleted files in
	 */
	public static File getTrashFolder() {
		return getLibrary().getFolderInLibrary("trash");
	}

}

class Library {

	static Logger log = LoggerFactory.getLogger(Library.class);

	private File libraryFolder;

	public Library(File folder) {
		this.libraryFolder = folder;
	}

	@Override
	public String toString() {
		return "Library [" + libraryFolder + "]";
	}

	public File getFileInLibrary(String file) {
		File f = new File(libraryFolder.getAbsolutePath() + "\\" + file);

		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				log.error("Could not create file " + file + ".");
				e.printStackTrace();
			}
		}

		return f;
	}

	public File getFolderInLibrary(String folder) {
		File f = new File(libraryFolder.getAbsolutePath() + "\\" + folder);

		if (!f.exists()) {
			f.mkdir();
		}

		return f;
	}

}
