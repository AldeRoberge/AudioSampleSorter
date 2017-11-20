package logger;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

class LogPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public LogPanel(Log log, int ID) {
		setBackground(Color.BLACK);
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		this.setPreferredSize(new Dimension(450, 30));

		JLabel lblNumber = new JLabel(ID + ":");
		lblNumber.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNumber.setForeground(Color.MAGENTA);
		add(lblNumber);

		JLabel lblTag = new JLabel(log.getTag() + ":");
		lblTag.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTag.setForeground(Color.ORANGE);
		add(lblTag);

		JLabel lblMessage = new JLabel(log.getMessage());
		lblMessage.setForeground(Color.CYAN);
		if (log.isError()) {
			lblMessage.setForeground(Color.RED);
		}

		add(lblMessage);

	}

}
