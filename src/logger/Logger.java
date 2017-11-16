package logger;

import file.WriteToFile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Logger {

	private static LogUI panel;

	private static ArrayList<Log> bufferedLogs = new ArrayList<Log>();

	private static final int IMPORTANT_INFO = 0;
	private static final int WARNING = 1;
	private static final int ERROR = 2;

	private static final String IMPORTANT_INFO_NAME = "Information";
	private static final String WARNING_NAME = "Warning";
	private static final String ERROR_NAME = "Fatal Error";

	public static void init(LogUI panel) {
		Logger.panel = panel;
	}

	private Logger() {
	}

	private WriteToFile logFileWriter = new WriteToFile("log.txt");

	public static void logInfo(String tag, String message) { // log info

		log(IMPORTANT_INFO, tag, message, null);
	}

	public static void logWarning(String tag, String message) { // log warning

		log(WARNING, tag, message, null);
	}

	public static void logWarning(String tag, String message, Throwable e) { // log warning with exception

		log(WARNING, tag, message, e);
	}

	public static void logError(String tag, String message) { // log error

		log(ERROR, tag, message, null);

	}

	public static void logErrorAndPrintstackTrace(String tag, String message) { // log error

		log(ERROR, tag, message, null);
		new Exception(message).printStackTrace();

	}

	public static void logError(String tag, String message, Throwable e) { // log error with exception

		log(ERROR, tag, message, e);
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

		Timestamp now = new Timestamp(new Date().getTime());

		Log log = new Log();
		log.severity = severityLevel;
		log.tag = tag;
		log.message = message;
		log.time = now;

		if (e != null) {
			log.error = sTTS(e);
		} else {
			log.error = "";
		}

		if (panel == null) {
			bufferedLogs.add(log);
		} else {
			if (bufferedLogs.size() != 0) {
				for (Log bufferedLog : bufferedLogs) {
					panel.addLogInfoPanel(bufferedLog);
				}

				bufferedLogs.clear();
			}

			panel.addLogInfoPanel(log);
		}

	}

	public static String logTypeToString(int type) {
		if (type == IMPORTANT_INFO) {
			return IMPORTANT_INFO_NAME;
		} else if (type == WARNING) {
			return WARNING_NAME;
		} else if (type == ERROR) {
			return ERROR_NAME;
		}

		return "Unkwon tag";
	}

	public void saveToFile(Log e) {
		logFileWriter.write(e.toString());
	}

	/**
	 * @return t's stacktrace to a string with \n
	 */
	private static String sTTS(Throwable t) {

		StringBuilder sb = new StringBuilder(t.toString());
		for (StackTraceElement ste : t.getStackTrace()) {
			sb.append("\n\tat ");
			sb.append(ste);
		}

		return sb.toString();
	}

}
