package samplerSorter.actions.editeable;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import samplerSorter.logger.Logger;

/**
 * See ActionEditor
 */
public class EditPropertyPanel extends JPanel {

	EditeableProperty propertyToEdit;

	public EditPropertyPanel(EditeableProperty propertyToEdit) {

		this.propertyToEdit = propertyToEdit;

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		Component horizontalStrut = Box.createHorizontalStrut(20);
		add(horizontalStrut);

		//BEGIN THE MADNESS, MOUHAHAHAHAHHA!

		if (propertyToEdit.ID == EditeableProperty.BOOLEAN_ID) {
			JCheckBox chckbxNewCheckBox = new JCheckBox("New check box");
			add(chckbxNewCheckBox);

			ActionListener act = new ActionListener() {
				public void actionPerformed(ActionEvent actionEvent) {
					propertyToEdit.setValue(chckbxNewCheckBox.isSelected());
				}
			};
			chckbxNewCheckBox.addActionListener(act);
		} else {
			Logger.logInfo("EditPropertyPanel", "Unimplemented propertyToEdit");
		}

	}

}
