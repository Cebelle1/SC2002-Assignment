package controller;

import java.util.List;

import controller.abstracts.AController;
import view.AdminHomePageView;
import model.AdminRole;
import model.abstracts.AEmployee;
//=====for TESTING
import model.payments.IPaymentProcessor;
import model.payments.PaymentMethodFactory;

//====
public class AdminController extends AController {
    private AdminHomePageView adminHomePageView = new AdminHomePageView(this);
    private AdminRole adminRole;
    private AEmployee currentuser;

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
                filterNavigate(choiceEdit);
                break;

            case 2: // Display All Staff List in term of branch, role, gender, age
                adminHomePageView.renderApp(2);
                int choiceDisplay = adminHomePageView.getInputInt("");
                switch (choiceDisplay) {
                    case 1: // branch
                        String branch = adminHomePageView.getInputString("Please Enter Branch Name as filter: ");
                        List<AEmployee> filterbybranch = adminRole.EmpFilterByBranch(branch);
                        adminHomePageView.printFilterStaff(filterbybranch); // continue coding from end, do i filter by
                                                                            // asking string or int?
                        break;

                    case 2: // role
                        String role = adminHomePageView.getInputString("Please Enter Role Name as filter: ");
                        List<AEmployee> filterbyrole = adminRole.EmpFilterByRole(role);
                        adminHomePageView.printFilterStaff(filterbyrole);
                        break;

                    case 3: // gender
                        String gender = adminHomePageView.getInputString("Filter by M(male) or F(female):  ");
                        List<AEmployee> filterByGender = adminRole.EmpFilterByGender(gender);
                        adminHomePageView.printFilterStaff(filterByGender);
                        break;

                    case 4: // age
                        int minAge = adminHomePageView.getInputInt("Please Enter Min Age: ");
                        int maxAge = adminHomePageView.getInputInt("Please Enter Max Age: ");
                        List<AEmployee> filterbyage = adminRole.EmpFilterByAge(minAge, maxAge);
                        adminHomePageView.printFilterStaff(filterbyage);
                        break;
                }

                // use Employeefilter to print according to the filter

            case 3: // Assign Managers
                break;

            case 4: // Promotion
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

    public void filterNavigate(int num) {
        switch (num) {
            case 1: // filter by branch

            case 2: // filter by role

            case 3: // filter by gender

            case 4: // filter by age

        }

    }

}