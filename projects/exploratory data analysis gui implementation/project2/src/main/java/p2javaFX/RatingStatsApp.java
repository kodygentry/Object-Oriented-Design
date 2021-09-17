package p2javaFX;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.Optional;
import java.util.Set;

import javafx.application.Application; 
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group; 
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text; 
import javafx.stage.Stage; 
         
public class RatingStatsApp extends Application {
	private static DatasetHandler dh;
	private static Text textNumberOfData;
	private static TableView<Dataset> tableView;
	@Override 
	public void start(Stage stage) throws InvalidPathException, IOException {
	   	dh = new DatasetHandler();
	   	Set<Dataset> datasets = dh.getDataSets();
		
		Text textTitle = new Text();      
		textTitle.setText("Rating Stats App"); 
		textTitle.setX(20); 
		textTitle.setY(20);
		textTitle.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
	    
	    Text textFolder = new Text();      
	    textFolder.setText("Data folder: " + dh.getFolderPath()); 
	    textFolder.setX(20); 
	    textFolder.setY(50); 
	    
	    textNumberOfData = new Text();      
	    textNumberOfData.setText("Datasets available: " + datasets.size()); 
	    textNumberOfData.setX(20); 
	    textNumberOfData.setY(70);
	    
	    Text clickCellNote = new Text();      
	    clickCellNote.setText("Double click on a dataset to display computed statistics."); 
	    clickCellNote.setX(20); 
	    clickCellNote.setY(100); 
	    
		tableView = new TableView<Dataset>();
		tableView.setLayoutX(50);
		tableView.setLayoutY(110);
		tableView.setPrefSize(269, 220);

		TableColumn<Dataset, String> column1 = new TableColumn<>("Data ID");
		column1.setCellValueFactory(new PropertyValueFactory<>("dataId"));
		
		TableColumn<Dataset, String> column2 = new TableColumn<>("Name of File");
		column2.setCellValueFactory(new PropertyValueFactory<>("rawFile"));
		
		TableColumn<Dataset, String> column3 = new TableColumn<>("No. of Ratings");
		column3.setCellValueFactory(new PropertyValueFactory<>("numberOfRatings"));
		
		tableView.getColumns().add(column1);
		tableView.getColumns().add(column2);
		tableView.getColumns().add(column3);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		if(datasets.size()==0) {
			tableView.setPlaceholder(new Label("There is no data to select from"));
		}
		else {
			for(Dataset dataset: datasets) {
				tableView.getItems().add(dataset);			
			}
		}
		
		tableView.setRowFactory( tv -> {
		    TableRow<Dataset> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		        	Dataset rowData = row.getItem();
		            showStatistics(rowData.getDataId());
		        }
		    });
		    return row ;
		});
		
		Button addBtn = new Button("Add New Dataset");
		addBtn.setLayoutX(220);
		addBtn.setLayoutY(360);
		
		EventHandler<MouseEvent> addDatasetEventHandler = new EventHandler<MouseEvent>() { 
		     @Override 
		     public void handle(MouseEvent e) { 
		    	TextInputDialog idInputdialog = new TextInputDialog(); 
		    	idInputdialog.setTitle("Input");
		    	idInputdialog.setHeaderText("Please enter new unique dataID:");
		    	
		    	Optional<String> result = idInputdialog.showAndWait();
				if (result.isPresent()){
					String dataID = idInputdialog.getEditor().getText();
			    	 
			    	if(!dh.checkID(dataID)) {
		    		 	TextInputDialog fileInputdialog = new TextInputDialog(); 
				    	fileInputdialog.setTitle("Input");
				    	fileInputdialog.setHeaderText("For new " +dataID + " collection, what is the source file name?");
				    	fileInputdialog.showAndWait();  
				    	String filePath = fileInputdialog.getEditor().getText();
				    	 
				    	boolean check = dh.addCollection(dataID, filePath);
						if(check) {
							Alert alert = new Alert(AlertType.INFORMATION);
				    		alert.setHeaderText("Collection " + dataID + " added.");
				    		alert.showAndWait();
				    		try {
								dh.saveDB();
								tableView.getItems().clear();
								Set<Dataset> datasets = dh.getDataSets();
								textNumberOfData.setText("Datasets available: " + datasets.size());
								if(datasets.size()==0) {
									tableView.setPlaceholder(new Label("There is no data to select from"));
								}
								else {
									for(Dataset dataset: datasets) {
										tableView.getItems().add(dataset);			
									}
								}
								showStatistics(dataID);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						else {
							Alert alert = new Alert(AlertType.ERROR);
				    		alert.setHeaderText("File not found.");
				    		alert.setContentText("Try again.");
				    		alert.show();
						}
			    	}
			    	else {
			    		 Alert alert = new Alert(AlertType.ERROR);
			    		 alert.setHeaderText(dataID +" is already in the current database.");
			    		 alert.show();
			    	}
				}
		     } 
		};
		
		addBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, addDatasetEventHandler);
		
		Pane root = new Pane(textTitle, textFolder, textNumberOfData, clickCellNote, tableView, addBtn);
		
		BackgroundFill background_fill = new BackgroundFill(Color.WHITE,  
                CornerRadii.EMPTY, Insets.EMPTY); 

		// create Background 
		Background background = new Background(background_fill); 
		root.setBackground(background);
		
		Scene sc = new Scene(root, 370, 420); 
		stage.setScene(sc);
		stage.show(); 
	}
   
	public static void showStatistics(String dataID) {
		Dataset d;
		try {
			d = dh.populateCollection(dataID);
		
			String rc = "3";
			int stats = d.statsExist();
			if(stats > 0){
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setHeaderText("Statistics are already computed and saved for data id: " + dataID);
				alert.setContentText("Have a new data? Want to process the statistics again?");
				ButtonType buttonTypeYes = new ButtonType("Yes");
				ButtonType buttonTypeNo = new ButtonType("No");
				alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
				
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == buttonTypeYes){
					rc="4";
				}
			}
			if (rc.contains("4") || (stats == 0)) {
				d.computeStats();
				dh.saveStats(dataID);
			}
	
			//this blocks processes computed statistics in dh
			int k = 20;
			//prints report to file and show in table
			Stage newState = new Stage();
			dh.printReport(dataID, k);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }

	public static void main(String args[]){ 
		launch(args); 
	} 

}
