package util.key;

import java.util.ArrayList;

import key.Key;

public class KeysToString {

	public static String keysToString(String prefix, ArrayList<Key> keys, String suffix) {
		String displayKeyNames = prefix;

		for (int i = 0; i < keys.size(); i++) {
			if (i != 0) {
				displayKeyNames += " + ";
			}
			displayKeyNames += keys.get(i).keyName;
		}

		return displayKeyNames += suffix;
	}

}
