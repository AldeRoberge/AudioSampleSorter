package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

class ToastMessage extends JDialog {
	private static final long serialVersionUID = 1L;

	private int milliseconds;

	/**
	 * Unused
	 */
	private ToastMessage(Component comp, String toDisplay, int time, Color backgroundColor, Color textColor) {
		this.milliseconds = time;
		setUndecorated(true);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();

		panel.setBackground(backgroundColor);

		panel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		getContentPane().add(panel, BorderLayout.CENTER);

		JLabel toastLabel = new JLabel("");
		toastLabel.setText(toDisplay);
		toastLabel.setFont(new Font("Dialog", Font.BOLD, 12));

		toastLabel.setForeground(textColor);

		setBounds(100, 100, toastLabel.getPreferredSize().width + 20, 31);

		setAlwaysOnTop(true);

		// set position relative to point
		setLocation(comp.getX(), comp.getY());
		panel.add(toastLabel);

		//

		new Thread() {
			public void run() {
				try {
					Thread.sleep(milliseconds);
					dispose();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	// Test toast
	public static void main(String[] args) {

		try {
			JFrame frame = new JFrame();
			frame.setVisible(true);

			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			frame.setBounds(100, 100, 450, 83);

			JButton btnTestToast = new JButton("Test Toast");
			btnTestToast.addActionListener(e -> {

				ToastMessage toastMessage = new ToastMessage(frame, "Hey there!", 3000, Color.WHITE, Color.BLACK); // isError
																													// =
																													// true
				toastMessage.setVisible(true);
			});
			frame.add(btnTestToast, BorderLayout.NORTH);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}