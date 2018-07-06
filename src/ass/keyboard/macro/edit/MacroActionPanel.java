package ass.keyboard.macro.edit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ass.action.interfaces.Action;

class MacroActionPanel extends JPanel {

	private static final Font FONT = new Font("Segoe UI Light", Font.BOLD, 14);

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
		lblTitle.setFont(FONT);
		lblTitle.setBounds(12, 0, 200, 19);
		lblTitle.setToolTipText(action.getDescription());
		panel.add(lblTitle);

		JButton btnRemove = new JButton("X");
		btnRemove.setForeground(Color.RED);
		btnRemove.addActionListener(e -> k.removeFromPanels(me));
		btnRemove.setFont(FONT);
		btnRemove.setToolTipText("Remove");
		btnRemove.setBounds(283, -1, 41, 22);
		panel.add(btnRemove);

		if (action.hasEditeableProperties()) {
			JButton btnEditProperty = new JButton("Edit");
			btnEditProperty.setEnabled(action.isEditable());
			btnEditProperty.addActionListener(e -> {
				k.propertyEditor.changeAction(action);
				k.propertyEditor.updateHeight();
				k.propertyEditor.setVisible(true);
			});
			btnEditProperty.setBounds(208, -1, 70, 22);
			panel.add(btnEditProperty);
		}
	}
}
