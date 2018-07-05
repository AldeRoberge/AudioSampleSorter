package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import alde.commons.util.window.UtilityJFrame;
import constants.Constants;

public class FloatingLoadingBar extends UtilityJFrame {
	private JProgressBar progressBar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				FloatingLoadingBar frame = new FloatingLoadingBar();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FloatingLoadingBar() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(FloatingLoadingBar.class
				.getResource("/com/sun/javafx/scene/control/skin/modena/dialog-warning@2x.png")));
		setTitle("Loading...");
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 85);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		progressBar = new JProgressBar();
		progressBar.setBackground(Color.WHITE);
		progressBar.setForeground(Constants.SICK_PURPLE);
		progressBar.setStringPainted(true);
		contentPane.add(progressBar, BorderLayout.CENTER);

		setVisible(true);
	}

	public void changeIcon(Image image) {
		setIconImage(image);
	}

	public void end() {
		setVisible(false);
		dispose();
	}

	public void setCurrentProgress(int current, int maximum) {
		progressBar.setMaximum(maximum);
		progressBar.setValue(current);
	}

}
