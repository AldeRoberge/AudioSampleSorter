package keybinds;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import global.icons.Icons;
import global.logger.Logger;
import keybinds.action.type.file.impl.PlayAction;
import keybinds.action.type.file.impl.RenameAction;
import keybinds.action.type.ui.impl.ShowUIAction;
import keybinds.key.Key;
import keybinds.macro.MacroAction;
import keybinds.macro.MacroEditor;
import keybinds.macro.list.MacroInfoPanel;

public class MacroLoader {

	private static final String TAG = "MacroLoader";

	private static final String SAVED_MACROS_FILENAME = "keyBinds.ss";

	public ArrayList<MacroAction> macroActions = new ArrayList<MacroAction>();

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

		addNewMacro(new MacroAction("Play", Icons.PLAY, new Key(KeyEvent.VK_SPACE), new PlayAction()));

		addNewMacro(new MacroAction("Show Credits", Icons.ABOUT, new Key(KeyEvent.VK_F1), ShowUIAction.SHOW_CREDITS_UI));
		addNewMacro(new MacroAction("Edit Macros", Icons.MACROS, new Key(KeyEvent.VK_F2), ShowUIAction.SHOW_MACRO_UI));
		addNewMacro(new MacroAction("Edit Settings", Icons.SETTINGS, new Key(KeyEvent.VK_F3), ShowUIAction.SHOW_SETTINGS_UI));
		addNewMacro(new MacroAction("Show Console", Icons.CONSOLE, new Key(KeyEvent.VK_F4), ShowUIAction.SHOW_CONSOLE));

		addNewMacro(new MacroAction("Rename", Icons.PENCIL, new Key(KeyEvent.VK_R), new RenameAction()));

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
