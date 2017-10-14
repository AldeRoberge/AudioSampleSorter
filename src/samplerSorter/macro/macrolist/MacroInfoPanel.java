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

	public MacroInfoPanel me = this;

	MacroAction keyBind;

	MacroEditor m;

	boolean isSelected = false;
	public static final Color selectedBackGround = Color.RED;
	public static final Color normalBackGround = Color.WHITE;

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

		setPreferredSize(new Dimension(351, 67));
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(5, 5, 340, 57);
		add(panel);
		panel.setLayout(null);

		lblKey = new JLabel("Keys");
		lblKey.setFont(new Font("Segoe UI Light", Font.PLAIN, 19));
		lblKey.setBounds(12, 0, 117, 57);
		panel.add(lblKey);

		labelAction = new JLabel("Actions");
		labelAction.setFont(new Font("Segoe UI Light", Font.PLAIN, 19));
		labelAction.setBounds(158, 0, 117, 57);
		panel.add(labelAction);

		btnNewButton = new JButton("X");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setForeground(new Color(255, 0, 0));
		btnNewButton.setMargin(new Insets(0, 0, 0, 0)); //allows for the 'X' to display even if theres not a lot of space around it
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.macroLoader.removeKeyBind(keyBind);
				m.macroListUI.removePanel(me);
			}
		});
		btnNewButton.setBounds(287, 13, 41, 29);
		panel.add(btnNewButton);

		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				System.out.println(m);
				System.out.println(m.macroListUI);
				System.out.println(me);

				m.macroListUI.keyBindPanelIsClicked(me);

				isSelected = !isSelected;

				if (isSelected) {

					setBackground(selectedBackGround);
					repaint();

				} else {

					setBackground(normalBackGround);
					repaint();

				}

			}
		});
	}

	public void updateText() {
		System.out.println("Updating text " + keyBind.actionsToPerform.size());

		lblKey.setText(Util.keysToString("", keyBind.keys, ""));
		labelAction.setText(keyBind.actionsToPerform.size() + " action");
	}
}
