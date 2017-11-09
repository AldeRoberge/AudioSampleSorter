package macro.macrolist;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import constants.Constants;
import constants.icons.Icons;
import macro.MacroAction;
import macro.MacroEditor;
import util.key.KeysToString;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MacroInfoPanel extends JPanel {

	private MacroInfoPanel me = this;

	MacroAction keyBind;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel labelShortcut;
	private JLabel labelDescription;

	public MacroInfoPanel(MacroAction keyBind, MacroEditor m) {
		this.keyBind = keyBind;

		setPreferredSize(new Dimension(337, 59));
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(5, 5, 326, 49);
		add(panel);
		panel.setLayout(null);

		labelShortcut = new JLabel("Keys");
		labelShortcut.setFont(new Font("Segoe UI Light", Font.PLAIN, 19));
		labelShortcut.setBounds(12, 0, 108, 49);
		panel.add(labelShortcut);

		labelDescription = new JLabel("Actions");
		labelDescription.setFont(new Font("Segoe UI Light", Font.PLAIN, 19));
		labelDescription.setBounds(124, 0, 108, 49);
		panel.add(labelDescription);

		JButton buttonDelete = new JButton("X");
		buttonDelete.setToolTipText("Delete");
		buttonDelete.setFont(new Font("Tahoma", Font.BOLD, 13));
		buttonDelete.setForeground(new Color(255, 0, 0));
		buttonDelete.setMargin(new Insets(-5, -5, -5, -5)); //allows for the 'X' to display even if theres not a lot of space around it
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				m.macroLoader.removeMacro(keyBind, me);
			}
		});
		buttonDelete.setBounds(280, 7, 35, 35);
		panel.add(buttonDelete);

		JButton buttonEdit = new JButton();
		buttonEdit.setToolTipText("Edit");
		buttonEdit.setBounds(234, 7, 35, 35);
		panel.add(buttonEdit);
		buttonEdit.setIcon(Icons.SETTINGS);
		buttonEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.macroListUI.keyBindPanelIsClicked(me);
			}
		});
		buttonEdit.setMargin(new Insets(0, 0, 0, 0));
		buttonEdit.setForeground(Constants.SICK_PURPLE);
		buttonEdit.setFont(new Font("Tahoma", Font.BOLD, 13));

	}

	//Called by refreshInfoPanel(), which is called on addKeyBindInfoPanel
	public void updateText() {
		String lblKeyText = KeysToString.keysToString("", keyBind.keys, "");

		labelShortcut.setText(lblKeyText);
		labelShortcut.setToolTipText(lblKeyText); //in case its cut off

		if (keyBind.actionsToPerform.size() >= 1) {
			labelDescription.setToolTipText(keyBind.actionsToPerform.toString());
		}

		if (keyBind.name != null) { //display the name if it has one

			labelDescription.setText(keyBind.name);

		} else {

			if (keyBind.actionsToPerform.size() > 1) {

				labelDescription.setText(keyBind.actionsToPerform.size() + " actions");

			} else if (keyBind.actionsToPerform.size() == 1) {

				labelDescription.setText(keyBind.actionsToPerform.get(0).toString());

			} else if (keyBind.actionsToPerform.size() == 0) {

				labelDescription.setText("No action");

			}
		}

	}
}
