package keybinds.action.editeable;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import global.logger.Logger;
import keybinds.action.Action;

import java.awt.Toolkit;

//See MacroEditorUI

public class EditeablePropertyEditor extends JFrame {

	private static final String TAG = "ActionEditorPanel";

	private static JPanel columnPanel = new JPanel();
	private static JPanel borderLayoutPanel;

	private static JScrollPane scrollPane;

	private static ArrayList<EditeablePropertyPanel> allValueEditorPanels = new ArrayList<EditeablePropertyPanel>();

	/**
	 * Create the frame.
	 */
	public EditeablePropertyEditor() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(EditeablePropertyEditor.class
				.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Paste-Black@2x.png")));
		setTitle("ActionEditor");

		JPanel contentPane = new JPanel();

		contentPane.setBounds(0, 0, 620, 400);
		contentPane.setLayout(new CardLayout(0, 0));

		this.setMinimumSize(new Dimension(390, 200));

		contentPane.setVisible(true);

		// scrolleable list

		// list

		scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 16, 620, 398);
		scrollPane.setAutoscrolls(true);
		contentPane.add(scrollPane);

		borderLayoutPanel = new JPanel();
		scrollPane.setViewportView(borderLayoutPanel);
		borderLayoutPanel.setLayout(new BorderLayout(0, 0));

		columnPanel = new JPanel();
		columnPanel.setLayout(new GridLayout(0, 1, 0, 0));
		columnPanel.setBackground(Color.red);
		borderLayoutPanel.add(columnPanel, BorderLayout.NORTH);

		// end

		setContentPane(contentPane);

		pack();

	}

	// TODO : update this when adding fiels
	public void changeAction(Action e) {

		clear();

		for (EditeableProperty a : e.getEditeableProperties()) {
			EditeablePropertyPanel infoPanel = new EditeablePropertyPanel(a);

			allValueEditorPanels.add(infoPanel);
			columnPanel.add(infoPanel);

			refreshInfoPanels();
		}

	}

	private static void refreshInfoPanels() {

		for (EditeablePropertyPanel logPanel : allValueEditorPanels) {

			logPanel.validate();
			logPanel.repaint();

		}

		columnPanel.validate();
		columnPanel.repaint();

		borderLayoutPanel.validate();
		borderLayoutPanel.repaint();

		scrollPane.validate();
		scrollPane.repaint();

	}

	void clear() {
		try {
			for (Iterator<EditeablePropertyPanel> iterator = allValueEditorPanels.iterator(); iterator.hasNext();) {
				EditeablePropertyPanel editPropertyPanel = iterator.next();

				iterator.remove();
				columnPanel.remove(editPropertyPanel);
			}

			refreshInfoPanels();
		} catch (Exception e) {
			Logger.logError(TAG, "Error in removeInfoPanel", e);
			e.printStackTrace();
		}
	}

}