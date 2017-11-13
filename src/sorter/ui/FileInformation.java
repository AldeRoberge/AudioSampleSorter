package sorter.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.util.Date;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;

import file.FileSizeToString;
import global.Constants;

public class FileInformation extends JPanel {

	private JLabel fileName;
	private JLabel path;
	private JLabel date;
	private JLabel size;

	private FileSystemView fileSystemView = FileSystemView.getFileSystemView();

	public FileInformation() {
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
		fileDetailsLabels.add(new JLabel("Last Modified", JLabel.TRAILING));
		date = new JLabel();
		fileDetailsValues.add(date);
		fileDetailsLabels.add(new JLabel("File size", JLabel.TRAILING));
		size = new JLabel();
		fileDetailsValues.add(size);

		int count = fileDetailsLabels.getComponentCount();
		for (int ii = 0; ii < count; ii++) {
			fileDetailsLabels.getComponent(ii).setEnabled(false);
		}

		setPreferredSize(new Dimension(450, 100)); //gives some extra space

		setLayout(new BorderLayout(3, 3));
		add(fileMainDetails, BorderLayout.CENTER);

	}

	/** Update the File details view with the details of this File. */
	void setFileDetails(File file) {
		//currentFile = file;
		Icon icon = fileSystemView.getSystemIcon(file);
		fileName.setIcon(icon);
		fileName.setText(fileSystemView.getSystemDisplayName(file));
		path.setText(file.getPath());
		date.setText(new Date(file.lastModified()).toString());
		size.setText(FileSizeToString.getFileSizeAsString(file));

		JFrame f = (JFrame) getTopLevelAncestor(); //will trace back to Sorter
		if (f != null) {
			f.setTitle(Constants.SOFTWARE_NAME + " - " + fileSystemView.getSystemDisplayName(file));
		}

		repaint();
	}

}
