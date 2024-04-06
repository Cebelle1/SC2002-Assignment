package model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import model.abstracts.AEmployee;

public class EmployeeFilter {
    private static List<AEmployee> employees;

    public EmployeeFilter(List<AEmployee> employee) {
        employees = employee;
    }

    // Filter employees by a given criteria (e.g., branch, age, gender)
    private static List<AEmployee> filterEmployees(Predicate<AEmployee> predicate) {
        return employees.stream().filter(predicate).collect(Collectors.toList());
    }

    // Filter employees by branch
    public static List<AEmployee> filterEmployeesByBranch(String branch) {
        Predicate<AEmployee> byBranch = employee -> employee.getBranch().equals(branch);
        return filterEmployees(byBranch);
    }

    // Filter employees by age range
    public static List<AEmployee> filterEmployeesByAgeRange(int minAge, int maxAge) {
        Predicate<AEmployee> byAgeRange = employee -> employee.getAge() >= minAge && employee.getAge() <= maxAge;
        return filterEmployees(byAgeRange);
    }

    // Filter employees by gender
    public List<AEmployee> filterEmployeesByGender(String gender) {
        Predicate<AEmployee> byGender = employee -> employee.getGender().equals(gender);
        return filterEmployees(byGender);

    }

    // Filter employees by role
    public List<AEmployee> filterEmployeesByRole(String role) {
        Predicate<AEmployee> byRole = employee -> employee.getRole().equals(role);
        return filterEmployees(byRole);
    }

}
