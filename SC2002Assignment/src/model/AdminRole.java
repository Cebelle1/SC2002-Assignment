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
    // =======================================Edit (add, remove, edit) staff list======================================================
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

    }

    // =======================================Display filter staff list======================================================
    public List<AEmployee> EmpAllWithoutFilter() {
        List<AEmployee> allAEmployees = EmployeeHandler.getAllUnsortedEmployees();
        return allAEmployees;
    }

    public List<AEmployee> EmpFilterByAge(int min, int max) {
        List<AEmployee> filter = EmployeeHandler.getAllUnsortedEmployees();
        EmployeeFilter empfilter = new EmployeeFilter(filter);
        List<AEmployee> filterbyage = empfilter.filterEmployeesByAgeRange(min, max); // TO DO: either change the
                                                                                     // method in class to static or
                                                                                     // change
                                                                                     // this line to non-static
        return filterbyage;

    }

    public List<AEmployee> EmpFilterByBranch(int inputBranch) {
        // TO DO: change the selection to dynamic. you need to account for new branches.
        // refer to CustomerCon case 10
        String inputStrBranch;

        List<Branch> branches = Branch.getAllBranches();
        inputStrBranch = branches.get(inputBranch - 1).getName();

        List<AEmployee> filter = EmployeeHandler.getAllUnsortedEmployees(); // create an object array of employees
        EmployeeFilter empfilter = new EmployeeFilter(filter); // create an object employee filter to use the class
        List<AEmployee> filterbyBranch = empfilter.filterEmployeesByBranch(inputStrBranch); // TO DO: either change the
                                                                                            // method in class to static
                                                                                            // or change this line to
                                                                                            // non-static
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
    //======================Assign Managers to each branch with quota constraints=====================================
        public void assignManagers (String staffName,int branchToAssignTo){

            List<Branch> allbranches = Branch.getAllBranches();
            String inputStrBranch = allbranches.get(branchToAssignTo - 1).getName();
            
            if (EmployeeDataManager.checkIfStaffExits(staffName) == true ){
                List<Branch> branches = Branch.getAllBranches();
                for(Branch branch : branches){
                    if( branch.getName().equals(inputStrBranch)){
                        if(branch.managerQuota()== false && branch.staffQuota() == false){ // can add the staff in
                            EmployeeDataManager.assignManagerToBranch(staffName,inputStrBranch);
                        }
                        else{
                            System.out.println("Branch "+ inputStrBranch +" has already met the quota. Staff "+ staffName+ " not assigned.");
                            return;
                        }
                    }
                }
            }
            else{
                System.out.println("Staff "+ staffName + " is not a manager or does not exist. Staff not assigned.");
            }
        }

    // =====================Promotion from staff to manager within the same branch===================================
    public void promotionStaff(String staffnameToPromote) {
        EmployeeDataManager.promoteStaffToManager(staffnameToPromote);
    }

    // ======================Transfer a staff/manager amongst branches================================================
    public void tranferStaff(String nameOfStaff, int branchToTransferTo) {

        List<Branch> allbranches = Branch.getAllBranches();
        String inputStrBranch = allbranches.get(branchToTransferTo - 1).getName();
        //check the person's branch and see if he is from that (no -> return)
        if (EmployeeDataManager.checkifStaffOrManager(nameOfStaff) == true){ //the person is a manager -> check quota before transfer
            assignManagers(nameOfStaff,branchToTransferTo);
        }
        else {
            EmployeeDataManager.transferStaffToBranch(nameOfStaff,inputStrBranch);
        }        
    }

    // ========================Payment method======================================================================
    // TO DO: the actual function works in your AdminController, just organize it.
    public void addPaymentMethod(String type) {
        IPaymentProcessor paymentProcessor = PaymentMethodFactory.createPaymentMethod("MasterCardPayment");
    }

    // ======================open/clos branch=======================================================================
    public void closeOpenBranch(List<Branch> branches, int branchChoice, int closeOrOpen) {
        Branch selectedBranch = branches.get(branchChoice);

        if (closeOrOpen == 1) { // 1-Open an exising, 2-Close an exising
            if (selectedBranch.canOpenBranch()) {
                selectedBranch.setOperation(true);
            } else {
                System.out.println("Staff quota not met, cannot open branch");
                return;
            }

        } else if (closeOrOpen == 2) {
            selectedBranch.setOperation(false);
        }
        BranchDataManager.updateBranchList(selectedBranch);
    }
}
