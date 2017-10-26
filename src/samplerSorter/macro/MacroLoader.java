package samplerSorter.macro;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import samplerSorter.actions.type.impl.PlayAction;
import samplerSorter.logger.Logger;
import samplerSorter.macro.macrolist.MacroInfoPanel;

public class MacroLoader {

	public static final String TAG = "MacroLoader";

	public static final String SAVED_MACROS_FILENAME = "keyBinds.ss";

	//Formelly known as 'keyBinds'
	public static ArrayList<MacroAction> macroActions = new ArrayList<MacroAction>();

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
		macroActions.add(tmp);
		macroEditor.macroListUI.addKeyBindInfoPanel(tmp);

		serialise();
	}

	public void removeMacro(MacroAction keyBind, MacroInfoPanel me) {
<<<<<<< HEAD
		macroActions.remove(keyBind);
=======
		keyBinds.remove(keyBind);
>>>>>>> e79edf06f91b91693f6e330a3935515b00f4ab1c
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

		/**MacroAction k2 = new MacroAction();
		k2.keys.add(new Key(KeyEvent.VK_R));
		k2.actionsToPerform.add(new RenameAction());
		
		addNewMacro(k2);*/

		//

		
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
