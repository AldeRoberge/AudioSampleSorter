package macro.macroedit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jnativehook.keyboard.NativeKeyEvent;

import action.ActionManager;
import action.editable.EditeablePropertyEditor;
import action.type.Action;
import key.Key;
import logger.Logger;
import macro.MacroAction;
import macro.MacroEditor;
import util.key.KeysToString;
import util.key.NativeKeyEventToKey;
import javax.swing.ImageIcon;

public class MacroEditorUI extends JPanel {

	private static final String TAG = "LogUI";

	//

	private MacroAction keyBindToEdit;

	//

	private static final Font RESULT_FONT_PLAIN = new Font("Segoe UI Light", Font.PLAIN, 20);
	private static final Font RESULT_FONT_BOLD = new Font("Segoe UI Light", Font.BOLD, 20);

	private boolean isListenningForKeyInputs = false;
	private JTextField keyEditorImputBox;

	private int keysPressedAndNotReleased = 0;

	private boolean newKeyBind;

	private static JPanel columnpanel = new JPanel();
	private static JPanel borderlaoutpanel;

	private static JScrollPane scrollPane;

	private ArrayList<Action> actions = new ArrayList<Action>();
	private ArrayList<MacroActionPanel> macroActionEditPanels = new ArrayList<MacroActionPanel>();

	public EditeablePropertyEditor actionEditor = new EditeablePropertyEditor();
	private JTextField titleEditor;

	public void onHide() {
		isListenningForKeyInputs = false;
	}

	public void onShow() {
	}

	/**
	 * Create the frame.
	 */
	public MacroEditorUI(MacroEditor m) {

		setBounds(0, 0, 355, 271);
		setVisible(true);
		setLayout(null);

		JButton btnAdd = new JButton("Save");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (newKeyBind) {
					newKeyBind = false;
					Logger.logInfo(TAG, "Creating new KeyBind");

					keyBindToEdit.actionsToPerform.clear();

					m.macroLoader.addNewMacro(keyBindToEdit);
				}

				keyBindToEdit.actionsToPerform.clear();

				for (Action action : actions) {
					keyBindToEdit.actionsToPerform.add(action);
				}

				m.macroListUI.refreshInfoPanel();

				m.macroLoader.serialise(); //just save

				m.showMacroEditUI();
			}
		});
		btnAdd.setBounds(12, 233, 330, 25);
		add(btnAdd);

		keyEditorImputBox = new JTextField("Click to edit");
		keyEditorImputBox.setToolTipText("Shortcut");
		keyEditorImputBox.setHorizontalAlignment(SwingConstants.CENTER);
		keyEditorImputBox.setFont(RESULT_FONT_PLAIN);
		keyEditorImputBox.setBackground(Color.decode("#FCFEFF")); //'Ultra Light Cyan'
		keyEditorImputBox.setEditable(false);
		keyEditorImputBox.setBounds(12, 185, 330, 35);
		keyEditorImputBox.setColumns(10);
		add(keyEditorImputBox);

		//Populate the ComboBox

		Logger.logInfo(TAG, "Found " + ActionManager.actions.size() + " actions.");

		JComboBox<Action> comboBox = new JComboBox<Action>();
		comboBox.setToolTipText("Add action");
		for (Action a : ActionManager.actions) {
			comboBox.addItem(a);
		}

		comboBox.setBounds(12, 74, 330, 22);
		add(comboBox);

		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (comboBox.getSelectedItem() != null) {
					Action selectedAction = (Action) comboBox.getSelectedItem();
					addActionAndActionEditPanel(selectedAction);

				}
			}
		});

		scrollPane = new JScrollPane();
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 109, 330, 63);
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

		titleEditor = new JTextField();
		titleEditor.setToolTipText("Click to edit title");
		titleEditor.setFont(new Font("Segoe UI Historic", Font.PLAIN, 20));
		titleEditor.setText("Title");
		titleEditor.setHorizontalAlignment(SwingConstants.CENTER);
		titleEditor.setBounds(72, 13, 270, 48);

		titleEditor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("New title : " + titleEditor.getText());
				keyBindToEdit.name = titleEditor.getText();
			}
		});

		add(titleEditor);
		titleEditor.setColumns(10);

		JButton btnIcon = new JButton("");
		btnIcon.setToolTipText("Click to edit icon");
		btnIcon.setIcon(new ImageIcon(MacroEditorUI.class
				.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Justify-Black.png")));
		btnIcon.setBounds(12, 13, 48, 48);
		add(btnIcon);

		keyEditorImputBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!isListenningForKeyInputs) {
					keyBindToEdit.clearKeys();
					keyEditorImputBox.setText("Press any key");
					Logger.logInfo(TAG, "Now listenning for key inputs");
					isListenningForKeyInputs = true;
					keyEditorImputBox.setFont(RESULT_FONT_PLAIN);
				} else {
					Logger.logInfo(TAG, "Already listenning for key inputs!");
				}
			}
		});

	}

	public void changeKeyBindToEdit(MacroAction keyBindToEdit) {

		//if keyBindToEdit is null, it means MacroListUI wants us to create a new keyBind

		if (keyBindToEdit != null) {
			newKeyBind = false;

			Logger.logInfo(TAG, "Editing existing keybind");

			for (Action a : keyBindToEdit.actionsToPerform) {
				addActionAndActionEditPanel(a);
			}

			titleEditor.setText(keyBindToEdit.name);

		} else {

			Logger.logInfo(TAG, "Creating new keyBind");

			newKeyBind = true;
			keyBindToEdit = new MacroAction();

			titleEditor.setText("Title");

		}

		this.keyBindToEdit = keyBindToEdit;

		updateImputBoxText();
	}

	private void updateImputBoxText() {
		if (keyBindToEdit.keys.size() > 0) {
			keyEditorImputBox.setText(KeysToString.keysToString("[", keyBindToEdit.keys, "]"));
		} else {
			keyEditorImputBox.setText("Click to edit");
		}
	}

	/**
	 * This is a work-around the previous keyboard register hook thing
	 *  that caused both the JTable and the PropertyEditor to not receive imputs.
	 */
	public void globalKeyBoardImput(NativeKeyEvent ke, boolean isPressed) {

		Key e = NativeKeyEventToKey.getJavaKeyEvent(ke);

		if (isListenningForKeyInputs) {

			if (isPressed) {

				if (!keyBindToEdit.keys.contains(e)) {
					keyBindToEdit.keys.add(e);

					keysPressedAndNotReleased++;

					//Build string for field showing name of keys

					updateImputBoxText();

				}
			} else {
				if (keysPressedAndNotReleased == 1) { //is last key to be released

					Logger.logInfo(TAG, "Stopped listenning for events.");
					isListenningForKeyInputs = false;
					keyEditorImputBox.setFont(RESULT_FONT_BOLD);

				}

				keysPressedAndNotReleased--;

			}
		}
	}

	// TODO : update this when adding fiels
	void addActionAndActionEditPanel(Action action) {

		MacroActionPanel infoPanel = new MacroActionPanel(action, this);

		columnpanel.add(infoPanel);

		actions.add(action);
		macroActionEditPanels.add(infoPanel);

		refreshPanels();

	}

	public void clearActionEditPanels() {
		try {
			for (Iterator<MacroActionPanel> iterator = macroActionEditPanels.iterator(); iterator.hasNext();) {
				MacroActionPanel infoP = iterator.next();

				iterator.remove();
				columnpanel.remove(infoP);
			}

			actions.clear();

			refreshPanels();

		} catch (Exception e) {
			Logger.logError(TAG, "Error in clearActionEditPanels", e);
			e.printStackTrace();
		}
	}

	void refreshPanels() {

		for (MacroActionPanel logPanel : macroActionEditPanels) {
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

	public static Color hex2Rgb(String colorStr) {
		//@formatter:off
		return new Color(Integer.valueOf(colorStr.substring(1, 3), 16), Integer.valueOf(colorStr.substring(3, 5), 16),
				Integer.valueOf(colorStr.substring(5, 7), 16));
		//@formatter:on
	}

	public void removeFromPanels(MacroActionPanel me) {

		actions.remove(me.action);

		columnpanel.remove(me);
		macroActionEditPanels.remove(me);

		refreshPanels();
	}
}