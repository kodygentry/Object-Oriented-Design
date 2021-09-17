package project3;

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
	 * Sort the list by degree
	 * @param inList input list
	 * @return reference to list sorted by rogue .. meaning product avg and review avg differ the most
	 */
	public static List<AbstractRatingSummary> sortByDegree(List<AbstractRatingSummary> inList) {

		//lambda here!
        inList.sort((AbstractRatingSummary r1, AbstractRatingSummary r2)->Long.compare(r2.getDegree(), r1.getDegree()));
		return inList;
	}

	/**
	 * Sort list by average difference
	 * @param inList input list
	 * @return reference to list sorted by rogue .. meaning product avg and review avg differ the most
	 */
	public static List<AbstractRatingSummary> sortByAvgDiff(List<AbstractRatingSummary> inList) {

		// sorting reviewer by ratings of products differs from respective average
		inList.sort((AbstractRatingSummary r1,AbstractRatingSummary r2) -> r2.avgScore().compareTo(r1.avgScore()));
		return inList;
	}

	/**
	 * Print report for the top k reviewers and products
	 * @param inList input list
	 * @param k number of lines to be included in the report for each statistic
	 * @return string object with full report
	 */
	public static String printReport(List<AbstractRatingSummary> inList, int k) {

		// filtering the reviewers
		List<AbstractRatingSummary> reviewers = inList.stream().filter(rs -> rs.getNodeID().startsWith("A"))
				.collect(Collectors.toList());

		int counter = reviewers.size();
		int dataSize = Math.min(counter, k);
		String separator = "--------------------------------------------------" + System.lineSeparator();
		
		StringBuilder reportBuilder = new StringBuilder(SUMMARY_HEADER + LINE_SEP);
		
		reportBuilder.append(separator + " Top " + dataSize + " REVIEWER ANALYSIS"+ LINE_SEP + separator);

		reportBuilder.append("Reviewers with highest number of reviews " + LINE_SEP);
		sortByDegree(reviewers);
		for (AbstractRatingSummary rr : reviewers.subList((counter > k) ? counter - k : 0, counter)) {
			reportBuilder.append(rr.toString());
		}

		reportBuilder.append(separator + "Reviewers with highest discrepancies per reviewer" + LINE_SEP);
		sortByAvgDiff(reviewers);
		for (AbstractRatingSummary rr : reviewers.subList((counter > k) ? counter - k : 0, counter)) {
			reportBuilder.append(rr.toString());
		}

		// filtering the products
		List<AbstractRatingSummary> products = inList.stream().filter(rs -> rs.getNodeID().startsWith("B"))
				.collect(Collectors.toList());
		counter = products.size();
		dataSize = Math.min(counter, k);
		reportBuilder.append(separator + " Top " + dataSize + " PRODUCT ANALYSIS"+ LINE_SEP + separator);
		reportBuilder.append("Products with highest number of reviews " + LINE_SEP);
		sortByDegree(products);
		for (AbstractRatingSummary rr : products.subList((counter > k) ? counter - k : 0, counter)) {
			reportBuilder.append(rr.toString());
		}

		reportBuilder.append(separator + "Products with highest rating discrepancies" + LINE_SEP);
		sortByAvgDiff(products);
		for (AbstractRatingSummary rr : products.subList((counter > k) ? counter - k : 0, counter)) {
			reportBuilder.append(rr.toString());
		}
		reportBuilder.append(separator);

		return reportBuilder.toString();
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
