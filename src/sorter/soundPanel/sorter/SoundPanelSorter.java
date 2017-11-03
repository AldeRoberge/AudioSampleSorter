package sorter.soundPanel.sorter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.commons.io.FilenameUtils;

import logger.Logger;
import property.Properties;
import sorter.Sorter;
import sorter.soundPanel.SoundPanel;

public class SoundPanelSorter {

	private static final String TAG = "SoundPanelsSorter";

	public static final Sort sortByName = new Sort(0, "Name");
	public static final Sort sortByDateModified = new Sort(1, "Date modified");
	public static final Sort sortByExtension = new Sort(2, "Type");
	public static final Sort sortBySize = new Sort(3, "Size");
	public static ArrayList<Sort> sortByTypes = new ArrayList<Sort>();

	static {
		sortByTypes.add(sortByName);
		sortByTypes.add(sortByDateModified);
		sortByTypes.add(sortByExtension);
		sortByTypes.add(sortBySize);
	}

	public Sort getSortByName(String name) {
		for (Sort s : sortByTypes) {
			if (name.equals(s.name)) {
				return s;
			}
		}

		Logger.logInfo(TAG, "Invalid getSortByName for " + name + ".");

		return null;
	}

	//

	public boolean ascending = Properties.ASCENDING.getValueAsBoolean(); //is switched on click (re-click)
	public Sort currentSort = getSortByName(Properties.SORT_BY.getValue());

	Sorter sorter;

	public SoundPanelSorter(Sorter s) {
		this.sorter = s;
	}

	public void sortBy(Sort s) {

		if (currentSort.equals(s)) {
			ascending = Properties.ASCENDING.setNewValue(!ascending);
		}

		currentSort = s;

		if (s.equals(sortByName)) {

			Collections.sort(sorter.soundPanels, new Comparator<SoundPanel>() {
				@Override
				public int compare(SoundPanel o1, SoundPanel o2) {

					if (ascending) {
						return o1.getFile().getName().compareTo(o2.getFile().getName());
					} else {
						return o1.getFile().getName().compareTo(o2.getFile().getName());
					}

				}
			});

		} else if (s.equals(sortByDateModified)) {
			Collections.sort(sorter.soundPanels, new Comparator<SoundPanel>() {

				@Override
				public int compare(SoundPanel o1, SoundPanel o2) {

					if (ascending) {
						return Long.compare(o1.getFile().lastModified(), o2.getFile().lastModified());
					} else {
						return o2.getFile().getName().compareTo(o1.getFile().getName());
					}

				}

			});

		} else if (s.equals(sortByExtension)) {

			Collections.sort(sorter.soundPanels, new Comparator<SoundPanel>() {
				@Override
				public int compare(SoundPanel o1, SoundPanel o2) {
					if (ascending) {
						return FilenameUtils.getExtension(o1.getFile().getName())
								.compareTo(FilenameUtils.getExtension(o2.getFile().getName()));
					} else {
						return o2.getFile().getName().compareTo(o1.getFile().getName());
					}

				}
			});

		} else if (s.equals(sortBySize)) {

			Collections.sort(sorter.soundPanels, new Comparator<SoundPanel>() {
				@Override
				public int compare(SoundPanel o1, SoundPanel o2) {
					if (ascending) {

						return Long.compare(o1.getFile().length(), o1.getFile().length());
					} else {
						return o2.getFile().getName().compareTo(o1.getFile().getName());
					}

				}
			});

		} else {
			Logger.logError(TAG, "Unknown sortBy! " + s);
		}

		sorter.clearAndRepopulatePanels();
	}

}