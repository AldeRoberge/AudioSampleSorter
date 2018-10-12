package ass.action.editeable;

import java.awt.Dimension;
import java.awt.FlowLayout;
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

import constants.property.Properties;


/**
 * See ActionEditor
 */
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

			JCheckBox booleanPropertyCheckbox = new JCheckBox(propertyToEdit.prefix);
			booleanPropertyCheckbox.setBounds(8, 9, 434, 25);
			booleanPropertyCheckbox.setSelected((Boolean) propertyToEdit.getValue());
			add(booleanPropertyCheckbox);

			ActionListener act = actionEvent -> {
				log.info("Value updated to " + booleanPropertyCheckbox.isSelected());
				propertyToEdit.setValue(booleanPropertyCheckbox.isSelected());
			};
			booleanPropertyCheckbox.addActionListener(act);

		} else if (propertyToEdit.getId() == EditableProperty.STRING_ID) { // JTEXTFIELD

			JLabel lblPrefix = new JLabel(propertyToEdit.prefix);
			lblPrefix.setBounds(12, 16, 56, 16);
			add(lblPrefix);

			JTextField stringPropertyField = new JTextField();
			stringPropertyField.setBounds(80, 13, 135, 22);
			stringPropertyField.setColumns(10);
			stringPropertyField.grabFocus();
			stringPropertyField.setText((String) propertyToEdit.getValue());

			stringPropertyField.getDocument().addDocumentListener(new DocumentListener() {

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
					log.info("Value updated to " + stringPropertyField.getText());
					propertyToEdit.setValue(stringPropertyField.getText());
				}

			});

			add(stringPropertyField);

		} else if (propertyToEdit.getId() == EditableProperty.INT_ID) { // JTEXTFIELD that only accepts ints

			setLayout(new FlowLayout(FlowLayout.LEFT));

			JLabel lblPrefix = new JLabel(propertyToEdit.prefix);
			lblPrefix.setBounds(12, 16, 56, 16);
			add(lblPrefix);

			JTextField intPropertyField = new JTextField();
			intPropertyField.setBounds(80, 13, 135, 22);
			intPropertyField.setColumns(10);
			intPropertyField.setText(propertyToEdit.getValue().toString());

			intPropertyField.addKeyListener(new KeyAdapter() { //Consume non integers
				public void keyTyped(KeyEvent e) {
					char c = e.getKeyChar();
					if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
						getToolkit().beep();
						e.consume();
					}

					if (!intPropertyField.getText().equals("")) {
						log.info("Value updated to " + intPropertyField.getText());
						propertyToEdit.setValue(Integer.parseInt(intPropertyField.getText()));
					}

				}

			});

			add(intPropertyField);

		} else if (propertyToEdit.getId() == EditableProperty.FILE_CHOOSER_ID) { // JFILECHOOSER

			// Select a single file/folder

			JFileChooser chooser = new JFileChooser();
			chooser.setMultiSelectionEnabled(false);
			chooser.setPreferredSize(new Dimension(800, 600));
			chooser.setAcceptAllFileFilterUsed(false);
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.setCurrentDirectory(new File(Properties.ROOT_FOLDER.getValue()));

			JLabel lblPrefix = new JLabel(propertyToEdit.prefix);
			add(lblPrefix);

			JButton btnOpenFileBrowser = new JButton("Open file browser");
			btnOpenFileBrowser.setToolTipText("Open file browser");
			btnOpenFileBrowser.addActionListener(e -> {
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

					String directory = chooser.getCurrentDirectory().toString();

					log.info("Value updated to " + chooser.getSelectedFile());
					propertyToEdit.setValue(chooser.getSelectedFile());
				}
			});
			add(btnOpenFileBrowser);

		} else {
			log.info("EditPropertyPanel", "Unimplemented propertyToEdit");
		}

	}
}
