import java.io.File;
import java.io.IOException;
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
public class InteractiveDBTester {
	//TODO Need to handle null parameter values with IllegalArgumentException
	public static void main(String[] args) {

        // *** Add code for steps 1 - 3 of the main method ***
		// Validate that only one argument is provided
		if (args.length != 1) {
			System.out.println("Please provide input file as command-line " +
					"argument");
			return;
		}
		
		// Instantiate the employee database
		EmployeeDatabase employeeDB = new EmployeeDatabase();
		
		// Open the file and parse information into the database
		File employeeFile = null;
		Scanner fileIn = null;
		try {
			employeeFile = new File(args[0]);
			fileIn = new Scanner(employeeFile);
			// For each line, read in the employee and all destinations
			while (fileIn.hasNextLine()) {
				String employeeLine = fileIn.nextLine();
				String[] employeeData = employeeLine.split(",");
				String employeeName = employeeData[0].toLowerCase();
				employeeDB.addEmployee(employeeName);
				List<String> employeeDest = employeeDB.getDestinations(
						employeeName);
				for (int i = 1; i < employeeData.length; i++) {
					employeeDest.add(employeeData[i]);
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
                    // *** Add code to implement this option ***
                    break;


                case 'f':
                    // *** Add code to implement this option ***
                    break;

                case 'h': 
                    printOptions();
                    break;

                case 'i':
                    // *** Add code to implement this option ***
                    break;
                    
                case 's':
                    // *** Add code to implement this option ***
                    break;

                case 'q':
                    done = true;
                    System.out.println("quit");
                    break;

                case 'r':
                    // *** Add code to implement this option ***
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
}
