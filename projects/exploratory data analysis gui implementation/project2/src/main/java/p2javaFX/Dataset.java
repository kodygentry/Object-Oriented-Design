package p2javaFX;

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


public class Dataset {
	
	/**
	 * 
	 * @param dataId
	 * @param nameOfFile
	 * @param numberOfRatings
	 */
	public Dataset(String dataId, Path nameOfFile, long numberOfRatings) {
		this.dataId = dataId;
		this.rawFile = nameOfFile;
		this.numberOfRatings = numberOfRatings;
		this.ratingList = new ArrayList<Rating>();
		this.ratingStat = new ArrayList<AbstractRatingSummary>(); 
	}

	/**
	 * 
	 * @param dataId
	 * @param inRawFile
	 * @throws InvalidDataPath
	 * @throws IOException
	 */
	public Dataset(String dataId, Path inRawFile) throws IOException {

		this.dataId = dataId;
		this.rawFile = inRawFile;
		this.numberOfRatings = Files.lines(inRawFile).count();
		this.ratingList = new ArrayList<Rating>();
		this.ratingStat = new ArrayList<AbstractRatingSummary>(); 
	}

	/**
	 * 
	 * @return number of ratings 
	 * @throws InvalidDataPath
	 * @throws IOException
	 */
	public int loadRatings() throws IOException {
		this.ratingList = new ArrayList<Rating>();
		BufferedReader br = new BufferedReader(new FileReader(this.getRawFile().toFile()));
        String line;
        while((line = br.readLine()) != null) {
			String[] tempArr = line.split(",");
			Rating r = new Rating(tempArr[0],tempArr[1], Float.parseFloat(tempArr[2]));
			this.ratingList.add(r);
        }
		br.close();
		return this.ratingList.size();
	}

	/**
	 * 
	 * @param inStatPath
	 * @return
	 * @throws IOException
	 */
	public int loadStats(Path inStatPath) throws IOException {
		//load stats if file exists
		if(!Files.exists(inStatPath)) {
			return 0;
		}
		BufferedReader brs = new BufferedReader(new FileReader(inStatPath.toFile()));
		String line; 
		// reading first line with the column name
		brs.readLine();
		
		while((line = brs.readLine()) != null) {
			final String[] tempArr = line.split(",");
			final int len = tempArr.length;
			if (len>5){
				RatingSummary rs = new RatingSummary(tempArr[0],Long.parseLong(tempArr[1]));
				rs.setList(Float.valueOf(tempArr[2]), Float.valueOf(tempArr[3]), Float.valueOf(tempArr[4]),Float.valueOf(tempArr[5]));
				this.ratingStat.add(rs);
			}
		}
        brs.close();
        return this.ratingStat.size();
	}

	/**
	 * 
	 */
	public boolean computeStats(){
		//do not append, start from scratch
		this.ratingStat = new ArrayList<AbstractRatingSummary>();

		//create unique list of users and products here 
		Set<String> NodeIds = this.ratingList.stream().map(r -> r.getReviewerID()).collect(Collectors.toSet());
		boolean newStats = NodeIds.addAll(this.ratingList.stream().map(r -> r.getProductID()).collect(Collectors.toSet()));

		//Create List from set
		List<String> uniqueList = NodeIds.stream().collect(Collectors.toCollection(ArrayList::new));

		//loop over field 
		for(String user:uniqueList){
			AbstractRatingSummary newSummary = new RatingSummary(user,this.ratingList);
			newStats = this.ratingStat.add(newSummary);
		}
		return newStats;
	}

	public String saveStats(){

		String statString = ""; 
		//writing a rating summary in each line
		for (AbstractRatingSummary rs : this.getRatingStat()) {
			statString += rs.toString();
		}
		return statString;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public Path getRawFile() {
		return this.rawFile;
	}

	public long getNumberOfRatings() {
		return numberOfRatings;
	}

	public void setNumberOfRatings(long numberOfRatings) {
		this.numberOfRatings = numberOfRatings;
	}

	public int statsExist(){
		return ratingStat.size();
	}

	public List<Rating> getRatingList(){
		return this.ratingList;
	}
	
	public List<AbstractRatingSummary> getRatingStat(){
		return this.ratingStat;
	}


	public void setRatingSummary(List<AbstractRatingSummary> ratingSummary) {
		this.ratingStat = ratingSummary;
	} 

	@Override
	public String toString(){
		//"dataID,RAW_FILE,RATINGS_NO,STAT_FILE,";

		return (this.getDataId()+","+this.getRawFile().getFileName().toString()+","+this.getNumberOfRatings()+",");	
	}

	private String dataId;
	private Path rawFile;
	private long numberOfRatings;
	private List<Rating> ratingList;
	private List<AbstractRatingSummary> ratingStat;
}
