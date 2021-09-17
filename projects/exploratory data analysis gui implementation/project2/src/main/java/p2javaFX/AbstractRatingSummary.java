package p2javaFX;

import java.util.List;

/**
 * Abstract Rating Summary 
 * Contains RatingID, nodeDegree, and List of Objects capturing statistics
    @author tesic
 */
public abstract class AbstractRatingSummary implements Comparable<AbstractRatingSummary>{

    /**
     * 
     * @param inNodeID
     * @param inDegree
     */
    public AbstractRatingSummary(final String inNodeID) {

        this.nodeID = inNodeID;
    }

    /**
     * 
     * @param inNodeID
     * @param inDegree
     */
    public AbstractRatingSummary(final String inNodeID, final long inDegree) {

        this.nodeID = inNodeID;
        this.degree = inDegree;

    }

    /**
     * 
     * @param inNodeID
     * @param inDegree
     * @param inList
     */
    public AbstractRatingSummary(final String inNodeID, final long inDegree, List<Float> inList) {

        this(inNodeID, inDegree);
        this.statList = inList;
    }

    /**
     * 
     * @param inDegree
     */
    public void setDegree(long inDegree){
        this.degree = inDegree;
    }
    
    /**
     * 
     * @return nodeID
     */
    public String getNodeID() {
		return nodeID;
	}
    
    /**
     * 
     * @return degree
     */
	public long getDegree() {
		return degree;
	}

	/**
     * 
     * @param inRatings
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
     * @return list reference to specific list object for subclasses
     */
    public abstract List<Float> createList();

    /**
     * 
     * @param newList
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
     * Defines what it means to have rogue score 
     */
    public abstract Float avgScore();

      /**
     * Defines what it means to have agreement score 
     */
    public abstract Float stDevScore();
    
    /**
     * Summarizes list of stats for natural ordering 
     */
    public abstract Float sortStats();

    /**
     * Collects rating Statistics for specific node
     * 
     * @param inRatings
     */
    public abstract void collectStats(final List<Rating> rawRatings);


    /**
	 * /**
     * Overrides equals so that subclass collections sort on Stats values 
     * Make sure that all subclasses implement similar sorting strategy
	 * @param inRating
	 * @return
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
     * Make sure that all subclasses implement similar sorting strategy
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

    private final String nodeID;
    private long degree; 
    private List<Float> statList;
}