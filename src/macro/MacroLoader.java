package macro;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import action.type.sound.imp.PlayAction;
import key.Key;
import logger.Logger;
import macro.macrolist.MacroInfoPanel;

public class MacroLoader {

	private static final String TAG = "MacroLoader";

	private static final String SAVED_MACROS_FILENAME = "keyBinds.ss";

	public static ArrayList<MacroAction> macroActions = new ArrayList<MacroAction>();

	private MacroEditor macroEditor;

	public MacroLoader(MacroEditor macroEditor) {
		this.macroEditor = macroEditor;

		//restore macros from file

		File f = new File(SAVED_MACROS_FILENAME);
		if (!f.exists()) {
			createNewFileAndStoreDefaultKeyBinds();
		} else {

			boolean deserialisationSuccess = false;

			ArrayList<MacroAction> arraylist = new ArrayList<MacroAction>();
			try {
				FileInputStream fis = new FileInputStream(SAVED_MACROS_FILENAME);

				ObjectInputStream ois = new ObjectInputStream(fis);
				arraylist = (ArrayList<MacroAction>) ois.readObject();
				ois.close();
				fis.close();

				Logger.logInfo(TAG, "Deserialization complete");

				deserialisationSuccess = true;
			} catch (IOException ioe) {
				//ioe.printStackTrace();
				Logger.logError(TAG, "IOException", ioe);
			} catch (ClassNotFoundException c) {
				Logger.logError(TAG, "ClassNotFoundException", c);
				//c.printStackTrace();
			}

			if (!deserialisationSuccess) {
				createNewFileAndStoreDefaultKeyBinds();
			} else {
				for (MacroAction tmp : arraylist) {
					addNewMacro(tmp);
				}
			}

		}
	}

	public void addNewMacro(MacroAction tmp) {
		macroActions.add(tmp);
		macroEditor.macroListUI.addKeyBindInfoPanel(tmp);

		serialise();
	}

	public void removeMacro(MacroAction keyBind, MacroInfoPanel me) {
		macroActions.remove(keyBind);
		macroEditor.macroListUI.removePanel(me);

		serialise();
	}

	private void createNewFileAndStoreDefaultKeyBinds() {

		File f = new File(SAVED_MACROS_FILENAME);
		Logger.logInfo(TAG, "File " + f.getAbsolutePath() + " does not exist, creating default...");

		try {
			f.createNewFile();
		} catch (IOException e1) {
			Logger.logError(TAG, "FATAL ERROR : Could not create new file", e1);
			e1.printStackTrace();
		}

		//Default keyBinds

		//Space = play

		MacroAction defaultPlayAction = new MacroAction();
		defaultPlayAction.keys.add(new Key(KeyEvent.VK_SPACE));
		defaultPlayAction.actionsToPerform.add(new PlayAction());

		addNewMacro(defaultPlayAction);

		//R = rename

		//UP ArrowKey is reserved
		//CTRL + A is reserved
		//DOWN ArrowKey is reserved

	}

	public void serialise() {

		try {
			FileOutputStream fos = new FileOutputStream(SAVED_MACROS_FILENAME);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(macroActions);
			oos.close();
			fos.close();
		} catch (IOException ioe) {
			Logger.logInfo(TAG, "Error while serialising");
			ioe.printStackTrace();
		}

		Logger.logInfo(TAG, "Serialising complete");
	}

}
