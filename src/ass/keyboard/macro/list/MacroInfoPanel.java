package ass.keyboard.macro.list;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ass.keyboard.macro.MacroAction;
import ass.keyboard.macro.MacroEditor;
import constants.icons.Icons;
import key.KeysToString;

public class MacroInfoPanel extends JPanel {

	private MacroInfoPanel me = this;

	MacroAction keyBind;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel labelTitle;

	public MacroInfoPanel(MacroAction keyBind, MacroEditor m) {
		this.keyBind = keyBind;

		setPreferredSize(new Dimension(343, 59));
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(5, 5, 332, 49);
		add(panel);
		panel.setLayout(null);

		labelTitle = new JLabel("Title");
		labelTitle.setFont(new Font("Segoe UI Light", Font.PLAIN, 19));
		labelTitle.setBounds(12, 0, 223, 49);
		panel.add(labelTitle);

		JButton buttonDelete = new JButton("X");
		buttonDelete.setFocusable(true);
		buttonDelete.setToolTipText("Delete");
		buttonDelete.setFont(new Font("Segoe UI Light", Font.BOLD, 13));
		buttonDelete.setForeground(new Color(255, 0, 0));
		buttonDelete.setMargin(new Insets(-5, -5, -5, -5)); //allows for the 'X' to display even if theres not a lot of space around it
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.macroLoader.removeMacro(keyBind);
				m.macroListUI.removePanel(me);
			}
		});
		buttonDelete.setBounds(290, 7, 35, 35);
		panel.add(buttonDelete);

		JButton buttonEdit = new JButton();
		buttonEdit.setFocusable(true);
		buttonEdit.setToolTipText("Edit actions");
		buttonEdit.setBounds(247, 7, 35, 35);
		buttonEdit.setIcon(Icons.SETTINGS.getImageIcon());
		buttonEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.macroListUI.keyBindPanelIsClicked(me);
			}
		});
		buttonEdit.setMargin(new Insets(0, 0, 0, 0));
		buttonEdit.setForeground(Color.DARK_GRAY);
		buttonEdit.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel.add(buttonEdit);
	}

	//Called by refreshInfoPanel(), which is called on addKeyBindInfoPanel
	public void updateText() {
		String keysShortcut = KeysToString.keysToString("", keyBind.keys, "");

		if (keyBind.getName() != null) { //display the name if it has one
			labelTitle.setText(keyBind.getName());
		} else {
			labelTitle.setText(keysShortcut);
		}

		if (keyBind.getIcon() != null) {
			labelTitle.setIcon(keyBind.getIcon());
		}

		if (keyBind.actionsToPerform.size() > 1) {

			labelTitle.setToolTipText(keyBind.actionsToPerform.toString());

		} else if (keyBind.actionsToPerform.size() == 0) {

		}

	}
}
