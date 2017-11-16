package ass.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

import ass.macro.ui.MacroAction;
import ass.macro.ui.MacroEditor;
import logger.Logger;

public class ToolBar extends JToolBar {

	private static final String TAG = "ToolBar";

	MacroEditor m;

	public ToolBar(MacroEditor macroEditor) {

		this.m = macroEditor;

		// mnemonics stop working in a floated toolbar
		setFloatable(false);

	}

	public void repopulate() { //this hold a refernece to macroLoader

		Logger.logInfo(TAG, "Repopulating...");

		removeAll(); //empties all the components
		repaint();

		for (MacroAction macroAction : m.macroLoader.macroActions) {

			if (macroAction.showInToolbar) {
				JButton button = new JButton("Action");
				try {
					button.setIcon(macroAction.getIcon());
					button.setText(macroAction.getName());
					button.setFocusable(false);

					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							System.out.println("Performing");
							macroAction.perform();
						}
					});
					add(button);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		}
	}

}