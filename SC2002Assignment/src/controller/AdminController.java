package controller;

import java.util.List;

import controller.abstracts.AController;
import view.AdminHomePageView;
import model.AdminRole;
import model.EmployeeHandler;
import model.StaffRole;
import model.abstracts.AEmployee;
//=====for TESTING
import model.payments.IPaymentProcessor;
import model.payments.PaymentMethodFactory;

//====
public class AdminController extends AController {
    private AdminHomePageView adminHomePageView = new AdminHomePageView(this);
    private AdminRole adminRole;
    private AEmployee currentuser;
    int inputBranch;
    int inputRole;
    String gender;

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
                // this.navigate(0); // remove this later after, this is use for testing
                // purposes!!
                break;

            case 2: // Display All Staff List in term of branch, role, gender, age
                adminHomePageView.renderApp(2);
                int choiceDisplay = adminHomePageView.getInputInt("");
                displayNavigate(choiceDisplay);
                // this.navigate(0); // remove this later after, this is use for testing
                // use Employeefilter to print according to the filter
                break;

            case 3: // Assign Managers
                break;

            case 4: // Promotion from staff to branch manager. Error handling: [when the staff is
                    // already a manager]
                String staffNameToPromote = adminHomePageView
                        .getInputString("Enter staff name to be promoted to Manager: ");
                adminRole.promotionStaff(staffNameToPromote);
                // this.navigate(0);

                break;

            case 5: // Transfer Staff
                break;

            case 6: // Edit paymnet method
                // =====Testing===
                String paymentMethod = adminHomePageView.getInputString(
                        "Input new payment method (Class name) with no space, uppercammel naming convention");
                IPaymentProcessor paymentProcessor = PaymentMethodFactory.createPaymentMethod(paymentMethod);

                break;

            case 7: // Manage Branch
                break;

        }

    }

    // =============================navigate for edit staff
    // list===================================
    public void editNavigate(int num) {
        switch (num) {
            case 1: // Add staff account if there an STAFFID already existing it will return
                    // "StaffID already exist"
                adminHomePageView.renderApp(3);
                String name = adminHomePageView.getInputString("Enter new Staff's Name: ");
                String staffID = adminHomePageView.getInputString("Enter new Staff's StaffID: ");
                String role = adminHomePageView.getInputString("Enter new Staff's Role: ");
                String gender = adminHomePageView.getInputString("Enter new Staff's Gender(M or F): ");
                int age = adminHomePageView.getInputInt("Enter new Staff's Age: ");
                String branch = adminHomePageView.getInputString("Enter new Staff's Branch: ");
                String password = "password";// default password for new users is "password"
                AEmployee newStaffAcc = new AdminRole(name, staffID, role, gender, age, branch, password);
                adminRole.addStaff(newStaffAcc);
                // this.navigate(2); // go back to edit account
                this.navigate(1);
                break;

            case 2: // Remove Staff account
                String staffNameToRemove = adminHomePageView.getInputString("Enter Staff Name to remove: ");
                adminRole.removeStaff(staffNameToRemove);
                this.navigate(1);
                break;

            case 3: // edit staff account
                String staffNameToEdit = adminHomePageView.getInputString("Enter Staff Name to edit: ");
                adminRole.removeStaff(staffNameToEdit);
                String nameUpdate = staffNameToEdit;
                String staffIDUpdate = adminHomePageView.getInputString("Enter Staff's StaffID: ");
                String roleUpdate = adminHomePageView.getInputString("Enter Staff's Role: ");
                String genderUpdate = adminHomePageView.getInputString("Enter Staff's Gender(M or F): ");
                int ageUpdate = adminHomePageView.getInputInt("Enter Staff's Age: ");
                String branchUpdate = adminHomePageView.getInputString("Enter Staff's Branch: ");
                String passwordUpdate = adminHomePageView.getInputString("Enter Staff's new password: ");// default
                                                                                                         // password
                // for new users is
                AEmployee EditStaffAcc = new AdminRole(nameUpdate, staffIDUpdate, roleUpdate, genderUpdate, ageUpdate,
                        branchUpdate, passwordUpdate);
                adminRole.addStaff(EditStaffAcc);
                this.navigate(1);
                break;

        }

    }

    // =============================navigate for display staff list with filters
    // ===================================
    public void displayNavigate(int num) {
        switch (num) {
            case 1: // branch
                while (true) {
                    inputBranch = adminHomePageView
                            .getInputInt("Select Branch Name as filter: (1)JP (2)JE (3)NTU:  ");
                    if (inputBranch > 0 && inputBranch < 4) {
                        break;
                    }
                    adminHomePageView.renderApp(10);
                }
                List<AEmployee> filterbybranch = adminRole.EmpFilterByBranch(inputBranch);
                adminHomePageView.printFilterStaff(filterbybranch); // continue coding from end, do i filter by
                                                                    // asking string or int?
                break;

            case 2: // role
                while (true) {
                    inputRole = adminHomePageView
                            .getInputInt("Select Role Name as filter: (1)Staffs (2)Managers ");
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
                    gender = adminHomePageView
                            .getInputString("Enter M for male or F for female as filter:  ");
                    if (gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F")) {
                        break;
                    }
                    adminHomePageView.renderApp(10);
                }
                List<AEmployee> filterByGender = adminRole.EmpFilterByGender(gender);
                adminHomePageView.printFilterStaff(filterByGender);
                break;

            case 4: // age
                int minAge = adminHomePageView.getInputInt("Enter Min Age as filter: ");
                int maxAge = adminHomePageView.getInputInt("Enter Max Age as filter: ");
                List<AEmployee> filterbyage = adminRole.EmpFilterByAge(minAge, maxAge);
                adminHomePageView.printFilterStaff(filterbyage);
                break;
            case 5: // print all no filter
                List<AEmployee> allAEmployees = adminRole.EmpAllWithoutFilter();
                adminHomePageView.printFilterStaff(allAEmployees); // print all emps to see the changes
        }

    }

}