package logger;

import java.util.ArrayList;

import file.WriteToFile;

public class Logger {

	private static LogUI logUI;

	private static ArrayList<Log> bufferedLogs = new ArrayList<Log>();

	/**
	 * Used by the UI to create the logger after creating the LogUI panel
	 * After creating LogUI, we do Logger.init(this);
	 */
	public static void init(LogUI panel) {
		Logger.logUI = panel;
	}

	private Logger() {
	}

	private WriteToFile logFileWriter = new WriteToFile("log.txt");

	public static void logInfo(String tag, String message) { // log info

		log(Log.IMPORTANT_INFO, tag, message, null);
	}

	public static void logWarning(String tag, String message) { // log warning

		log(Log.WARNING, tag, message, null);
	}

	public static void logWarning(String tag, String message, Throwable e) { // log warning with exception

		log(Log.WARNING, tag, message, e);
	}

	public static void logError(String tag, String message) { // log error

		log(Log.ERROR, tag, message, null);

	}

	public static void logErrorAndPrintstackTrace(String tag, String message) { // log error

		log(Log.ERROR, tag, message, null);
		new Exception(message).printStackTrace();

	}

	public static void logError(String tag, String message, Throwable e) { // log error with exception

		log(Log.ERROR, tag, message, e);
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

		if (logUI == null) {
			bufferedLogs.add(log);
		} else {

			for (Log l : bufferedLogs) {
				logUI.addLog(l);
			}
			bufferedLogs.clear();

			logUI.addLog(log);
		}

	}

	public void saveToFile(Log e) {
		logFileWriter.write(e.toString());
	}

	/**
	 * @return t's stacktrace to a string with \n, will return null if t is null
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

}
