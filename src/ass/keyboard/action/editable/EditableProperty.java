package ass.keyboard.action.editable;

import java.io.File;
import java.io.Serializable;

import logger.Logger;

/**
 * A property of an Action that can be edited (UI : see Action -> ActionEditor)
 * 
 * Thanks to http://www.oracle.com/technetwork/articles/java/juneau-generics-2255374.html
 */

public class EditableProperty<V> implements Serializable, Cloneable {

	private static final String TAG = "EditableProperty";

	public static final int BOOLEAN_ID = 0;
	public static final int STRING_ID = 1;
	public static final int INT_ID = 2;
	public static final int FILE_CHOOSER_ID = 3;

	//

	public String prefix; //used by Buttons, CheckBoxes, etc

	private int ID = -1;
	private V defaultValue;
	private V value;

	// Pass type in as parameter to constructor
	public EditableProperty(V defaultValue, String label) {
		prefix = label;
		value = defaultValue;
		this.defaultValue = defaultValue;

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
	 * @return the obj
	 */
	public int getId() {
		return ID;
	}

	public boolean isDefaultValue() {
		return value.equals(defaultValue);
	}

	/**
	 * @param value the obj to set
	 */
	public void setValue(V v) {
		value = v;
	}

}
