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
public class EmployeeDatabase {
	
	private ArrayList<Employee> employeeDB;

	public EmployeeDatabase() {
		employeeDB = new ArrayList<Employee>();
	}
	
	public void addEmployee(String e) {
		employeeDB.add(new Employee(e));
	}
	
	public void addDestination(String e, String d) {
		if (this.hasDestination(e, d)) { return; }
		List<String> employeeDest = this.getDestinations(e);
		employeeDest.add(d);
	}
	
	public boolean containsEmployee(String e) {
		Iterator<Employee> employeeIter = this.iterator();
		while (employeeIter.hasNext()) {
			if (employeeIter.next().getUsername().equalsIgnoreCase(e)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean containsDestination(String d) {
		Iterator<Employee> employeeIter = this.iterator();
		while (employeeIter.hasNext()) {
			List<String> employeeDest = employeeIter.next().getWishlist();
			Iterator<String> destIter = employeeDest.iterator();
			while (destIter.hasNext()) {
				if (destIter.next().equalsIgnoreCase(d)) { return true; }
			}
		}
		return false;
	}
	
	public boolean hasDestination(String e, String d) {
		if (!this.containsEmployee(e)) { return false; }
		List<String> employeeDest = this.getDestinations(e);
		Iterator<String> destIter = employeeDest.iterator();
		// Search for a match. Return false if none are found.
		while (destIter.hasNext()) {
			if (destIter.next().equalsIgnoreCase(d)) { return true; }
		}
		return false;
	}
	
	public List<String> getEmployees(String d) {
		ArrayList<String> employeeList = new ArrayList<String>();
		Iterator<Employee> employeeIter = this.iterator();
		while (employeeIter.hasNext()) {
			String employeeName = employeeIter.next().getUsername();
			if (this.hasDestination(employeeName,d)) {
				employeeList.add(employeeName);
			}
		}
		return (List<String>) employeeList;
	}
	
	public List<String> getDestinations(String e) {
		Iterator<Employee> employeeIter = this.iterator();
		while (employeeIter.hasNext()) {
			Employee testEmployee = employeeIter.next();
			if (testEmployee.getUsername().equalsIgnoreCase(e)) {
				return testEmployee.getWishlist();
			}
		}
		return null;
	}
	
	public Iterator<Employee> iterator() {
		return employeeDB.iterator();
	}
	
	public boolean removeEmployee(String e) {
		Iterator<Employee> employeeIter = this.iterator();
		while (employeeIter.hasNext()) {
			Employee testEmployee = employeeIter.next();
			if (testEmployee.getUsername().equalsIgnoreCase(e)) {
				employeeDB.remove(testEmployee);
				return true;
			}
		}
		return false;
	}
	
	public boolean removeDestination(String d) {
		boolean destRemoved = false;
		Iterator<Employee> employeeIter = this.iterator();
		while (employeeIter.hasNext()) {
			if (employeeIter.next().getWishlist().remove(d)) {
				destRemoved = true;
			}
		}
		return destRemoved;
	}
	
	public int size() {
		return employeeDB.size();
	}
	
}
