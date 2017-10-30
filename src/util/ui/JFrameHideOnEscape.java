package util.ui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

public class JFrameHideOnEscape {
	/**
	 * Makes a JFrame hide (setVisible false) on Escape key press
	 * Thanks to https://stackoverflow.com/a/661244/3224295
	 */

	public static void addEscapeListener(final JFrame frame) {

		frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel"); //$NON-NLS-1$
		frame.getRootPane().getActionMap().put("Cancel", new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});

	}
}
