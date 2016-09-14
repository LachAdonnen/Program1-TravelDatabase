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
		this.checkNullParams(e);
		Employee testEmployee = this.retrieveEmployee(e);
		if (testEmployee == null) { employeeDB.add(new Employee(e)); }
		return;
	}
	
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
	
	public boolean containsEmployee(String e) {
		this.checkNullParams(e);
		return (this.retrieveEmployee(e) != null);
	}
	
	public boolean containsDestination(String d) {
		this.checkNullParams(d);
		Iterator<Employee> employeeIter = this.iterator();
		while (employeeIter.hasNext()) {
			if (employeeIter.next().getWishlist().contains(d)) { return true; }
		}
		return false;
	}
	
	public boolean hasDestination(String e, String d) {
		this.checkNullParams(e, d);
		Employee testEmployee = this.retrieveEmployee(e);
		if (testEmployee != null) {
			return testEmployee.getWishlist().contains(d);
		}
		else { return false; }
	}
	
	public List<String> getEmployees(String d) {
		this.checkNullParams(d);
		ArrayList<String> employeeList = new ArrayList<String>();
		Iterator<Employee> employeeIter = this.iterator();
		while (employeeIter.hasNext()) {
			Employee testEmployee = employeeIter.next();
			if (testEmployee.getWishlist().contains(d)) {
				employeeList.add(testEmployee.getUsername());
			}
		}
		return employeeList;
	}
	
	public List<String> getDestinations(String e) {
		this.checkNullParams(e);
		Employee testEmployee = this.retrieveEmployee(e);
		if (testEmployee != null) { return testEmployee.getWishlist(); }
		else { return null; }
	}
	
	public Iterator<Employee> iterator() {
		return employeeDB.iterator();
	}
	
	public boolean removeEmployee(String e) {
		this.checkNullParams(e);
		Employee testEmployee = this.retrieveEmployee(e);
		if (testEmployee != null) { return employeeDB.remove(testEmployee); }
		else { return false; }
	}
	
	public boolean removeDestination(String d) {
		this.checkNullParams(d);
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
	
	private void checkNullParams(Object... objs) {
		for (Object o : objs) {
			if (o == null) { throw new IllegalArgumentException(); }
		}
		return;
	}
	
}
