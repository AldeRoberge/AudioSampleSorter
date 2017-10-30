package util.ui;

import java.util.Locale;

import org.ocpsoft.prettytime.PrettyTime;

public class PrettyTimeStatic {

	public static PrettyTime prettyTime = new PrettyTime();

	static {
		prettyTime.setLocale(new Locale("en"));
	}

}
