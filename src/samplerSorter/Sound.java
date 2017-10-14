package samplerSorter;

import java.io.File;

import samplerSorter.properties.Properties;
import samplerSorter.util.FileNameUtil;

public class Sound {

	public File file;

	public Sound(File path) {
		this.file = path;
	}

	public String getName(boolean withSuffix) {
		if (withSuffix) {
			return file.getName();
		} else {
			return FileNameUtil.removeExtension(file.getName());
		}
	}

	public String getPath() {
		return file.getAbsolutePath();
	}

}
