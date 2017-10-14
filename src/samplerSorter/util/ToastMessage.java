package samplerSorter.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

public class ToastMessage extends JDialog {
	private static final long serialVersionUID = 1L;
	
	int miliseconds;

	public ToastMessage(String toastString, int time, Point point, boolean isError) {
		this.miliseconds = time;
		setUndecorated(true);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();

		if (!isError) {
			panel.setBackground(Color.GRAY);
		} else {
			panel.setBackground(Color.red);
		}

		panel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		getContentPane().add(panel, BorderLayout.CENTER);

		JLabel toastLabel = new JLabel("");
		toastLabel.setText(toastString);
		toastLabel.setFont(new Font("Dialog", Font.BOLD, 12));

		if (!isError) {
			toastLabel.setForeground(Color.WHITE);
		} else {
			toastLabel.setForeground(Color.WHITE);
		}

		setBounds(100, 100, toastLabel.getPreferredSize().width + 20, 31);

		setAlwaysOnTop(true);

		/*Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int y = dim.height / 2 - getSize().height / 2;
		int half = y / 2;
		setLocation(dim.width / 2 - getSize().width / 2, y + half);
		panel.add(toastLabel);
		setVisible(false);*/

		//

		// set position relative to point
		setLocation(point.x, point.y);
		panel.add(toastLabel);
		setVisible(false);

		//

		new Thread() {
			public void run() {
				try {
					Thread.sleep(miliseconds);
					dispose();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public static void makeToast(Component component, String toastMsg, int numberOfMilisc, boolean isError) {

		try {
			ToastMessage toastMessage = new ToastMessage(toastMsg, numberOfMilisc, component.getLocationOnScreen(),
					isError);
			toastMessage.setVisible(true);

		} catch (Exception e) {
			/*Exception in thread "main" java.lang.ExceptionInInitializerError
			at masterbots.SpamBotLauncher.main(SpamBotLauncher.java:97)
			Caused by: java.awt.IllegalComponentStateException: component must be showing on the screen to determine its location*/
		}

	}

	//Test toast
	public static void main(String[] args) {

		try {
			JFrame frame = new JFrame();
			frame.setVisible(true);

			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			frame.setBounds(100, 100, 450, 83);

			JButton btnTestToast = new JButton("Test Toast");
			btnTestToast.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					ToastMessage toastMessage = new ToastMessage("Hey there!", 3000, frame.getLocationOnScreen(), true); //isError = true
					toastMessage.setVisible(true);
				}
			});
			frame.add(btnTestToast, BorderLayout.NORTH);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}