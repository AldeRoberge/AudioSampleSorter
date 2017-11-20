package ass.keyboard.macro.edit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ass.keyboard.action.Action;

class MacroActionPanel extends JPanel {

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

		JLabel lblTitle = new JLabel(action.toString());
		lblTitle.setFont(new Font("Segoe UI Light", Font.BOLD, 14));
		lblTitle.setBounds(12, 0, 259, 19);
		lblTitle.setToolTipText(action.getDescription());
		panel.add(lblTitle);

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

		if (action.hasEditeableProperties()) {
			JButton btnEditProperty = new JButton("Edit");
			btnEditProperty.setEnabled(action.isEditable());
			btnEditProperty.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					k.propertyEditor.changeAction(action);
					k.propertyEditor.setVisible(true);
				}
			});
			btnEditProperty.setBounds(208, -1, 70, 22);
			panel.add(btnEditProperty);
		}
	}
}
