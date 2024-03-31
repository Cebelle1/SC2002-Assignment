package model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import model.abstracts.AEmployee;

public class EmployeeFilter {
    private List<AEmployee> employees;

    public EmployeeFilter(List<AEmployee> employees) {
        this.employees = employees;
    }

    // Filter employees by a given criteria (e.g., branch, age, gender)
    private List<AEmployee> filterEmployees(Predicate<AEmployee> predicate) {
        return employees.stream().filter(predicate).collect(Collectors.toList());
    }

    // Filter employees by branch
    public List<AEmployee> filterEmployeesByBranch(String branch) {
        Predicate<AEmployee> byBranch = employee -> employee.getBranch().equals(branch);
        return filterEmployees(byBranch);
        //Collections.sort(allEmployees, Comparator.comparing(AEmployee::getBranch));
    }

    // Filter employees by age range
    public List<AEmployee> filterEmployeesByAgeRange(int minAge, int maxAge) {
        Predicate<AEmployee> byAgeRange = employee -> employee.getAge() >= minAge && employee.getAge() <= maxAge;
        return filterEmployees(byAgeRange);
    }

    // Filter employees by gender
    public List<AEmployee> filterEmployeesByGender(String gender) {
        Predicate<AEmployee> byGender = employee -> employee.getGender().equals(gender);
        return filterEmployees(byGender);
    }
}
