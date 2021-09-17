package p2javaFX;

import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *  Inventory of the datasets in DATA_FILE_FOLDER, kept in DATA_FILE_NAME
    @author tesic
    @author tarek
 */
public class DataAnalysis{
    
	/**
 	* 
 	* @return reference to list sorted by rogue .. meaning product avg and review avg differ the most 
 	*/
	 public static List<AbstractRatingSummary> sortByDegree(List<AbstractRatingSummary> inList) {
		
		// sorting reviewer by ratings of products differs from respective average product ratings to most 
		Collections.sort(inList, new Comparator<AbstractRatingSummary>() {
		    public int compare(AbstractRatingSummary r1, AbstractRatingSummary r2) {
				return Long.compare(r2.getDegree(), r1.getDegree());
		    }
		});
		return inList;
	}

    /**
 	* 
 	* @return reference to list sorted by rogue .. meaning product avg and review avg differ the most 
 	*/
	public static List sortByAvgDiff(List<AbstractRatingSummary> inList) {
		
		// sorting reviewer by ratings of products differs from respective average product ratings to most 
		Collections.sort(inList, new Comparator<AbstractRatingSummary>() {
		    public int compare(AbstractRatingSummary r1, AbstractRatingSummary r2) {
				return r2.avgScore().compareTo(r1.avgScore());
		    }
		});
		return inList;
    }

    /**
 	* 
 	* @return reference to list sorted by rogue .. meaning product avg and review avg differ the most 
 	*/
	public static List sortByStDevDiff(List<AbstractRatingSummary> inList) {
		
		// sorting reviewer by ratings of products differs from respective average product ratings to most 
		Collections.sort(inList, new Comparator<AbstractRatingSummary>() {
		    public int compare(AbstractRatingSummary r1, AbstractRatingSummary r2) {
				return r2.stDevScore().compareTo(r1.stDevScore());
		    }
		});
		return inList;
	}
	
	/**
	 * 
	 * @param k top K to be saved in the report 
	 * @return string object with full report 
	 */
	public static String printReport(List<AbstractRatingSummary> inList,int k){
		Stage stage = new Stage();
        
		// filtering the reviewers
		List<AbstractRatingSummary> reviewers = inList.stream()
				  .filter(rs -> rs.getNodeID().startsWith("A")).collect(Collectors.toList());
		
		int counter = reviewers.size();

		String separator =  "--------------------------------------------------"+DataAnalysis.LINE_SEP;
		String reportPrint = separator + "Highest "+Math.min(counter,k)+" number of reviews per reviewer" + DataAnalysis.SUMMARY_HEADER;
		sortByDegree(reviewers);
		for(AbstractRatingSummary rr: reviewers.subList((counter>k)?counter-k:0,counter)){
			reportPrint += rr.toString();
		}
		
		Text highestNoOfReviewsReviewerText = new Text();      
		highestNoOfReviewsReviewerText.setText("Highest "+Math.min(counter,k)+" number of reviews per reviewer");
		TableView<AbstractRatingSummary> highestNoOfReviewsReviewerTable = DataAnalysis.createTableView(reviewers, counter, k);
		
		reportPrint +=separator + "Highest "+Math.min(counter,k)+" rating discrepancies per reviewer (wrt other reviewers)" + DataAnalysis.SUMMARY_HEADER+DataAnalysis.LINE_SEP;
		sortByAvgDiff(reviewers);
		for(AbstractRatingSummary rr: reviewers.subList((counter>k)?counter-k:0,counter)){
			reportPrint += rr.toString();
		}
		
		Text highestRatingDiscrepanciesReviewerText = new Text();      
		highestRatingDiscrepanciesReviewerText.setText("Highest "+Math.min(counter,k)+" rating discrepancies per reviewer (wrt other reviewers)");
		TableView<AbstractRatingSummary> highestRatingDiscrepanciesReviewerTable = DataAnalysis.createTableView(reviewers, counter, k);

		reportPrint +=separator + "Highest "+Math.min(counter,k)+" rating variation per reviewer (wrt other reviewers)" + DataAnalysis.SUMMARY_HEADER;
		sortByStDevDiff(reviewers);
		for(AbstractRatingSummary rr: reviewers.subList((counter>k)?counter-k:0,counter)){
			reportPrint += rr.toString();
		}
		
		Text highestRatingVariationReviewerText = new Text();      
		highestRatingVariationReviewerText.setText("Highest "+Math.min(counter,k)+" rating variation per reviewer (wrt other reviewers)");
		TableView<AbstractRatingSummary> highestRatingVariationReviewerTable = DataAnalysis.createTableView(reviewers, counter, k);

		// filtering the products
		List<AbstractRatingSummary> products = inList.stream()
				  .filter(rs -> rs.getNodeID().startsWith("B")).collect(Collectors.toList());
		
		counter = products.size();

		reportPrint += separator + "Highest "+Math.min(counter,k)+" number of reviews per product" +  DataAnalysis.SUMMARY_HEADER;
		sortByDegree(products);
		for(AbstractRatingSummary rr: products.subList((counter>k)?counter-k:0, counter)){
			reportPrint += rr.toString();
		}
		
		Text highestNoOfReviewsProductText = new Text();      
		highestNoOfReviewsProductText.setText("Highest "+Math.min(counter,k)+" number of reviews per product");
		TableView<AbstractRatingSummary> highestNoOfReviewsProductTable = DataAnalysis.createTableView(reviewers, counter, k);
		
		reportPrint +=separator + "Highest "+Math.min(counter,k)+" rating discrepancies per product (wrt other products)" +  DataAnalysis.SUMMARY_HEADER;
		sortByAvgDiff(products);
		for(AbstractRatingSummary rr: products.subList((counter>k)?counter-k:0, counter)){
			reportPrint += rr.toString();
		}
		
		Text highestRatingDiscrepanciesProductText = new Text();      
		highestRatingDiscrepanciesProductText.setText("Highest "+Math.min(counter,k)+" rating discrepancies per product (wrt other products)");
		TableView<AbstractRatingSummary> highestRatingDiscrepanciesProductTable = DataAnalysis.createTableView(reviewers, counter, k);

		reportPrint +=separator + "Highest "+Math.min(counter,k)+" rating variation per product (wrt other products)" + DataAnalysis.SUMMARY_HEADER;
		sortByStDevDiff(products);
		for(AbstractRatingSummary rr: products.subList((counter>k)?counter-k:0, counter)){
			reportPrint += rr.toString();
		}
		
		Text highestRatingVariationProductText = new Text();      
		highestRatingVariationProductText.setText("Highest "+Math.min(counter,k)+" rating variation per product (wrt other products)");
		TableView<AbstractRatingSummary> highestRatingVariationProductTable = DataAnalysis.createTableView(reviewers, counter, k);
		
		VBox vBox = new VBox();   
	      
	    //Setting the space between the nodes of a VBox pane 
	    vBox.setSpacing(10);
		//retrieving the observable list of the VBox 
	    ObservableList<Node> list = vBox.getChildren(); 
	    //Adding all the nodes to the observable list 
	    list.addAll(highestNoOfReviewsReviewerText, highestNoOfReviewsReviewerTable,  
	    		highestRatingDiscrepanciesReviewerText, highestRatingDiscrepanciesReviewerTable,
	    		highestRatingVariationReviewerText, highestRatingVariationReviewerTable, 
	    		highestNoOfReviewsProductText, highestNoOfReviewsProductTable, 
	    		highestRatingDiscrepanciesProductText, highestRatingDiscrepanciesProductTable, 
	    		highestRatingVariationProductText, highestRatingVariationProductTable);
		
	    ScrollPane root = new ScrollPane();
	    vBox.setAlignment(Pos.CENTER);;
		root.setContent(vBox);
		BackgroundFill background_fill = new BackgroundFill(Color.WHITE,  
                CornerRadii.EMPTY, Insets.EMPTY); 

		// create Background 
		Background background = new Background(background_fill); 
		vBox.setBackground(background);
		
		Scene sc = new Scene(root, 600, 500, Color.WHITE); 
		stage.setScene(sc);
		stage.setTitle("Statistics");
		stage.show();
		return reportPrint;
	}
	
	public static TableView<AbstractRatingSummary> createTableView(List<AbstractRatingSummary> reviewers, int counter, int k)
	{
		TableView<AbstractRatingSummary> tableView = new TableView<AbstractRatingSummary>();
		tableView.setPrefSize(583, 250);
		TableColumn<AbstractRatingSummary, String> column1 = new TableColumn<>("Id");
		column1.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getNodeID()));
		
		TableColumn<AbstractRatingSummary, String> column2 = new TableColumn<>("Degree");
		column2.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getDegree()+""));
		
		TableColumn<AbstractRatingSummary, String> column3 = new TableColumn<>("Product avg");
		column3.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getList().get(0)+""));
		
		TableColumn<AbstractRatingSummary, String> column4 = new TableColumn<>("Product st.dev");
		column4.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getList().get(1)+""));
		
		TableColumn<AbstractRatingSummary, String> column5 = new TableColumn<>("Reviewer avg");
		column5.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getList().get(2)+""));
		
		TableColumn<AbstractRatingSummary, String> column6 = new TableColumn<>("Reviewer st.dev");
		column6.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getList().get(3)+""));
		
		tableView.getColumns().add(column1);
		tableView.getColumns().add(column2);
		tableView.getColumns().add(column3);
		tableView.getColumns().add(column4);
		tableView.getColumns().add(column5);
		tableView.getColumns().add(column6);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		if(reviewers.size()==0) {
			tableView.setPlaceholder(new Label("There is no data in the list"));
		}
		else {
			for(AbstractRatingSummary rr: reviewers.subList((counter>k)?counter-k:0,counter)){
				tableView.getItems().add(rr);
			}
		}
		
		return tableView;
	}
	
	
	public static final String LINE_SEP = System.lineSeparator();
	/**
	 * The file name of where the database is going to be saved.
	 */
	public static final String DELIMITER = ",";
	public static final String DB_FOLDER = "data";
	public static final String DB_FILENAME = "data.csv";
	public static final String STAT_FILE_TEMPLATE = "ratingSummary_<dataID>.csv";
	public static final String REPORT_FILE_TEMPLATE = "report_<dataID>.csv";
	public static final String RESULTS_FILE_TEMPLATE = "results_<dataID>.csv";
	public static final String SUMMARY_HEADER = System.lineSeparator()+"Id,degree,product avg,product st.dev,reviewer avg,reviewer st.dev"+System.lineSeparator();
}