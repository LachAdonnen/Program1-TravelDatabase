import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

///////////////////////////////////////////////////////////////////////////////
// Title:            Prog1-EmployeeTravelDatabase
// Files:            EmployeeDatabase.java
// Semester:         Fall 2016
//
// Author:           Alex McClain, gamcclain@wisc.edu
// CS Login:         gamcclain@wisc.edu
// Lecturer's Name:  Charles Fischer
// Lab Section:      LAB ###
///////////////////////////////////////////////////////////////////////////////

/**
 * Manages a database of Employee objects with the ability to add, remove, and
 * retrieve Employee objects by referencing the usernames.
 * @author Alex McClain
 */
public class EmployeeDatabase {
	
	// Database object
	private ArrayList<Employee> employeeDB;

	/**
	 * Constructor for the Employee database. Instantiates a new database using
	 * an ArrayList.
	 */
	public EmployeeDatabase() {
		employeeDB = new ArrayList<Employee>();
	}
	
	/**
	 * Adds an Employee object to the data base with the given username. If the
	 * username is already in use, nothing happens.
	 * @param e The username for the new Employee.
	 */
	public void addEmployee(String e) {
		this.checkNullParams(e);
		Employee testEmployee = this.retrieveEmployee(e);
		if (testEmployee == null) { employeeDB.add(new Employee(e)); }
		return;
	}
	
	/**
	 * Adds a destination to the specified Employee's wish list.
	 * @param e The username for the Employee.
	 * @param d The destination code to be added.
	 */
	public void addDestination(String e, String d) {
		this.checkNullParams(e, d);
		Employee testEmployee = this.retrieveEmployee(e);
		if (testEmployee != null) {
			List<String> employeeDest = testEmployee.getWishlist();
			if (!employeeDest.contains(d)) { employeeDest.add(d); }
			return; // Found a matching employee, so no need to search more
		}
		// No matching employee found, so throw an exception
		else { throw new IllegalArgumentException(); }
	}
	
	/**
	 * Returns whether the specified Employee is present in the database.
	 * @param e The username for the Employee.
	 * @return Whether the Employee is present in the database.
	 */
	public boolean containsEmployee(String e) {
		this.checkNullParams(e);
		return (this.retrieveEmployee(e) != null);
	}
	
	/**
	 * Returns whether the specified destination is present for at least one
	 * Employee in the database.
	 * @param d The destination code to search for.
	 * @return Whether there is at least one Employee with the destination in
	 * their wish list.
	 */
	public boolean containsDestination(String d) {
		this.checkNullParams(d);
		Iterator<Employee> employeeIter = this.iterator();
		while (employeeIter.hasNext()) {
			if (employeeIter.next().getWishlist().contains(d)) { return true; }
		}
		return false;
	}
	
	/**
	 * Returns whether the specified Employee has the specified destination in
	 * their wish list.
	 * @param e The username for the Employee.
	 * @param d The destination code to search for.
	 * @return Whether the destination is present in this Employee's wish list.
	 */
	public boolean hasDestination(String e, String d) {
		this.checkNullParams(e, d);
		Employee testEmployee = this.retrieveEmployee(e);
		if (testEmployee != null) {
			return testEmployee.getWishlist().contains(d);
		}
		else { return false; }
	}
	
	/**
	 * Returns a list of Employees that have the specified destination in their
	 * wish list.
	 * @param d The destination code to search for.
	 * @return List of Employees with the destination in their wish list.
	 */
	public List<String> getEmployees(String d) {
		this.checkNullParams(d);
		// Stores the list of Employees to be returned
		ArrayList<String> employeeList = new ArrayList<String>();
		
		// Loop over each Employee and check for the destination
		Iterator<Employee> employeeIter = this.iterator();
		while (employeeIter.hasNext()) {
			Employee testEmployee = employeeIter.next();
			if (testEmployee.getWishlist().contains(d)) {
				employeeList.add(testEmployee.getUsername());
			}
		}
		return employeeList;
	}
	
	/**
	 * Returns the wish list of a single Employee.
	 * @param e The username for the Employee.
	 * @return A list of destinations on the Employee's wish list.
	 */
	public List<String> getDestinations(String e) {
		this.checkNullParams(e);
		Employee testEmployee = this.retrieveEmployee(e);
		if (testEmployee != null) { return testEmployee.getWishlist(); }
		else { return null; }
	}
	
	/**
	 * Returns an iterator for the Employee database.
	 * @return An iterator for the Employee database.
	 */
	public Iterator<Employee> iterator() {
		return employeeDB.iterator();
	}
	
	/**
	 * Removes an Employee from the database.
	 * @param e The username for the Employee to be removed.
	 * @return Whether the Employee was successfully removed.
	 */
	public boolean removeEmployee(String e) {
		this.checkNullParams(e);
		Employee testEmployee = this.retrieveEmployee(e);
		if (testEmployee != null) { return employeeDB.remove(testEmployee); }
		else { return false; }
	}
	
	/**
	 * Removes a destination from all Employee wish lists.
	 * @param d The destination code to be removed.
	 * @return Whether the destination was successfully removed.
	 */
	public boolean removeDestination(String d) {
		this.checkNullParams(d);
		// Stores whether the destination was removed from at least one Employee
		boolean destRemoved = false;
		
		// Loop over each Employee and attempt to remove the destination
		Iterator<Employee> employeeIter = this.iterator();
		while (employeeIter.hasNext()) {
			if (employeeIter.next().getWishlist().remove(d)) {
				destRemoved = true;
			}
		}
		return destRemoved;
	}
	
	/**
	 * Returns the current number of Employees in the database.
	 * @return The number of Employees in the database.
	 */
	public int size() {
		return employeeDB.size();
	}
	
	/**
	 * Retrieves a single Employee from the database by iterating over the list
	 * until the correct username is found.
	 * @param e The username for the Employee.
	 * @return The Employee matching the specified username.
	 */
	private Employee retrieveEmployee(String e) {
		this.checkNullParams(e);
		Iterator<Employee> employeeIter = this.iterator();
		while (employeeIter.hasNext()) {
			Employee testEmployee = employeeIter.next();
			if (testEmployee.getUsername().equalsIgnoreCase(e)) {
				return testEmployee;
			}
		}
		return null;
	}
	
	/**
	 * Checks to ensure that all parameters are non-null. Throws an exception
	 * if any of the objects entered are null.
	 * @param objs List of parameters to check.
	 */
	private void checkNullParams(Object... objs) {
		for (Object o : objs) {
			if (o == null) { throw new IllegalArgumentException(); }
		}
		return;
	}
	
}
