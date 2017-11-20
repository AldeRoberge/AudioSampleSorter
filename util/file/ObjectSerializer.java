package file;

import logger.Logger;

import java.io.*;
import java.util.ArrayList;

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

	/**
	 * Changes the serialized object, and serializes it
	 * @param t
	 */
	public void set(T t) {
		this.t = t;

		serialise();
	}

	public T get() {
		return t;
	}

	/**
	 * @param fileToSaveTo path of file to create and save data to
	 */
	public ObjectSerializer(String fileToSaveTo) {
		this.file = new File(fileToSaveTo);

		if (!file.exists()) {
			try {
				file.createNewFile();

				serialise();
			} catch (IOException e1) {
				Logger.logError(TAG, "FATAL ERROR : Could not create new file", e1);
				e1.printStackTrace();
			}
		}

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

	public void serialise() {

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

	public static void main(String[] args) {
		ObjectSerializer<ArrayList<String>> genericSerialiserTest = new ObjectSerializer<ArrayList<String>>("serialization_test_file.ser");

		System.out.println(genericSerialiserTest.get());

		ArrayList<String> t = new ArrayList<String>();
		t.add("Hey!");
		genericSerialiserTest.set(t);
	}
}