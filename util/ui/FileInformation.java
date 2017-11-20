package ui;

import constants.Properties;
import file.FileSizeToString;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * Very important :
 * @see ass.file.Visualiser
 */
public class FileInformation extends JPanel {

	private boolean displayFileDate;

	private JLabel fileName;
	private JLabel path;
	private JLabel date;
	private JLabel size;

	private FileSystemView fileSystemView = FileSystemView.getFileSystemView();

	protected FileInformation() {

		displayFileDate = Properties.DISPLAY_FILE_DATE.getValueAsBoolean();

		// details for a File
		JPanel fileMainDetails = new JPanel(new BorderLayout(4, 2));
		fileMainDetails.setBorder(new EmptyBorder(0, 6, 0, 6));

		JPanel fileDetailsLabels = new JPanel(new GridLayout(0, 1, 2, 2));
		fileMainDetails.add(fileDetailsLabels, BorderLayout.WEST);

		JPanel fileDetailsValues = new JPanel(new GridLayout(0, 1, 2, 2));
		fileMainDetails.add(fileDetailsValues, BorderLayout.CENTER);

		fileDetailsLabels.add(new JLabel("File", JLabel.TRAILING));
		fileName = new JLabel();
		fileDetailsValues.add(fileName);

		fileDetailsLabels.add(new JLabel("Path", JLabel.TRAILING));
		path = new JLabel();
		fileDetailsValues.add(path);

		if (displayFileDate) {
			fileDetailsLabels.add(new JLabel("Last Modified", JLabel.TRAILING));
			date = new JLabel();
			fileDetailsValues.add(date);
		}

		fileDetailsLabels.add(new JLabel("File size", JLabel.TRAILING));
		size = new JLabel();
		fileDetailsValues.add(size);

		int count = fileDetailsLabels.getComponentCount();
		for (int ii = 0; ii < count; ii++) {
			fileDetailsLabels.getComponent(ii).setEnabled(false);
		}

		setLayout(new BorderLayout(3, 3));
		add(fileMainDetails, BorderLayout.CENTER);

		setBorder(new EmptyBorder(10, 10, 10, 10));

	}

	/** Update the File details view with the details of this File. */
	protected void setFileDetails(File file) {

		System.out.println(file.getName());

		//currentFile = file;
		Icon icon = fileSystemView.getSystemIcon(file);
		fileName.setIcon(icon);
		fileName.setText(fileSystemView.getSystemDisplayName(file));
		path.setText(file.getPath());

		if (displayFileDate) {
			date.setText(new Date(file.lastModified()).toString());
		}

		size.setText(FileSizeToString.getFileSizeAsString(file) + " (" + file.length() + " bytes)");

		/**JFrame f = (JFrame) getTopLevelAncestor(); //will trace back to Sorter
		if (f != null) {
			f.setTitle(Constants.SOFTWARE_NAME + " - " + fileSystemView.getSystemDisplayName(file));
		}*/

		repaint();
	}

	protected void setFilesDetails(ArrayList<File> files) {
		String multipleFilesSelected = "Multiple files selected";

		fileName.setIcon(null);
		fileName.setText(multipleFilesSelected);
		path.setText(multipleFilesSelected);

		if (displayFileDate) {
			date.setText(multipleFilesSelected);
		}

		int totalBytes = 0;
		for (File f : files) {
			totalBytes += f.length();
		}

		size.setText(FileSizeToString.getByteSizeAsString(totalBytes) + " (" + multipleFilesSelected + ")");

		repaint();
	}

}
