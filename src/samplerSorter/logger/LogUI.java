package samplerSorter.logger;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class LogUI extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String TAG = "LogUI";

	static JPanel columnpanel = new JPanel();
	static JPanel borderlaoutpanel;

	public static JScrollPane scrollPane;

	private static ArrayList<LogInfoPanel> allLogInfoPanels = new ArrayList<LogInfoPanel>();

	/**
	 * Create the frame.
	 */
	public LogUI() {
		
		
		setBounds(0, 0, 620, 400);
		setVisible(true);
		setLayout(new CardLayout(0, 0));

		// scrolleable list

		// list

		scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 16, 620, 398);
		scrollPane.setAutoscrolls(true);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane);

		borderlaoutpanel = new JPanel();
		scrollPane.setViewportView(borderlaoutpanel);
		borderlaoutpanel.setLayout(new BorderLayout(0, 0));

		columnpanel = new JPanel();
		columnpanel.setLayout(new GridLayout(0, 1, 0, 1));
		columnpanel.setBackground(Color.gray);
		borderlaoutpanel.add(columnpanel, BorderLayout.NORTH);

		// end

		/**Log e = new Log();
		
		e.time = new Timestamp(System.currentTimeMillis());
		e.severity = 1;
		e.tag = "WHAT";
		e.message = "CONQUER";
		e.error = "BETRAY";
		
		addLogInfoPanel(e);*/
		
		Logger.init(this);

	}

	// TODO : update this when adding fiels
	public void addLogInfoPanel(Log log) {

		LogInfoPanel infoPanel = new LogInfoPanel(log);

		columnpanel.add(infoPanel);
		allLogInfoPanels.add(infoPanel);

		refreshInfoPanels();
	}

	public static void refreshInfoPanels() {

		for (LogInfoPanel logPanel : allLogInfoPanels) {

			logPanel.validate();
			logPanel.repaint();

		}

		columnpanel.validate();
		columnpanel.repaint();

		borderlaoutpanel.validate();
		borderlaoutpanel.repaint();

		scrollPane.validate();
		scrollPane.repaint();

	}

	public static void main(String... args) {
		LogUI e = new LogUI();
	}

	public void clearLog() {
		try {
			for (Iterator<LogInfoPanel> iterator = allLogInfoPanels.iterator(); iterator.hasNext();) {
				LogInfoPanel infoP = iterator.next();

				iterator.remove();
				columnpanel.remove(infoP);
			}

			refreshInfoPanels();
		} catch (Exception e) {
			Logger.logError(TAG, "Error in removeInfoPanel", e);
			e.printStackTrace();
		}
	}

}