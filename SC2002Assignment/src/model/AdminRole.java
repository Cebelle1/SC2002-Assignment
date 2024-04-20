package model;

import model.payments.*;
import java.util.List;
import model.abstracts.AEmployee;

/**
 * AdminRole class contains the methods to handle admin responsibility like performing administrative tasks 
 * and access sensitive system functionalities.
 * This class extends the abstract base model class {@link AEmployee}.
 *
 * @author Nicole
 * @version 1.0
 */

public class AdminRole extends AEmployee {

    private PaymentMethodFactory paymentMethodFactory = new PaymentMethodFactory();
    
    /**
     * The constructor for AdminRole class which takes in the Admin information
     * which calls the base constructor of AEmployee class.
     * 
     * @param Name The name of the staff.
     * @param StaffID The unique identifier of each employee in the Organisation.
     * @param Role The role the staff plays in the Organisation.
     * @param Gender The gender of the staff.
     * @param Age The number of years the employee has lived??
     * @param Branch The division the staff is working at.
     * @param Password The unqiue phrase or word to login to the system.
     * 
     */
    public AdminRole(String Name, String StaffID, String Role, String Gender, int Age, String Branch, String Password) {
        super(Name, StaffID, Role, Gender, Age, Branch, Password);
    }

    
    // =======================================Edit (add, remove, edit) staff list======================================================
    /**
    * Adds a new staff member to the system.
   *
    * @param newAEmployee The new staff member to be added.
    */
    public void addStaff(AEmployee newAEmployee) {
        int checker = EmployeeDataManager.addNewStaffAccount(newAEmployee);
        if (checker == -1) {
            System.out.println("StaffID already exist");
            System.out.println("Staff Account update list status: Not Successful");
            return;
        }
        System.out.println("Staff Account update list status: Successful");
    }

    public void removeStaff(String staffNameToRemove) {

        EmployeeDataManager.removeStaffAccount(staffNameToRemove);
    }

    // =======================================Display filter staff list======================================================
    /**
     * Retrieve all the employees in the system
     * @return A list containing all employees in the system, without any specific sorting or filtering applied.
     */
    public List<AEmployee> EmpAllWithoutFilter() {
        List<AEmployee> allAEmployees = EmployeeHandler.getAllUnsortedEmployees();
        return allAEmployees;
    }


    /**
     * Retrieve all the employees applying the age filter
     * @param min
     * @param max
     * @return A list containing all employees in the system with age within the min and max age stated
     */
    public List<AEmployee> EmpFilterByAge(int min, int max) {
        List<AEmployee> filter = EmployeeHandler.getAllUnsortedEmployees();
        EmployeeFilter empfilter = new EmployeeFilter(filter);
        List<AEmployee> filterbyage = empfilter.filterEmployeesByAgeRange(min, max); 
        return filterbyage;

    }

    /**
     * Retrieve all the employees applying the Branch filter
     * @param inputBranch
     * @return A list containing all employees in the system according to the branch stated 
     */
    public List<AEmployee> EmpFilterByBranch(int inputBranch) {
        String inputStrBranch;
        List<Branch> branches = Branch.getAllBranches();
        inputStrBranch = branches.get(inputBranch - 1).getName();
        List<AEmployee> filter = EmployeeHandler.getAllUnsortedEmployees(); // create an object array of employees
        EmployeeFilter empfilter = new EmployeeFilter(filter); // create an object employee filter to use the class
        List<AEmployee> filterbyBranch = empfilter.filterEmployeesByBranch(inputStrBranch); 
        return filterbyBranch;
    }

    /**
     * Retrieve all the employees applying the Gender filter
     * @param inputgender
     * @return A list containing all employees in the system according to gender stated 
     */
    public List<AEmployee> EmpFilterByGender(String inputgender) {

        List<AEmployee> filter = EmployeeHandler.getAllUnsortedEmployees(); // create an object array of employees
        EmployeeFilter empfilter = new EmployeeFilter(filter); // create an object employee filter to use the class
        List<AEmployee> filterbyGender = empfilter.filterEmployeesByGender(inputgender);
        return filterbyGender;

    }

    /**
     * Retrieve all the employees applying the Role Filter 
     * @param inputrole
     * @return A list containing all employees in the system according to the role stated
     */
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
    /**
     *Assigns a manager to a specified branch if the branch has not reached its quota limit.
     *If the specified staff member does not exist or is not a manager, no action is taken.
     * @param staffName
     * @param branchToAssignTo
     */
    public void assignManagers (String staffName,int branchToAssignTo){

            List<Branch> allbranches = Branch.getAllBranches();
            String inputStrBranch = allbranches.get(branchToAssignTo - 1).getName();
            
            if (EmployeeDataManager.checkIfStaffExits(staffName) == true ){
                List<Branch> branches = Branch.getAllBranches();
                for(Branch branch : branches){
                    if( branch.getName().equals(inputStrBranch)){
                        if(branch.managerQuota()== false){ // can add the staff in
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
    /**
     * Promotes a staff member to manager status within the same branch.
     *
     * @param staffnameToPromote The name of the staff member to be promoted to manager.
     */
    public void promotionStaff(String staffnameToPromote) {
        EmployeeDataManager.promoteStaffToManager(staffnameToPromote);
    }

    // ======================Transfer a staff/manager amongst branches================================================
    /**
     * Transfers a staff member or manager to another branch.
     *
     * @param nameOfStaff        
     * @param branchToTransferTo 
     */
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
    /**
    * Adds a new payment method of the specified type.
    *
    * @param type The type of payment method to add.
    */
    public void addPaymentMethod(String type) {
        IPaymentProcessor paymentProcessor = PaymentMethodFactory.createPaymentMethod("MasterCardPayment");
    }

    // ======================open/close branch=======================================================================
    /**
     * Opens or closes a branch based on the specified choice of branch
     * 
     * @param branches
     * @param branchChoice
     * @param closeOrOpen
     */
    
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
