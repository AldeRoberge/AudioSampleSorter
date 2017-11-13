package keybinds.action.editable;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import global.logger.Logger;
import sorter.ui.FileImporter;

/**
 * See ActionEditor
 */
public class EditablePropertyPanel extends JPanel {

	private static final String TAG = "EditPropertyPanel";

	private static final int WIDTH = 450;
	private static final int HEIGHT = 44;

	//

	private EditableProperty propertyToEdit;

	//

	public EditablePropertyPanel(EditableProperty property) {

		propertyToEdit = property;

		setSize(new Dimension(WIDTH, HEIGHT)); // default dimensions
		setLayout(new FlowLayout(FlowLayout.LEFT)); // default layout

		if (propertyToEdit.ID == EditableProperty.BOOLEAN_ID) { // JCHECKBOX

			setLayout(null);

			JCheckBox chckbxNewCheckBox = new JCheckBox(propertyToEdit.prefix);
			chckbxNewCheckBox.setBounds(8, 9, 434, 25);
			chckbxNewCheckBox.setSelected((Boolean) propertyToEdit.getValue());
			add(chckbxNewCheckBox);

			ActionListener act = new ActionListener() {
				public void actionPerformed(ActionEvent actionEvent) {
					Logger.logInfo(TAG, "setNewValue : boolean");
					propertyToEdit.setValue(chckbxNewCheckBox.isSelected());
				}
			};
			chckbxNewCheckBox.addActionListener(act);

		} else if (propertyToEdit.ID == EditableProperty.STRING_ID) { // JTEXTFIELD

			JLabel lblPrefix = new JLabel(propertyToEdit.prefix);
			lblPrefix.setBounds(12, 16, 56, 16);
			add(lblPrefix);

			JTextField stringInputField = new JTextField();
			stringInputField.setBounds(80, 13, 135, 22);
			stringInputField.setColumns(10);
			stringInputField.grabFocus();

			stringInputField.getDocument().addDocumentListener(new DocumentListener() {

				@Override
				public void removeUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
				}

				@Override
				public void insertUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					// TODO Auto-generated method stub
				}
			});

			stringInputField.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent ev) {
					System.out.println("ehhhh");
				}
			});

			add(stringInputField);

		} else if (propertyToEdit.ID == EditableProperty.INT_ID) { // JTEXTFIELD
																	// that only
																	// accepts
																	// ints

			setLayout(new FlowLayout(FlowLayout.LEFT));

			JLabel lblPrefix = new JLabel(propertyToEdit.prefix);
			lblPrefix.setBounds(12, 16, 56, 16);
			add(lblPrefix);

			JTextField intInputField = new JTextField();
			intInputField.setBounds(80, 13, 135, 22);
			intInputField.setColumns(10);

			intInputField.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent ev) {
					System.out.println("ehhhh");
				}
			});

			add(intInputField);

		} else if (propertyToEdit.ID == EditableProperty.FILE_CHOOSER_ID) { // JFILECHOOSER

			// Select a single file/folder

			JFileChooser chooser = new JFileChooser();
			chooser.setMultiSelectionEnabled(false);
			chooser.setPreferredSize(new Dimension(800, 600));
			chooser.setAcceptAllFileFilterUsed(false);
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

			JLabel lblPrefix = new JLabel(propertyToEdit.prefix);
			add(lblPrefix);

			JButton btnOpenFileBrowser = new JButton("Browse files");
			btnOpenFileBrowser.setToolTipText("Open file browser");
			btnOpenFileBrowser.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (chooser.showOpenDialog(FileImporter.fileImporterParent) == JFileChooser.APPROVE_OPTION) {

						String directory = chooser.getCurrentDirectory().toString();

						Logger.logInfo(TAG, "setNewValue : file");
						propertyToEdit.setValue(chooser.getSelectedFile());
					}
				}
			});
			add(btnOpenFileBrowser);

		} else {
			Logger.logInfo("EditPropertyPanel", "Unimplemented propertyToEdit");
		}

	}
}
