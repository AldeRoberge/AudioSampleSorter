package ass;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import ass.file.ListenForSelectedFilesChanges;
import ass.keyboard.action.interfaces.FileEvent;
import ass.keyboard.macro.ListenForMacroChanges;
import ass.keyboard.macro.MacroAction;
import constants.Constants;
import constants.icons.Icons;
import constants.library.LibraryManager;
import constants.property.Properties;
import file.FileSizeToString;
import file.FileTypes;
import file.ObjectSerializer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import logger.Logger;
import ui.MiddleOfTheScreen;
import ui.PrettyTimeStatic;

/**
Credits :

@author Andrew Thompson
@version 2011-06-08
@see http://codereview.stackexchange.com/q/4446/7784
@license LGPL

Modifications :
Browses for all children folders (alows to see if the node has children without clicking)
Made FileBro only display file name, path, date and size
FileBro now displays file date as a 'PrettyDate' and file size as readeable (KB, MB, GB, etc)
*/
public class FileManager extends JPanel implements ActionListener, ListenForMacroChanges {

	/** Title of the application */
	public static final String TAG = "ASS MEKANIK 3000"; //TODO RENAME TO TAG
	/** Provides nice icons and names for files. */
	private FileSystemView fileSystemView;

	/** currently selected File. */
	public ArrayList<File> selectedFiles = new ArrayList<File>();

	/** File-system tree. Built Lazily */
	private JTree tree;
	private DefaultTreeModel treeModel;

	/** Directory listing */
	private JTable table;
	private JProgressBar progressBar;

	/** Table model for File array. */
	private FileTableModel fileTableModel;
	private ListSelectionListener listSelectionListener;
	private boolean cellSizesSet = false;

	/** File labels. */

	private JLabel nameLabel;
	private JLabel pathLabel;
	private JLabel dateLabel;
	private JLabel sizeLabel;

	/** File details. */
	private JLabel fileName;
	private JTextField path;
	private JLabel date;
	private JLabel size;

	/** Toolbar */
	private JToolBar toolBar;

	/** Popup menu */

	private JPopupMenu popupMenu = new JPopupMenu();

	private ObjectSerializer<File> currentPath = new ObjectSerializer<File>(LibraryManager.getFileFile());

	public FileManager() {

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

		table.setComponentPopupMenu(popupMenu);

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

					selectedFiles.clear();

					for (int i = minIndex; i <= maxIndex; i++) {
						if (lsm.isSelectedIndex(i)) {

							//Fixes row being incorrect after sortings
							int row = table.convertRowIndexToModel(i);

							selectedFiles.add(fileTableModel.getFile(row));
						}
					}

					if (!isAdjusting) {
						if (selectedFiles.size() == 1) { //amount of selected files == 1

							File selectedFile = selectedFiles.get(0);

							setFileDetails(selectedFile);

						} else if (selectedFiles.size() > 1) { //more than 1 selected file

							setFilesDetails(selectedFiles);

						}

						tellSelectedFilesChanged();

						//Update toolbar and menu
						//tellSelectedFilesChanged(); TODO

						//Save on every selection change, might cause performance issues TODO
						//(this is done to fix actions not serialising the list after moving and deleting)
						//filesSerialiser.serialise(fileTableModel.getFiles());

						//filesSerialiser.serialise();

					}

				}

			}

		};
		table.getSelectionModel().addListSelectionListener(listSelectionListener);

		JScrollPane tableScroll = new JScrollPane(table);
		Dimension d = tableScroll.getPreferredSize();
		tableScroll.setPreferredSize(new Dimension((int) d.getWidth(), (int) d.getHeight() / 2));
		detailView.add(tableScroll, BorderLayout.CENTER);

		// the File tree

		// show the file system roots.

		tree = new JTree();
		tree.setRootVisible(false);
		tree.setCellRenderer(new FileTreeCellRenderer());
		tree.setVisibleRowCount(15);

		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent evt) {

				TreePath[] treePaths = tree.getSelectionModel().getSelectionPaths();
				for (TreePath treePath : treePaths) {
					DefaultMutableTreeNode selectedElement = (DefaultMutableTreeNode) treePath.getLastPathComponent();

					showChildren(selectedElement);
				}

			}
		});

		if (Properties.ROOT_FOLDER.isDefaultValue()) {
			changeRootFolder();
		} else {
			updateRoot();
		}

		JScrollPane treeScroll = new JScrollPane(tree);

		Dimension preferredSize = treeScroll.getPreferredSize();
		Dimension widePreferred = new Dimension(200, (int) preferredSize.getHeight());
		treeScroll.setPreferredSize(widePreferred);

		// details for a File
		JPanel fileMainDetails = new JPanel(new BorderLayout(4, 2));
		fileMainDetails.setBorder(new EmptyBorder(0, 6, 0, 6));

		JPanel fileDetailsLabels = new JPanel(new GridLayout(0, 1, 2, 2));
		fileMainDetails.add(fileDetailsLabels, BorderLayout.WEST);

		JPanel fileDetailsValues = new JPanel(new GridLayout(0, 1, 2, 2));
		fileMainDetails.add(fileDetailsValues, BorderLayout.CENTER);

		nameLabel = new JLabel("File", JLabel.TRAILING);
		fileDetailsLabels.add(nameLabel);
		fileName = new JLabel();
		fileDetailsValues.add(fileName);

		pathLabel = new JLabel("Path", JLabel.TRAILING);
		fileDetailsLabels.add(pathLabel);
		path = new JTextField(5);
		path.setBorder(null); //Removes the border
		path.setEditable(false);
		fileDetailsValues.add(path);

		sizeLabel = new JLabel("File size", JLabel.TRAILING);
		fileDetailsLabels.add(sizeLabel);
		size = new JLabel();
		fileDetailsValues.add(size);

		dateLabel = new JLabel("Last Modified", JLabel.TRAILING);
		fileDetailsLabels.add(dateLabel);
		date = new JLabel();
		fileDetailsValues.add(date);

		//

		toolBar = new JToolBar();
		// remove the ability to move around the toolbar
		toolBar.setFloatable(false);

		JPanel fileView = new JPanel(new BorderLayout(3, 3));

		JScrollPane toolBarScrollPane = new JScrollPane(toolBar);
		toolBarScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		toolBarScrollPane.setViewportView(toolBar);

		fileView.add(toolBarScrollPane, BorderLayout.NORTH);
		fileView.add(fileMainDetails, BorderLayout.CENTER);

		detailView.add(fileView, BorderLayout.SOUTH);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScroll, detailView);
		splitPane.setDividerLocation(250);
		add(splitPane, BorderLayout.CENTER);

		updateEnabledFields(0);

	}

	void changeRootFolder() {
		JOptionPane.showMessageDialog(this, "Please select the upmost folder of your sound library");

		Logger.logInfo(TAG, "Selecting a new root folder...");

		JFileChooser chooser = new JFileChooser();
		chooser.setLocation(MiddleOfTheScreen.getMiddleOfScreenLocationFor(chooser));
		chooser.setCurrentDirectory(new File(Properties.ROOT_FOLDER.getValue()));
		chooser.setDialogTitle("Select folder");
		chooser.setApproveButtonText("Choose");
		Action details = chooser.getActionMap().get("viewTypeDetails"); // show details view
		details.actionPerformed(null);
		chooser.setFileFilter(FileTypes.AUDIO_FILES);
		chooser.setMultiSelectionEnabled(true); // shift + click to select multiple files
		chooser.setPreferredSize(new Dimension(800, 600));
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

			String directory = chooser.getCurrentDirectory().toString();

			Properties.ROOT_FOLDER.setNewValue(directory);

			updateRoot();

		} else {
			Logger.logInfo(TAG + " (Folder Selector)", "No selection");
		}
	}

	private void updateRoot() {

		File rootFile = new File(Properties.ROOT_FOLDER.getValue());

		if (rootFile.exists() && rootFile.isDirectory()) {

			DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootFile);

			File[] roots = fileSystemView.getFiles(rootFile, true);
			for (File fileSystemRoot : roots) {
				DefaultMutableTreeNode node = new DefaultMutableTreeNode(fileSystemRoot);
				File[] files = fileSystemView.getFiles(fileSystemRoot, true);
				for (File file : files) {
					if (file.isDirectory()) {
						node.add(new DefaultMutableTreeNode(file));
					}
				}

				root.add(node);

			}

			treeModel = new DefaultTreeModel(root);
			tree.setModel(treeModel);

			tree.expandRow(0); //Opens desktop content

		}

	}

	public void showRootFile() {
		// ensure the main files are displayed
		tree.setSelectionInterval(0, 0);
	}

	private TreePath findTreePath(File find) {
		for (int ii = 0; ii < tree.getRowCount(); ii++) {
			TreePath treePath = tree.getPathForRow(ii);
			Object object = treePath.getLastPathComponent();
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) object;
			File nodeFile = (File) node.getUserObject();

			if (nodeFile == find) {
				return treePath;
			}
		}
		// not found!
		return null;
	}

	/** Update the table on the EDT */
	private void setTableData(List<File> newFiles) {

		//Validate files

		final List<File> currentFiles = fileTableModel.getFiles();

		for (Iterator<File> iterator = newFiles.iterator(); iterator.hasNext();) {

			File file = iterator.next();

			if (currentFiles.contains(file) || !file.exists() || !FileTypes.AUDIO_FILES.accept(file) || file.isDirectory()) {
				iterator.remove();
			}

		}

		//Update table

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (fileTableModel == null) {
					fileTableModel = new FileTableModel();
					table.setModel(fileTableModel);
				}
				table.getSelectionModel().removeListSelectionListener(listSelectionListener);
				fileTableModel.setFiles(newFiles);
				table.getSelectionModel().addListSelectionListener(listSelectionListener);
				if (!cellSizesSet) {

					table.setRowHeight(40);
					setColumnWidth(0, -1);
					cellSizesSet = true;

				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		if (event.getSource() instanceof FileMenuItem) {
			FileMenuItem menu = (FileMenuItem) event.getSource();

			Logger.logInfo(TAG, "Performing event(s)...");

			menu.perform();
		}

	}

	public void addFiles(ArrayList<File> filesToAdd) {
		final List<File> files = fileTableModel.getFiles();

		for (Iterator<File> iterator = filesToAdd.iterator(); iterator.hasNext();) {
			files.add(iterator.next());
		}

		setTableData(files);

	}

	public void removeFiles(ArrayList<File> filesToRemove) {
		final List<File> files = fileTableModel.getFiles();

		for (Iterator<File> iterator = files.iterator(); iterator.hasNext();) {
			File file = iterator.next();

			if (filesToRemove.contains(file)) {
				iterator.remove();
			}
		}

		setTableData(files);

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

	/** Add the files that are contained within the directory of this node.
	Thanks to Hovercraft Full Of Eels for the SwingWorker fix. */
	private void showChildren(final DefaultMutableTreeNode node) {

		Thread thread = new Thread("File searcher") {

			public void run() {

				tree.setEnabled(false);

				Logger.status("Searching...");

				//

				File parent = (File) node.getUserObject();

				File[] filesInsideParent = fileSystemView.getFiles(parent, true);

				if (parent.isDirectory() && node.isLeaf()) { //Node (parent) is a folder

					for (File childFiles : filesInsideParent) {

						//Populate parent with childFiles

						DefaultMutableTreeNode child = new DefaultMutableTreeNode(childFiles);

						if (childFiles.isDirectory()) { //Children is folder

							for (File childChildFile : fileSystemView.getFiles(childFiles, true)) { //Files inside children

								/**if (childChildFile.isDirectory()) { //getNextNode(childFile)
									child.add(new DefaultMutableTreeNode(childChildFile)); //
								}*/

								child.add(new DefaultMutableTreeNode(childChildFile)); //Add all the nodes inside children

							}

						}

						node.add(child); //Add all the children of the parent

					}

				}

				if (parent.isDirectory()) {
					setTableData(new ArrayList<File>(Arrays.asList(filesInsideParent)));
				}

				Logger.status();

				tree.setEnabled(true);
				setFilesDetails(null);

			}
		};

		thread.start();

	}

	/** Update the File details view with the details of this File. */
	private void setFileDetails(File file) {

		updateEnabledFields(1);

		Icon icon = fileSystemView.getSystemIcon(file);

		fileName.setIcon(icon);
		fileName.setText(fileSystemView.getSystemDisplayName(file));

		path.setText(file.getPath());

		date.setText(PrettyTimeStatic.prettyTime.format(new Date(file.lastModified())));

		size.setText(FileSizeToString.getFileSizeAsString(file) + " (" + file.length() + " bytes)");

		repaint();

		selectedFiles.clear();
		selectedFiles.add(file);

		if (FileTypes.AUDIO_FILES.accept(file)) {

			if (Properties.PLAY_ON_CLICK.getValueAsBoolean()) {
				System.out.println("Playing on click...");

				ASS.getAudioPlayer().play(file);
			}

		}

		/**else if (FileTypes.VIDEO_FILES.accept(file)) {
			Logger.logError(TAG, "Video files are not currently supported!");
		} else if (FileTypes.PICTURE_FILES.accept(file)) {
			Logger.logError(TAG, "Picture files are not currently supported!");
		} else if (FileTypes.TEXT_FILES.accept(file)) {
			Logger.logError(TAG, "Text files are not currently supported!");
		} else {
			Logger.logError(TAG, "Unknown file Type " + file.getName());
		}*/

	}

	/** Update the File details view with the details for these files
	 */
	private void setFilesDetails(ArrayList<File> files) {

		fileName.setIcon(null);
		fileName.setText(""); //Empty because multiple files are selected
		path.setText("");
		date.setText("");
		size.setText("");

		if (files == null) { //used to reset

			updateEnabledFields(0);
			selectedFiles.clear();

		} else {

			updateEnabledFields(files.size());

			selectedFiles = files;

			int totalBytes = 0;
			for (File f : files) {
				totalBytes += f.length();
			}

			size.setText(FileSizeToString.getByteSizeAsString(totalBytes) + " (" + files.size() + " files selected" + ")");

		}

		repaint();

	}

	private void updateEnabledFields(int size2) {

		Color enabled = Color.BLACK;
		Color disabled = Color.GRAY;

		if (size2 == 1) {
			nameLabel.setForeground(enabled);
			pathLabel.setForeground(enabled);
			dateLabel.setForeground(enabled);
			sizeLabel.setForeground(enabled);
		} else if (size2 > 1) {
			nameLabel.setForeground(disabled);
			pathLabel.setForeground(disabled);
			dateLabel.setForeground(disabled);
			sizeLabel.setForeground(enabled);
		} else if (size2 == 0) {
			nameLabel.setForeground(disabled);
			pathLabel.setForeground(disabled);
			dateLabel.setForeground(disabled);
			sizeLabel.setForeground(disabled);
		}

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				JFrame f = new JFrame();
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				FileManager ff = new FileManager();
				ff.showRootFile();

				f.setContentPane(ff);

				ArrayList<Image> images = new ArrayList<Image>();
				images.add(Icons.SMALL_ICON.getImage());
				images.add(Icons.BIG_ICON.getImage());
				f.setIconImages(images);

				f.pack();
				f.setLocationByPlatform(true);
				f.setMinimumSize(f.getSize());
				f.setVisible(true);

			}
		});
	}

	//Waiting for selected files change (used by MacroLoader to update weither MacroAction is enabled based on policy)
	private ArrayList<ListenForSelectedFilesChanges> waitingForChanges = new ArrayList<>();

	public void registerWaitingForFileChanges(ListenForSelectedFilesChanges f) {
		waitingForChanges.add(f);
	}

	void tellSelectedFilesChanged() {

		for (ListenForSelectedFilesChanges l : waitingForChanges) {
			l.filesChanged(selectedFiles.size());
		}

	}

	public void removeSelectedFiles() {
		removeFiles(selectedFiles);
	}

	private void updatePopupMenuAndToolBar() {
		popupMenu.removeAll();
		toolBar.removeAll();

		for (MacroAction macroAction : macros) { //populate JMenuItem

			if (macroAction.showInMenu) {
				JMenuItem item = new FileMenuItem(macroAction);
				item.addActionListener(this);
				item.setEnabled(macroAction.isEnabled);
				popupMenu.add(item);
			}

			if (macroAction.showInToolbar) {

				JButton button = new JButton(macroAction.getName());
				try {
					button.setIcon(macroAction.getIcon());
					button.setToolTipText(macroAction.getToolTip());

					button.setFocusable(false);
					button.setEnabled(macroAction.isEnabled);

					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) { //Perform action(s)
							macroAction.perform();
						}
					});
					toolBar.add(button);

					//Begin menu

					JPopupMenu menu = new JPopupMenu("Menu");

					JMenuItem item = new JMenuItem("Perform");
					item.setEnabled(macroAction.isEnabled);
					item.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							macroAction.perform();
						}
					});
					menu.add(item);

					menu.add(new JSeparator());

					item = new JMenuItem("Edit");
					item.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {

							//m.setVisible(true);
							//m.showMacroListUI(macroAction);

						}
					});
					menu.add(item);

					item = new JMenuItem("Hide from toolbar");
					item.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							//macroAction.showInToolbar = false; show false
							//macroChanged(newMacros); //Makes the button disappear
						}
					});
					menu.add(item);

					//Mouse listener for menu

					button.addMouseListener(new MouseAdapter() {
						public void mousePressed(MouseEvent ev) {
							if (ev.isPopupTrigger()) {
								menu.show(ev.getComponent(), ev.getX(), ev.getY());
							}
						}

						public void mouseReleased(MouseEvent ev) {
							if (ev.isPopupTrigger()) {
								menu.show(ev.getComponent(), ev.getX(), ev.getY());
							}
						}

						public void mouseClicked(MouseEvent ev) {
						}
					});

					//End menu

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	//UPDATE POPUPMENU BEGIN

	public ArrayList<MacroAction> macros = new ArrayList<MacroAction>();

	@Override
	public void macroChanged(ArrayList<MacroAction> newMacros) {
		macros = newMacros;
		updatePopupMenuAndToolBar();
	}

	public void updateFile(FileEvent fe) {
		fileTableModel.updateFile(fe);
	}

	//UPDATE POPUPMENU END

}

class FileTableModel extends AbstractTableModel {

	private static final String TAG = "FileTableModel";
	private List<File> files;
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

	public void setFiles(List<File> files) {
		this.files = files;
		fireTableDataChanged();
	}

	//Custom

	public void updateFile(FileEvent f) {

		if (files.contains(f.oldFile)) {
			files.set(files.indexOf(f.oldFile), f.newFile);
			fireTableDataChanged();

			Logger.logInfo(TAG, "Changed table data...");
		} else {
			Logger.logError(TAG, "MAJOR ERROR : files to update do NOT contain reference to old file!");
		}

	}

	public List<File> getFiles() {
		return files;
	}

	public void addFile(File file) {
		files.add(file);
		fireTableDataChanged();
	}

}

/** A TreeCellRenderer for a File. */
class FileTreeCellRenderer extends DefaultTreeCellRenderer {

	private FileSystemView fileSystemView;

	private JLabel label;

	FileTreeCellRenderer() {
		label = new JLabel();
		label.setOpaque(true);
		fileSystemView = FileSystemView.getFileSystemView();
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		File file = (File) node.getUserObject();
		label.setIcon(fileSystemView.getSystemIcon(file));
		label.setText(fileSystemView.getSystemDisplayName(file));
		label.setToolTipText(file.getPath());

		if (selected) {
			label.setBackground(backgroundSelectionColor);
			label.setForeground(textSelectionColor);
		} else {
			label.setBackground(backgroundNonSelectionColor);
			label.setForeground(textNonSelectionColor);
		}

		return label;
	}
}

class CellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		// if (value>17 value<26) {
		this.setValue(table.getValueAt(row, column));
		this.setFont(this.getFont().deriveFont(Font.ROMAN_BASELINE));
		//}
		return this;
	}
}

class FileMenuItem extends JMenuItem {

	private MacroAction macroAction;

	public FileMenuItem(MacroAction ma) {
		super(ma.getName());

		this.macroAction = ma;

		setToolTipText(macroAction.getToolTip());
		setIcon(macroAction.getIcon());
	}

	public void perform() {
		macroAction.perform();
	}

}