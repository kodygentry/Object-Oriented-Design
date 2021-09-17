package project1;

import java.util.Scanner;
import java.io.IOException;

/**
 * DO NOT MODIFY: Exploratory Data Analysis of Amazon Product Reviews
 * @author tesic
 * @version 2.0
 */
public class RatingStatsApp {

	// Used to read from System's standard input
	static final Scanner CONSOLE_INPUT = new Scanner(System.in);

	public static void main(final String[] args) {

		String selection = "";

		try {
			DatasetHandler dh = new DatasetHandler();
			int dbSize = dh.getDataSets();

			System.out.println("Loading the datasets from:" + dh.getFolderPath() + System.lineSeparator());
			System.out.println(dh.printDB());
			System.out.println("     " + dbSize + " datasets available");

			final String welcomeMessage = "Choose one of the following functions:" + System.lineSeparator()
					+ "\t 1. Display computed statistics for specific dataID." + System.lineSeparator()
					+ "\t 2. Add new collection and compute statistics." + System.lineSeparator() + "\t 0. Exit program."
					+ System.lineSeparator();

			System.out.println(welcomeMessage);
			String newDataID = "";

			selection = CONSOLE_INPUT.nextLine().strip();
			while (!selection.contains("0")) {
				//dataset is processed 
				boolean found = false;
				dbSize = dh.getDataSets();
				if (selection.contains("1")) {
					if (dbSize < 1) {
						System.out.println("There is no data to select from, select another option\n");
					} else {
						System.out.println(dh.printDB());
						System.out.println("Please enter dataID from the list: \n");
						newDataID = CONSOLE_INPUT.nextLine().strip();
						if (!(dh.checkID(newDataID))) {
							System.out.print("dataID " + newDataID + " not in the current database, select another option \n");
						} else {
							found = true;
						}
					}
					// end 1
				} else if (selection.contains("2")) {
					System.out.println("Please enter new unique dataID: \n");
					newDataID = CONSOLE_INPUT.nextLine().strip();
					if (!(dh.checkID(newDataID))) {
						System.out.println("For new " + newDataID + " collection, what is the source file name?\n");
						final String input = CONSOLE_INPUT.nextLine().strip();
						boolean check = dh.addCollection(newDataID, input);
						if (check) {
							System.out.println("Collection " + newDataID + " added\n");
							found = true;
						} else {
							System.out.println("File " + input + " not found.");
							System.out.println("Try again.");
						}
					} else {
						System.out.println(newDataID + " is in the current database, displaying existing statistics.\n");
						found = true;
					}
					// end 2
				} else if (selection.contains("h")) {
					System.out.println(welcomeMessage);
					selection = CONSOLE_INPUT.nextLine().strip();
					continue;
				} // end selection

				if (found) {
					final String processStats = newDataID + ": statistics are already computed and saved \n"
							+ "Choose one of the following functions:\n\n" + "\t 3. Use existing stat data.\n"
							+ "\t 4. Process statistics again, I have new data.\n";
					var d = dh.populateCollection(newDataID);
					String rc = "3";
					if (d.hasStats()) {
						System.out.println(processStats);
						rc = CONSOLE_INPUT.nextLine().strip();
					}
					if (rc.contains("4") || !(d.hasStats())) {
						d.computeStats();
						// if stats were computed again, save them.
						dh.saveStatsToFile(newDataID);
					}

					// this blocks processes computed statistics in dh
					int k = 20;
					// prints report to file and console
					dh.saveReportToFile(newDataID, k);

					// new data saved
					dh.saveDBToFile();
				} // end if found

				System.out.println("Please enter 0 to exit or 'h' to start again.\n");
				selection = CONSOLE_INPUT.nextLine().strip();
			} // end while

		} catch (IOException e) {
			System.out.println("Dataset path not found: " + e.getMessage());
		}
		System.out.println("Goodbye!");
	}// end mail
}// end class
