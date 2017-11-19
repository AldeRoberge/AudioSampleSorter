package ass.keyboard.action.editable;

import ass.keyboard.action.Action;
import logger.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

//See MacroEditorUI

public class EditablePropertyEditor extends JFrame {

	private static final String TAG = "EditablePropertyEditor";

	private static JPanel columnPanel = new JPanel();
	private static JPanel borderLayoutPanel;

	private static JScrollPane scrollPane;

	private static ArrayList<EditablePropertyPanel> allValueEditorPanels = new ArrayList<EditablePropertyPanel>();

	/**
	 * Create the frame.
	 */
	public EditablePropertyEditor() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(EditablePropertyEditor.class
				.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Paste-Black@2x.png")));
		setTitle("Edit Action Property");

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

		for (EditableProperty<?> a : e.getEditableProperties()) {
			EditablePropertyPanel infoPanel = new EditablePropertyPanel(a);

			allValueEditorPanels.add(infoPanel);
			columnPanel.add(infoPanel);

			refreshInfoPanels();
		}

	}

	private static void refreshInfoPanels() {

		for (EditablePropertyPanel logPanel : allValueEditorPanels) {

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
			for (Iterator<EditablePropertyPanel> iterator = allValueEditorPanels.iterator(); iterator.hasNext();) {
				EditablePropertyPanel editPropertyPanel = iterator.next();

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