package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import constants.library.LibraryManager;
import logger.Logger;

/**
 * @author VaypeNaysh
 *
 * @param <T> object to deserialize on ObjectSerializer(String fileToSaveTo) and to get with get()
 * Always check if isNull() (if the file exists and is not empty) otherwise get() will return null
 */
public class ObjectSerializer<T extends Serializable> {
	private static final String TAG = "ObjectSerializer";

	private File file; //file to serialise object to
	private T t;

	public boolean isNull() {
		return (t == null);
	}

	public T get() {
		return t;
	}

	/**
	 * @param file path of file to create and save data to
	 */
	public ObjectSerializer(File file) {
		this.file = file;

		if (file.exists() && !(file.length() == 0)) {
			try {
				FileInputStream fis = new FileInputStream(file);

				ObjectInputStream ois = new ObjectInputStream(fis);
				t = (T) ois.readObject();
				ois.close();
				fis.close();

			} catch (IOException ioe) {
				ioe.printStackTrace();
				Logger.logError(TAG, "IOException", ioe);
			} catch (ClassNotFoundException c) {
				c.printStackTrace();
				Logger.logError(TAG, "ClassNotFoundException", c);
			}
		} else {
			Logger.logWarning(TAG, "File " + file.getAbsolutePath() + " is empty!");
		}

	}

	public void set(T t) {
		this.t = t;

		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(t);
			oos.close();
			fos.close();
		} catch (IOException ioe) {
			Logger.logInfo(TAG, "Error while serialising");
			ioe.printStackTrace();
		}
	}

	/**
	 * Test
	 */
	public static void main(String[] args) {
		ObjectSerializer<ArrayList<String>> genericSerialiserTest = new ObjectSerializer<ArrayList<String>>(LibraryManager.getMacroFile());

		System.out.println(genericSerialiserTest.get());

		ArrayList<String> t = new ArrayList<String>();
		t.add("Hey!");
		genericSerialiserTest.set(t);
		
		System.out.println(genericSerialiserTest.get());
	}
}