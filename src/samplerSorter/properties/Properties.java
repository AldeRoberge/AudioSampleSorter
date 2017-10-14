package samplerSorter.properties;

public class Properties {

	private static final String fileName = "ss.properties";

	// 

	public static PropertyFileManager e = new PropertyFileManager(fileName);

	// Booleans

	public static final String TRUE = "TRUE";
	public static final String FALSE = "FALSE";

	// SORT_BY types

	public static final String SORT_BY_NAME = "NAME";
	public static final String SORT_BY_SIZE = "SIZE";

	// 

	//RunSS UI checkBox 'include subfolders'
	public static Property INCLUDE_SUBFOLDERS = new Property("INCLUDE_SUBFOLDERS", TRUE, e);

	//RunSS JFileChooser uses this to restore the previous directory
	public static Property LAST_OPENNED_LOCATION = new Property("LAST_OPENNED_LOCATION", "C:\\", e);

	//Set in Settings and checked by RunSS to know if .wav or .mp3 should be displayed in SoundPanel
	public static Property DISPLAY_SOUND_SUFFIXES = new Property("DISPLAY_SOUND_SUFFIXES", TRUE, e);

	//Used by Settings to adjust volume
	public static Property MAIN_VOLUME_SLIDER_VALUE = new Property("MAIN_VOLUME_SLIDER_VALUE", "100", e);

	//Main pan slider value
	public static Property MAIN_PAN_SLIDER_VALUE = new Property("MAIN_PAN_SLIDER_VALUE", "50", e);

	//RunSS PlayOnClickChbkx and on SoundPanel click, to check if the sound should be played
	public static Property PLAY_ON_CLICK = new Property("PLAY_ON_CLICK", TRUE, e);

	/**public static String SORT_BY_KEY = "SORT_BY";
	public static Property SORT_BY = new Property(SORT_BY_KEY, SORT_BY_NAME, e);*/

}