package macro.macroedit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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

import action.ActionManager;
import action.type.Action;
import logger.Logger;
import macro.Key;
import macro.MacroAction;
import macro.MacroEditor;
import util.key.KeysToString;

public class MacroEditorUI extends JPanel {

	private static final String TAG = "LogUI";

	//

	private MacroAction keyBindToEdit;

	//

	private static final Font RESULT_FONT_PLAIN = new Font("Segoe UI Light", Font.PLAIN, 20);
	private static final Font RESULT_FONT_BOLD = new Font("Segoe UI Light", Font.BOLD, 20);

	private boolean isListenningForKeyInputs = false;
	private JTextField keyEditor;

	private int keysPressedAndNotReleased = 0;

	private boolean newKeyBind;

	private static JPanel columnpanel = new JPanel();
	private static JPanel borderlaoutpanel;

	private static JScrollPane scrollPane;

	private ArrayList<Action> actions = new ArrayList<Action>();
	private ArrayList<MacroActionEditPanel> macroActionEditPanels = new ArrayList<MacroActionEditPanel>();

	public ActionEditor actionEditor = new ActionEditor();

	public void onHide() {
		isListenningForKeyInputs = false;
	}

	public void onShow() {
	}

	/**
	 * Create the frame.
	 */
	public MacroEditorUI(MacroEditor m) {

		setBounds(0, 0, 365, 271);
		setVisible(true);
		setLayout(null);

		JButton btnAdd = new JButton("Save");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.out.println("new keybind : " + newKeyBind);

				if (newKeyBind) {
					newKeyBind = false;
					Logger.logInfo(TAG, "Creating new KeyBind");

					keyBindToEdit.actionsToPerform.clear();

					m.macroLoader.addNewMacro(keyBindToEdit);
				}

				keyBindToEdit.actionsToPerform.clear();

				for (Action action : actions) { //on save : 

					//System.out.println(keyBindToEdit.toString());
					//System.out.println(keyBindToEdit.actionsToPerform);

					keyBindToEdit.actionsToPerform.add(action);
				}

				m.macroListUI.refreshInfoPanel();

				m.macroLoader.serialise(); //just save

				m.showMacroEditUI();
			}
		});
		btnAdd.setBounds(12, 233, 341, 25);
		add(btnAdd);

		keyEditor = new JTextField("Click to edit");
		keyEditor.setToolTipText("Click to edit");
		keyEditor.setHorizontalAlignment(SwingConstants.CENTER);
		keyEditor.setFont(RESULT_FONT_PLAIN);
		keyEditor.setBackground(Color.decode("#FCFEFF")); //'Ultra Light Cyan'
		keyEditor.setEditable(false);
		keyEditor.setBounds(12, 13, 341, 35);
		keyEditor.setColumns(10);
		add(keyEditor);

		//Populate the ComboBox

		Logger.logInfo(TAG, "Found " + ActionManager.actions.size() + " actions.");

		JComboBox<Action> comboBox = new JComboBox<Action>();
		for (Action a : ActionManager.actions) {
			comboBox.addItem(a);
		}

		comboBox.setBounds(91, 61, 262, 22);
		add(comboBox);

		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (comboBox.getSelectedItem() != null) {
					Action selectedAction = (Action) comboBox.getSelectedItem();
					addActionAndActionEditPanel(selectedAction);

				}
			}
		});

		JLabel lblAction = new JLabel("Add action :");
		lblAction.setBounds(12, 64, 83, 16);
		add(lblAction);

		scrollPane = new JScrollPane();
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 96, 341, 125);
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

		keyEditor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!isListenningForKeyInputs) {
					keyBindToEdit.clearKeys();
					keyEditor.setText("Press any key");
					Logger.logInfo(TAG, "Now listenning for key inputs");
					isListenningForKeyInputs = true;
					keyEditor.setFont(RESULT_FONT_PLAIN);
				} else {
					Logger.logInfo(TAG, "Already listenning for key inputs!");
				}
			}
		});

		// end

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

			@Override
			public boolean dispatchKeyEvent(KeyEvent ke) {
				synchronized (MacroEditorUI.class) {

					if (isListenningForKeyInputs) {
						/**System.out.println("Event ID : " + ke.getID());
						System.out.println("Key Code " + ke.getKeyCode());*/

						if (ke.getID() == KeyEvent.KEY_PRESSED) {
							Key e = new Key(ke.getKeyCode());

							if (!keyBindToEdit.keys.contains(e)) {
								keysPressedAndNotReleased++;

								keyBindToEdit.keys.add(e);

								//Build string for field showing name of keys

								updateTxtTest();

							}
						} else if (ke.getID() == KeyEvent.KEY_RELEASED) {
							if (keysPressedAndNotReleased == 1) { //is last key to be released

								Logger.logInfo(TAG, "Stopped listenning for events.");
								isListenningForKeyInputs = false;
								keyEditor.setFont(RESULT_FONT_BOLD);

							}

							keysPressedAndNotReleased--;

						}

					}

				}
				return true;
			}

		});

	}

	public void changeKeyBindToEdit(MacroAction keyBindToEdit) {

		if (keyBindToEdit != null) {
			newKeyBind = false;

			for (Action a : keyBindToEdit.actionsToPerform) {
				addActionAndActionEditPanel(a);
			}

		} else {
			System.out.println("keyBindToEdit is null, creating new keyBind");

			newKeyBind = true;
			keyBindToEdit = new MacroAction();

		}

		this.keyBindToEdit = keyBindToEdit;

		updateTxtTest();
	}

	private void updateTxtTest() {
		if (keyBindToEdit.keys.size() > 0) {
			keyEditor.setText(KeysToString.keysToString("[", keyBindToEdit.keys, "]"));
		} else {
			keyEditor.setText("Click to edit");
		}
	}

	// TODO : update this when adding fiels
	void addActionAndActionEditPanel(Action action) {

		MacroActionEditPanel infoPanel = new MacroActionEditPanel(action, this);

		columnpanel.add(infoPanel);

		actions.add(action);
		macroActionEditPanels.add(infoPanel);

		refreshPanels();

	}

	public void clearActionEditPanels() {
		try {
			for (Iterator<MacroActionEditPanel> iterator = macroActionEditPanels.iterator(); iterator.hasNext();) {
				MacroActionEditPanel infoP = iterator.next();

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

		for (MacroActionEditPanel logPanel : macroActionEditPanels) {
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

	public void removeFromPanels(MacroActionEditPanel me) {

		actions.remove(me.action);

		columnpanel.remove(me);
		macroActionEditPanels.remove(me);

		refreshPanels();
	}
}