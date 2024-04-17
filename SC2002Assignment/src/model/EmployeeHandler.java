package model;

import model.abstracts.AEmployee;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.function.Predicate;

/**
 * The EmployeeHandler class responsible for managing employees and providing access to employee data.
 * 
 * @author Loo Si Hui
 * @version 1.0
 */
public class EmployeeHandler {
    private List<AEmployee> employees;
    private static List<AEmployee> allEmployees; // All employees, unsorted (Static for now... not sure if need to
                                                 // change)
    private EmployeeFilter employeeFilter = new EmployeeFilter(employees); // Not tested
    private String role;


     /**
     * Constructs an EmployeeHandler object with the specified role and list of employees.
     *
     * @param role     
     * @param employees The list of employees managed by the handler.
     */
    public EmployeeHandler(String role, List<AEmployee> employees) {
        this.employees = employees;
        this.role = role;
    }

    /**
     * Retrieves all employees associated with this handler.
     *
     * @return A list of all employees managed by this handler.
     */
    public List<AEmployee> getAllEmployeesByRole() {
        return employees;
    }


    /**
     * Retrieves all employees, unsorted.
     *
     * @return A list of all employees, unsorted.
     */
    public static List<AEmployee> getAllUnsortedEmployees() {
        return allEmployees;
    }

    /**
     * Adds an employee to the list of employees managed by this handler.
     *
     * @param employee The employee to add.
     */
    public void addEmployee(AEmployee employee) {
        employees.add(employee);
    }

    /**
     * Retrieves the role associated with this employee handler.
     *
     * @return The role associated with this employee handler.
     */
    public String getRole() {
        return this.role;
    }

     /**
     * Sets the list of all employees, unsorted.
     *
     * @param allEmps The list of all employees to set.
     */
    public static void setAllEmployees(List<AEmployee> allEmps) {
        allEmployees = allEmps;
    }

    /**
     * Removes an employee with the specified ID from the list of employees managed by this handler.
     *
     * @param employeeID 
     * @return {@code true} if the employee was removed successfully, {@code false} otherwise.
     */
    public boolean removeEmployee(String employeeID) {
        return employees.removeIf(employee -> employee.getStaffID().equals(employeeID));
    }

    /**
     * Edits the details of an employee with the specified ID.
     *
     * @param employeeID 
     * @param newEmployee 
     * @return {@code true} if the employee was edited successfully, {@code false} otherwise.
     */
    public boolean editEmployee(String employeeID, AEmployee newEmployee) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getStaffID().equals(employeeID)) {
                employees.set(i, newEmployee);
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves employees associated with a specific branch.
     *
     * @param branch The branch to filter employees by.
     * @return A list of employees associated with the specified branch.
     */
    public List<AEmployee> getEmployeesByBranch(String branch) {
        return employeeFilter.filterEmployeesByBranch(branch);
    }


     /**
     * Retrieves employees.
     *
     * @return A collection of employees managed by this handler.
     * @throws UnsupportedOperationException if the method is unimplemented. 
     */
    public Collection<Branch> getEmployees() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEmployees'");
    }

}
