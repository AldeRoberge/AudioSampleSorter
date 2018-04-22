package ass.action;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ass.action.editeable.EditableProperty;
import ass.action.interfaces.FileAction;
import ass.action.interfaces.FileEvent;
import constants.icons.Icons;

public class RenameAction extends FileAction {

	@Override
	public String toString() {
		String TAG = "Rename";
		return TAG;
	}

	@Override
	public ArrayList<EditableProperty> getEditableProperties() {
		return null;
	}

	@Override
	public String getDescription() {
		return "Renames the selected file";
	}

	@Override
	public int getPolicy() {
		return FileAction.PERFORMED_ON_ONE_OR_MANY_FILES_ONLY_POLICY;
	}

	@Override
	public void unperform() {
		// TODO Auto-generated method stub
	}

	/**
	 * Returns null if the user cancels
	 */

	@Override
	public FileEvent perform(File fileAffected) {

		String input = (String) JOptionPane.showInputDialog(new JFrame(),
				"Enter the new name for '" + fileAffected.getName() + "' (without extension).",
				"Edit " + fileAffected.getName() + "'s name", JOptionPane.INFORMATION_MESSAGE,
				Icons.PENCIL.getImageIcon(), null, "");

		if (input == null) {
			return null;
		} else {
			return rename(fileAffected, input);
		}

	}

}
