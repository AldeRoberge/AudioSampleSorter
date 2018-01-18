package ass.action.interfaces;

import java.io.File;

import ass.ASS;
import ass.FileManager;
import ass.file.player.AudioPlayer;
import file.FileNameUtil;
import logger.Logger;

public abstract class FileAction implements Action, Cloneable {

	protected static final String TAG = "FileAction";
	public static ASS ASS;

	/**
	 * Do not use this method, use perform() instead
	 */
	public abstract FileEvent perform(File fileAffected);

	public abstract void unperform();

	@Override
	public FileAction clone() throws CloneNotSupportedException {
		return (FileAction) super.clone();
	}

	/**
	 * @param file file to move
	 * @param new name (without extension)
	 * @return FileEvent(oldFile, newFile)
	 */
	protected FileEvent rename(File file, String newName) {
		String basePath = file.getParent();

		String newFileName = newName + FileNameUtil.getExtension(file.getName());

		String newPath = basePath + "\\" + newFileName;

		File oldFile = file;
		File newFile = new File(newPath);

		if (file.renameTo(newFile)) {
			Logger.logInfo(TAG + " (Rename file)", "Success...");
			return new FileEvent(oldFile, newFile);
		} else {
			Logger.logError(TAG + " (Rename file)", "Failure...");
			return new FileEvent(oldFile, oldFile);
		}

	}

	/**
	 * @param file file to move
	 * @param newPath new path to move file to, can be a non existing folder, it will be created
	 * @return FileEvent(oldFile, newFile)
	 */
	protected FileEvent moveFileTo(File file, String newPath) {

		File oldFile = file;
		File newFile = new File(newPath);

		if (file.renameTo(newFile)) {
			Logger.logInfo(TAG + " (Move file)", "Success...");
			return new FileEvent(oldFile, newFile);
		} else {
			Logger.logError(TAG + " (Move file)", "Failure...");
			return new FileEvent(oldFile, oldFile);
		}

	}

	@SuppressWarnings("static-access")
	public void ready() {
		for (File f : ASS.fileBro.selectedFiles) {

			//Make sure we arent using the file
			ASS.getAudioPlayer().stopUsing(f);

			FileEvent fe = perform(f);

			if (fe != null) {
				ASS.fileBro.updateFile(fe);
			} else {
				Logger.logInfo(TAG, "No FileEvent, ignoring this action result.");
			}

		}
	}

}
