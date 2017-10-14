package samplerSorter.macro.macrolist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import samplerSorter.macro.MacroAction;
import samplerSorter.macro.MacroEditor;

public class MacroListUI extends JPanel {

	private static final String TAG = "LogUI";

	public MacroEditor m;

	static JPanel columnpanel = new JPanel();
	static JPanel borderlaoutpanel;

	public JScrollPane scrollPane;

	private ArrayList<MacroInfoPanel> panels = new ArrayList<MacroInfoPanel>();
	private JButton btnAdd;

	/**
	 * Create the frame.
	 */
	public MacroListUI(MacroEditor m) {

		this.m = m;

		setBounds(0, 0, 355, 272);
		setVisible(true);
		setLayout(null);

		// scrolleable list

		// list

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 355, 248);
		scrollPane.setAutoscrolls(true);
		// scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane);

		borderlaoutpanel = new JPanel();
		scrollPane.setViewportView(borderlaoutpanel);
		borderlaoutpanel.setLayout(new BorderLayout(0, 0));

		columnpanel = new JPanel();
		columnpanel.setLayout(new GridLayout(0, 1, 0, 1));
		columnpanel.setBackground(Color.gray);
		borderlaoutpanel.add(columnpanel, BorderLayout.NORTH);

		btnAdd = new JButton("Add new macro");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.showMacroListUI(null); //null because theres no panel to edit
			}
		});
		btnAdd.setBounds(0, 247, 355, 25);
		add(btnAdd);

	}

	public void onShow() {
	}

	public void onHide() {
	}

	// TODO : update this when adding fiels
	public void addKeyBindInfoPanel(MacroAction macroAction) {

		MacroInfoPanel infoPanel = new MacroInfoPanel(macroAction, m);

		columnpanel.add(infoPanel);
		panels.add(infoPanel);

		refreshInfoPanel();

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