package project1;

/**
 * DO NOT MODIFY: Ratings
 * @author tesic
 */
public class Rating {

    /**
     * Constructor.
     * 
     * @param reviewerID	id of the reviewer
     * @param productID     id of the product
     * @param rating 		rating
     */
	
	public Rating(String reviewerID, String productID, float rating) {
		this.reviewerID = reviewerID;
		this.productID = productID;
		this.productRating = rating;
    }
    
	/**
     * Getter method for accessing the id of the reviewer.
     * @return The reviewerID
     */
    public String getReviewerID() {
        return reviewerID;
    }


    /**
     * Getter method for accessing the id of the product.
     * @return The productID
     */
    public String getProductID() {
        return productID;
    }

    /**
     * Getter method for accessing the rating.
     * @return rating
     */
    public float getRating() {
        return this.productRating;
    }
    
    /**
     * The id of the reviewer. 
     */
    private final String reviewerID;
    
    /**
     *  The id of the product.
     */
    private final String productID;
    
    /**
     * The rating that the reviewer gave.
     */
    private float productRating;
    
}
