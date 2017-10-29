package samplerSorter.history;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import samplerSorter.logger.Logger;

import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.Toolkit;

//See MacroEditorUI

class HistoryEditor extends JFrame {

	private static final String TAG = "HistoryEditor";

	private static JPanel columnpanel = new JPanel();
	private static JPanel borderlaoutpanel;
	private static JScrollPane scrollPane;

	private static ArrayList<HistoryEventPanel> allPanels = new ArrayList<HistoryEventPanel>();

	/**
	 * Create the frame.
	 */
	private HistoryEditor() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(HistoryEditor.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
		setTitle("HistoryEditor");

		JPanel contentPanel = new JPanel();
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		JPanel buttonPanel = new JPanel();
		contentPanel.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnNewButton = new JButton("Redo");
		buttonPanel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Undo");
		buttonPanel.add(btnNewButton_1);

		JPanel scrollPanePanel = new JPanel();
		contentPanel.add(scrollPanePanel, BorderLayout.CENTER);
		scrollPanePanel.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 16, 620, 398);
		scrollPane.setAutoscrolls(true);
		scrollPanePanel.add(scrollPane, BorderLayout.CENTER);

		borderlaoutpanel = new JPanel();
		scrollPane.setViewportView(borderlaoutpanel);
		borderlaoutpanel.setLayout(new BorderLayout(0, 0));

		columnpanel = new JPanel();
		columnpanel.setLayout(new GridLayout(0, 1, 0, 1));
		columnpanel.setBackground(Color.gray);
		borderlaoutpanel.add(columnpanel, BorderLayout.NORTH);

	}

	// TODO : update this when adding fiels
	public void addHistoryEvent(Event he) {

		HistoryEventPanel infoPanel = new HistoryEventPanel(he);

		columnpanel.add(infoPanel);
		allPanels.add(infoPanel);

		refreshInfoPanels();

	}

	private static void refreshInfoPanels() {

		for (HistoryEventPanel logPanel : allPanels) {

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

	public void remove(Event e) {
		try {
			for (Iterator<HistoryEventPanel> iterator = allPanels.iterator(); iterator.hasNext();) {
				HistoryEventPanel editPropertyPanel = iterator.next();

				iterator.remove();
				columnpanel.remove(editPropertyPanel);
			}

			refreshInfoPanels();
		} catch (Exception e1) {
			Logger.logError(TAG, "Error in removeInfoPanel", e1);
			e1.printStackTrace();
		}
	}

}