package util.file;

import java.io.File;

//Source https://www.mkyong.com/java/how-to-get-file-size-in-java/

public class FileSizeToString {

	public static String getFileSizeAsString(File file) {

		double bytes = file.length();
		double kilobytes = (bytes / 1024);
		double megabytes = (kilobytes / 1024);
		double gigabytes = (megabytes / 1024);

		if (gigabytes > 1) {

			return roundUp(gigabytes) + " GB";

		} else if (megabytes > 1) {

			return roundUp(megabytes) + " MB";

		} else if (kilobytes > 1) {

			return roundUp(kilobytes) + " KB";

		} else {

			return roundUp(bytes) + " B";

		}

	}

	private static double roundUp(double d) { //Round up double to two digits after comma
		//https://stackoverflow.com/questions/11701399/round-up-to-2-decimal-places-in-java

		return Math.round(d * 100.0) / 100.0;
	}

}
