package util;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class ToolBar extends JToolBar {

	public ToolBar() {
		// mnemonics stop working in a floated toolbar
		setFloatable(false);

		JButton openFile = new JButton("Open");
		openFile.setMnemonic('o');

		openFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				/**try {
					desktop.open(currentFile);
				} catch (Throwable t) {
					showThrowable(t);
				}
				repaint();*/
			}
		});
		add(openFile);

		JButton editFile = new JButton("Edit");
		editFile.setMnemonic('e');
		editFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				/*try {
					desktop.edit(currentFile);
				} catch (Throwable t) {
					showThrowable(t);
				}*/
			}
		});
		add(editFile);

		JButton printFile = new JButton("Print");
		printFile.setMnemonic('p');
		printFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				/*try {
					desktop.print(currentFile);
				} catch (Throwable t) {
					showThrowable(t);
				}*/
			}
		});
		add(printFile);

	}

}