package ass.file;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JToolBar;

import ass.keyboard.macro.ListenForMacroChanges;
import ass.keyboard.macro.MacroAction;
import ass.keyboard.macro.MacroEditor;
import logger.Logger;

public class ToolBar extends JToolBar implements ListenForMacroChanges {

	private static final String TAG = "ToolBar";

	public ToolBar(MacroEditor macroEditor) {
		super();
		setFloatable(false); //removes the drag function
	}

	@Override
	public void macroChanged(ArrayList<MacroAction> newMacros) {

		Logger.logInfo(TAG, "Repopulating...");

		removeAll(); //empties all the components
		repaint();

		for (MacroAction macroAction : newMacros) {

			if (macroAction.showInToolbar) {
				JButton button = new JButton("Action");
				try {
					button.setIcon(macroAction.getIcon());
					button.setText(macroAction.getName());
					button.setFocusable(false);

					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) { //Perform action(s)
							macroAction.perform();
						}
					});
					add(button);

					//Begin menu

					JPopupMenu menu = new JPopupMenu("Menu");

					JMenuItem item = new JMenuItem("Perform");
					item.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							macroAction.perform();
						}
					});
					menu.add(item);

					menu.add(new JSeparator());

					item = new JMenuItem("Edit");
					item.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {

							//m.setVisible(true);
							//m.showMacroListUI(macroAction);

						}
					});
					menu.add(item);

					item = new JMenuItem("Hide from toolbar");
					item.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							macroAction.showInToolbar = false;
							macroChanged(newMacros); //Makes the button disappear
						}
					});
					menu.add(item);

					//Mouse listener for menu

					button.addMouseListener(new MouseAdapter() {
						public void mousePressed(MouseEvent ev) {
							if (ev.isPopupTrigger()) {
								menu.show(ev.getComponent(), ev.getX(), ev.getY());
							}
						}

						public void mouseReleased(MouseEvent ev) {
							if (ev.isPopupTrigger()) {
								menu.show(ev.getComponent(), ev.getX(), ev.getY());
							}
						}

						public void mouseClicked(MouseEvent ev) {
						}
					});

					//End menu

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		}
	}

}