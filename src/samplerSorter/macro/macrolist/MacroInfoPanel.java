package samplerSorter.macro.macrolist;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import samplerSorter.macro.MacroAction;
import samplerSorter.macro.MacroEditor;
import samplerSorter.util.Util;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MacroInfoPanel extends JPanel {

	MacroEditor m;

	public MacroInfoPanel me = this;

	MacroAction keyBind;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblKey;
	private JLabel labelAction;
	private JButton btnNewButton;

	public MacroInfoPanel(MacroAction keyBind, MacroEditor m) {
		this.keyBind = keyBind;
		this.m = m;

		setPreferredSize(new Dimension(345, 67));
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(5, 5, 333, 57);
		add(panel);
		panel.setLayout(null);

		lblKey = new JLabel("Keys");
		lblKey.setFont(new Font("Segoe UI Light", Font.PLAIN, 19));
		lblKey.setBounds(12, 0, 117, 57);
		panel.add(lblKey);

		labelAction = new JLabel("Actions");
		labelAction.setFont(new Font("Segoe UI Light", Font.PLAIN, 19));
		labelAction.setBounds(158, 0, 110, 57);
		panel.add(labelAction);

		btnNewButton = new JButton("X");
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

		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				m.macroListUI.keyBindPanelIsClicked(me);

			}
		});
	}
<<<<<<< HEAD

	public void updateText() {
		lblKey.setText(Util.keysToString("", keyBind.keys, ""));

		if (keyBind.actionsToPerform.size() > 1) {

			labelAction.setText(keyBind.actionsToPerform.size() + " action");
			labelAction.setToolTipText(keyBind.actionsToPerform.toString());

		} else if (keyBind.actionsToPerform.size() == 1) {

			labelAction.setText(keyBind.actionsToPerform.get(0).toString());

		} else if (keyBind.actionsToPerform.size() == 0) {

			labelAction.setText("No action");

		}

=======

	public void updateText() {
		lblKey.setText(Util.keysToString("", keyBind.keys, ""));
		labelAction.setText(keyBind.actionsToPerform.size() + " action");
>>>>>>> e79edf06f91b91693f6e330a3935515b00f4ab1c
	}
}
