package model;

import model.payments.*;

import java.util.List;

import model.abstracts.AEmployee;

public class AdminRole extends AEmployee {
    private PaymentMethodFactory paymentMethodFactory = new PaymentMethodFactory();

    public AdminRole(String Name, String StaffID, String Role, String Gender, int Age, String Branch, String Password) {
        super(Name, StaffID, Role, Gender, Age, Branch, Password);
    }

    // Add your individual role method here like edit staffacc, display staff list
    // etc
    // create with i can get the fill Employee with no filter

    public List<AEmployee> EmpFilterByAge(int min, int max) {
        List<AEmployee> filter = EmployeeHandler.getAllUnsortedEmployees(); // create an object array of employees
        EmployeeFilter empfilter = new EmployeeFilter(filter); // create an object employee filter to use the class
        List<AEmployee> filterbyage = empfilter.filterEmployeesByAgeRange(min, max);
        return filterbyage;

    }

    public List<AEmployee> EmpFilterByBranch(int inputBranch) {
        String inputStrBranch;
        if (inputBranch == 1) {
            inputStrBranch = "JP";
        } else if (inputBranch == 2) {
            inputStrBranch = "JE";
        } else {
            inputStrBranch = "NTU";
        }
        List<AEmployee> filter = EmployeeHandler.getAllUnsortedEmployees(); // create an object array of employees
        EmployeeFilter empfilter = new EmployeeFilter(filter); // create an object employee filter to use the class
        List<AEmployee> filterbyBranch = empfilter.filterEmployeesByBranch(inputStrBranch);
        return filterbyBranch;
    }

    public List<AEmployee> EmpFilterByGender(String inputgender) {

        List<AEmployee> filter = EmployeeHandler.getAllUnsortedEmployees(); // create an object array of employees
        EmployeeFilter empfilter = new EmployeeFilter(filter); // create an object employee filter to use the class
        List<AEmployee> filterbyGender = empfilter.filterEmployeesByGender(inputgender);
        return filterbyGender;

    }

    public List<AEmployee> EmpFilterByRole(int inputrole) {
        String inputStrRole;
        if (inputrole == 1) {
            inputStrRole = "S";
        } else {
            inputStrRole = "M";
        }

        List<AEmployee> filter = EmployeeHandler.getAllUnsortedEmployees(); // create an object array of employees
        EmployeeFilter empfilter = new EmployeeFilter(filter); // create an object employee filter to use the class
        List<AEmployee> filterbyRole = empfilter.filterEmployeesByRole(inputStrRole);
        return filterbyRole;

    }

    // Not tested, designing.
    public void addPaymentMethod(String type) {
        IPaymentProcessor paymentProcessor = PaymentMethodFactory.createPaymentMethod("MasterCardPayment");
    }
}
