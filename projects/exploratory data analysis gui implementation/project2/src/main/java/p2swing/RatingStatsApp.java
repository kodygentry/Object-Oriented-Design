package p2swing;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.io.IOException;
import java.util.Vector;


/**
 *  Provides functionality for interacting with the user, main class
 @author tesic
 @author toufik

  */
public class RatingStatsApp {

	public static void main(final String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				try {
					DatasetHandler dh = new DatasetHandler();
					String setup = "Loading the datasets from:" + DataAnalysis.LINE_SEP
							+ "     data folder: " + dh.getFolderPath() + DataAnalysis.LINE_SEP
							+ "     datasets available: " + dh.getDataSets() + DataAnalysis.LINE_SEP;
		
					JOptionPane.showMessageDialog(null, setup);
					// Creates and set's up the Main menu frame
					JFrame mainMenu = new JFrame("Main Menu");
					mainMenu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
					mainMenu.setVisible(true);
					mainMenu.setLayout(new BorderLayout());
		
					// Creates main menu panel
					JPanel topMenuPanel = new JPanel();
					JLabel textMenu = new JLabel("Choose one of the following functions:");
					topMenuPanel.add(textMenu);
		
					// Creates main menu center panel
					JPanel centerMenuPanel = new JPanel();
					JButton b1MainMenu = new JButton(" 1. Display computed statistics for specific dataID.");
					centerMenuPanel.add(b1MainMenu);
					
					// Creates main menu south panel
					JPanel southMenuPanel = new JPanel();
					JButton b2MainMenu = new JButton(" 2. Add new collection and compute statistics.");
					southMenuPanel.add(b2MainMenu);
		
					//Adds panels to the frame in their respective place
			        	mainMenu.add(topMenuPanel, BorderLayout.NORTH);
					mainMenu.add(centerMenuPanel, BorderLayout.CENTER);
					mainMenu.add(southMenuPanel, BorderLayout.SOUTH);		
					mainMenu.pack(); // Makes all the stuff in the frame fit perfectly
					mainMenu.setResizable(false); // Makes main window unable to be resized
					mainMenu.setLocationRelativeTo(null); // Makes window appear at the center of the screen
					mainMenu.setPreferredSize(mainMenu.getPreferredSize()); // Makes the main frame the perfect size
		
					//Adds Action Listener for Button 1 of Main Menu
					b1MainMenu.addActionListener(new ActionListener() {
						/** actionPerformed adds event for main menu
						 * @param e
						 */
						@Override
						public void actionPerformed(ActionEvent e) {
							
							mainMenu.setVisible(false); // Hides main menu
							int dbSize = dh.getDataSets();
							
							// Displays error message if no data exists
							if (dbSize < 1) {
								JOptionPane.showMessageDialog(null,
										"There is no data to select from, select another option",
										"User must select another option",
										JOptionPane.WARNING_MESSAGE);
								mainMenu.setVisible(true);// Restarts the program after error occurs	
							} 
							else {
								
								// Creates frame for option 1 of the menu
								JFrame option1Frame = new JFrame("Collection Statistics Library");
								option1Frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
								option1Frame.setSize(1000, 400);
								option1Frame.setVisible(true);
								option1Frame.setLocationRelativeTo(null); // Makes window appear at the center of the screen
								option1Frame.setLayout(new BorderLayout());
								// Get's all the data from the data excel file
								String content = dh.printDB();
								// Splits data from datahandler line by line
								String rows[] = content.split("\n");
								Vector<Vector<String>> dataVector = new Vector<Vector<String>>();
								// Gets the data from the excel file ready for the table  and saves it into a vector string
								for (String row : rows) {
									row = row.trim(); 
									if (!row.contains("dataID,NUMBER,RATINGS,STATS")) {
										Vector<String> data = new Vector<String>();
										data.addAll(Arrays.asList(row.split(",")));
										dataVector.add(data);
									}
								}
								// Creates the header of the Excel Jtable
								Vector<String> header = new Vector<String>(2);
								header.add("dataID");
								header.add("NUMBER");
								header.add("RATINGS");
								header.add("STATS");
								
								// Creates the table model with the data and header
								TableModel model = new DefaultTableModel(dataVector, header);
								
								// This makes JTable not editable
								JTable table = new JTable(model) {
									/** isCellEditable returns false as cell not editable
									 * @param row
									 * @param column
									 * @return false
									 */
									@Override
									public boolean isCellEditable(int row, int column) {
										//all cells false
										return false;
									}
								};

								//When clicked in a row of a table it takes the dataID column as newDataID
		
								table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){

									public void valueChanged(ListSelectionEvent event) {

										if(event.getValueIsAdjusting()){

											String newDataID = table.getValueAt(table.getSelectedRow(), 0).toString();
											
											// Displays error message if newDataID cant be found
											if (!(dh.checkID(newDataID))) {
												JOptionPane.showMessageDialog(null,
														"" + newDataID + " is not in the current database, try again!",
														"Error",
														JOptionPane.WARNING_MESSAGE);
											}

											else {
												Dataset d = null;
												try {
													d = dh.populateCollection(newDataID);
													d.computeStats();
												} catch (IOException ex) {
													ex.printStackTrace();
												}
												int stats = d.statsExist();
												if(stats > 0) {
													// Creates frame for option 1 Extension
													JFrame option1ExtensionFrame = new JFrame("Options");
													option1ExtensionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
													option1ExtensionFrame.setSize(1000, 400);
													option1ExtensionFrame.setVisible(true);
													option1ExtensionFrame.setLocationRelativeTo(null); // Makes window appear at the center of the screen
													option1ExtensionFrame.setLayout(new BorderLayout());
													// Create panels for option 1 extensions
													JPanel topPanelOption1Extension = new JPanel();
													JPanel centerPanelOption1Extension = new JPanel();
													JPanel southPanelOption1Extension = new JPanel();
													// Creates label,text area, and buttons for the extra options for option 1
													JLabel option1ExtensionLabel = new JLabel("" + newDataID + ": statistics are already computed and saved. \nChoose one of the following functions: ");
													//Buttons for after entering dataID 
													JButton option3Button = new JButton("Use existing stat data.");
													JButton option4Button = new JButton("Process statistics again, I have new data.");
													// Adds label,text area, and buttons to the panel
													topPanelOption1Extension.add(option1ExtensionLabel);
													centerPanelOption1Extension.add(option3Button);
													southPanelOption1Extension.add(option4Button);
													// Adds panels to option 1 extension frame
													option1ExtensionFrame.add(topPanelOption1Extension, BorderLayout.NORTH);
													option1ExtensionFrame.add(centerPanelOption1Extension, BorderLayout.CENTER);
													option1ExtensionFrame.add(southPanelOption1Extension, BorderLayout.SOUTH);
													option1ExtensionFrame.pack(); // Makes all the stuff in the frame fit perfectly
													option1ExtensionFrame.setResizable(false); // Makes main window unable to be resized
													option1ExtensionFrame.setLocationRelativeTo(null); // Makes window appear at the center of the screen
													option1ExtensionFrame.setPreferredSize(option1ExtensionFrame.getPreferredSize()); // Makes the main frame the perfect size
		
													String finalNewDataID = newDataID;
		
													// Buttons that allows user to run report with two options
													// Button 3 is "use previous data" 
													// Button 4 is "calculate with new data"
													option3Button.addActionListener(new ActionListener() {
														/** actionPerformed creates and displays data report
														 * @param e action event to compute and display
														 */
														@Override
														public void actionPerformed(ActionEvent e) {
															// This closes the frame that holds buttons 3 and 4 upon being clicked
															option1ExtensionFrame.setVisible(false);
															// This blocks processes computed statistics in dh
															int k = 30;
															// Prints report to file
															String dataIdStats = dh.saveReportToFile(finalNewDataID, k);
															
															String rows_2[] = dataIdStats.split("\n");
																				
															Vector<Vector<String>> dataVector_2 = new Vector<Vector<String>>();
															Vector<String> header2 = new Vector<String>(2);
															header2.add("Id");
															header2.add("Degree");
															header2.add("Product Avg");
															header2.add("Reviewer Avg");
															// Creates displayStatsFrame to displayed stats of found dataID
															JFrame displayStatsFrame = new JFrame("Display computed statistics of " + finalNewDataID);
															displayStatsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // closes only the computed stats frame
															displayStatsFrame.setSize(1000, 400);
															displayStatsFrame.setVisible(true);
															displayStatsFrame.setLocationRelativeTo(null); // Makes window appear at the center of the screen
															// Creates JText Area to display computed statistics of the picked dataID
															displayStatsFrame.setLayout(new BorderLayout());
															//Vector to hold header for displayed stats jtable
															// Gets the data from the excel file ready for the table  and saves it into a vector string
															for (String row2 : rows_2) {
																row2 = row2.trim();
																if (!row2.contains("Id,degree,product avg,reviewer avg")) {
																	Vector<String> data_2 = new Vector<String>();
																	data_2.addAll(Arrays.asList(row2.split(",")));
																	dataVector_2.add(data_2);
																}
															}
															//Vector to hold header for displayed stats jtable
															// Gets the data from the excel file ready for the table  and saves it into a vector string
																TableModel model_2 = new DefaultTableModel(dataVector_2, header2); 
																JTable table_2 = new JTable(model_2) {
																	/** isCellEditable returns false as cell not editable
																	 * @param row2
																	* @param column2
																	* @return false
																	*/
																	@Override
																	public boolean isCellEditable(int row2, int column2) {
																		//all cells false
																		return false;
																	}
																};
		
																table_2.setPreferredScrollableViewportSize(table_2.getPreferredSize()); // Makes the size of the table fit the Frame
																table_2.setFillsViewportHeight(true); // Makes the size of the table fit the Frame
																displayStatsFrame.add(new JScrollPane(table_2), BorderLayout.CENTER); // Adds JTable and scroller to the frame
																JScrollPane scroll = new JScrollPane(table_2, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
																// Add computed stats text to the stats frame
																displayStatsFrame.add(scroll);
																// Makes display stats frame be the perfect size to fit everything
																displayStatsFrame.setPreferredSize(displayStatsFrame.getPreferredSize());
																						
														}
													});
		
													String finalNewDataID1 = newDataID;
													Dataset finalD = d;
													
													// This option computes a new report and prints the output to text area
													option4Button.addActionListener(new ActionListener() {
														/** actionPerformed creates new data report and displays correct frames to look nice
														 * @param e event action for computing new data report
														 */
														@Override
														public void actionPerformed(ActionEvent e) {
														// This closes the frame that holds buttons 3 and 4 upon being clicked
														option1ExtensionFrame.setVisible(false);
														JPanel centralPanelOption2 = new JPanel();												
														String k_string = JOptionPane.showInputDialog(centralPanelOption2,"Please enter the number of statistics to show");
															finalD.computeStats();
															//if stats were computed again, save them.
															dh.saveStatsToFile(finalNewDataID1);
															try {
																dh.saveDBToFile();
															} catch (IOException ex) {
																ex.printStackTrace();
															}
															// This blocks processes computed statistics in dh
															int k = Integer.parseInt(k_string);
															// Prints report to file
															String dataIdStats = dh.saveReportToFile(finalNewDataID1, k);
															String rows_2[] = dataIdStats.split("\n");					
															Vector<Vector<String>> dataVector_2 = new Vector<Vector<String>>();
															Vector<String> header2 = new Vector<String>(2);
															header2.add("Id");
															header2.add("Degree");
															header2.add("Product Avg");
															header2.add("Reviewer Avg");
															// Creates displayStatsFrame to displayed stats of found dataID
															JFrame displayStatsFrame = new JFrame("Display computed statistics of " + finalNewDataID1);
															displayStatsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // closes only the computed stats frame
															displayStatsFrame.setSize(1000, 400);
															displayStatsFrame.setVisible(true);
															displayStatsFrame.setLocationRelativeTo(null); // Makes window appear at the center of the screen
															// Creates JText Area to display computed statistics of the picked dataID
															displayStatsFrame.setLayout(new BorderLayout());
															//Vector to hold header for displayed stats jtable
															// Gets the data from the excel file ready for the table  and saves it into a vector string
															for (String row2 : rows_2) {
																row2 = row2.trim();
																if (!row2.contains("Id,degree,product avg,reviewer avg")) {
																	Vector<String> data_2 = new Vector<String>();
																	data_2.addAll(Arrays.asList(row2.split(",")));
																	dataVector_2.add(data_2);
																}
															}
															//Vector to hold header for displayed stats jtable
															// Gets the data from the excel file ready for the table  and saves it into a vector string
															TableModel model_2 = new DefaultTableModel(dataVector_2, header2); 
															JTable table_2 = new JTable(model_2) {
																/** isCellEditable returns false as cell not editable
																 * @param row2
																* @param column2
																* @return false
																*/
																@Override
																public boolean isCellEditable(int row2, int column2) {
																	//all cells false
																	return false;
																}
															};
		
															table_2.setPreferredScrollableViewportSize(table_2.getPreferredSize()); // Makes the size of the table fit the Frame
															table_2.setFillsViewportHeight(true); // Makes the size of the table fit the Frame
															displayStatsFrame.add(new JScrollPane(table_2), BorderLayout.CENTER); // Adds JTable and scroller to the frame
															JScrollPane scroll = new JScrollPane(table_2, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
															// Add computed stats text to the stats frame
															displayStatsFrame.add(scroll);
															// Makes display stats frame be the perfect size to fit everything
															displayStatsFrame.setPreferredSize(displayStatsFrame.getPreferredSize());	
														}
													});
												}
												if(stats == 0) {
													// This blocks processes computed statistics in dh
													int k = 30;
													// Prints report to file
													String dataIdStats = dh.saveReportToFile(newDataID, k);
													String separator =  "--------------------------------------------------"+DataAnalysis.LINE_SEP;
													String tables[] = dataIdStats.split(separator);
													Vector<String> header2 = new Vector<String>(2);
													header2.add("Id");
													header2.add("Degree");
													header2.add("Product Avg");
													header2.add("Reviewer Avg");
													// Creates displayStatsFrame to displayed stats of found dataID
													JFrame displayStatsFrame = new JFrame("Display computed statistics of " + newDataID);
													displayStatsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // closes only the computed stats frame
													displayStatsFrame.setSize(1000, 400);
													JPanel container = new JPanel();
													container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
													displayStatsFrame.setLocationRelativeTo(null); // Makes window appear at the center of the screen
													// Creates JText Area to display computed statistics of the picked dataID
													displayStatsFrame.setLayout(new BorderLayout());
													//Vector to hold header for displayed stats jtable
													// Gets the data from the excel file ready for the table  and saves it into a vector string
													for (String tableInfo: tables) {
														if(tableInfo.length()<10) continue;
														Vector<Vector<String>> dataVector_2 = new Vector<Vector<String>>();
														String[] rows = tableInfo.split("\n");
														for (String row : rows) {
															row = row.trim();
															if(row.contains("Highest")) {
																JLabel label2 = new JLabel(row);
																container.add(label2);
															}
															else if (!row.contains("Id,degree,product avg,reviewer avg")) {
																Vector<String> data_2 = new Vector<String>();
																data_2.addAll(Arrays.asList(row.split(",")));
																dataVector_2.add(data_2);																}
														}
														
														TableModel model_2 = new DefaultTableModel(dataVector_2, header2); 
														JTable table_2 = new JTable(model_2) {
															/** isCellEditable returns false as cell not editable
															 * @param row2
															 * @param column2
															 * @return false
															 */
															@Override
															public boolean isCellEditable(int row2, int column2) {
																//all cells false
																return false;
															}
														};
		
														table_2.setPreferredScrollableViewportSize(table.getPreferredSize()); // Makes the size of the table fit the Frame
														table_2.setFillsViewportHeight(true); // Makes the size of the table fit the Frame
														container.add(table_2, null);
													}
														
													// Add computed stats text to the stats frame
													displayStatsFrame.add(new JScrollPane(container));
													displayStatsFrame.setVisible(true);
													// Makes display stats frame be the perfect size to fit everything
													displayStatsFrame.setPreferredSize(displayStatsFrame.getPreferredSize());
												}
											}
										}
									}
								});
								
								option1Frame.add(new JScrollPane(table), BorderLayout.NORTH); // Adds scroller to the table for larger entries
								table.setPreferredScrollableViewportSize(table.getPreferredSize()); // Makes the size of the table fit the Frame
								table.setFillsViewportHeight(true); // Makes the size of the table fit the Frame
								option1Frame.add(new JScrollPane(table), BorderLayout.NORTH); // Adds scrollable JTable to the frame
								// Creates central panel for Option 1 with it's label, text area for the input DataID, and find button
								JPanel centralPanelOption1 = new JPanel();
								JPanel southPanelOption1 = new JPanel();
								JButton returnMainMenu = new JButton("Main Menu");
								// Adds a return to main menu button
								southPanelOption1.add(returnMainMenu);
								// Adds central panel to Option 1 frame
								option1Frame.add(centralPanelOption1, BorderLayout.CENTER);
								option1Frame.add(southPanelOption1, BorderLayout.SOUTH);
								returnMainMenu.addActionListener(new ActionListener() {
									/** actionPerformed makes main menu visible and hides option 1 frame
									 * @param e event action to make main menu visible
									 */
									@Override
									public void actionPerformed(ActionEvent e) {
										mainMenu.setVisible(true);
										option1Frame.setVisible(false);
									}
		
								});
							}
						}
					});
		
					// Adds Action Listener for Button 2 of Main Menu
					b2MainMenu.addActionListener(new ActionListener() {
						/** actionPerformed creates action listener event for adding new data collection
						 * @param e event parameter for button 2
						 */
						@Override
						public void actionPerformed(ActionEvent e) {
							
							mainMenu.setVisible(false); // Hides main menu
							JFrame option2Frame = new JFrame("Add new collection and compute statistics");
							option2Frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
							option2Frame.setSize(1000, 400);
							option2Frame.setVisible(false); // This frame wasn't in use so I turned it off for now 
							option2Frame.setLocationRelativeTo(null); // Makes window appear at the center of the screen
							option2Frame.setLayout(new BorderLayout());
		
							// Creates central panel for Option 2 with it's label, text area for the input DataID, and find button
							JPanel centralPanelOption2 = new JPanel();
							String newDataID = JOptionPane.showInputDialog(centralPanelOption2,"Please enter new unique dataID:");
							if(newDataID == null) {
								JOptionPane.showMessageDialog(null, "You did not input anything. You will be redirected back to the main menu.");
								mainMenu.setVisible(true); // Added this for program to resume error
							}
							if (!(dh.checkID(newDataID))) {
								option2Frame.setVisible(false); // Turns off the last frame
								System.out.println("For new " + newDataID + " collection, what is the source file name?\n");
								final String sourceFileName = JOptionPane.showInputDialog(centralPanelOption2,"For new " + newDataID + " collection, what is the source file name?");
								if(sourceFileName == null) {
									JOptionPane.showMessageDialog(null, "You did not input anything. You will be redirected back to the main menu.");
									mainMenu.setVisible(true);
								}
								boolean check = dh.addCollection(newDataID,sourceFileName);
								if(check) {
									JOptionPane.showMessageDialog(centralPanelOption2,"Collection " + newDataID + " added.");
		
									Dataset d = null;
									try {
										d = dh.populateCollection(newDataID);
									} catch (IOException ex) {
										ex.printStackTrace();
									}
									int stats = d.statsExist();
									JPanel centralPanelOption3 = new JPanel();												
									String k_string = JOptionPane.showInputDialog(centralPanelOption3,"Please enter the number of statistics to show");
									if (stats == 0) {
										d.computeStats();
										//if stats were computed again, save them.
										dh.saveStatsToFile(newDataID);
		
										try {
											dh.saveDBToFile();
											mainMenu.setVisible(true);
										} catch (IOException ex) {
											ex.printStackTrace();
										}
									}
									// This blocks processes computed statistics in dh
									int k = Integer.parseInt(k_string);
									// Prints report to file
									String dataIdStats = dh.saveReportToFile(newDataID, k);
									String rows_2[] = dataIdStats.split("\n");						
									Vector<Vector<String>> dataVector_2 = new Vector<Vector<String>>();
									Vector<String> header2 = new Vector<String>(2);
									header2.add("Id");
									header2.add("Degree");
									header2.add("Product Avg");
									header2.add("Reviewer Avg");
									// Creates displayStatsFrame to displayed stats of found dataID
									JFrame displayStatsFrame = new JFrame("Display computed statistics of " + newDataID);
									displayStatsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // closes only the computed stats frame
									displayStatsFrame.setSize(1000, 400);
									displayStatsFrame.setVisible(true);
									displayStatsFrame.setLocationRelativeTo(null); // Makes window appear at the center of the screen
									// Creates JText Area to display computed statistics of the picked dataID
									displayStatsFrame.setLayout(new BorderLayout());
									//Vector to hold header for displayed stats jtable
									// Gets the data from the excel file ready for the table  and saves it into a vector string
									 for (String row2 : rows_2) {
										 row2 = row2.trim();
										 if (!row2.contains("Id,degree,product avg,reviewer avg")) {
											 Vector<String> data_2 = new Vector<String>();
											 data_2.addAll(Arrays.asList(row2.split(",")));
											 dataVector_2.add(data_2);
										}
									}
																
									TableModel model_2 = new DefaultTableModel(dataVector_2, header2); 
									JTable table_2 = new JTable(model_2) {
									/** isCellEditable returns false as cell not editable
									 * @param row2
									 * @param column2
									 * @return false
									 */
										@Override
										public boolean isCellEditable(int row2, int column2) {   					
										//all cells false
											return false;
										}
									};
									table_2.setPreferredScrollableViewportSize(table_2.getPreferredSize()); // Makes the size of the table fit the Frame
									table_2.setFillsViewportHeight(true); // Makes the size of the table fit the Frame
									displayStatsFrame.add(new JScrollPane(table_2), BorderLayout.CENTER); // Adds JTable and scroller to the frame
									JScrollPane scroll = new JScrollPane(table_2, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
									// Add computed stats text to the stats frame
									displayStatsFrame.add(scroll);
									// Makes display stats frame be the perfect size to fit everything
									displayStatsFrame.setPreferredSize(displayStatsFrame.getPreferredSize());
								}
								
								else {
									JOptionPane.showMessageDialog(null,
											"File not found... Returning to the main menu",
											"Error",
											JOptionPane.WARNING_MESSAGE);
							
									mainMenu.setVisible(true);
									option2Frame.setVisible(false);
								}
							}
							
							else {
								JOptionPane.showMessageDialog(null,
									"" + newDataID + " is in the current database, displaying existing statistics.",
									"Message",
									JOptionPane.WARNING_MESSAGE);
									option2Frame.setVisible(false);
									mainMenu.setVisible(true);
									Dataset d = null;
									try {
										d = dh.populateCollection(newDataID);
									} catch (IOException ex) {
										ex.printStackTrace();
									}
									int stats = d.statsExist();
									if (stats == 0) {
										d.computeStats();	//if stats were computed again, save them.
										dh.saveStatsToFile(newDataID);
										try {
											dh.saveDBToFile();
											mainMenu.setVisible(true);
										} catch (IOException ex) {
											ex.printStackTrace();
										}
									}
								// This blocks processes computed statistics in dh
								int k = 30;
								// Prints report to file
								String dataIdStats = dh.saveReportToFile(newDataID, k);								
								String rows_2[] = dataIdStats.split("\n");														
								Vector<Vector<String>> dataVector_2 = new Vector<Vector<String>>();
								Vector<String> header2 = new Vector<String>(2);
								header2.add("Id");
								header2.add("Degree");
								header2.add("Product Avg");
								header2.add("Reviewer Avg");
								// Creates displayStatsFrame to displayed stats of found dataID
								JFrame displayStatsFrame = new JFrame("Display computed statistics of " + newDataID);
								displayStatsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // closes only the computed stats frame
								displayStatsFrame.setSize(1000, 400);
								displayStatsFrame.setVisible(true);
								displayStatsFrame.setLocationRelativeTo(null); // Makes window appear at the center of the screen
								// Creates JText Area to display computed statistics of the picked dataID
								displayStatsFrame.setLayout(new BorderLayout());
								//Vector to hold header for displayed stats jtable
								// Gets the data from the excel file ready for the table  and saves it into a vector string					 
								for (String row2 : rows_2) {
									row2 = row2.trim();
									if (!row2.contains("Id,degree,product avg,reviewer avg")) {
										Vector<String> data_2 = new Vector<String>();
										data_2.addAll(Arrays.asList(row2.split(",")));
										dataVector_2.add(data_2);
									}
								}
		
								TableModel model_2 = new DefaultTableModel(dataVector_2, header2); 
								JTable table_2 = new JTable(model_2) {
								/** isCellEditable returns false as cell not editable
								 * @param row2
								 * @param column2
								 * @return false
								 */
									@Override
									public boolean isCellEditable(int row2, int column2) {   					
										//all cells false
										  return false;
									}
								};
								table_2.setPreferredScrollableViewportSize(table_2.getPreferredSize()); // Makes the size of the table fit the Frame
								table_2.setFillsViewportHeight(true); // Makes the size of the table fit the Frame
								displayStatsFrame.add(new JScrollPane(table_2), BorderLayout.CENTER); // Adds JTable and scroller to the frame
								JScrollPane scroll = new JScrollPane(table_2, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
								// Add computed stats text to the stats frame
								displayStatsFrame.add(scroll);
								// Makes display stats frame be the perfect size to fit everything
								displayStatsFrame.setPreferredSize(displayStatsFrame.getPreferredSize());
							}
						}
					});
				} catch (IOException e) {
		
					JOptionPane.showMessageDialog(null,
							"Dataset path not found: " + e.getMessage(),
								"Error",
							JOptionPane.WARNING_MESSAGE);
		
					JOptionPane.showMessageDialog(null,
							"Please check the file and try again, exiting." ,
							"Error",
							JOptionPane.WARNING_MESSAGE);
		
					JOptionPane.showMessageDialog(null,
							"Goodbye!" ,
							"Error",
							JOptionPane.WARNING_MESSAGE);
					System.exit(0);
		
				}
				
			}
		});
		
	}
}
