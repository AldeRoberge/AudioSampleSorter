package samplerSorter.logger;

import java.sql.Timestamp;

class Log {

	/**
	 * IMPORTANT_INFO = 0; WARNING = 1; ERROR = 2;
	 */

	public Timestamp time;
	public int severity;
	public String tag;
	public String message;
	public String error;

	@Override
	public String toString() {
		return "Time: " + time.toString() + " Severity: " + severity + " Tag: " + tag + " Message: " + message
				+ " Error: " + error;
	}

}