package macro.ui.edit;

import macro.action.Action;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

		JButton btnRemove = new JButton("X");
		btnRemove.setForeground(Color.RED);
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				k.removeFromPanels(me);
			}
		});
		btnRemove.setFont(new Font("Segoe UI Light", Font.BOLD, 13));
		btnRemove.setToolTipText("Remove");
		btnRemove.setBounds(283, -1, 41, 22);
		panel.add(btnRemove);

		JButton btnEdit = new JButton("Edit");
		btnEdit.setEnabled(action.isEditable());
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
