package macro.macrolist;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import constants.Constants;
import constants.Icons;
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
	private JLabel lblKey;
	private JLabel labelAction;

	public MacroInfoPanel(MacroAction keyBind, MacroEditor m) {
		this.keyBind = keyBind;

		setPreferredSize(new Dimension(337, 59));
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(5, 5, 326, 49);
		panel.setToolTipText("Click to edit");
		add(panel);
		panel.setLayout(null);

		lblKey = new JLabel("Keys");
		lblKey.setFont(new Font("Segoe UI Light", Font.PLAIN, 19));
		lblKey.setBounds(12, 0, 108, 49);
		panel.add(lblKey);

		labelAction = new JLabel("Actions");
		labelAction.setFont(new Font("Segoe UI Light", Font.PLAIN, 19));
		labelAction.setBounds(124, 0, 108, 49);
		panel.add(labelAction);

		JButton btnNewButton = new JButton("X");
		btnNewButton.setToolTipText("Remove");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setForeground(new Color(255, 0, 0));
		btnNewButton.setMargin(new Insets(-5, -5, -5, -5)); //allows for the 'X' to display even if theres not a lot of space around it
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				m.macroLoader.removeMacro(keyBind, me);
			}
		});
		btnNewButton.setBounds(280, 7, 35, 35);
		panel.add(btnNewButton);

		JButton button = new JButton();
		button.setToolTipText("Edit");
		button.setBounds(234, 7, 35, 35);
		panel.add(button);
		button.setIcon(Icons.SETTINGS);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.macroListUI.keyBindPanelIsClicked(me);
			}
		});
		button.setMargin(new Insets(0, 0, 0, 0));
		button.setForeground(Constants.SICK_PURPLE);
		button.setFont(new Font("Tahoma", Font.BOLD, 13));

	}

	public void updateText() {
		String lblKeyText = KeysToString.keysToString("", keyBind.keys, "");

		lblKey.setText(lblKeyText);
		lblKey.setToolTipText(lblKeyText); //in case its cut off

		if (keyBind.actionsToPerform.size() > 1) {

			labelAction.setText(keyBind.actionsToPerform.size() + " actions");
			labelAction.setToolTipText(keyBind.actionsToPerform.toString());

		} else if (keyBind.actionsToPerform.size() == 1) {

			labelAction.setText(keyBind.actionsToPerform.get(0).toString());

		} else if (keyBind.actionsToPerform.size() == 0) {

			labelAction.setText("No action");

		}

	}
}
