package samplerSorter.logger;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import samplerSorter.util.ui.PrettyTimeStatic;

import java.awt.Font;

class LogInfoPanel extends JPanel {

	// prettytime formats time

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LogInfoPanel(Log log) {
		setBackground(Color.GREEN);

		setPreferredSize(new Dimension(589, 57));
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 589, 55);
		add(panel);
		panel.setLayout(null);

		JLabel lblAgo = new JLabel(PrettyTimeStatic.prettyTime.format(new Date(log.time.getTime())));
		lblAgo.setFont(new Font("Segoe UI Light", Font.PLAIN, 13));
		lblAgo.setBounds(336, 13, 248, 14);
		panel.add(lblAgo);
		lblAgo.setForeground(Color.BLACK);
		lblAgo.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel lblTag = new JLabel(log.tag);
		lblTag.setFont(new Font("Myriad Pro", Font.PLAIN, 16));
		lblTag.setBounds(10, 13, 323, 14);
		panel.add(lblTag);
		lblTag.setForeground(Color.BLACK);

		JLabel lblMessage = new JLabel(log.message);
		lblMessage.setFont(new Font("Segoe UI Light", Font.PLAIN, 13));
		lblMessage.setToolTipText(log.message);
		lblMessage.setBounds(10, 28, 574, 14);
		panel.add(lblMessage);
		lblMessage.setForeground(Color.BLACK);

		if (log.severity == 0) { // important info
			setBackground(Color.GREEN);
		} else if (log.severity == 1) { // warning
			setBackground(Color.ORANGE);
		} else if (log.severity == 2) { // error
			setBackground(Color.RED);

			panel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					JOptionPane optionPane = new JOptionPane(log.error, JOptionPane.ERROR_MESSAGE);
					JDialog dialog = optionPane.createDialog("Fatal Error");
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
				}
			});
		}

	}
}
