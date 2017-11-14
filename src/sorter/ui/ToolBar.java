package sorter.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

import global.icons.Icons;
import keybinds.macro.MacroAction;
import keybinds.macro.MacroEditor;

public class ToolBar extends JToolBar {

	MacroEditor m;

	public void repopulate() {

		System.out.println("Repopulating");

		removeAll(); //empties all the components
		repaint();

		for (MacroAction macroAction : m.macroLoader.macroActions) {
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

	public ToolBar(MacroEditor macroEditor) {

		this.m = macroEditor;

		// mnemonics stop working in a floated toolbar
		setFloatable(false);

	}

}