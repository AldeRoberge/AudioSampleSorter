package wip;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFrame;

import ui.WrapLayout;

public class FileWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileWindow window = new FileWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FileWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new WrapLayout());

		frame.add(new ASSFile(new File("ass.properties")).getFilePanel());
		frame.add(new ASSFile(new File("ass.properties")).getFilePanel());
		frame.add(new ASSFile(new File("ass.properties")).getFilePanel());
		frame.add(new ASSFile(new File("ass.properties")).getFilePanel());
		frame.add(new ASSFile(new File("ass.properties")).getFilePanel());
		frame.add(new ASSFile(new File("ass.properties")).getFilePanel());
		frame.add(new ASSFile(new File("ass.properties")).getFilePanel());

	}

}
