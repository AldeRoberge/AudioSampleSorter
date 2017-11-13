package property;

public class Properties {

	//Properties are stored in this file : 
	private static final String fileName = "ss.properties";

	// 

	private static PropertyFileManager e = new PropertyFileManager(fileName);

	// Booleans

	public static final String TRUE = "TRUE";
	public static final String FALSE = "FALSE";

	//Checked before launching the UI, if set to TRUE will open Credits
	public static Property FIRST_LAUNCH = new Property("FIRST_LAUNCH", TRUE, e);

	//RunSS UI checkBox 'include subfolders'
	public static Property INCLUDE_SUBFOLDERS = new Property("INCLUDE_SUBFOLDERS", TRUE, e);

	//RunSS JFileChooser uses this to restore the previous directory
	public static Property LAST_OPENED_LOCATION = new Property("LAST_OPENED_LOCATION", "C:\\", e);

	//Set in Settings and checked by RunSS to know if .wav or .mp3 should be displayed in SoundPanel
	public static Property DISPLAY_SOUND_SUFFIXES = new Property("DISPLAY_SOUND_SUFFIXES", TRUE, e);

	//Used by Settings to adjust volume
	public static Property MAIN_VOLUME_SLIDER_VALUE = new Property("MAIN_VOLUME_SLIDER_VALUE", "100", e);

	//Main pan slider value
	public static Property MAIN_PAN_SLIDER_VALUE = new Property("MAIN_PAN_SLIDER_VALUE", "20", e);

	//RunSS PlayOnClickChbkx and on SoundPanel click, to check if the sound should be played
	public static Property PLAY_ON_CLICK = new Property("PLAY_ON_CLICK", TRUE, e);

	//Used by RunSS onJFileChooser to reset the location where the jfilechooser was
	public static Property SPECTRUM_ANALYZER_STATUS = new Property("SPECTRUM_ANALYZER_STATUS", "oscillo", e);

	public static Property HORIZONTAL_SPLITPANE_DIVIDERLOCATION = new Property("SPLITPANE_DIVIDERLOCATION", "200", e);

}