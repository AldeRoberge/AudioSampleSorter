package ass;

import java.io.File;

import ass.file.FileManager;
import ass.keyboard.action.interfaces.FileAction;
import ass.keyboard.action.interfaces.FileEvent;

public class FileActionManager {

	static FileManager fMan;

	public static void perform(FileAction a) {
		System.out.println("Performing action on " + fMan.selectedFiles.size() + " selected files.");

		for (File f : fMan.selectedFiles) {

			//Make sure we arent using the file
			fMan.fileVisualiser.getAudioPlayer().stopUsing(f);

			FileEvent fe = a.perform(f);

			if (fe == null) { //no changes
				System.out.println("Doing nothing! No changes!");
			} else {
				fMan.fileTableModel.updateFile(fe);
			}
		}
	}
}
