package model;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import model.abstracts.AEmployee;

/**
 * The EmployeeFilter class handles the filtering of employees by various criteria.
 * 
 * @author Loo Si Hui
 * @version 1.0
 */
public class EmployeeFilter {
    private static List<AEmployee> employees;

    /**
     * The EmployeeFilter constructor takes in a list of employee
     * @param employee The list of employee
     */
    public EmployeeFilter(List<AEmployee> employee) {
        employees = employee;
    }

    /**
     * The base function that filters employees by a given criteria
     * @param predicate The predicate to filter employees by
     * @return The list of filtered employees
     */
    // Filter employees by a given criteria (e.g., branch, age, gender)
    private static List<AEmployee> filterEmployees(Predicate<AEmployee> predicate) {
        return employees.stream().filter(predicate).collect(Collectors.toList());
    }


    /**
     * The function to call to filter employees by branch
     * @param branch The branch name to filter the employees
     * @return The list of filtered employees by branch
     */
    public List<AEmployee> filterEmployeesByBranch(String branch) { // Filter employees by branch
        Predicate<AEmployee> byBranch = employee -> employee.getBranch().equals(branch);
        return filterEmployees(byBranch);
    }

    /**
     * The function to call to filter employees by age range
     * @param minAge Lower age bound
     * @param maxAge Upper age bound
     * @return The list of filtered employees by age range
     */
    public List<AEmployee> filterEmployeesByAgeRange(int minAge, int maxAge) { // Filter employees by age range
        Predicate<AEmployee> byAgeRange = employee -> employee.getAge() >= minAge && employee.getAge() <= maxAge;
        return filterEmployees(byAgeRange);
    }

    /**
     * The function to call to filter employees by gender
     * @param gender The gender to filter the employees
     * @return The list of filtered employees by gender
     */
    public List<AEmployee> filterEmployeesByGender(String gender) { // Filter employees by gender
        Predicate<AEmployee> byGender = employee -> employee.getGender().equals(gender);
        return filterEmployees(byGender);

    }

    /**
     * The function to call to filter employees by role
     * @param role The role to filter the employees
     * @return The listo f filtered employees by role
     */
    public List<AEmployee> filterEmployeesByRole(String role) { // Filter employees by role
        Predicate<AEmployee> byRole = employee -> employee.getRole().equals(role);
        return filterEmployees(byRole);
    }

}
