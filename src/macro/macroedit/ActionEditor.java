package macro.macroedit;

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

import action.editable.EditPropertyPanel;
import action.editable.EditeableProperty;
import action.type.Action;
import logger.Logger;

//See MacroEditorUI

public class ActionEditor extends JFrame {

	private static final String TAG = "ActionEditorPanel";

	private static JPanel columnPanel = new JPanel();
	private static JPanel borderLayoutPanel;

	private static JScrollPane scrollPane;

	private static ArrayList<EditPropertyPanel> allValueEditorPanels = new ArrayList<EditPropertyPanel>();

	/**
	 * Create the frame.
	 */
	public ActionEditor() {
		setTitle("ActionEditor");

		JPanel contentPane = new JPanel();

		contentPane.setBounds(0, 0, 620, 400);
		contentPane.setVisible(true);
		contentPane.setLayout(new CardLayout(0, 0));

		this.setMinimumSize(new Dimension(390, 200));

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
		columnPanel.setLayout(new GridLayout(0, 1, 0, 1));
		columnPanel.setBackground(Color.gray);
		borderLayoutPanel.add(columnPanel, BorderLayout.NORTH);

		// end

		setContentPane(contentPane);

	}

	// TODO : update this when adding fiels
	public void changeAction(Action e) {

		clear();

		for (EditeableProperty a : e.getEditeableProperties()) {
			EditPropertyPanel infoPanel = new EditPropertyPanel(a);

			columnPanel.add(infoPanel);
			allValueEditorPanels.add(infoPanel);

			refreshInfoPanels();
		}

	}

	private static void refreshInfoPanels() {

		for (EditPropertyPanel logPanel : allValueEditorPanels) {

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
			for (Iterator<EditPropertyPanel> iterator = allValueEditorPanels.iterator(); iterator.hasNext();) {
				EditPropertyPanel editPropertyPanel = iterator.next();

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