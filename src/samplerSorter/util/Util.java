package samplerSorter.util;

import java.util.ArrayList;

import samplerSorter.macro.Key;

public class Util {

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
