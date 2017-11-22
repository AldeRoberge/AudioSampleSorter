package ass.keyboard.action.editable;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ass.keyboard.action.interfaces.Action;
import logger.Logger;
import ui.MiddleOfTheScreen;

//See MacroEditorUI

public class EditablePropertyEditor extends JFrame {

	private static final String TAG = "EditablePropertyEditor";

	private JPanel editeablePropertyPanels = new JPanel();
	private static JScrollPane scrollPane;

	private static ArrayList<EditablePropertyPanel> allValueEditorPanels = new ArrayList<EditablePropertyPanel>();

	/**
	 * Create the frame.
	 */
	public EditablePropertyEditor() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(EditablePropertyEditor.class.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Paste-Black@2x.png")));
		setTitle("Edit Action Properties");

		JPanel contentPane = new JPanel();

		contentPane.setBounds(0, 0, 620, 400);
		contentPane.setLayout(new CardLayout(0, 0));

		contentPane.setVisible(true);

		// scrolleable list

		scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 16, 620, 398);
		scrollPane.setAutoscrolls(true);
		contentPane.add(scrollPane);

		editeablePropertyPanels = new JPanel();
		editeablePropertyPanels.setLayout(new BoxLayout(editeablePropertyPanels, BoxLayout.PAGE_AXIS));
		scrollPane.setViewportView(editeablePropertyPanels);

		setContentPane(contentPane);

		// end

		pack();

		setLocation(MiddleOfTheScreen.getMiddleOfScreenLocationFor(this));

	}

	// TODO : update this when adding fields
	public void changeAction(Action e) {

		allValueEditorPanels.clear();
		editeablePropertyPanels.removeAll();

		for (EditableProperty<?> a : e.getEditableProperties()) {
			EditablePropertyPanel infoPanel = new EditablePropertyPanel(a);

			allValueEditorPanels.add(infoPanel);
			editeablePropertyPanels.add(infoPanel);
		}

		refreshInfoPanels();

	}

	private void refreshInfoPanels() {

		for (EditablePropertyPanel logPanel : allValueEditorPanels) {

			logPanel.validate();
			logPanel.repaint();

		}

		editeablePropertyPanels.validate();
		editeablePropertyPanels.repaint();

		scrollPane.validate();
		scrollPane.repaint();

	}

}