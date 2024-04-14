package controller;

import java.util.List;

import controller.abstracts.AController;
import view.AdminHomePageView;
import view.BranchView;
import view.payments.PaymentView;
import model.AdminRole;
import model.Branch;
import model.BranchDataManager;
import model.EmployeeDataManager;
import model.abstracts.AEmployee;
import model.payments.IPaymentProcessor;
import model.payments.PaymentMethodFactory;


public class AdminController extends AController {
    private AdminHomePageView adminHomePageView = new AdminHomePageView(this);
    private PaymentView paymentV = new PaymentView();
    private BranchView branchV = new BranchView();
    private AdminRole adminRole;
    private AEmployee currentuser;
    int inputBranch;
    int inputRole;
    int checker;
    String gender, genderUpdate;
    String role, roleUpdate;
    int minAge,maxAge;
    int managePaymentChoice;

    public AdminController(AEmployee user) {
        this.currentuser = user;
        adminRole = new AdminRole(user.getName(), user.getStaffID(), user.getRole(), user.getGender(), user.getAge(),
                user.getBranch(), user.getPassword());

    }

    public void navigate(int page) {
        switch (page) {
            case 0:
                adminHomePageView.renderApp(0);
                int choice = adminHomePageView.getInputInt("");
                if (choice > 7) { // HARDCODED, CHANGE IF NEEDED
                    System.out.println("Invalid Option");
                    this.navigate(0);
                }
                this.navigate(choice);
                break;

            case 1: // Edit Staff Accounts
                adminHomePageView.renderApp(1);
                int choiceEdit = adminHomePageView.getInputInt("");
                editNavigate(choiceEdit);
                adminHomePageView.exitPrompt();
                this.navigate(0);
                break;

            case 2: // Display All Staff List in term of branch, role, gender, age
                adminHomePageView.renderApp(2);
                int choiceDisplay = adminHomePageView.getInputInt("");
                displayNavigate(choiceDisplay);
                this.navigate(0); 
                break;

            case 3: // Assign Managers
                String staffName = adminHomePageView.getInputString("Enter Manager name to be tranfered: ");
                int branchToAssignTo = getBranchName();
                adminRole.assignManagers(staffName,branchToAssignTo);
                adminHomePageView.exitPrompt();
                this.navigate(0);
                break;

            case 4: // Promotion
                String staffNameToPromote = adminHomePageView.getInputString("Enter staff name to be promoted to Manager: ");
                adminRole.promotionStaff(staffNameToPromote);
                adminHomePageView.exitPrompt();
                this.navigate(0);
                break;

            case 5: // Transfer Staff
                String nameOfStaff = adminHomePageView.getInputString("Enter staff/Manager name to be tranfered: ");
                int branchToTransferTo = getBranchName();
                adminRole.tranferStaff(nameOfStaff, branchToTransferTo);
                adminHomePageView.exitPrompt();
                this.navigate(0);
                break;

            case 6: // Edit paymnet method
                editPayments();
                
                break;

            case 7: // Manage Branch
                adminHomePageView.renderManageBranch();
                int manageBranchChoice = adminHomePageView.getInputInt("Choose an option");
                manageBranch(manageBranchChoice);
                adminHomePageView.exitPrompt();
                this.navigate(0);
                break;
        }



    }
    // =============================navigate for edit staff list===================================
    public void editNavigate(int num) {
        switch (num) {
            case 1: // Add staff account if there an STAFFID already existing it will return
                    // "StaffID already exist"
                adminHomePageView.renderApp(3);
                String name = adminHomePageView.getInputString("Enter new Staff's Name: ");
                String staffID = adminHomePageView.getInputString("Enter new Staff's StaffID: ");
                
                boolean validRole = false;
                while (!validRole) {
                    role = adminHomePageView.getInputString("Enter new Staff's Role: ");
                    if (role.equalsIgnoreCase("M") || role.equalsIgnoreCase("S")|| role.equalsIgnoreCase("A")) {
                        validRole = true;
                        role = role.toUpperCase();
                    } else {
                        adminHomePageView.renderApp(10); // Render error message
                    }
                }

                boolean validGender = false;
                while (!validGender) {
                    gender = adminHomePageView.getInputString("Enter new Staff's Gender (M or F): ");
                    if (gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F")) {
                        validGender = true;
                        gender = gender.toUpperCase();
                    } else {
                        adminHomePageView.renderApp(10); // Render error message
                    }
                }

                int age = adminHomePageView.getInputInt("Enter new Staff's Age: ");
                
                while (true) {
                    List<Branch> branches = Branch.getAllBranches();
                    checker = branchV.displayAllBranchForAccount(branches);
                    inputBranch = adminHomePageView.getInputInt("Enter branch  for new Staff: ");
                    if (inputBranch > 0 && inputBranch < (checker + 1)) {
                        break;
                    }
                    adminHomePageView.renderApp(10);
                }           
                List<Branch> branches = Branch.getAllBranches();
                String branch = branches.get(inputBranch - 1).getName();
                String password = "password";// default password for new users is "password"
                AEmployee newStaffAcc = new AdminRole(name, staffID, role, gender, age, branch, password);
                adminRole.addStaff(newStaffAcc);
                break;

            case 2: // Remove Staff account
                String staffNameToRemove = adminHomePageView.getInputString("Enter Staff Name to remove: ");
                adminRole.removeStaff(staffNameToRemove);
                break;

            case 3: // edit staff account
                String staffNameToEdit = adminHomePageView.getInputString("Enter Staff Name to edit: ");
                adminRole.removeStaff(staffNameToEdit);
                String nameUpdate = staffNameToEdit;
                String staffIDUpdate = adminHomePageView.getInputString("Enter Staff's StaffID: ");

                boolean validrole1 = false;
                while(!validrole1){
                    String roleUpdate = adminHomePageView.getInputString("Enter new Staff's Role: ");
                    if (roleUpdate.equalsIgnoreCase("M") || roleUpdate.equalsIgnoreCase("S")|| roleUpdate.equalsIgnoreCase("A")){
                        roleUpdate =roleUpdate.toUpperCase();
                        validrole1 = true;
                    }else{
                    adminHomePageView.renderApp(10);}
                }
                boolean validgender1 = false;
                while(!validgender1){
                    String genderUpdate = adminHomePageView.getInputString("Enter new Staff's Gender (M or F): ");
                    if (genderUpdate.equalsIgnoreCase("M") || genderUpdate.equalsIgnoreCase("F")){
                        genderUpdate = genderUpdate.toUpperCase();
                        validgender1 =true;
                    }else{
                    adminHomePageView.renderApp(10);}
                }
                int ageUpdate = adminHomePageView.getInputInt("Enter Staff's Age: ");

                while (true) {
                    List<Branch> branchs = Branch.getAllBranches();
                    checker = branchV.displayAllBranchForAccount(branchs);
                    inputBranch = adminHomePageView.getInputInt("Enter branch for Staff: ");
                    if (inputBranch > 0 && inputBranch < (checker + 1)) {
                        break;
                    }
                    adminHomePageView.renderApp(10);
                }           
                List<Branch> branches1 = Branch.getAllBranches();
                String branchUpdate = branches1.get(inputBranch - 1).getName();
                String passwordUpdate = adminHomePageView.getInputString("Enter Staff's new password: ");                                                                                                         
                AEmployee EditStaffAcc = new AdminRole(nameUpdate, staffIDUpdate, roleUpdate, genderUpdate, ageUpdate,
                        branchUpdate, passwordUpdate);
                adminRole.addStaff(EditStaffAcc);
                break;
        }
    }

    // =============================navigate for display staff list with filters===================================
    public void displayNavigate(int num) {
        switch (num) {
            case 1: // branch
                while (true) {
                    List<Branch> branches = Branch.getAllBranches();
                    checker = branchV.displayAllBranch(branches);
                    inputBranch = adminHomePageView.getInputInt("Select Branch Name as filter: ");
                    if (inputBranch > 0 && inputBranch < (checker + 1)) {
                        break;
                    }
                    adminHomePageView.renderApp(10);
                }
                List<AEmployee> filterbybranch = adminRole.EmpFilterByBranch(inputBranch);
                adminHomePageView.printFilterStaff(filterbybranch);
                break;

            case 2: // role
                while (true) {
                    inputRole = adminHomePageView.getInputInt("Select Role Name as filter: (1)Staffs (2)Managers ");
                    if (inputRole > 0 && inputRole < 3) {
                        break;
                    }
                    adminHomePageView.renderApp(10);
                }
                List<AEmployee> filterbyrole = adminRole.EmpFilterByRole(inputRole);
                adminHomePageView.printFilterStaff(filterbyrole);
                break;

            case 3: // gender
                while (true) {
                    gender = adminHomePageView.getInputString("Enter gender(M/F) as filter:  ");
                    if (gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F")) {
                        gender = gender.toUpperCase();
                        break;
                    }
                    adminHomePageView.renderApp(10);
                }
                List<AEmployee> filterByGender = adminRole.EmpFilterByGender(gender);
                adminHomePageView.printFilterStaff(filterByGender);
                break;

            case 4: // age
                while (true) {
                    minAge = adminHomePageView.getInputInt("Enter Min Age as filter: ");
                    if (minAge < 0) {
                        adminHomePageView.renderApp(10); // Render error message for negative age
                    } else {
                        break; // Exit loop if input is valid
                    }
                }
                while (true) {
                    maxAge = adminHomePageView.getInputInt("Enter Max Age as filter: ");
                    if (maxAge < 0) {
                        adminHomePageView.renderApp(10); // Render error message for negative age
                    } else if (maxAge < minAge) {
                        adminHomePageView.renderApp(11); // Render error message for maxAge < minAge
                    } else {
                        break; // Exit loop if input is valid
                    }
                }
                List<AEmployee> filterbyage = adminRole.EmpFilterByAge(minAge, maxAge);
                adminHomePageView.printFilterStaff(filterbyage);
                break;
            case 5: // print all no filter
                List<AEmployee> allAEmployees = adminRole.EmpAllWithoutFilter();
                adminHomePageView.printFilterStaff(allAEmployees); // print all emps to see the changes
        }

    }

    // ===========================open/close branch=====================================================
    public void manageBranch(int choice) { // 1-Open an exising, 2-Close an exising
        List<Branch> openedClosedBranch = null;
        if (choice == 1) {  //Open
            openedClosedBranch = Branch.getClosedBranches();
        } else if (choice == 2) {   //Close
            openedClosedBranch = Branch.getOpenBranches();
        }else if (choice == 3){
            openNewBranch();
            return;
        }

        branchV.displayOpenBranch(openedClosedBranch, true);
        int branchChoice = adminHomePageView.getInputInt("Which branch do you want to close/open?");

        adminRole.closeOpenBranch(openedClosedBranch, branchChoice - 1, choice);

    }

    private void openNewBranch(){
        String branchName = adminHomePageView.getInputString("Enter Branch Name: ");
        String branchAddress = adminHomePageView.getInputString("Enter Branch Address: ");
        int staffQuota = adminHomePageView.getInputInt("Enter Staff Quota:");
        String operation = "close";
        
        EmployeeDataManager.createNewBranch(branchName, branchAddress, staffQuota, operation);

    }

    //=============================Assign Managers to Branches with quota constratits=============================
    public int getBranchName(){
        List<Branch> branches = Branch.getAllBranches();
                while(true){
                    checker = branchV.displayAllBranch(branches);
                    inputBranch = adminHomePageView.getInputInt("Select Branch to be assigned to: ");
                        if (inputBranch > 0 && inputBranch < (checker + 1)) {
                            break;
                        }
                    adminHomePageView.renderApp(10);
                }
        return inputBranch;
    }

    //============================Manage Payment=======================================================================

    private void editPayments(){
        adminHomePageView.displayManagePayment();
        boolean checker = true;
        
        while (checker){
            managePaymentChoice = adminHomePageView.getInputInt("Choice: ");
                if(managePaymentChoice>0 && managePaymentChoice <3){
                    checker=false;
                }else{
                    adminHomePageView.renderApp(10);
                }
        }
        switch (managePaymentChoice) {
            case 1: // add new payment method
                String paymentMethod = adminHomePageView.getInputString(
                "Input new payment method (Class name) with no space, UpperCammel naming convention");
                IPaymentProcessor paymentProcessor = PaymentMethodFactory.createPaymentMethod(paymentMethod);
                break;
            case 2: // remove payment method
                String paymentName = paymentV.getPaymentName(BranchDataManager.readPaymentMethods());
                BranchDataManager.removePaymentMethod(paymentName);
                break;
        }
        

    }
}