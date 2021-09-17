package project2;

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
	 * Populate the list that keeps statistics 
	 * Make sure the object was initialized 
	 * @param rawRatings input list of ratings
	 */
	@Override
	public void collectStats(final List<Rating> rawRatings){

		//determine here is product or review we are collecting stats for
		if(this.getNodeID().startsWith("A")){
			this.collectReviewerStats(rawRatings);
		} else {
			this.collectProductStats(rawRatings);
		}
	}

	/**
	 * Collects product stats for nodeID -- never call this function directly, only through collectStats
	 * @param rawRatings input list of ratings
	 */
	private void collectProductStats(final List<Rating> rawRatings) {

		//collect product stats 
		List<Rating> filteredList = rawRatings.stream().filter(r -> r.getProductID().equals(this.getNodeID())).collect(Collectors.toList());
		//get degreee here 
		this.setDegree((long) filteredList.size());

		// Only extracting the ratings list from the filtered object
		final List<Float> ratingNumbers = filteredList.stream().map(r -> r.getRating()).collect(Collectors.toList());
		// Calculating prroduct average
		float productAvg = (float) ratingNumbers.stream().mapToDouble(a -> a).sum();
		productAvg /= ((double) (ratingNumbers.size()));


		//Calculating all their reviewers average 
		List<String> reviewersList = filteredList.stream().map(r -> r.getReviewerID()).collect(Collectors.toList());
		//get all their reviews 
		List<Rating> reviewerProduct = rawRatings.stream().filter((r->reviewersList.contains(r.getReviewerID()))).collect(Collectors.toList());
		final List<Double> reviewerStats = reviewerProduct.stream().mapToDouble(r -> r.getRating()).boxed().collect(Collectors.toList());
		
		// Calculating average
		float reviewerAvg = (float) reviewerStats.stream().mapToDouble(a -> a).sum();
		reviewerAvg /= ((double) (reviewerStats.size()));

		//add to the list 
		this.setList(createList(productAvg,reviewerAvg));

	}

	/**
	 * Collects product stats for nodeID -- never call this function directly, only through collectStats
	 * @param rawRatings input list of ratings
	 */
	private void collectReviewerStats(final List<Rating> rawRatings) {
		
		List<Rating> filteredList = rawRatings.stream().filter(r -> r.getReviewerID().equals(this.getNodeID()))
						.collect(Collectors.toList());
		//get degreee here 
		this.setDegree((long) filteredList.size());
		// Only extracting the ratings list from the filtered object
		final List<Double> ratingNumbers = filteredList.stream().mapToDouble(r -> r.getRating()).boxed().collect(Collectors.toList());

		// Calculating avg and standard deviation
		float reviewerAvg = (float) (ratingNumbers.stream().mapToDouble(a -> a).sum()); 
		reviewerAvg /= ((double) (ratingNumbers.size()));

		//get all their products 
		List<String> productsList = filteredList.stream().map(r->r.getProductID()).collect(Collectors.toList());
		//get all users from filteredList 
		List<Rating> reviewerProduct = rawRatings.stream().filter((r -> productsList.contains(r.getProductID()))).collect(Collectors.toList());
		
		final List<Double> reviewerStats = reviewerProduct.stream().mapToDouble(r -> r.getRating()).boxed().collect(Collectors.toList());
		
		// Calculating avg and standard deviation
		float productAvg = (float) reviewerStats.stream().mapToDouble(a -> a).sum();
		productAvg /= ((double) (reviewerStats.size()));

		//add to the list 
		this.setList(createList(productAvg,reviewerAvg));
	
	}

	////////// Statistics block

	/** 
	 * @return sort by biggest difference between product and review average in collection 
	 */
	public Float avgScore(){
		
		float score = Math.abs(this.getList().get(0).floatValue()-this.getList().get(1).floatValue());
		return Float.valueOf(score);
	}

	/** 
	 * @return summary of statistics as key to sorting the rating summaries 
	 */
	public Float sortStats(){
		return Float.valueOf((float) this.getList().stream().mapToDouble(v -> v).max().getAsDouble());
	}

}
