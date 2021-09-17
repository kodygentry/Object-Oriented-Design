package project1;

import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.Files;
import java.nio.file.FileSystems;
import java.nio.file.InvalidPathException;
import java.io.IOException;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

/**
 *  Handles datasets 
    @author tesic
 */
public class DatasetHandler{

	/**
	 * Default constructor
	 * @throws InvalidPathException if the default paths cannot be found/created
	 * @throws IOException data.csv is corrupted
	 */
	public DatasetHandler() throws InvalidPathException,IOException{

		this.setPaths();
		this.db = new HashSet<>();
		this.loadDB();
	}

	/**
	 * Loads the data from data.csv, if any
	 * @throws IOException data.csv is corrupted
	 */
	public void loadDB() throws IOException{

		// Java 11
		if(Files.notExists(dbPath)){
			System.err.println("DB file \"" + dbPath.toString() + "\" will be created.");
			Files.createFile(dbPath);
		}else{
			
			final String content = Files.readString(dbPath);
			// array of lines
			final String[] lines = content.split(System.lineSeparator());
			Dataset d = null;
			for (final String line : lines) {
				final String[] col = line.split(DataAnalysis.DELIMITER);

				Path dataPath = Path.of(folderPath.toString(),col[2]);
				System.out.println(dataPath);
				if (col.length > 2 && !col[0].equals("dataID")) {
					d = new Dataset(col[0], dataPath);
				}
				if (d != null) {
					db.add(d);
				}
			}
		}

	}

	/**
	 * implement printDB() method
	 * add javadoc
	 */
	public String printDB(){

		// your code here 

	}

	/**
	 * implement saveDBToFile() method
	 * add javadoc
	 */
	public void saveDBToFile() throws IOException{
		
		//your code here
		
	}

	/**
	 * Save report to file for a dataset and prints it to a console
	 * @param dataID unique dataset identifier
	 * @param k number of top results to be saved in the report
	 */
	public void saveReportToFile(final String dataID, int k){
		
		try{
			
			String report = DataAnalysis.printReport(this.getCollection(dataID).getRatingStat(),k);
			System.out.println(report);
			Files.writeString(this.defineReportPath(dataID),report);
			
		}catch(IOException e){
			System.out.println("Failed to save report, file not found");
		}
	}

	/**
	 * implement saveStatsToFile() method
	 * add javadoc
	 */
	public void saveStatsToFile(final String dataID){
		
		//your code here




	}

	//////////////////////PATH HANDLING METHODS//////////////////////////////////////

	/**
	 * Set default relative paths here
	 * @throws InvalidPathException if the default paths cannot be found/created
	 * @throws IOException if the default files cannot be found/created
	 */
	public void setPaths()throws InvalidPathException,IOException{
		
		// the expected structure: data and ID folder at the same level, project1 within
		// ID folder
		String workDir = new java.io.File(".").getCanonicalFile().getParentFile().getParent();
		this.folderPath = FileSystems.getDefault().getPath(workDir, DataAnalysis.DB_FOLDER);
		this.dbPath = FileSystems.getDefault().getPath(workDir, DataAnalysis.DB_FOLDER, DataAnalysis.DB_FILENAME);
	}

	/**
	 * Get data.csv path
	 * @return default path to data.csv
	 * @throws InvalidPathException if the default path cannot be created/found
	 */
	public Path getDbPath() throws InvalidPathException{
		return this.dbPath;
	}

	/**
	 * get data folder path
	 * @return default full path to data folder 
	 * @throws InvalidPathException if the default path cannot be created/found
	 */
	public Path getFolderPath() throws InvalidPathException{
		return this.folderPath;
	}

	/**
	 * Get datasets
	 * @return number of dataset entries
	 */
	public int getDataSets() {
		return this.db.size();
	}

	/**
	 * Check if dataset is in the database
	 * @param dataID unique dataset identifier
	 * @return true if it exists in the datasets already
	 */
	public boolean checkID(String dataID){

		return this.db.stream().anyMatch(t -> t.getDataId().equals(dataID));
	}

	/**
	 * Check if dataset is in the database
	 * @param input filename 
	 * @return path to ratings file
	 * @throws InvalidPathException if the default paths cannot be found/created
	 * @throws IOException if the default files cannot be found/created
	 */
	public Path defineRawPath(String input) throws InvalidPathException,IOException {

		Path pathRaw = FileSystems.getDefault().getPath(folderPath.toString(),input);
		if (!Files.exists(pathRaw)){
			Files.createFile(pathRaw);
			System.err.println(pathRaw + " created");
		}
		return pathRaw;
	}

	/**
	 * Define statistics file path
	 * @param dataID unique dataset identifier
	 * @return path to stat file
	 * @throws InvalidPathException if the default paths cannot be found/created
	 * @throws IOException if the default files cannot be found/created
	 */
	public Path defineStatPath(final String dataID) throws  InvalidPathException,IOException {

		String temp = DataAnalysis.STAT_FILE_TEMPLATE.replace(DataAnalysis.DATA_ID_TEMPLATE, dataID); 
		Path pathStat = FileSystems.getDefault().getPath(folderPath.toString(),temp);
		if (!Files.exists(pathStat)){
			Files.createFile(pathStat);
			System.err.println(pathStat);
		}
		return pathStat;
	}

	/**
	 * Define report path
	 * @param dataID unique dataset identifier
	 * @return path to report file
	 * @throws InvalidPathException if the default paths cannot be found/created
	 * @throws IOException if the default files cannot be found/created
	 */
	public Path defineReportPath(final String dataID) throws  InvalidPathException,IOException {

		String temp = DataAnalysis.REPORT_FILE_TEMPLATE.replace(DataAnalysis.DATA_ID_TEMPLATE, dataID); 
		Path pathStat = FileSystems.getDefault().getPath(folderPath.toString(),temp);
		if (!Files.exists(pathStat)){
			Files.createFile(pathStat);
			System.err.println(pathStat);
		}
		return pathStat;
	}

	/**
	 * 
	 * @param inData inpout dataset
	 * @return false if both loads failed 
	 * @throws IOException if the reading from the rating file fails
	 */
	public boolean addDataset(Dataset inData) throws IOException{

		boolean loaded = false;
		
		//populate rawFile correctly
		if (Files.exists(inData.getRawFile())){
			loaded = addRatings(inData);
		}
		else {
			throw new IOException(inData.getRawFile().toString());
		}

		try{
			Path statPath = defineStatPath(inData.getDataId());
			if (Files.exists(statPath)){
				loaded = addStats(inData);
			}
			else {
				System.out.println("Loading statistics failed");
			}
	    }catch(Exception e){
			System.out.println("Exeption: Loading statistics failed");
		}

		return loaded;
	}


	/**
	 * Load Ratings
	 * @param inData input dataset
	 * @return boolean if computation was succesfull (either ratings exist or they were loaded from a file)
	 */
	public boolean addRatings(Dataset inData){
		//assume element is initialized with dataID and path to raw data 
		try{
			if (inData.getRatingList().isEmpty()){
				inData.loadRatings();
			}
			return true;
		}catch(InvalidPathException e){
			System.err.println("Invalid path "+inData.getRawFile().toString());
		}catch(Exception e){
			System.err.println("What happened?");
		}
		return false;
	}

    /**
	 * Saves computed statistics into a file
	 * @param inData dataset reference
	 * @return boolean on success of computation of statistics
	 */
	public boolean addStats(Dataset inData){
		//assume element is initialized with dataID and path to raw data
		try{
			Path statPath = defineStatPath(inData.getDataId());
			///////////////////////////////////////////////////
			if (!inData.hasStats()){
				inData.loadStats(statPath);
			}
			return true;
		}catch(InvalidPathException e){
			System.err.println("Invalid stat path for dataId "+ inData.getDataId());
		}catch(IOException e){
			System.err.println("Invalid stat file for dataId" + inData.getDataId());
		}
		return false;
	}

	/**
	 * 
	 * @param dataID unique dataset identifier
	 * @return reference to dataset object to be loaded 
	 */
	public Dataset getCollection(String dataID){
		Dataset found = null; 
        Iterator<Dataset> value = this.db.iterator(); 
        while (value.hasNext()) { 
			found = value.next();
			if (dataID.contains(found.getDataId())){	
				break; 
			}
		} 
		return found;
	}

	/**
	 * 
	 * @param dataID unique dataset identifier
	 * @return reference to dataset object to be loaded
	 * @throws IOException if the reading from the rating file fails 
	 */
	public Dataset populateCollection(String dataID) throws IOException{

		Dataset found = null; 
        Iterator<Dataset> value = this.db.iterator(); 
        while (value.hasNext()) { 
			found = value.next();
			if (dataID.equalsIgnoreCase(found.getDataId())){	
			  this.addDataset(found);
			  break; 
			}
		} 
		return found;
	}

	/**
	 * Add Collection 
	 * @param dataID unique datasdet identifier
	 * @param input filename of the ratings file 
	 * @return if the unique identifier is already there or no
	 */
	public boolean addCollection(final String dataID, final String input) {

		Dataset newData = null;

		try{
			if (checkID(dataID)){
				System.out.println("WARNING:you are to replace old record of "+dataID+" with new one!");
				db.removeIf(s -> s.getDataId().equals(dataID));				
			}
			//check input 
			Path inRawFile = defineRawPath(input);
			if(Files.exists(inRawFile)) {
				newData = new Dataset(dataID,inRawFile);
				newData.computeStats();
				db.add(newData);
				//System.err.println(inRawFile.toString());
				return true;
			}
			else {
				return false;
			}
		}catch(IOException e){
			return false;
		}
	}

	private final Set<Dataset> db;
	private Path folderPath;
	private Path dbPath;
}