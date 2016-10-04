import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

///////////////////////////////////////////////////////////////////////////////
// Title:            Prog1-EmployeeTravelDatabase
// Files:            InteractiveDBTester.java
// Semester:         Fall 2016
//
// Author:           Alex McClain, gamcclain@wisc.edu
// CS Login:         gamcclain@wisc.edu
// Lecturer's Name:  Charles Fischer
// Lab Section:      LAB ###
///////////////////////////////////////////////////////////////////////////////

/**
 * Main class for the Employee database. Supports user input for a variety of
 * functions to manipulate the state of the database. Loads a text file to
 * populate the database upon starting.
 * @author Alex McClain
 */
public class InteractiveDBTester {
	// Instance of the database
	private static EmployeeDatabase employeeDB;
	
	/**
	 * Main method for the Employee database. Runs the main loop for user input.
	 * @param args File path for a text file containing the initial data to
	 * populate into the database.
	 */
	public static void main(String[] args) {
		// Validate that only one argument is provided
		if (args.length != 1) {
			System.out.println("Please provide input file as command-line " +
					"argument");
			return;
		}
		
		// Instantiate the employee database
		employeeDB = new EmployeeDatabase();
		
		// Open the file and parse information into the database
		File employeeFile = new File(args[0]);
		Scanner fileIn = null;
		try {
			fileIn = new Scanner(employeeFile);
			// For each line, read in the employee and all destinations
			while (fileIn.hasNextLine()) {
				/*
				 * Each line of the file has the following format:
				 * name,dest1,dest2,...,destN
				 */
				String employeeLine = fileIn.nextLine();
				String[] employeeData = employeeLine.split(",");
				String employeeName = employeeData[0].toLowerCase();
				employeeDB.addEmployee(employeeName);
				List<String> employeeDest = employeeDB.getDestinations(
						employeeName);
				// Start at i = 1 since employeeData[0] is the username
				for (int i = 1; i < employeeData.length; i++) {
					employeeDest.add(employeeData[i].toLowerCase());
				}
			}
		}
		catch (IOException e) {
			System.out.println("Error: Cannot access input file");
		}
		finally {
			fileIn.close();
		}
		
        Scanner stdin = new Scanner(System.in);  // for reading console input
        printOptions();
        boolean done = false;
        while (!done) {
            System.out.print("Enter option ( dfhisqr ): ");
            String input = stdin.nextLine();
            input = input.toLowerCase();  // convert input to lower case

            // only do something if the user enters at least one character
            if (input.length() > 0) {
                char choice = input.charAt(0);  // strip off option character
                String remainder = "";  // used to hold the remainder of input
                if (input.length() > 1) {
                    // trim off any leading or trailing spaces
                    remainder = input.substring(1).trim(); 
                }

                switch (choice) {
                
                case 'd':
                	if (employeeDB.removeDestination(remainder)) {
                		System.out.println("destination discontinued");
                	}
                	else { System.out.println("destination not found"); }
                    break;

                case 'f':
                	if (employeeDB.containsEmployee(remainder)) {
                		System.out.println(remainder + ":" + ListToString(
                				employeeDB.getDestinations(remainder), ","));
                	}
                	else { System.out.println("employee not found"); }
                    break;

                case 'h': 
                    printOptions();
                    break;

                case 'i':
                    printDatabaseInformation();
                    break;
                    
                case 's':
                    if (employeeDB.containsDestination(remainder)) {
                    	System.out.println(remainder + ":" + ListToString(
                    			employeeDB.getEmployees(remainder), ","));
                    }
                    else { System.out.println("destination not found"); }
                    break;

                case 'q':
                    done = true;
                    System.out.println("quit");
                    break;

                case 'r':
                	if (employeeDB.removeEmployee(remainder)) {
                		System.out.println("employee removed");
                	}
                	else { System.out.println("employee not found"); }
                    break;

                default:  // ignore any unknown commands
                    break;
                }
            }
        }
        
        stdin.close();
    }

    /**
     * Prints the list of command options along with a short description of
     * one.  This method should not be modified.
     */
    private static void printOptions() {
         System.out.println("d <destination> - discontinue the given <destination>");
         System.out.println("f <Employee> - find the given <Employee>");
         System.out.println("h - display this help menu");
         System.out.println("i - display information about this Employee database");
         System.out.println("s <destination> - search for the given <destination>");
         System.out.println("q - quit");
         System.out.println("r <Employee> - remove the given <Employee>");

    }
    
    /**
     * Generates a string from a list by combining all list entries with the
     * specified delimiter.
     * @param list List to convert into a string.
     * @param delimiter Delimiter to use as a separator for each list entry.
     * @return A string containing all entries from the list.
     */
    private static String ListToString(List<String> list, String delimiter) {
    	String toString = "";
    	Iterator<String> listIter = list.iterator();
    	while (listIter.hasNext()) {
    		toString += listIter.next();
    		if (listIter.hasNext()) { toString += delimiter; }
    	}
    	return toString;
    }
    
    /**
     * Calculates all database statistics and prints them to the console. This
     * is done by generating a reverse index of the Employee database indexed
     * by destination. Then, both databases are used to find totals, maximums,
     * minimums, and averages. The reverse index database does not persist and
     * is generated each time this method is called.
     */
    private static void printDatabaseInformation() {
    	/*
    	 * Instantiate variables needed for calculations.
    	 */
    	// Store the total number of employees along with the min/max number of
    	// destinations per employee 
    	int uniqueEmployees = employeeDB.size();
    	int maxDestinationsPerEmployee = 0;
    	int minDestinationsPerEmployee = 0;
    	double avgDestinationsPerEmployee = 0.0;
    	// Stores the numerator for average calculation
    	int totalEmployees = 0;
    	
    	// Store the total number of destination along with the min/max number
    	// of employees per destination and which destinations are on the most
    	// wish lists.
    	int uniqueDestinations = 0;
    	int maxEmployeesPerDestination = 0;
    	int minEmployeesPerDestination = 0;
    	double avgEmployeesPerDestination = 0.0;
    	// Stores the numerator for average calculation
    	int totalDestinations = 0;
    	
    	// Stores the list of most popular destinations
    	ArrayList<String> mostPopularDestinations = new ArrayList<String>();
    	
    	/*
    	 * Store a reverse-index database for destinations.
    	 * destList[0] stores the destination code
    	 * destList[1] stores the number of Employees with that destination
    	 */
    	ArrayList<String[]> destList = new ArrayList<String[]>();
    	
    	/*
    	 * Loop through all employees and cache reverse-index data. We also
    	 * track the destinations/Employee numbers as each wish list is
    	 * retrieved.
    	 */
    	Iterator<Employee> employeeIter = employeeDB.iterator();
    	while (employeeIter.hasNext()) {
    		Employee testEmployee = employeeIter.next();
    		List<String> testWishlist = testEmployee.getWishlist();
    		
    		// Test the wish list size for min/max, add to average calculation
    		int testNumDest = testWishlist.size();
    		totalDestinations += testWishlist.size();
    		if (testNumDest > maxDestinationsPerEmployee) {
    			maxDestinationsPerEmployee = testNumDest;
    		}
    		if (testNumDest < minDestinationsPerEmployee
    				|| minDestinationsPerEmployee == 0) { 
    			minDestinationsPerEmployee = testNumDest;
    		}
    		
    		/* 
    		 * Loop over destinations and add to reverse-index
    		 */
    		Iterator<String> destinationIter = testWishlist.iterator();
    		while (destinationIter.hasNext()) {
    			String testDestination = destinationIter.next();
    			// Check if the destination has been processed before
    			Iterator<String[]> indexIter = destList.iterator();
    			boolean destProcessed = false;
    			while (indexIter.hasNext()) {
    				String[] destAndCount = indexIter.next();
    				if (destAndCount[0].equals(testDestination)) {
    					int curCount = Integer.valueOf(destAndCount[1]);
    					destAndCount[1] = Integer.toString(++curCount);
    					destProcessed = true;
    				}
    			} // Finished searching reverse-index
    			// If not processed, add a new entry to the list
    			if (!destProcessed) {
    				String[] newDestination = {testDestination, "1"};
    				destList.add(newDestination);
    			}
    		} // Finished processing the Employee wish list
    	} // FInished processing all Employees in the database
    		
		// Use the reverse index to get destination statistics
    	uniqueDestinations = destList.size();
		Iterator<String[]> destinationIter = destList.iterator();
    	while (destinationIter.hasNext()) {
    		String[] testDestination = destinationIter.next();
    		int testNumEmp = Integer.valueOf(testDestination[1]);
    		totalEmployees += testNumEmp;
    		if (testNumEmp > maxEmployeesPerDestination) {
    			maxEmployeesPerDestination = testNumEmp;
    		}
    		if (testNumEmp < minEmployeesPerDestination
    				|| minEmployeesPerDestination == 0) {
    			minEmployeesPerDestination = testNumEmp;
    		}
    	}
    	// Loop again to find the destinations that had the max number
		destinationIter = destList.iterator();
    	while (destinationIter.hasNext()) {
    		String[] testDestination = destinationIter.next();
    		int testNumEmp = Integer.valueOf(testDestination[1]);
    		if (testNumEmp == maxEmployeesPerDestination) {
    			mostPopularDestinations.add(testDestination[0]);
    		}
    	}
    	
    	// Calculate the average values
    	avgEmployeesPerDestination = (double)totalEmployees
    			/ uniqueDestinations;
    	avgDestinationsPerEmployee = (double)totalDestinations
    			/ uniqueEmployees;
    	
    	/*
    	 * Line 1: Employee and Destination Counts
    	 */
    	System.out.println("Employees: " + uniqueEmployees + 
    			", Destinations: " + uniqueDestinations);
    	
    	/*
    	 * Line 2: Destinations per Employee
    	 */
    	System.out.printf("# of destinations/employee: most %d, least %d, " +
    			"average %.1f", maxDestinationsPerEmployee,
    			minDestinationsPerEmployee, avgDestinationsPerEmployee);
    	System.out.println("");
    	
    	/*
    	 * Line 3: Employees per Destination
    	 */
    	System.out.printf("# of employees/destination: most %d, least %d, " +
    			"average %.1f", maxEmployeesPerDestination,
    			minEmployeesPerDestination, avgEmployeesPerDestination);
    	System.out.println("");
    	
    	/*
    	 * Line 4: Most Popular Destination
    	 */
    	String displayListOfDestinations = "Most popular destination: " +
    			ListToString(mostPopularDestinations, ",");
    	System.out.printf("%s [%d]", displayListOfDestinations,
    			maxEmployeesPerDestination);
    	System.out.println("");
    }
}
