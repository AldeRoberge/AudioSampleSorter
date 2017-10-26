package samplerSorter.macro.macroedit.actionCustomizer;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import samplerSorter.actions.Action;
import samplerSorter.actions.editeable.EditPropertyPanel;
import samplerSorter.actions.editeable.EditeableProperty;
import samplerSorter.logger.Logger;

//Is stored inside a Container inside MacroEditorUI

public class ActionEditor extends JFrame {

	private static final String TAG = "ActionEditorPanel";

	static JPanel columnpanel = new JPanel();
	static JPanel borderlaoutpanel;

	public static JScrollPane scrollPane;

	private static ArrayList<EditPropertyPanel> allValueEditorPanels = new ArrayList<EditPropertyPanel>();

	/**
	 * Create the frame.
	 */
	public ActionEditor() {

		JPanel contentPane = new JPanel();
		
		contentPane.setBounds(0, 0, 620, 400);
		contentPane.setVisible(true);
		contentPane.setLayout(new CardLayout(0, 0));

		// scrolleable list

		// list

		scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 16, 620, 398);
		scrollPane.setAutoscrolls(true);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane);

		borderlaoutpanel = new JPanel();
		scrollPane.setViewportView(borderlaoutpanel);
		borderlaoutpanel.setLayout(new BorderLayout(0, 0));

		columnpanel = new JPanel();
		columnpanel.setLayout(new GridLayout(0, 1, 0, 1));
		columnpanel.setBackground(Color.gray);
		borderlaoutpanel.add(columnpanel, BorderLayout.NORTH);

		// end
		
		setContentPane(contentPane);

	}

	// TODO : update this when adding fiels
	public void changeAction(Action e) {

		clear();

		for (EditeableProperty a : e.getEditeableProperties()) {
			EditPropertyPanel infoPanel = new EditPropertyPanel(a);

			columnpanel.add(infoPanel);
			allValueEditorPanels.add(infoPanel);

			refreshInfoPanels();
		}

	}

	public static void refreshInfoPanels() {

		for (EditPropertyPanel logPanel : allValueEditorPanels) {

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

	public void clear() {
		try {
			for (Iterator<EditPropertyPanel> iterator = allValueEditorPanels.iterator(); iterator.hasNext();) {
				EditPropertyPanel editPropertyPanel = iterator.next();

				iterator.remove();
				columnpanel.remove(editPropertyPanel);
			}

			refreshInfoPanels();
		} catch (Exception e) {
			Logger.logError(TAG, "Error in removeInfoPanel", e);
			e.printStackTrace();
		}
	}

}