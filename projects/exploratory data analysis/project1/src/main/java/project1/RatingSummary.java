package project1;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

/**
 * Ratings Summary supporting inner and outer statistics of the review 
 * @author tesic
*/
public class RatingSummary extends AbstractRatingSummary{

	/**
	 * 
	 * @param inNodeID unique identifier
	 * @param inDegree number of ratings for unique identifier
	 * @param inList list of review statistics
	 */
	public RatingSummary(final String inNodeID, final long inDegree, final List<Float> inList) {
		super(inNodeID, inDegree, inList);
	}

	/**
     * 
     * @param inNodeID unique identifier
     * @param inDegree number of ratings for unique identifier
     */
    public RatingSummary(final String inNodeID, final long inDegree) {

		super(inNodeID, inDegree);
		this.setList();
	}

	/**
     * Constructor
     * 
     * @param id        	product/review id
     * @param degree		number of times reviewed
     * @param productAvg    average rating of the product
     * @param reviewerAvg   average rating of the reviewer
     */
	public RatingSummary(final String id, final long degree, final float productAvg, final float reviewerAvg) {
		this(id, degree);
		this.setList(productAvg, reviewerAvg);
	}

	/**
	 * Constructor w 2 parameters
	 * @param id			product/review id
	 * @param rawRatings	review data
	 */
	public RatingSummary(final String id, final List<Rating> rawRatings) {
		super(id); 
		this.collectStats(rawRatings);
	}

	/**
     * List setter
     */
	public void setList() {
		super.setList(createList());
	}

	/**
     * List setter from stat data
     * 
     * @param productAvg    average rating of the product
     * @param reviewerAvg   average rating of the reviewer
     */
	public void setList(float productAvg, float reviewerAvg) {
		super.setList(this.createList(productAvg,reviewerAvg));
	}

	/**
     * Create List
	 * @return list reference
     */
	public List<Float> createList(){
		return new ArrayList<>();
	}

	/**
     * Create List
	 * @param productAvg    average rating of the product
     * @param reviewerAvg   average rating of the reviewer
	 * @return list reference
     */
	public List<Float> createList(float productAvg, float reviewerAvg) {
		List<Float> newList = createList();
		newList.add(Float.valueOf(productAvg));
		newList.add(Float.valueOf(reviewerAvg));
		return newList;
	}


	/**
	 * Prints RatingSummary object as form Id,degree,product avg,reviewer avg\n
	 * @return string
	 */
	@Override
	public String toString(){
		return (this.getNodeID()+","+this.getDegree()+","+this.printStats()+"\n");	
	}

	private String printStats() {

		StringBuilder stats = new StringBuilder();
		for(int i=0; i< this.getList().size(); i++) {
			stats.append(String.format("%.3f",this.getList().get(i)));
			if(i<this.getList().size()-1) {
				stats.append(",");
			}
		}
		return stats.toString();
	}

	/**
	 * implement collectStats
	 * add javadoc
	 */
	public void collectStats(final List<Rating> rawRatings){

		// your code here

	}

	/**
	 * implement sortStats
	 * add javadoc
	 */
	public Float sortStats(){

		// your code here

	}

	/** 
	 * implement avgScore method
	 * add javadoc 
	 */
	public Float avgScore(){
		
		// your code here
	}

}
