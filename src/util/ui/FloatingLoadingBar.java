package util.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import constants.icons.Icons;

import java.awt.Window.Type;
import javax.swing.JProgressBar;
import java.awt.Toolkit;

public class FloatingLoadingBar extends JFrame {
	private JProgressBar progressBar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FloatingLoadingBar frame = new FloatingLoadingBar();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FloatingLoadingBar() {
		setIconImage(Icons.FLOATING_LOADING_BAR.getImage());
		setTitle("Loading...");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 85);
		setLocation(MiddleOfTheScreen.getLocationFor(this));
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		progressBar = new JProgressBar();
		contentPane.add(progressBar, BorderLayout.CENTER);

		setVisible(true);
	}

	public void changeTitle(String newTitle) {
		setTitle(newTitle);
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
