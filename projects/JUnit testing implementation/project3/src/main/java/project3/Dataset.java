package project3;

/* Team needs to import relevant packages here */

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.nio.file.Path;
import java.nio.file.Files;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import java.nio.file.Paths;

/**
 * Dataset
 * 
 * @author tesic
 */
public class Dataset {

	// Constructors

	/**
	 * Default constructor
	 */
	public Dataset() throws IOException{
		this("", Paths.get(""));
	}

	/**
	 * Dataset constructor w 2 parameters
	 * @param dataId unique dataset identifier
	 * @param inRawFile  Amazon rating input file
	 */
	public Dataset(String dataId, Path inRawFile) throws IOException {
		this.dataId = dataId;
		this.rawFile = inRawFile;
		this.numberOfRatings = Files.lines(inRawFile).count();
		this.ratingList = new ArrayList<>();
		this.ratingStat = new ArrayList<>();
	}

	/**
	 * Load Ratings 
	 * @return number of ratings
	 */
	public int loadRatings() {
		this.ratingList = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(this.getRawFile().toFile()))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] tempArr = line.split(",");
				Rating r = new Rating(tempArr[0], tempArr[1], Float.parseFloat(tempArr[2]));
				this.ratingList.add(r);
			}
		} catch (IOException e) {
			System.err.println("Loading statistics from " + this.getRawFile() + " failed.");
		}
		return this.ratingList.size();
	}

	/**
	 * Load Statistics from stat file
	 * @param inStatPath statistics file
	 * @return number of statistics processed
	 */
	public int loadStats(Path inStatPath) {

		// load stats if file exists
		if (!Files.exists(inStatPath)) {
			return 0;
		}

		try (BufferedReader brs = new BufferedReader(new FileReader(inStatPath.toFile()))) {
			String line = brs.readLine();

			while ((line = brs.readLine()) != null) {
				final String[] tempArr = line.split(",");
				final int len = tempArr.length;
				if (len > 5) {
					RatingSummary rs = new RatingSummary(tempArr[0], Long.parseLong(tempArr[1]));
					rs.setList(Float.valueOf(tempArr[2]), Float.valueOf(tempArr[3]));
					this.ratingStat.add(rs);
				}
			}
		} catch (IOException e) {
			System.err.println("Loading statistics from " + inStatPath + " failed.");
		}
		return this.ratingStat.size();
	}

	/**
	 * Compute Statistics for object ratings
	 * @return if computing statistics from raw file was a success
	 */
	public boolean computeStats() {
		// do not append, start from scratch
		this.ratingStat = new ArrayList<>();

		// create unique list of users and products here
		Set<String> nodeIds = this.ratingList.stream().map(r -> r.getReviewerID()).collect(Collectors.toSet());
		boolean newStats = nodeIds.addAll(this.ratingList.stream().map(r -> r.getProductID()).collect(Collectors.toSet()));

		// Create List from set
		List<String> uniqueList = nodeIds.stream().collect(Collectors.toCollection(ArrayList::new));

		// loop over field
		for (String user : uniqueList) {
			AbstractRatingSummary newSummary = new RatingSummary(user, this.ratingList);
			newStats = this.ratingStat.add(newSummary);
		}
		return newStats;
	}

	/**
	 * Compute Statistics for object ratings
	 * @return if computing statistics from raw file was a success
	 */
	public String saveStats() {

		StringBuilder statString = new StringBuilder();
		// writing a rating summary in each line
		for (AbstractRatingSummary rs : this.getRatingStat()) {
			statString.append(rs.toString());
		}
		return statString.toString();
	}

	/**
	 * Data ID getter
	 * @return  unique dataset identifier
	 */
	public String getDataId() {
		return dataId;
	}

	/**
	 * Data ID setter
	 * @param  dataId set unique dataset identifier
	 */
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	/**
	 * Get ratings filename
	 * @return Path to ratings file
	 */
	public Path getRawFile() {
		return this.rawFile;
	}

	/**
	 * rating list getter
	 * @return list of ratings
	 */
	public List<Rating> getRatingList() {
		return this.ratingList;
	}

	/**
	 * stat list getter
	 * @return list of rating stats
	 */
	public List<AbstractRatingSummary> getRatingStat() {
		return this.ratingStat;
	}

	/**
	 * @return if the object has stats
	 */
	public boolean hasStats(){
		return !(this.ratingStat.isEmpty());
	}

	/**
	 * Rating stat list setter
	 * @param ratingSummary list of rating stats
	 */
	public void setRatingSummary(List<AbstractRatingSummary> ratingSummary) {
		this.ratingStat = ratingSummary;
	}

	/** Print out format is dataID,RAW_FILE,RATINGS_NO,STAT_FILE 
	 * @return formatted output 
	*/
	@Override
	public String toString() {
		// return (this.dataId + "," + this.ratingList.size() + "," + this.rawFile.getFileName());
		return (this.dataId + "," + this.numberOfRatings + "," + this.rawFile.getFileName());
	}

	private String dataId;
	private Path rawFile;
	private long numberOfRatings;
	private List<Rating> ratingList;
	private List<AbstractRatingSummary> ratingStat;
}
