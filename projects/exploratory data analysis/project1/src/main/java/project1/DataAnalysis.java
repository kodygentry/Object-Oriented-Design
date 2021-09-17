package project1;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class, all static methods Inventory of the datasets in
 * DATA_FILE_FOLDER, kept in DATA_FILE_NAME
 * 
 * @author tesic
 */
public class DataAnalysis {

	/** Private Constructor */
	private DataAnalysis() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Implement method
	 * Add javadoc
	 */
	public static List<AbstractRatingSummary> sortByDegree(List<AbstractRatingSummary> inList) {

		// your code here
	}

	/**
	 * Implement method
	 * Add javadoc
	 */
	public static List<AbstractRatingSummary> sortByAvgDiff(List<AbstractRatingSummary> inList) {

		// your code here 
	}

	
	/**
	 * Implement method
	 * Add javadoc
	 */
	public static String printReport(List<AbstractRatingSummary> inList, int k) {

		//your code here

		
	}

	/**
	 * The file name of where the database is going to be saved.
	 */
	public static final String LINE_SEP = System.lineSeparator();
	public static final String DELIMITER = ",";
	public static final String DB_FOLDER = "data";
	public static final String DB_FILENAME = "data.csv";
	public static final String DATA_ID_TEMPLATE = "<dataID>";
	public static final String STAT_FILE_TEMPLATE = "ratingSummary_<dataID>.csv";
	public static final String REPORT_FILE_TEMPLATE = "report_<dataID>.csv";
	public static final String RESULTS_FILE_TEMPLATE = "results_<dataID>.csv";
	public static final String SUMMARY_HEADER ="Id,degree,product avg,reviewer avg";
}