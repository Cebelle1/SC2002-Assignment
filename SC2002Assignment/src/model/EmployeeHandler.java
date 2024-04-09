package model;

import model.abstracts.AEmployee;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.function.Predicate;

public class EmployeeHandler {
    private List<AEmployee> employees;
    private static List<AEmployee> allEmployees; // All employees, unsorted (Static for now... not sure if need to
                                                 // change)
    private EmployeeFilter employeeFilter = new EmployeeFilter(employees); // Not tested
    private String role;

    public EmployeeHandler(String role, List<AEmployee> employees) {
        this.employees = employees;
        this.role = role;
    }

    public List<AEmployee> getAllEmployeesByRole() {
        return employees;
    }

    public static List<AEmployee> getAllUnsortedEmployees() {
        return allEmployees;
    }

    public void addEmployee(AEmployee employee) {
        employees.add(employee);
    }

    public String getRole() {
        return this.role;
    }

    // ========Not Yet Tested==================

    public static void setAllEmployees(List<AEmployee> allEmps) {
        allEmployees = allEmps;
    }

    public boolean removeEmployee(String employeeID) {
        return employees.removeIf(employee -> employee.getStaffID().equals(employeeID));
    }

    public boolean editEmployee(String employeeID, AEmployee newEmployee) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getStaffID().equals(employeeID)) {
                employees.set(i, newEmployee);
                return true;
            }
        }
        return false;
    }

    public List<AEmployee> getEmployeesByBranch(String branch) {
        return employeeFilter.filterEmployeesByBranch(branch);
    }

    public Collection<Branch> getEmployees() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEmployees'");
    }

}
