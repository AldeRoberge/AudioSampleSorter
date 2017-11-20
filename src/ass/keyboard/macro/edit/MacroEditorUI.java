package ass.keyboard.macro.edit;

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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jnativehook.keyboard.NativeKeyEvent;

import ass.keyboard.action.Action;
import ass.keyboard.action.editable.EditablePropertyEditor;
import ass.keyboard.action.type.file.impl.PlayAction;
import ass.keyboard.action.type.file.impl.RenameAction;
import ass.keyboard.action.type.file.impl.TestAction;
import ass.keyboard.action.type.ui.impl.ShowUIAction;
import ass.keyboard.key.Key;
import ass.keyboard.macro.MacroAction;
import ass.keyboard.macro.MacroEditor;
import icons.UserIcon;
import icons.iconChooser.GetIcon;
import icons.iconChooser.IconChooser;
import key.KeysToString;
import key.NativeKeyEventToKey;
import logger.Logger;

public class MacroEditorUI extends JPanel implements GetIcon {

	private static final String TAG = "MacroEditor";

	private static ArrayList<Action> default_actions = new ArrayList<Action>();

	static {

		// Simple UI Actions
		ShowUIAction.init();

		for (Action sUIa : ShowUIAction.UIActions) {
			default_actions.add(sUIa);
		}

		//Other Actions
		default_actions.add(new PlayAction());
		default_actions.add(new RenameAction());
		default_actions.add(new TestAction());

		Logger.logInfo(TAG, "Found " + default_actions + " actions.");

	}

	private IconChooser iconChooser = new IconChooser();

	private MacroEditorUI me = this;

	//

	private MacroAction keyBindToEdit;

	//

	private static final Font RESULT_FONT_PLAIN = new Font("Segoe UI Light", Font.PLAIN, 20);
	private static final Font RESULT_FONT_BOLD = new Font("Segoe UI Light", Font.BOLD, 20);

	private boolean isListenningForKeyInputs = false;
	private ArrayList<Key> keysPressedAndNotReleased = new ArrayList<Key>();
	private JTextField keyEditorImputBox;

	private boolean newKeyBind;

	private static JPanel columnpanel = new JPanel();
	private static JPanel borderlaoutpanel;

	private static JScrollPane scrollPane;

	private ArrayList<Action> actions = new ArrayList<Action>();
	private ArrayList<MacroActionPanel> macroActionEditPanels = new ArrayList<MacroActionPanel>();

	public EditablePropertyEditor propertyEditor = new EditablePropertyEditor();
	private JTextField titleEditor;
	private JButton iconButton;
	private JCheckBox chckBoxToolbar;
	private JCheckBox chckboxMenu;

	public void onHide() {
		isListenningForKeyInputs = false;
	}

	public void onShow() {
	}

	/**
	 * Create the frame.
	 */
	public MacroEditorUI(MacroEditor m) {

		setBounds(0, 0, 355, 310);
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

				m.macroLoader.tellMacroChanged();
			}
		});
		btnAdd.setBounds(12, 272, 330, 25);
		add(btnAdd);

		keyEditorImputBox = new JTextField("Click to edit macro");
		keyEditorImputBox.setToolTipText("Shortcut");
		keyEditorImputBox.setHorizontalAlignment(SwingConstants.CENTER);
		keyEditorImputBox.setFont(RESULT_FONT_PLAIN);
		keyEditorImputBox.setBackground(Color.decode("#FCFEFF")); //'Ultra Light Cyan'
		keyEditorImputBox.setEditable(false);
		keyEditorImputBox.setBounds(12, 224, 330, 35);
		keyEditorImputBox.setColumns(10);
		add(keyEditorImputBox);

		//Populate the ComboBox

		JComboBox<Action> comboBox = new JComboBox<Action>();
		comboBox.setToolTipText("Add action");
		for (Action a : default_actions) {
			comboBox.addItem(a);
		}

		comboBox.setBounds(105, 58, 237, 22);
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
		scrollPane.setBounds(12, 93, 330, 118);
		scrollPane.setAutoscrolls(true);
		// scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane);

		borderlaoutpanel = new JPanel();
		scrollPane.setViewportView(borderlaoutpanel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		borderlaoutpanel.setLayout(new BorderLayout(0, 0));

		columnpanel = new JPanel();
		columnpanel.setLayout(new GridLayout(0, 1, 0, 1));
		columnpanel.setBackground(Color.gray);
		borderlaoutpanel.add(columnpanel, BorderLayout.NORTH);

		titleEditor = new JTextField();
		titleEditor.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				updateName();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				updateName();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}

			public void updateName() {
				if (keyBindToEdit != null) { //sometimes is triggered on software launch
					keyBindToEdit.setName(titleEditor.getText());
				}
			}
		});

		titleEditor.setToolTipText("Click to edit title");
		titleEditor.setFont(new Font("Segoe UI Light", Font.BOLD, 20));
		titleEditor.setText("Title");
		titleEditor.setHorizontalAlignment(SwingConstants.CENTER);
		titleEditor.setBounds(56, 13, 209, 32);

		titleEditor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("KeyBind has a new title : " + titleEditor.getText());
				keyBindToEdit.setName(titleEditor.getText());
			}
		});

		add(titleEditor);
		titleEditor.setColumns(10);

		//

		iconButton = new JButton();
		iconButton.setToolTipText("Click to edit icon");
		iconButton.setIcon(new ImageIcon(MacroEditorUI.class.getResource("/com/sun/deploy/uitoolkit/impl/fx/ui/resources/image/graybox_error.png")));

		iconButton.setFocusable(false); //Removes the stupid 'selected' border
		iconButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iconChooser.getIcon(me);
			}
		});

		//

		iconButton.setBounds(12, 13, 32, 32);
		add(iconButton);

		chckBoxToolbar = new JCheckBox("Toolbar");
		chckBoxToolbar.setToolTipText("Show in toolbar");
		chckBoxToolbar.setSelected(true);

		ActionListener act = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				keyBindToEdit.showInToolbar = chckBoxToolbar.isSelected();
			}
		};
		chckBoxToolbar.addActionListener(act);

		chckBoxToolbar.setBounds(273, 13, 82, 16);
		add(chckBoxToolbar);

		JLabel lblAddAction = new JLabel("Add action :");
		lblAddAction.setBounds(12, 61, 81, 16);
		add(lblAddAction);

		chckboxMenu = new JCheckBox("Menu");
		chckboxMenu.setToolTipText("Show in right click menu");
		chckboxMenu.setSelected(true);
		chckboxMenu.setBounds(273, 33, 82, 16);

		ActionListener act2 = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				keyBindToEdit.showInMenu = chckboxMenu.isSelected();
			}
		};
		chckboxMenu.addActionListener(act2);
		add(chckboxMenu);

		keyEditorImputBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!isListenningForKeyInputs) {
					keyBindToEdit.clearKeys();
					keyEditorImputBox.setText("Press any key(s)");
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

			chckBoxToolbar.setSelected(keyBindToEdit.showInToolbar);
			chckboxMenu.setSelected(keyBindToEdit.showInMenu);

			if (keyBindToEdit.getIcon() != null) {

				//System.out.println("Setting icon : " + keyBindToEdit.getIconPath());
				iconButton.setIcon(keyBindToEdit.getIcon());

			}

		} else {

			Logger.logInfo(TAG, "Creating new keyBind");

			newKeyBind = true;
			keyBindToEdit = new MacroAction("Title");

		}

		titleEditor.setText(keyBindToEdit.getName());

		this.keyBindToEdit = keyBindToEdit;

		updateImputBoxText();
	}

	private void updateImputBoxText() {
		if (keyBindToEdit != null) {
			if (keyBindToEdit.keys.size() > 0) {
				keyEditorImputBox.setText(KeysToString.keysToString("[", keyBindToEdit.keys, "]"));
			} else {
				keyEditorImputBox.setText("Edit shortcut");
			}
		} //Else : its a new keyBind, so do nothing
	}

	/**
	 * This is a work-around the previous keyboard register hook thing
	 *  that caused both the JTable and the PropertyEditor to not receive imputs.
	 */
	public void globalKeyBoardImput(NativeKeyEvent ke, boolean isPressed) {

		Key k = NativeKeyEventToKey.getJavaKeyEvent(ke);

		if (isListenningForKeyInputs) {

			if (isPressed) {
				keyPressed(k);
			} else {
				keyReleased(k);
			}
		}

		updateImputBoxText();
	}

	private void keyPressed(Key k) {
		if (!keysPressedAndNotReleased.contains(k)) {
			keysPressedAndNotReleased.add(k);

			if (!keyBindToEdit.keys.contains(k)) {
				keyBindToEdit.keys.add(k);
			}
		}
	}

	private void keyReleased(Key k) {

		if (keysPressedAndNotReleased.contains(k)) {
			keysPressedAndNotReleased.remove(k);
		}

		if (keysPressedAndNotReleased.size() == 0) { //no more keys to be released
			Logger.logInfo(TAG, "Stopped listenning for events.");
			isListenningForKeyInputs = false;
			keyEditorImputBox.setFont(RESULT_FONT_BOLD);
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
		return new Color(Integer.valueOf(colorStr.substring(1, 3), 16), Integer.valueOf(colorStr.substring(3, 5), 16), Integer.valueOf(colorStr.substring(5, 7), 16));
		//@formatter:on
	}

	public void removeFromPanels(MacroActionPanel me) {

		actions.remove(me.action);

		columnpanel.remove(me);
		macroActionEditPanels.remove(me);

		refreshPanels();
	}

	@Override
	public void GetResponse(UserIcon icon) { //Get response after asking IconChooser
		keyBindToEdit.setIcon(icon);
		iconButton.setIcon(icon.getImageIcon());
	}
}