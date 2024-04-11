package model;

import model.payments.*;
import view.AdminHomePageView;
import view.BranchView;

import java.util.List;

import javax.print.DocFlavor.STRING;

import controller.AdminController;
import model.abstracts.AEmployee;

public class AdminRole extends AEmployee {
    private PaymentMethodFactory paymentMethodFactory = new PaymentMethodFactory();
    // AdminController adminController;

    public AdminRole(String Name, String StaffID, String Role, String Gender, int Age, String Branch, String Password) {
        super(Name, StaffID, Role, Gender, Age, Branch, Password);
    }

    // Add your individual role method here like edit staffacc, display staff list
    // etc
    // create with i can get the fill Employee with no filter
    // =======================================Edit (add, remove, edit) staff
    // list======================================================
    public void addStaff(AEmployee newAEmployee) {
        int checker = EmployeeDataManager.addNewStaffAccount(newAEmployee);
        if (checker == -1) {
            System.out.println("StaffID already exist");
            System.out.println("Staff Account update list status: Not Successful");
            return;
        }
        System.out.println("Staff Account update list status: Successful");

        // System.out.println("staffID already exist.");
    }

    public void removeStaff(String staffNameToRemove) {
        List<AEmployee> allAEmployees = EmpAllWithoutFilter();
        for (AEmployee employee : allAEmployees) {
            if (employee.getName().equals(staffNameToRemove)) {
                EmployeeDataManager.removeStaffAccount(staffNameToRemove);
                // System.out.println("Staff account removed from staff list");
                return;
            }
        }
        // System.out.println("Staff Name Do Not exist");
    }

    // =======================================Display filter staff
    // list======================================================
    public List<AEmployee> EmpAllWithoutFilter() {
        List<AEmployee> allAEmployees = EmployeeHandler.getAllUnsortedEmployees();
        return allAEmployees;
    }

    public List<AEmployee> EmpFilterByAge(int min, int max) {
        List<AEmployee> filter = EmployeeHandler.getAllUnsortedEmployees(); // create an object array of employees
        EmployeeFilter empfilter = new EmployeeFilter(filter); // create an object employee filter to use the class
        List<AEmployee> filterbyage = empfilter.filterEmployeesByAgeRange(min, max);  //TO DO: either change the method in class to static or change this line to non-static
        return filterbyage;

    }


    public List<AEmployee> EmpFilterByBranch(int inputBranch) {
        //TO DO: change the selection to dynamic. you need to account for new branches. refer to CustomerCon case 10 
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
        List<AEmployee> filterbyBranch = empfilter.filterEmployeesByBranch(inputStrBranch); //TO DO: either change the method in class to static or change this line to non-static
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

    // TO DO: the actual function works in your AdminController, just organize it.
    public void addPaymentMethod(String type) {
        IPaymentProcessor paymentProcessor = PaymentMethodFactory.createPaymentMethod("MasterCardPayment");
    }

    
    public void closeOpenBranch(List<Branch> branches, int branchChoice, int closeOrOpen){
        Branch selectedBranch = branches.get(branchChoice);
    
        if(closeOrOpen == 1){   //1-Open an exising, 2-Close an exising
            if(selectedBranch.canOpenBranch()){
                selectedBranch.setOperation(true);
            }else{
                System.out.println("Staff quota not met, cannot open branch");
                return;
            }
            
        }else if ( closeOrOpen == 2){
            selectedBranch.setOperation(false);
        }
        BranchDataManager.updateBranchList(selectedBranch);
    }
}
