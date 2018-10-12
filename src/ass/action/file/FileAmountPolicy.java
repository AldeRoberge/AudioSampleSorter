package ass.action.file;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * File policy (if it can be performed on the current amount of selected files in FileManager
 * ex : Play can only be performed on 1 file
 */
public class FileAmountPolicy {

	private static Logger log = LoggerFactory.getLogger(FileAmountPolicy.class);

	public static final FileAmountPolicy PERFORMED_ON_ZERO_FILES_ONLY_POLICY = new FileAmountPolicy(-2); //0 selected files
	public static final FileAmountPolicy PERFORMED_ON_ONE_FILE_ONLY_POLICY = new FileAmountPolicy(-1); //1 selected file
	public static final FileAmountPolicy PERFORMED_ON_ZERO_TO_MANY_FILES_POLICY = new FileAmountPolicy(0); //0 to infinity selected files
	public static final FileAmountPolicy PERFORMED_ON_ONE_OR_MANY_FILES_ONLY_POLICY = new FileAmountPolicy(1); //1 to infinity selected files
	public static final FileAmountPolicy PERFORMED_ON_MANY_FILES_ONLY_POLICY = new FileAmountPolicy(2); //2 to infinity selected files

	public int policy;

	private FileAmountPolicy(int policy) {
		this.policy = policy;
	}

	public boolean canBePerformedOnFiles(int numberOfFiles) {

		if (policy == PERFORMED_ON_ZERO_FILES_ONLY_POLICY.policy) {
			return numberOfFiles == 0;
		} else if (policy == PERFORMED_ON_ONE_FILE_ONLY_POLICY.policy) {
			return numberOfFiles == 1;
		} else if (policy == PERFORMED_ON_ZERO_TO_MANY_FILES_POLICY.policy) {
			return numberOfFiles >= 0;
		} else if (policy == PERFORMED_ON_ONE_OR_MANY_FILES_ONLY_POLICY.policy) {
			return numberOfFiles >= 1;
		} else if (policy == PERFORMED_ON_MANY_FILES_ONLY_POLICY.policy) {
			return numberOfFiles >= 2;
		}

		log.error("Unknown file policy : " + policy);
		return false;

	}

	public static String getPolicyAsString(FileAmountPolicy policy) {
		return getPolicyAsString(policy.policy);
	}

	public static String getPolicyAsString(int policy) {

		if (policy == PERFORMED_ON_ZERO_FILES_ONLY_POLICY.policy) {
			return "no files";
		} else if (policy == PERFORMED_ON_ONE_FILE_ONLY_POLICY.policy) {
			return "one file";
		} else if (policy == PERFORMED_ON_ZERO_TO_MANY_FILES_POLICY.policy) {
			return "any number of files";
		} else if (policy == PERFORMED_ON_ONE_OR_MANY_FILES_ONLY_POLICY.policy) {
			return "one or more files";
		} else if (policy == PERFORMED_ON_MANY_FILES_ONLY_POLICY.policy) {
			return "more than two files";
		}
		log.error("Unknown file policy : " + policy);
		return "Unknown file policy : " + policy;

	}

}
