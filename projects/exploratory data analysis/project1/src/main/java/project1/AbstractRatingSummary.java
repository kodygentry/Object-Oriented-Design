package project1;

import java.util.List;

/**
 * DO NOT MODIFY: Abstract Rating Summary 
 * Contains RatingID, nodeDegree, and List of Objects capturing statistics
 * @author tesic
 */
public abstract class AbstractRatingSummary implements Comparable<AbstractRatingSummary>{

    //Constructors
    /**
     * Default Constructor
     */
    protected AbstractRatingSummary() {
        this("",0,null);
    }

    /**
     * Constructor with 1 input parameter
     * @param inNodeID unique identifier
     */
    protected AbstractRatingSummary(final String inNodeID) {
        this(inNodeID,0,null);
    }

    /**
     * Constructor with 2 input parameters
     * @param inNodeID unique identifier
     * @param inDegree number of reviews for identifier
     */
    protected AbstractRatingSummary(final String inNodeID, final long inDegree) {

        this(inNodeID,inDegree,null);

    }

    /**
     * Constructor with 3 input parameters
     * @param inNodeID unique identifier
     * @param inDegree number of reviews for identifier
     * @param inList list of computed statistics
     */
    protected AbstractRatingSummary(final String inNodeID, final long inDegree, List<Float> inList) {

        this.nodeID = inNodeID;
        this.degree = inDegree;
        this.statList = inList;
    }

     /**
     * 
     * @param inDegree number of reviews for identifier
     */
    public void setDegree(long inDegree){
        this.degree = inDegree;
    }

    /**
     * 
     * @return unique identifier
     */
    public String getNodeID() {
		return nodeID;
	}
    
    /**
     * 
     * @return number of reviews
     */
	public long getDegree() {
		return degree;
	}

	/**
     * 
     * @param inRatings get number of ratings for a unique identifiedr from the list
     */
    public void setDegree(final List<Rating> inRatings){

        long count = 0;
        for(final Rating ir: inRatings){
            if (this.nodeID.equals(ir.getReviewerID())||this.nodeID.equals(ir.getProductID())){
                count++;
            }
        }
        this.degree = count; 

    }

    /**
     * 
     * @return abstract method that returns a reference to the new list
     */
    public abstract List<Float> createList();

    /**
     * 
     * @param newList sets list 
     */
    public void setList(final List<Float> newList) {
        this.statList = newList;
    }

    /**
     * 
     * @return list reference to specific list object for subclasses
     */
    public List<Float> getList() {
        return this.statList;
    }

    ///////////////////////////////////////////////////////////////////
    ///// abstract method block for computing stats on abstract objects 

    /**
     * 
     * @return average score 
     */
    public abstract Float avgScore();
    
    /**
     * Summarizes list of stats for natural ordering (used in compareTo and equals override)
     * @return criterion for sorting lists of stats 
     */
    public abstract Float sortStats();

    /**
     * Collects rating Statistics for specific node
     * @param rawRatings collect stats from input ratings
     */
    public abstract void collectStats(final List<Rating> rawRatings);


    /**
     * Overrides compareTo so that subclass collections sort on Stats values 
     * Make sure that all subclasses implement correct sortStats method
	 * @param inStat rating summary to compare to 
	 * @return integer value
	 */
	public int compareTo(AbstractRatingSummary inStat){

		if (this == inStat ){
            return 0; 
        }else{
            return this.sortStats().compareTo(inStat.sortStats());
		}
    }
 
    /**
     * Overrides equals so that subclass collections sort on Stats values 
     * Make sure that all subclasses implement correct sortStats method
     * @param obj object to compare to
     * @return if the object is equal to this object 
     */
    @Override
    public boolean equals(Object obj){
        if (this == obj){
            return true; 
        }
        if (obj instanceof AbstractRatingSummary){
            float temp = ((AbstractRatingSummary) obj).sortStats();
            if (this.sortStats()==temp){
                return true;
            }
        }
        return false; 
    }

    /**
     * Overrides hashcode so that subclass collections sort on Stats values 
     * Make sure that all subclasses implement correct sortStats method
     * @return hash code
     */
    @Override
    public int hashCode(){
        return this.avgScore().intValue();
    }

    private final String nodeID;
    private long degree; 
    private List<Float> statList;
}