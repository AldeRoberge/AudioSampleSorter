package samplerSorter.macro;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import samplerSorter.actions.type.PlayAction;
import samplerSorter.actions.type.RenameAction;
import samplerSorter.logger.Logger;
import samplerSorter.macro.macrolist.MacroInfoPanel;
import samplerSorter.macro.macrolist.MacroListUI;

public class MacroLoader {

	public static final String TAG = "MacroLoader";

	public static final String SAVED_MACROS_FILENAME = "keyBinds.ss";

	public ArrayList<MacroAction> keyBinds = new ArrayList<MacroAction>();

	public MacroEditor macroEditor;

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
		keyBinds.add(tmp);
		macroEditor.macroListUI.addKeyBindInfoPanel(tmp);

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

		MacroAction k1 = new MacroAction();
		k1.keys.add(new Key(KeyEvent.VK_SPACE));
		k1.actionsToPerform.add(new PlayAction());

		addNewMacro(k1);

		//R = rename

		MacroAction k2 = new MacroAction();
		k2.keys.add(new Key(KeyEvent.VK_R));
		k2.actionsToPerform.add(new RenameAction());

		//

		addNewMacro(k2);
	}

	public void serialise() {
		Logger.logInfo(TAG, "Serialising");

		try {
			FileOutputStream fos = new FileOutputStream(SAVED_MACROS_FILENAME);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(keyBinds);
			oos.close();
			fos.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void removeKeyBind(MacroAction keyBind) {
		keyBinds.remove(keyBind);
	}

}
