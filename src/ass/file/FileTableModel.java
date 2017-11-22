package ass.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;

import ass.keyboard.action.interfaces.FileEvent;
import logger.Logger;

/** A TableModel to hold File[]. */
public class FileTableModel extends AbstractTableModel {

	private static final String TAG = "FileTableModel";
	private ArrayList<File> files;

	private ArrayList<File> selectedFiles;

	private FileSystemView fileSystemView = FileSystemView.getFileSystemView();
	private String[] columns = { "Icon", "File", "Path", "Size", "Last Modified" };

	FileTableModel() {
		files = new ArrayList<File>();
	}

	public Object getValueAt(int row, int column) {
		File file = files.get(row);
		switch (column) {
		case 0:
			return fileSystemView.getSystemIcon(file);
		case 1:
			return fileSystemView.getSystemDisplayName(file);
		case 2:
			return file.getPath();
		case 3:
			return file.length();
		case 4:
			return file.lastModified();
		default:
			System.err.println("Logic Error");
		}
		return "";
	}

	public int getColumnCount() {
		return columns.length;
	}

	public Class<?> getColumnClass(int column) {
		switch (column) {
		case 0:
			return ImageIcon.class;
		case 3:
			return Long.class;
		case 4:
			return Date.class;
		}
		return String.class;
	}

	public String getColumnName(int column) {
		return columns[column];
	}

	public int getRowCount() {
		return files.size();
	}

	public File getFile(int row) {
		return files.get(row);
	}

	public void setFiles(ArrayList<File> files) {
		this.files = files;
		fireTableDataChanged();
	}

	public void updateFile(FileEvent f) {

		if (files.contains(f.oldFile)) {
			files.set(files.indexOf(f.oldFile), f.newFile);
			fireTableDataChanged();

			Logger.logError(TAG, "Changed table data");

			System.out.println("Old : " + f.oldFile);
			System.out.println("New : " + f.newFile);
		} else {
			Logger.logError(TAG, "MAJOR ERROR : files to update do NOT contain reference to old file!");
		}
		
		
		

	}

	public ArrayList<File> getFiles() {
		return files;
	}

	public void addFile(File file) {
		files.add(file);
		fireTableDataChanged();
	}

}