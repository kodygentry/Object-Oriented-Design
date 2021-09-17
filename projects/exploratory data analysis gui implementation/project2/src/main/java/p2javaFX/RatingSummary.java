package p2javaFX;

import java.lang.Float;
import java.lang.String;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

/**
* Ratings Summary supporting inner and outer statistics of the review 
  @author tesic
  @author tarek
*/

public class RatingSummary extends AbstractRatingSummary{

	/**
	 * 
	 * @param inNodeID
	 * @param inDegree
	 * @param inList
	 */
	public RatingSummary(final String inNodeID, final long inDegree, final List<Float> inList) {
		super(inNodeID, inDegree, inList);
	}

	/**
     * 
     * @param inNodeID
     * @param inDegree
     */
    public RatingSummary(final String inNodeID, final long inDegree) {

		super(inNodeID, inDegree);
		this.setList();
		// this is where stats are saved
	}

	/**
     * Constructor.
     * 
     * @param id        	product/review id
     * @param degree		number of times reviewed
     * @param productAvg    average rating of the product
     * @param productStDev 	standard deviation of the product's rating
     * @param reviewerAvg   average rating of the reviewer
     * @param reviewerStDev standard deviation of the reviewer's ratings
     */
	public RatingSummary(final String id, final long degree, final float productAvg, final float productStDev, final float reviewerAvg,
	final float reviewerStDev) {
		this(id, degree);
		this.setList(productAvg, productStDev, reviewerAvg,reviewerStDev);
	}

	/**
	 * 
	 * @param id
	 * @param degree
	 * @param rawRatings
	 */
	public RatingSummary(final String id, final List<Rating> rawRatings) {
		super(id); 
		this.collectStats(rawRatings);
	}

	public void setList() {
		super.setList(createList());
	}

	public void setList(float productAvg, float productStDev, float reviewerAvg, float reviewerStDev) {
		super.setList(this.createList(productAvg, productStDev, reviewerAvg, reviewerStDev));
	}

	@Override
	public List<Float> createList(){
		return new ArrayList<Float>();
	}

	public List<Float> createList(float productAvg, float productStDev, float reviewerAvg, float reviewerStDev) {
		List<Float> newList = createList();
		newList.add(Float.valueOf(productAvg));
		newList.add(Float.valueOf(productStDev));
		newList.add(Float.valueOf(reviewerAvg));
		newList.add(Float.valueOf(reviewerStDev));
		return newList;
	}


	/**
	 * Prints RatingSummary object as form Id,degree,product avg,product st.dev,reviewer avg,reviewer st.dev\n
	 */
	@Override
	public String toString(){
		return (this.getNodeID()+","+this.getDegree()+","+this.printStats()+"\n");	
	}

	private String printStats() {
		String stats = "";
		for(int i=0; i< this.getList().size(); i++) {
//			stats+=new DecimalFormat("##.##").format(this.getList().get(i));
			stats+=this.getList().get(i);
			if(i<this.getList().size()-1) {
				stats+=",";
			}
		}
		return stats;
	}

	/**
	 * Populate the list that keeps statistics 
	 * Make sure the object was initialized 
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
	 * @param rawRatings
	 */
	public void collectProductStats(final List<Rating> rawRatings) {

		//collect product stats 
		List<Rating> filteredList = rawRatings.stream().filter(r -> r.getProductID().equals(this.getNodeID())).collect(Collectors.toList());
		//get degreee here 
		this.setDegree((long) filteredList.size());

		// Only extracting the ratings list from the filtered object
		final List<Float> ratingNumbers = filteredList.stream().map(r -> r.getRating()).collect(Collectors.toList());

		// Calculating avg and standard deviation
		float productAvg = (float) ratingNumbers.stream().mapToDouble(a -> a).sum();
		productAvg /= ((double) (ratingNumbers.size()));
		float productStDev = calculateSD(ratingNumbers,productAvg);
		//get all their reviewers 
		List<String> reviewersList = filteredList.stream().map(r->r.getReviewerID()).collect(Collectors.toList());
		//get all their reviews 
		List<Rating> reviewerProduct = rawRatings.stream().filter((r->reviewersList.contains(r.getReviewerID()))).collect(Collectors.toList());
		final List<Double> reviewerStats = reviewerProduct.stream().mapToDouble(r -> r.getRating()).boxed().collect(Collectors.toList());
		
		// Calculating avg and standard deviation
		float reviewerAvg = (float) reviewerStats.stream().mapToDouble(a -> a).sum();
		reviewerAvg /= ((double) (reviewerStats.size()));
		float reviewerStDev = calculateSD(reviewerStats,reviewerAvg);

		//add to the list 
		this.setList(createList(productAvg, productStDev, reviewerAvg,reviewerStDev));

	}

	/**
	 * Collects product stats for nodeID -- never call this function directly, only through collectStats
	 * @param rawRatings
	 */
	public void collectReviewerStats(final List<Rating> rawRatings) {
		
		List<Rating> filteredList = rawRatings.stream().filter(r -> r.getReviewerID().equals(this.getNodeID()))
						.collect(Collectors.toList());
		//get degreee here 
		this.setDegree((long) filteredList.size());
		// Only extracting the ratings list from the filtered object
		final List<Double> ratingNumbers = filteredList.stream().mapToDouble(r -> r.getRating()).boxed().collect(Collectors.toList());

		// Calculating avg and standard deviation
		float reviewerAvg = (float) (ratingNumbers.stream().mapToDouble(a -> a).sum()); 
		reviewerAvg /= ((double) (ratingNumbers.size()));
		final float reviewerStDev = calculateSD(ratingNumbers,reviewerAvg);
		
		//get all their products 
		List<String> productsList = filteredList.stream().map(r->r.getProductID()).collect(Collectors.toList());
		//get all users from filteredList 
		List<Rating> reviewerProduct = rawRatings.stream().filter((r -> productsList.contains(r.getProductID()))).collect(Collectors.toList());
		
		final List<Double> reviewerStats = reviewerProduct.stream().mapToDouble(r -> r.getRating()).boxed().collect(Collectors.toList());
		
		// Calculating avg and standard deviation
		float productAvg = (float) reviewerStats.stream().mapToDouble(a -> a).sum();
		productAvg /= ((double) (reviewerStats.size()));
		final float productStDev = calculateSD(reviewerStats,productAvg);

		//add to the list 
		this.setList(createList(productAvg, productStDev, reviewerAvg,reviewerStDev));
	
	}

	////////// Statistics block

	/** 
	 * @return sort by biggest difference between product and review average in collection 
	 */
	public Float avgScore(){

		float score = Math.abs(this.getList().get(0).floatValue()-this.getList().get(2).floatValue());
		return Float.valueOf(score);
	}

	/** 
	 * @return sort by biggest difference between product and review st.dev. in collection   
	 */
	public Float stDevScore(){

		float score = Math.abs(this.getList().get(1).floatValue()-this.getList().get(3).floatValue());
		return Float.valueOf(score);
	}

	/** 
	 * @return summary of statistics as key to sorting the rating summaries 
	 */
	public Float sortStats(){
		return Float.valueOf((float) this.getList().stream().mapToDouble(v -> v).max().getAsDouble());
	}

	/**
	 * Method to calculate standard deviation of a list
	 * @param nums
	 * @param mean
	 * @return
	 */
	public float calculateSD(final List<? extends Number> nums, final float mean) {
		final int length = nums.size();

		//https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/stream/DoubleStream.html
		double standardDeviation = nums.stream().mapToDouble(a -> Math.pow(Double.valueOf(a+"") - mean, 2)).sum();
		
		return (float) Math.sqrt(standardDeviation / length);

	}

}
