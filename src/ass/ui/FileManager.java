/*
 * $Id$
 *
 * Copyright 2015 Valentyn Kolesnikov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ass.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import ass.constants.Constants;
import ass.icons.Icons;
import file.ObjectSerializer;
import logger.Logger;

/**
 * Credits :
 * 
 * @author Andrew Thompson
 * @version 2011-06-01
 */
public class FileManager extends JPanel {

	private String TAG = "FileManager";

	public ObjectSerializer<ArrayList<File>> importedFilesSerialiser = new ObjectSerializer<ArrayList<File>>("imported.ser");

	/** Provides nice icons and names for files. */
	private FileSystemView fileSystemView;

	/** Directory listing */
	private JTable table;

	//

	/** Table model for File[]. */
	private FileTableModel fileTableModel;
	private ListSelectionListener listSelectionListener;
	private boolean cellSizesSet = false;
	private int rowIconPadding = 6;

	public FileManager(FileVisualiser openFileManager) {

		/* :) */

		setLayout(new BorderLayout(3, 3));
		setBorder(new EmptyBorder(5, 5, 5, 5));

		fileSystemView = FileSystemView.getFileSystemView();

		JPanel detailView = new JPanel(new BorderLayout(3, 3));

		table = new JTable();
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setAutoCreateRowSorter(true);
		table.setShowVerticalLines(false);
		table.setSelectionBackground(Constants.SICK_PURPLE);
		table.setSelectionForeground(Color.WHITE);

		fileTableModel = new FileTableModel();
		table.setModel(fileTableModel);

		//set font bold for column 1 (see CellRenderer at the bottom of this class)
		table.getColumnModel().getColumn(1).setCellRenderer(new CellRenderer());

		listSelectionListener = new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				ListSelectionModel lsm = (ListSelectionModel) e.getSource();

				boolean isAdjusting = e.getValueIsAdjusting();

				if (!lsm.isSelectionEmpty()) {
					// Find out which indexes are selected.
					int minIndex = lsm.getMinSelectionIndex();
					int maxIndex = lsm.getMaxSelectionIndex();

					ArrayList<File> selectedFiles = new ArrayList<File>();

					for (int i = minIndex; i <= maxIndex; i++) {
						if (lsm.isSelectedIndex(i)) {

							//Fixes row being incorrect after sorting
							int row = table.convertRowIndexToModel(i);

							selectedFiles.add(fileTableModel.getFile(row));

							//
						}
					}

					if (!isAdjusting) {
						if (selectedFiles.size() == 1) { //amount of selected files == 1

							File selecteFiled = selectedFiles.get(0);

							openFileManager.setSelectedFile(selecteFiled);

						} else if (selectedFiles.size() > 1) { //more than 1 selected file

							openFileManager.setSelectedFiles(selectedFiles);

						}
					}

				}

			}

		};
		table.getSelectionModel().addListSelectionListener(listSelectionListener);

		JScrollPane tableScroll = new JScrollPane(table);
		Dimension d = tableScroll.getPreferredSize();
		tableScroll.setPreferredSize(new Dimension((int) d.getWidth(), (int) d.getHeight() / 2));
		detailView.add(tableScroll, BorderLayout.CENTER);

		add(detailView, BorderLayout.CENTER);

		JPanel simpleOutput = new JPanel(new BorderLayout(3, 3));
		JProgressBar progressBar = new JProgressBar();
		simpleOutput.add(progressBar, BorderLayout.EAST);
		progressBar.setVisible(false);

		add(simpleOutput, BorderLayout.SOUTH);

		//Import files stored in serialiser (saves from session to session imported files)
		if (!importedFilesSerialiser.isNull()) {
			importFiles(importedFilesSerialiser.get());
		}

	}

	//Used by FileImporter and importedFileSerialise
	public void importFiles(ArrayList<File> filesToImport) {

		final ArrayList<File> oldFile = fileTableModel.getFiles();

		ArrayList<File> newFile = new ArrayList<File>();
		newFile.addAll(oldFile);

		for (File file : filesToImport) {
			if (!oldFile.contains(file) && file.exists()) {
				newFile.add(file);
			} else {
				String error = "";

				if (oldFile.contains(file)) {
					error = "is a duplicate file";
				} else {
					error = "doesn't exist at this path '" + file.getAbsolutePath() + "'";
				}

				Logger.logWarning(TAG, "Warning : File '" + file.getName() + "' " + error + ".");

			}
		}

		setTableData(newFile);

	}

	/** Update the table on the EDT */
	private void setTableData(ArrayList<File> files) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				table.getSelectionModel().removeListSelectionListener(listSelectionListener);
				fileTableModel.setFiles(files);
				table.getSelectionModel().addListSelectionListener(listSelectionListener);
				if (!cellSizesSet) {

					int rowHeight = 32;

					// size adjustment to better account for icons
					table.setRowHeight(rowHeight); //TODO make this an editeable property

					setColumnWidth(0, -1);
					// setColumnWidth(3, 60);
					// table.getColumnModel().getColumn(3).setMaxWidth(120);
					// setColumnWidth(4, -1);

					cellSizesSet = true;
				}
			}
		});

		importedFilesSerialiser.set(files);
	}

	private void setColumnWidth(int column, int width) {
		TableColumn tableColumn = table.getColumnModel().getColumn(column);
		if (width < 0) {
			// use the preferred width of the header..
			JLabel label = new JLabel((String) tableColumn.getHeaderValue());
			Dimension preferred = label.getPreferredSize();
			// altered 10->14 as per camickr comment.
			width = (int) preferred.getWidth() + 14;
		}
		tableColumn.setPreferredWidth(width);
		tableColumn.setMaxWidth(width);
		tableColumn.setMinWidth(width);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception weTried) {
					weTried.printStackTrace();
				}
				JFrame f = new JFrame(Constants.SOFTWARE_NAME);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				FileManager fileManager = new FileManager(new FileVisualiser());
				f.setContentPane(fileManager);

				f.setIconImage(Icons.SOFTWARE_ICON.getImage());

				f.pack();
				f.setLocationByPlatform(true);
				f.setMinimumSize(f.getSize());
				f.setVisible(true);
			}
		});
	}
}

/** A TableModel to hold File[]. */
class FileTableModel extends AbstractTableModel {

	private static final String TAG = null;
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

	public ArrayList<File> getFiles() {
		return files;
	}

	public void addFile(File file) {
		files.add(file);
		fireTableDataChanged();
	}

}

class CellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		// if (value>17 value<26) {
		this.setValue(table.getValueAt(row, column));
		this.setFont(this.getFont().deriveFont(Font.BOLD));
		//}
		return this;
	}
}
