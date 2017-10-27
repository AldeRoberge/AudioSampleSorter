package samplerSorter.actions.editeable;

import java.io.File;
import java.io.Serializable;

import samplerSorter.logger.Logger;

/**
 * A property that can be edited (Action -> ActionEditor)
 * 
 * Thanks to http://www.oracle.com/technetwork/articles/java/juneau-generics-2255374.html
 */

public class EditeableProperty<V> implements Serializable {

	private static final String TAG = "EditeableProperty";

	public static final int BOOLEAN_ID = 0;
	public static final int STRING_ID = 1;
	public static final int INT_ID = 2;
	public static final int FILE_CHOOSER_ID = 3;
	public static final int MANY_FILES_CHOOSER_ID = 4;
	public static final int DIRECTORY_CHOOSER_ID = 5;

	//

	public String prefix; //used by buttons, jcheckbox, etc

	public int ID = -1;
	private V value;

	// Pass type in as parameter to constructor
	/**
	 * @param defaultValue value to begin with
	 * @param prefix prefix (label) before edit component (JCheckBox, JTextArea...)
	 */
	public EditeableProperty(V defaultValue, String pref) {
		prefix = pref;
		value = defaultValue;

		if (defaultValue instanceof Boolean) {
			ID = BOOLEAN_ID;
		} else if (defaultValue instanceof String) {
			ID = STRING_ID;
		} else if (defaultValue instanceof Integer) {
			ID = INT_ID;
		} else if (defaultValue instanceof File) {
			ID = FILE_CHOOSER_ID;
		} else {
			Logger.logError(TAG, "Unknown object type");
		}

	}

	/**
	 * @return the obj
	 */
	public V getValue() {
		return value;
	}

	/**
	 * @param value the obj to set
	 */
	public void setValue(V v) {
		value = v;
	}

}
