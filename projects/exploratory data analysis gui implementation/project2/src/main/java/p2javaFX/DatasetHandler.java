package p2javaFX;

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.FileSystems;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

/**
 *  Handles datasets 
    @author tesic
    @author tarek
 */
public class DatasetHandler{

	/**
	 * 
	 * @throws InvalidPathException
	 * @throws IOException
	 */
	public DatasetHandler() throws InvalidPathException,IOException{

		this.setPaths();
		this.db = new HashSet<Dataset>();
		this.loadDB();
	}

	/**
	 * 
	 */
	public void loadDB() throws IOException{

		// Java 11
		if(Files.notExists(dbPath)){
			System.out.println("DB file \"" + dbPath.toString() + "\" will be created.");
			Files.createFile(dbPath);
		}else{
			
			final String content = Files.readString(dbPath);

			// array of lines
			final String[] lines = content.split(System.lineSeparator());
			Dataset d = null;
			for (final String line : lines) {
				final String[] col = line.split(DataAnalysis.DELIMITER);
				if (col.length > 2 && !col[0].equals("dataID")) {
					try {
						d = new Dataset(col[0], Path.of(DataAnalysis.DB_FOLDER,col[1]),Long.parseLong(col[2]));
					}catch(NumberFormatException nfe) {
						System.out.println("Number format error.");
					}
				}
				if (d != null) {
					db.add(d);
				}
			}
		}

	}

	/**
	 * 
	 * @return
	 */
	public String printDB(){

		String content = "";
		if (db.size()>0){
				content = "dataID,RAW_FILE,RATINGS_NO,STAT_FILE" + DataAnalysis.LINE_SEP;;
		}
		for (final Dataset d : db) {
			content = content + d.toString() + DataAnalysis.STAT_FILE_TEMPLATE.replace("<dataID>", d.getDataId())+ DataAnalysis.LINE_SEP; 

		}
		System.out.println(content);
		return content;
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void saveDB() throws IOException{
		// Java 11
		String content = this.printDB();
		Files.writeString(dbPath, content);
	}

	/**
	 * 
	 * @param dataID
	 * @param k
	 */
	public void printReport(final String dataID, int k){
		
		try{
			
			String report = DataAnalysis.printReport(this.getCollection(dataID).getRatingStat(),k);
			System.out.println(report);
			Files.writeString(this.defineReportPath(dataID),report);
			
		}catch(IOException e){
			System.out.println("Failed to save report, file not found");
		}
	}

	/**
	 * Saves computed statistics into a file
	 * @param dataID
	 */
	public void saveStats(final String dataID){
		
		try{
			
			String report = "Id,degree,product avg,product st.dev,reviewer avg,reviewer st.dev" + DataAnalysis.LINE_SEP;
			report+=this.getCollection(dataID).saveStats();
			Files.writeString(this.defineStatPath(dataID),report);
			
		}catch(IOException e){
			System.out.println("Failed to save statistics");
		}
	}

	//////////////////////PATH HANDLING METHODS//////////////////////////////////////
	/**
	 * 
	 */
	public void setPaths()throws InvalidPathException,IOException{
		String workDir = new java.io.File( "." ).getCanonicalPath();
		this.folderPath = FileSystems.getDefault().getPath(workDir,DataAnalysis.DB_FOLDER);
		this.dbPath = FileSystems.getDefault().getPath(workDir,DataAnalysis.DB_FOLDER,DataAnalysis.DB_FILENAME);
	}

	/**
	 * 
	 * @return
	 * @throws InvalidPathException
	 */
	public Path getDbPath() throws InvalidPathException{
		return this.dbPath;
	}

	/**
	 * 
	 * @return
	 * @throws InvalidPathException
	 */
	public Path getFolderPath() throws InvalidPathException{
		return this.folderPath;
	}

	/**
	 * 
	 * @return int number of dataset entries
	 */
	public Set<Dataset> getDataSets() {
		return this.db;
	}

	/**
	 * 
	 * @param dataId
	 * @return true if it exists in the datasets already
	 */
	public boolean checkID(String dataID){

		return this.db.stream().anyMatch(t -> t.getDataId().equals(dataID));
	}

	public Path defineRawPath(String input) throws InvalidPathException,IOException {

		Path pathRaw = FileSystems.getDefault().getPath(folderPath.toString(),input);
		if (!Files.exists(pathRaw)){
			Files.createFile(pathRaw);
			System.out.println(pathRaw + " created");
		}
		return pathRaw;
	}

	public Path defineStatPath(final String dataID) throws  InvalidPathException,IOException {

		String temp = DataAnalysis.STAT_FILE_TEMPLATE.replace("<dataID>", dataID); 
		Path pathStat = FileSystems.getDefault().getPath(folderPath.toString(),temp);
		if (!Files.exists(pathStat)){
			Files.createFile(pathStat);
			System.out.println(pathStat + " created");
		}
		return pathStat;
	}

	public Path defineReportPath(final String dataID) throws  InvalidPathException,IOException {

		String temp = DataAnalysis.REPORT_FILE_TEMPLATE.replace("<dataID>", dataID); 
		Path pathStat = FileSystems.getDefault().getPath(folderPath.toString(),temp);
		if (!Files.exists(pathStat)){
			Files.createFile(pathStat);
			System.out.println(pathStat + " created");
		}
		return pathStat;
	}

	/**
	 * 
	 * @param inData
	 * @return false if both loads failed 
	 * @throws IOException 
	 */
	public boolean addDataset(Dataset inData) throws IOException{

		boolean loaded = false;

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
	 * Saves computed statistics into a file
	 * @param inData
	 */
	public boolean addRatings(Dataset inData){
		//assume element is initialized with dataID and path to raw data 
		try{
			if(inData.getRatingList().size()>0) {
				System.out.println("Number of ratings: "+ inData.getRatingList().size());
			}
			else {
				System.out.println("Number of ratings: "+ inData.loadRatings());
			}
			return true;
		}catch(InvalidPathException e){
			System.out.println("Invalid path "+inData.getRawFile().toString());
		}catch(IOException e){
			System.out.println("Invalid file "+inData.getRawFile().toString());
		}catch(Exception e){
			System.out.println("What happened?");
		}
		return false;
	}

		/**
	 * Saves computed statistics into a file
	 * @param inData
	 */
	public boolean addStats(Dataset inData){
		//assume element is initialized with dataID and path to raw data
		Path statPath = null;
		try{
			if(inData.statsExist()>0) {
				System.out.println("Number of ratings: "+ inData.statsExist());
			}
			else {
				statPath = defineStatPath(inData.getDataId());
				System.out.println("Number of stats: "+inData.loadStats(statPath));
			}
			return true;
		}catch(InvalidPathException e){
			System.out.println("Invalid stat path "+statPath.toString());
		}catch(IOException e){
			System.out.println("Invalid stat file "+statPath.toString());
		}
		return false;
	}

	/**
	 * 
	 * @param dataID
	 * @return reference to dataset object to be loaded
	 * @throws IOException 
	 */
	public Dataset getCollection(String dataID) throws IOException{
		Dataset found = null; 
        Iterator<Dataset> value = this.db.iterator(); 
        while (value.hasNext()) { 
			found = value.next();
			if (dataID.contains(found.getDataId())){	
			  //this.addDataset(found);	
			  break; 
			}
		} 
		return found;
	}

	/**
	 * 
	 * @param dataID
	 * @return reference to dataset object to be loaded
	 * @throws IOException 
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
	 * 
	 * @param dataID
	 * @param input
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
				System.out.println(inRawFile.toString());
				db.add(newData);
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

