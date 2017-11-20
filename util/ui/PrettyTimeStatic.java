package ui;

import java.util.Locale;

import org.ocpsoft.prettytime.PrettyTime;

class PrettyTimeStatic {

	private static PrettyTime prettyTime = new PrettyTime();

	static {
		prettyTime.setLocale(new Locale("en"));
	}

}
