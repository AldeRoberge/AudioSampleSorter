package logger;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

import file.FileManager;

public class Logger extends JPanel {
	public Logger() {
	}

	private static FileManager logFileWriter = new FileManager("log.txt"); //File to save Logs to

	private static LoggerUI loggerUI = new LoggerUI();

	public static void logInfo(String tag, String message) { // log info
		log(Log.IMPORTANT_INFO, tag, message, null);
	}

	public static void logWarning(String tag, String message) { // log warning

		log(Log.WARNING, tag, message, null);
	}

	public static void logWarning(String tag, String message, Throwable e) { // log warning with exception

		log(Log.WARNING, tag, message, e);
	}

	public static void logError(String tag, Exception v) { // log error
		log(Log.ERROR, tag, "", v);

	}

	public static void logErrorAndPrintstackTrace(String tag, String message) { // log error

		log(Log.ERROR, tag, message, null);
		new Exception(message).printStackTrace();

	}

	public static void logError(String tag, String message, Throwable e) { // log error with exception

		log(Log.ERROR, tag, message, e);
	}

	public static void logError(String tag, String error) {
		log(Log.ERROR, tag, error, null);
	}

	/***
	 * Exception is nulleable
	 * DO NOT use this method for logging, use LogError and LogInfo
	 */
	private static void log(int severityLevel, String tag, String message, Throwable e) {
		if (severityLevel <= 1) {
			System.out.println("[" + tag + "] " + message);
		} else {
			System.err.println("[" + tag + "] " + message);
		}

		Log log = new Log(severityLevel, tag, message, sTTS(e));

		loggerUI.addLog(log);

	}

	public void saveToFile(Log e) {
		logFileWriter.add(e.toString());
	}

	/**
	 * @return Converts Throwable "t" to a String with \n, returns null if t is null
	 */
	private static String sTTS(Throwable t) {
		if (t == null) { //Quick fail
			return null;
		} else {
			StringBuilder sb = new StringBuilder(t.toString());
			for (StackTraceElement ste : t.getStackTrace()) {
				sb.append("\n\tat ");
				sb.append(ste);
			}
			return sb.toString();
		}
	}

	public static JPanel getLogUIPanel() {
		return loggerUI;
	}

	//Loading bar

	private static JProgressBar status;

	public static JProgressBar getStatusField() {
		if (status == null) {
			status = new JProgressBar();
			loading(false);
		}

		return status;
	}

	public static void loading(boolean isLoading) {
		getStatusField().setIndeterminate(isLoading);
	}

}

class Log {

	public static final int IMPORTANT_INFO = 0;
	public static final int WARNING = 1;
	public static final int ERROR = 2;

	private static final String IMPORTANT_INFO_NAME = "Information";
	private static final String WARNING_NAME = "Warning";
	private static final String ERROR_NAME = "Fatal Error";

	private Timestamp time;
	private int severity;
	private String tag;
	private String message;
	private String error;

	public Log(int severityLevel, String tag, String message, String error) {
		this.severity = severityLevel;
		this.tag = tag;
		this.message = message;
		this.error = error;
		this.time = new Timestamp(new Date().getTime());
	}

	public String getTag() {
		return tag;
	}

	public String getMessage() {
		return message;
	}

	/**
	 * @return null if no error string. Use isError() to check severity
	 */
	public String getError() {
		return error;
	}

	public boolean isError() {
		return severity == ERROR;
	}

	public boolean isWarning() {
		return severity == WARNING;
	}

	public boolean isInfo() {
		return severity == IMPORTANT_INFO;
	}

	@Override
	public String toString() {
		return "Time: " + time.toString() + " Severity: " + severity + " Tag: " + tag + " Message: " + message + " Error: " + error;
	}

	public static String logTypeToString(int severity) {
		if (severity == IMPORTANT_INFO) {
			return IMPORTANT_INFO_NAME;
		} else if (severity == WARNING) {
			return WARNING_NAME;
		} else if (severity == ERROR) {
			return ERROR_NAME;
		}

		System.err.println("Unknown type " + severity);
		return "Unknown tag";
	}

	public String getSeverityAsString() {
		return logTypeToString(severity);
	}

	public int getSeverity() {
		return severity;
	}

}

class LogPanel extends JPanel {

	private Log log;

	private static final long serialVersionUID = 272727;

	public LogPanel(int ID, Log log) {
		this.log = log;

		setBackground(Color.BLACK);
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		this.setPreferredSize(new Dimension(450, 30));

		JLabel lblIndex = new JLabel(ID + ":");
		lblIndex.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblIndex.setForeground(Color.MAGENTA);
		add(lblIndex);

		JLabel lblTag = new JLabel(log.getTag() + ":");
		lblTag.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTag.setForeground(Color.ORANGE);
		add(lblTag);

		JLabel lblMessage = new JLabel(log.getMessage());
		if (log.isError()) {
			lblMessage.setForeground(Color.RED);
		} else {
			lblMessage.setForeground(Color.CYAN);
		}

		add(lblMessage);

	}

	public Log getLog() {
		return log;
	}

}

class LoggerUI extends JPanel {

	ArrayList<LogPanel> logs = new ArrayList<LogPanel>(); //currentLog = logs.size()

	boolean showAllTags = false;
	boolean showAllPriority = false;
	private JPanel ui;
	private JComboBox<String> tagFilterCombobox;
	private JComboBox<String> severityFilterCombobox;
	private JLabel lblShowOnly;
	private JLabel lblFrom;

	public static final String ALL_TAGS_STRING = "[Any]";
	public static final String ALL_SEVERITY_STRING = "[Any]";

	String tagFilter = "";
	String severityFilter = "";

	private int tagSelectedItem = 0;
	private int severitySelectedItem = 0;
	private JButton btnUpdate;
	private Component horizontalStrut;

	public LoggerUI() {
		setLayout(new BorderLayout(0, 0));

		JScrollPane uiScrollpane = new JScrollPane();
		add(uiScrollpane, BorderLayout.CENTER);

		ui = new JPanel();
		uiScrollpane.setViewportView(ui);
		ui.setLayout(new BoxLayout(ui, BoxLayout.Y_AXIS));

		JPanel selectPanel = new JPanel();
		add(selectPanel, BorderLayout.SOUTH);

		lblShowOnly = new JLabel("Show");
		selectPanel.add(lblShowOnly);

		severityFilterCombobox = new JComboBox<String>();
		severityFilterCombobox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				System.out.println("Selected item for severity filter combobox changed.");
				severitySelectedItem = severityFilterCombobox.getSelectedIndex();
				severityFilter = (String) severityFilterCombobox.getSelectedItem();

				refreshLogList();
			}
		});
		selectPanel.add(severityFilterCombobox);

		lblFrom = new JLabel("from");
		selectPanel.add(lblFrom);

		tagFilterCombobox = new JComboBox<String>();
		tagFilterCombobox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				System.out.println("Selected item for tag filter combobox changed.");
				tagSelectedItem = tagFilterCombobox.getSelectedIndex();
				tagFilter = (String) tagFilterCombobox.getSelectedItem();

				refreshLogList();
			}
		});
		selectPanel.add(tagFilterCombobox);

	}

	public void addLog(Log newLog) {
		logs.add(new LogPanel(logs.size(), newLog));

		refreshLogList();

		//Update logs

		List<String> tags = new ArrayList<>();

		tags.add(ALL_TAGS_STRING);

		for (LogPanel log : logs) {
			String tag = log.getLog().getTag();
			if (!tags.contains(tag)) {
				tags.add(tag);
			}
		}

		System.out.println(tagSelectedItem);

		tagFilterCombobox.setModel(new DefaultComboBoxModel(tags.toArray()));
		tagFilterCombobox.setSelectedIndex(tagSelectedItem);

		// Severity

		List<String> severities = new ArrayList<>();

		severities.add(ALL_SEVERITY_STRING);

		for (LogPanel log : logs) {
			String severity = log.getLog().getSeverityAsString();

			if (!severities.contains(severity)) {
				severities.add(severity);
			}
		}

		severityFilterCombobox.setModel(new DefaultComboBoxModel(severities.toArray()));
		severityFilterCombobox.setSelectedIndex(severitySelectedItem);

	}

	public void refreshLogList() {

		// Refresh

		ui.removeAll();

		for (LogPanel log : logs) {

			boolean acceptTag = false;
			boolean acceptSeverity = false;

			if (tagFilter.equals(ALL_TAGS_STRING)) {
				acceptTag = true;
			} else {
				acceptTag = log.getLog().getTag().equals(tagFilter);
			}

			if (severityFilter.equals(ALL_SEVERITY_STRING)) {
				acceptSeverity = true;
			} else {
				acceptSeverity = log.getLog().getSeverityAsString().equals(severityFilter);
			}

			System.out.println(acceptTag + " " + acceptSeverity);

			if (acceptTag && acceptSeverity) {
				System.out.println("Added one");

				ui.add(log);
				ui.repaint();
				ui.revalidate();
			} else {
				System.out.println("Did not add one");
			}

		}

	}

}
