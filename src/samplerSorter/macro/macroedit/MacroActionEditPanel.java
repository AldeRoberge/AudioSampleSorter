package samplerSorter.macro.macroedit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import samplerSorter.actions.Action;

public class MacroActionEditPanel extends JPanel {

	public MacroActionEditPanel me = this;

	MacroEditorUI k;

	Action action;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MacroActionEditPanel(Action action, MacroEditorUI k) {
		this.action = action;
		this.k = k;

		setPreferredSize(new Dimension(337, 24));
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(1, 1, 336, 22);
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
		btnNewButton.setBounds(290, 0, 41, 22);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Edit");
		btnNewButton_1.setBounds(220, 0, 70, 22);
		panel.add(btnNewButton_1);

	}
}
