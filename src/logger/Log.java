package logger;

import java.sql.Timestamp;
import java.util.Date;

import com.sun.istack.Nullable;

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

	public Log(int severityLevel, String tag, String message, @Nullable String error) {
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

	public static String logTypeToString(int type) {
		if (type == IMPORTANT_INFO) {
			return IMPORTANT_INFO_NAME;
		} else if (type == WARNING) {
			return WARNING_NAME;
		} else if (type == ERROR) {
			return ERROR_NAME;
		}

		System.err.println("Unknown type " + type);
		return "Unknown tag";
	}

}