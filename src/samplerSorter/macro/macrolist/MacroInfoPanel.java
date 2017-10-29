package samplerSorter.macro.macrolist;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import samplerSorter.constants.Constants;
import samplerSorter.constants.Icons;
import samplerSorter.macro.MacroAction;
import samplerSorter.macro.MacroEditor;
import samplerSorter.util.key.KeysToString;

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

		setPreferredSize(new Dimension(345, 67));
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(5, 5, 333, 57);
		panel.setToolTipText("Click to edit");
		add(panel);
		panel.setLayout(null);

		lblKey = new JLabel("Keys");
		lblKey.setFont(new Font("Segoe UI Light", Font.PLAIN, 19));
		lblKey.setBounds(12, 0, 110, 57);
		panel.add(lblKey);

		labelAction = new JLabel("Actions");
		labelAction.setFont(new Font("Segoe UI Light", Font.PLAIN, 19));
		labelAction.setBounds(134, 0, 110, 57);
		panel.add(labelAction);

		JButton btnNewButton = new JButton("X");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setForeground(new Color(255, 0, 0));
		btnNewButton.setMargin(new Insets(0, 0, 0, 0)); //allows for the 'X' to display even if theres not a lot of space around it
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.out.println("M : " + m);
				System.out.println("M.macroloader : " + m.macroLoader);

				m.macroLoader.removeMacro(keyBind, me);
			}
		});
		btnNewButton.setBounds(280, 13, 41, 29);
		panel.add(btnNewButton);

		JButton button = new JButton();
		button.setIcon(Icons.SETTINGS);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.macroListUI.keyBindPanelIsClicked(me);
			}
		});
		button.setMargin(new Insets(0, 0, 0, 0));
		button.setForeground(Constants.SICK_PURPLE);
		button.setFont(new Font("Tahoma", Font.BOLD, 13));
		button.setBounds(234, 13, 41, 29);
		panel.add(button);

	}

	public void updateText() {
		lblKey.setText(KeysToString.keysToString("", keyBind.keys, ""));

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
