package ass;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import constants.Properties;
import logger.Logger;

public class LibraryManager {

	private static final String TAG = "LibraryManager";

	private static Library library;

	public static void main(String args[]) {
		System.out.println(LibraryManager.getTrashFolder().getAbsolutePath());
	}

	public static Library getLibrary() {

		if (library == null) {
			File LIBRARY_FOLDER = new File(Properties.LIBRARY_LOCATION.getValue());

			if (/**Properties.LIBRARY_LOCATION.isDefaultValue()*/
			true) {

				LIBRARY_FOLDER = new File(new File(".").getAbsolutePath() + "/library/");

				Logger.logInfo(TAG, "Library is at " + LIBRARY_FOLDER.getPath() + "...");

				if (!LIBRARY_FOLDER.exists()) {
					Logger.logInfo(TAG, "Creating new folder...");

					LIBRARY_FOLDER.mkdir();
				}

				Properties.LIBRARY_LOCATION.setNewValue(LIBRARY_FOLDER.getAbsolutePath());

				library = new Library(LIBRARY_FOLDER);
			}
		}

		return library;

	}

	/**
	 * return the file used by MacroLoader to serialise macros to
	 */
	public static File getMacroSerFile() {
		return getLibrary().getFileInLibrary("savedMacros.ser", true);
	}

	/**
	 * return the file used by FileManager to serialise imported files to
	 */
	public static File getFileSerFile() {
		return getLibrary().getFileInLibrary("importedFiles.ser", true);
	}

	/**
	 * return the folder used to store deleted files in
	 */
	public static File getTrashFolder() {
		return getLibrary().getFolderInLibrary("trash", true);
	}

}