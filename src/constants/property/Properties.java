package constants.property;

import alde.commons.properties.Property;
import alde.commons.properties.PropertyFileManager;

public class Properties {

	static PropertyFileManager propertyFile;

	static {
		propertyFile = new PropertyFileManager("ass.properties");
	}

	// Booleans

	public static Property ROOT_FOLDER = new Property("ROOT_FOLDER", "Root folder of library", "C:\\", propertyFile);

	//Checked before launching the UI, if set to TRUE will open Credits
	public static Property FIRST_LAUNCH = new Property("FIRST_LAUNCH", "First launch will display credits", Property.TRUE, propertyFile);

	//Prompt on close, are you sure to exit?
	public static Property PROMPT_ON_EXIT = new Property("PROMPT_ON_EXIT", "Ask before closing", Property.TRUE, propertyFile);

	//Prompt on close, are you sure to exit?
	public static Property SIZE_WIDTH = new Property("SIZE_X", "Window height", Property.TRUE, propertyFile);
	//Prompt on close, are you sure to exit?
	public static Property SIZE_HEIGHT = new Property("SIZE_Y", "Window width", Property.TRUE, propertyFile);

	//RunSS UI checkBox 'include subfolders'
	public static Property INCLUDE_SUBFOLDERS = new Property("INCLUDE_SUBFOLDERS", "Include subfolders", Property.TRUE, propertyFile);

	//Set in Settings and checked by RunSS to know if .wav or .mp3 should be displayed in SoundPanel
	public static Property DISPLAY_SOUND_SUFFIXES = new Property("DISPLAY_SOUND_SUFFIXES", "Display suffix", Property.TRUE,
			propertyFile);

	//Used by FileInformation to know wiether of not to display file date
	public static Property DISPLAY_FILE_DATE = new Property("DISPLAY_FILE_DATE", "Display file date", Property.FALSE, propertyFile);

	//Used by Settings to adjust volume
	public static Property MAIN_VOLUME_SLIDER_VALUE = new Property("MAIN_VOLUME_SLIDER_VALUE", "Volume", "20", propertyFile);

	//Main pan slider value
	public static Property MAIN_PAN_SLIDER_VALUE = new Property("MAIN_PAN_SLIDER_VALUE", "Pan", "50", propertyFile);

	//RunSS PlayOnClickChbkx and on SoundPanel click, to check if the sound should be played
	public static Property PLAY_ON_CLICK = new Property("PLAY_ON_CLICK", "Play sounds on click", Property.TRUE, propertyFile);

	//Used by RunSS onJFileChooser to reset the location where the jfilechooser was
	public static Property SPECTRUM_ANALYZER_STATUS = new Property("SPECTRUM_ANALYZER_STATUS", "Status of spectrum analyser", "oscillo",
			propertyFile);

	public static Property HORIZONTAL_SPLITPANE_DIVIDERLOCATION = new Property("SPLITPANE_DIVIDERLOCATION", "Divider location", "200",
			propertyFile);

	public static Property LIBRARY_LOCATION = new Property("LIBRARY_LOCATION", "Location of library", "C:\\", propertyFile);

}