package constants.property;

import alde.commons.properties.Properties;
import alde.commons.properties.Property;

public class PropertiesImpl extends Properties { //TODO add descriptions

	static {
		setPropertyFile("ass.properties");
	}

	// Booleans

	public static Property ROOT_FOLDER = new Property("ROOT_FOLDER", "", "C:\\", propertyFile);

	//Checked before launching the UI, if set to TRUE will open Credits
	public static Property FIRST_LAUNCH = new Property("FIRST_LAUNCH", "", TRUE, propertyFile);

	//Prompt on close, are you sure to exit?
	public static Property PROMPT_ON_EXIT = new Property("PROMPT_ON_EXIT", "", TRUE, propertyFile);

	//Prompt on close, are you sure to exit?
	public static Property SIZE_WIDTH = new Property("SIZE_X", "", TRUE, propertyFile);
	//Prompt on close, are you sure to exit?
	public static Property SIZE_HEIGH = new Property("SIZE_Y", "", TRUE, propertyFile);

	//RunSS UI checkBox 'include subfolders'
	public static Property INCLUDE_SUBFOLDERS = new Property("INCLUDE_SUBFOLDERS", "", TRUE, propertyFile);

	//Set in Settings and checked by RunSS to know if .wav or .mp3 should be displayed in SoundPanel
	public static Property DISPLAY_SOUND_SUFFIXES = new Property("DISPLAY_SOUND_SUFFIXES", "", TRUE, propertyFile);

	//Used by FileInformation to know wiether of not to display file date
	public static Property DISPLAY_FILE_DATE = new Property("DISPLAY_FILE_DATE", "", FALSE, propertyFile);

	//Used by Settings to adjust volume
	public static Property MAIN_VOLUME_SLIDER_VALUE = new Property("MAIN_VOLUME_SLIDER_VALUE", "", "20", propertyFile);

	//Main pan slider value
	public static Property MAIN_PAN_SLIDER_VALUE = new Property("MAIN_PAN_SLIDER_VALUE", "", "50", propertyFile);

	//RunSS PlayOnClickChbkx and on SoundPanel click, to check if the sound should be played
	public static Property PLAY_ON_CLICK = new Property("PLAY_ON_CLICK", "", TRUE, propertyFile);

	//Used by RunSS onJFileChooser to reset the location where the jfilechooser was
	public static Property SPECTRUM_ANALYZER_STATUS = new Property("SPECTRUM_ANALYZER_STATUS", "", "oscillo",
			propertyFile);

	public static Property HORIZONTAL_SPLITPANE_DIVIDERLOCATION = new Property("SPLITPANE_DIVIDERLOCATION", "", "200",
			propertyFile);

	public static Property LIBRARY_LOCATION = new Property("LIBRARY_LOCATION", "", "C:\\", propertyFile);

}