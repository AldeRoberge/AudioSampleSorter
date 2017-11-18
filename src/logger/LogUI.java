package logger;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;

public class LogUI extends JPanel {
	private JPanel panel;

	private int current = 0;

	/**
	 * Create the panel.
	 */
	public LogUI() {

		Logger.init(this);

		setBounds(0, 0, 600, 250);
		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

	}

	public void addLog(Log log) {
		panel.add(new LogPanel(log, current++));
	}

}
