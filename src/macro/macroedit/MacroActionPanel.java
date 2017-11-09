package macro.macroedit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import action.type.Action;

public class MacroActionPanel extends JPanel {

	private MacroActionPanel me = this;

	Action action;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MacroActionPanel(Action action, MacroEditorUI k) {
		this.action = action;

		setPreferredSize(new Dimension(325, 24));
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(1, 1, 324, 22);
		add(panel);
		panel.setLayout(null);

		JLabel lblAction = new JLabel(action.toString());
		lblAction.setFont(new Font("Segoe UI Light", Font.BOLD, 14));
		lblAction.setBounds(12, 0, 150, 19);
		panel.add(lblAction);

		JButton btnNewButton = new JButton("X");
		btnNewButton.setForeground(Color.RED);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				k.removeFromPanels(me);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setToolTipText("Remove");
		btnNewButton.setBounds(283, -1, 41, 22);
		panel.add(btnNewButton);

		JButton btnEdit = new JButton("Edit");
		btnEdit.setEnabled(action.isEditeable());
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				k.actionEditor.changeAction(action);
				k.actionEditor.setVisible(true);
			}
		});
		btnEdit.setBounds(208, -1, 70, 22);
		panel.add(btnEdit);

	}
}
