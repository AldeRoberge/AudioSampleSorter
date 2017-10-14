package samplerSorter.logger;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import samplerSorter.util.WriteToFile;

public class Logger {

	public static LogUI panel;

	private static ArrayList<Log> bufferedLogs = new ArrayList<Log>();

	public static final int IMPORTANT_INFO = 0;
	public static final int WARNING = 1;
	public static final int ERROR = 2;

	public static final String IMPORTANT_INFO_NAME = "Information";
	public static final String WARNING_NAME = "Warning";
	public static final String ERROR_NAME = "Fatal Error";

	public static void init(LogUI panel) {
		Logger.panel = panel;
	}

	@SuppressWarnings("unused")
	private Logger() {
	}

	/**
	 * This is used as a backup if database fails
	 */
	private WriteToFile logFileWriter = new WriteToFile("log.txt");

	/**
	 * Logger is for logging serious info, warnings, or errors. Saves to a DB
	 * file and a text file.
	 */

	public static void logInfo(String tag, String message) { // log info

		log(IMPORTANT_INFO, tag, message, null);
	}

	public static void logWarning(String tag, String message) { // log warning

		log(WARNING, tag, message, null);
	}

	/***
	 * Exception is nulleable
	 */
	public static void logWarning(String tag, String message, Exception e) { // log warning with exception

		log(WARNING, tag, message, e);
	}

	public static void logError(String tag, String message) { // log error

		log(ERROR, tag, message, null);
	}

	/***
	 * Exception is nulleable
	 */
	public static void logError(String tag, String message, Exception e) { // log error with exception

		log(ERROR, tag, message, e);
	}

	/***
	 * Exception is nulleable
	 * DO NOT use this method for logging, use LogError and LogInfo
	 */
	private static void log(int severityLevel, String tag, String message, Exception e) {
		System.out.println("[" + tag + "] " + message);

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

		//PrettyTimeStatic.prettyTime.format(new Date(log.time.getTime()))
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

	public static String sTTS(Exception e) {
		// convert the stacktrace to a string with \n
		StringBuilder sb = new StringBuilder(e.toString());
		for (StackTraceElement ste : e.getStackTrace()) {
			sb.append("\n\tat ");
			sb.append(ste);
		}

		return sb.toString();
	}

}
