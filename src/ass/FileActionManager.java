package ass;

import java.io.File;

import ass.file.FileManager;
import ass.keyboard.action.interfaces.FileAction;
import ass.keyboard.action.interfaces.FileEvent;

public class FileActionManager {

	static FileManager fMan;

	public static void perform(FileAction a) {
		for (File f : fMan.selectedFiles) {

			//Make sure we arent using the file
			fMan.fileVisualiser.getAudioPlayer().stopUsing(f);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			FileEvent fe = a.perform(f);

			fMan.fileTableModel.updateFile(fe);
		}
	}

}
