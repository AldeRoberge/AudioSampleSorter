package ass.macro.action.editable;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ass.ui.other.fileImporter.FileImporter;
import logger.Logger;

/**
 * See ActionEditor
 */

@SuppressWarnings("unchecked")
public class EditablePropertyPanel extends JPanel {

	private static final String TAG = "EditPropertyPanel";

	private static final int WIDTH = 450;
	private static final int HEIGHT = 44;

	//

	private EditableProperty propertyToEdit;

	//

	public EditablePropertyPanel(EditableProperty<?> property) {

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
					propertyToEdit.setValue((Boolean) chckbxNewCheckBox.isSelected());
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
			stringInputField.setText((String) propertyToEdit.getValue());

			stringInputField.getDocument().addDocumentListener(new DocumentListener() {

				@Override
				public void removeUpdate(DocumentEvent e) {
					updateValue();
				}

				@Override
				public void insertUpdate(DocumentEvent e) {
					updateValue();
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					updateValue();
				}

				public void updateValue() {
					propertyToEdit.setValue(stringInputField.getText());
				}

			});

			add(stringInputField);

		} else if (propertyToEdit.ID == EditableProperty.INT_ID) { // JTEXTFIELD that only accepts ints

			setLayout(new FlowLayout(FlowLayout.LEFT));

			JLabel lblPrefix = new JLabel(propertyToEdit.prefix);
			lblPrefix.setBounds(12, 16, 56, 16);
			add(lblPrefix);

			JTextField intInputField = new JTextField();
			intInputField.setBounds(80, 13, 135, 22);
			intInputField.setColumns(10);
			intInputField.setText(propertyToEdit.getValue().toString());

			intInputField.addKeyListener(new KeyAdapter() { //Consume non integers
				public void keyTyped(KeyEvent e) {
					char c = e.getKeyChar();
					if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
						getToolkit().beep();
						e.consume();
					}
				}
			});

			intInputField.getDocument().addDocumentListener(new DocumentListener() {

				@Override
				public void removeUpdate(DocumentEvent e) {
					updateValue();
				}

				@Override
				public void insertUpdate(DocumentEvent e) {
					updateValue();
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					updateValue();
				}

				public void updateValue() {
					propertyToEdit.setValue(Integer.parseInt((String) intInputField.getText()));
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
