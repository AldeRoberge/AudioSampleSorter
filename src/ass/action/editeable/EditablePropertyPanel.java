package ass.action.editeable;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import constants.property.PropertiesImpl;


/**
 * See ActionEditor
 */

@SuppressWarnings("unchecked")
class EditablePropertyPanel extends JPanel {

	private Logger log = LoggerFactory.getLogger(EditablePropertyPanel.class);
	
	private static final int WIDTH = 450;
	private static final int HEIGHT = 44;

	//

	private EditableProperty propertyToEdit;

	//

	public EditablePropertyPanel(EditableProperty<?> property) {

		propertyToEdit = property;

		setSize(new Dimension(WIDTH, HEIGHT)); // default dimensions
		setLayout(new FlowLayout(FlowLayout.LEFT)); // default layout

		if (propertyToEdit.getId() == EditableProperty.BOOLEAN_ID) { // JCHECKBOX

			JCheckBox chckbxNewCheckBox = new JCheckBox(propertyToEdit.prefix);
			chckbxNewCheckBox.setBounds(8, 9, 434, 25);
			chckbxNewCheckBox.setSelected((Boolean) propertyToEdit.getValue());
			add(chckbxNewCheckBox);

			ActionListener act = new ActionListener() {
				public void actionPerformed(ActionEvent actionEvent) {
					log.info("Value updated to " + chckbxNewCheckBox.isSelected());
					propertyToEdit.setValue(chckbxNewCheckBox.isSelected());
				}
			};
			chckbxNewCheckBox.addActionListener(act);

		} else if (propertyToEdit.getId() == EditableProperty.STRING_ID) { // JTEXTFIELD

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
					log.info("Value updated to " + stringInputField.getText());
					propertyToEdit.setValue(stringInputField.getText());
				}

			});

			add(stringInputField);

		} else if (propertyToEdit.getId() == EditableProperty.INT_ID) { // JTEXTFIELD that only accepts ints

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

					if (!intInputField.getText().equals("")) {
						log.info("Value updated to " + intInputField.getText());
						propertyToEdit.setValue(Integer.parseInt(intInputField.getText()));
					}

				}

			});

			add(intInputField);

		} else if (propertyToEdit.getId() == EditableProperty.FILE_CHOOSER_ID) { // JFILECHOOSER

			// Select a single file/folder

			JFileChooser chooser = new JFileChooser();
			chooser.setMultiSelectionEnabled(false);
			chooser.setPreferredSize(new Dimension(800, 600));
			chooser.setAcceptAllFileFilterUsed(false);
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.setCurrentDirectory(new File(PropertiesImpl.ROOT_FOLDER.getValue()));

			JLabel lblPrefix = new JLabel(propertyToEdit.prefix);
			add(lblPrefix);

			JButton btnOpenFileBrowser = new JButton("Open file browser");
			btnOpenFileBrowser.setToolTipText("Open file browser");
			btnOpenFileBrowser.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

						String directory = chooser.getCurrentDirectory().toString();

						log.info("Value updated to " + chooser.getSelectedFile());
						propertyToEdit.setValue(chooser.getSelectedFile());
					}
				}
			});
			add(btnOpenFileBrowser);

		} else {
			log.info("EditPropertyPanel", "Unimplemented propertyToEdit");
		}

	}
}
