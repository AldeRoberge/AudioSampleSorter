package sorter.other;

import java.awt.Color;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Unnused, not workign as intended 
 */

public class AnimatedLoadingIcon {

	static Icon icon = new ImageIcon(new File(".").getAbsolutePath() + "/res/loadingCursor2.gif");

	public static JFrame frame;

	public static void start() {

		JLabel label = new JLabel(icon);

		frame = new JFrame("Animation");

		frame.setUndecorated(true);
		frame.setAlwaysOnTop(true);
		frame.getContentPane().setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
		frame.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
		frame.requestFocus();
		frame.requestFocusInWindow();

		frame.setType(javax.swing.JFrame.Type.UTILITY); //no tray icon

		JPanel p = new JPanel();
		p.setOpaque(false);
		p.setBackground(new Color(0, 0, 0, 0));
		frame.getContentPane().add(p);

		p.add(label);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);

		frame.setVisible(true);

	}

	public static void main(String[] args) {
		start();
	}
}