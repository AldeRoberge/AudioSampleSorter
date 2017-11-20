package ass.keyboard.macro.list;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import ass.keyboard.macro.MacroAction;
import ass.keyboard.macro.MacroEditor;
import constants.Constants;
import icons.Icons;
import key.KeysToString;

public class MacroListUI extends JPanel {

	private static final String TAG = "LogUI";

	private MacroEditor m;

	private static JPanel columnpanel = new JPanel();
	private static JPanel borderlaoutpanel;

	private JScrollPane scrollPane;

	private ArrayList<MacroInfoPanel> panels = new ArrayList<MacroInfoPanel>();

	/**
	 * Create the frame.
	 */
	public MacroListUI(MacroEditor m) {

		this.m = m;

		setBounds(0, 0, 355, 310);

		setLayout(null);

		// scrolleable list

		// list

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 355, 286);
		scrollPane.setAutoscrolls(true);

		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane);

		borderlaoutpanel = new JPanel();
		scrollPane.setViewportView(borderlaoutpanel);
		borderlaoutpanel.setLayout(new BorderLayout(0, 0));

		columnpanel = new JPanel();
		columnpanel.setLayout(new GridLayout(0, 1, 0, 1));
		columnpanel.setBackground(Color.gray);
		borderlaoutpanel.add(columnpanel, BorderLayout.NORTH);

		JButton btnAdd = new JButton("Create new macro");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.showMacroListUI(null); //null because theres no panel to edit
			}
		});
		btnAdd.setBounds(0, 286, 355, 25);
		add(btnAdd);

		setVisible(true);
	}

	public void onShow() {
		refreshInfoPanel();
	}

	public void onHide() {
	}

	// TODO : update this when adding fiels
	public void addKeyBindInfoPanel(MacroAction macroAction) {

		MacroInfoPanel infoPanel = new MacroInfoPanel(macroAction, m);

		columnpanel.add(infoPanel);
		panels.add(infoPanel);

		//refreshInfoPanel(); TODO this removed might cause bugs

	}

	public void refreshInfoPanel() {

		for (MacroInfoPanel logPanel : panels) {

			logPanel.updateText();
			logPanel.validate();
			logPanel.repaint();

		}

		columnpanel.validate();
		columnpanel.repaint();

		borderlaoutpanel.validate();
		borderlaoutpanel.repaint();

		scrollPane.validate();
		scrollPane.repaint();

	}

	public void keyBindPanelIsClicked(MacroInfoPanel me) {

		m.showMacroListUI(me.keyBind); //null
	}

	public void removePanel(MacroInfoPanel macroInfoPanel) {

		columnpanel.remove(macroInfoPanel);
		refreshInfoPanel();

	}

}

class MacroInfoPanel extends JPanel {

	private MacroInfoPanel me = this;

	MacroAction keyBind;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel labelActions;
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

		labelActions = new JLabel("Actions");
		labelActions.setHorizontalAlignment(SwingConstants.RIGHT);
		labelActions.setFont(new Font("Segoe UI Light", Font.PLAIN, 19));
		labelActions.setBounds(132, 0, 92, 49);
		panel.add(labelActions);

		labelTitle = new JLabel("Title and Icon");
		labelTitle.setFont(new Font("Segoe UI Light", Font.PLAIN, 19));
		labelTitle.setBounds(12, 0, 122, 49);
		panel.add(labelTitle);

		JButton buttonDelete = new JButton("X");
		buttonDelete.setFocusable(false);
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
		buttonEdit.setFocusable(false);
		buttonEdit.setToolTipText("Edit actions");
		buttonEdit.setBounds(247, 7, 35, 35);
		buttonEdit.setIcon(Icons.SETTINGS.getImageIcon());
		buttonEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.macroListUI.keyBindPanelIsClicked(me);
			}
		});
		buttonEdit.setMargin(new Insets(0, 0, 0, 0));
		buttonEdit.setForeground(Constants.SICK_PURPLE);
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
		} else {
			labelActions.setText(keysShortcut);
			labelActions.setToolTipText(keysShortcut); //in case its cut off
		}

		if (keyBind.actionsToPerform.size() > 1) {

			labelActions.setText(keyBind.actionsToPerform.size() + " actions");
			labelActions.setToolTipText(keyBind.actionsToPerform.toString());

		} else if (keyBind.actionsToPerform.size() == 1) {

			labelActions.setText(keyBind.actionsToPerform.get(0).toString());
			labelActions.setToolTipText(keyBind.actionsToPerform.toString());

		} else if (keyBind.actionsToPerform.size() == 0) {

			labelActions.setText("No action");

		}

	}
}
