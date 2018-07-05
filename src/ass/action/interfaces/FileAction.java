package ass.action.interfaces;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import alde.commons.util.file.FileNameUtil;
import ass.ASS;

public abstract class FileAction implements Action, Cloneable {

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
			log.info("(Rename file) Success...");
			return new FileEvent(oldFile, newFile);
		} else {
			log.error("(Rename file) Failure...");
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
			log.info("(Move file) Success...");
			return new FileEvent(oldFile, newFile);
		} else {
			log.error("(Move file) Failure...");
			return new FileEvent(oldFile, oldFile);
		}

	}

	@SuppressWarnings("static-access")
	public void ready() {
		for (File f : ASS.fileManager.selectedFiles) {

			//Make sure we arent using the file
			ASS.getAudioPlayer().stopUsing(f);

			FileEvent fe = perform(f);

			if (fe != null) {
				ASS.fileManager.updateFile(fe);
			} else {
				log.info("No FileEvent, ignoring this action result.");
			}

		}
	}

}
