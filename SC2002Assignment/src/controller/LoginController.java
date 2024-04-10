package controller;

import java.util.List;

import controller.abstracts.AController;
import model.EmployeeHandler;
import model.ResetPassword;
import model.abstracts.AEmployee;
import view.LoginView;
import model.EmployeeFilter;

public class LoginController extends AController {
    AEmployee currentUser;
    LoginView loginView = new LoginView(this);
    AuthenticationController authentication;
    private List<EmployeeHandler> allStaffList;
    private static boolean loggedIn;

    ResetPassword reset;
    AdminController adminController;
    StaffController staffController;
    ManagerController managerController;

    public LoginController(List<EmployeeHandler> allStaffList) {
        this.authentication = new AuthenticationController(this, allStaffList); // Constructor Injection, tight coupling
                                                                                // bsince AuthC needs the dependencies
                                                                                // to func properly
        this.reset = new ResetPassword(allStaffList);
        this.allStaffList = allStaffList;
    }

    // Setter method to set staffs after loading
    public void setStaffs(List<EmployeeHandler> allStaffs) {
        this.allStaffList = allStaffs;
    }

    public void setCurrentUser(AEmployee user) {
        this.currentUser = user;
        // Set current user is only confirmed after loggin in (auth). So controller type
        // is not defined until then.
        // Yall can use polymorphism to define controller object.
    }

    public AEmployee getCurrentUser() {
        // return this.getCurrentUser();
        return this.currentUser;
    }

    // Setter method to set staffs after loading or for dynamic updates (might not
    // be needed depending on whether we allow different logins in a single session)
    public void setAllStaffList(List<EmployeeHandler> allStaffList) {
        this.allStaffList = allStaffList;
        // Re-initialize dependencies that rely on allStaffList if needed
        this.authentication = new AuthenticationController(this, allStaffList);
        this.reset = new ResetPassword(allStaffList);
    }

    // Navigation for employees
    // Choose staff option, S/M/A
    public void navigate(int page) {
        switch (page) {
            case 0:
                // System.out.println("LoginControllerTest");
                loginView.renderApp(0); // default 0
                // then from LoginView it comes to this method
                int choice = loginView.getInputInt(""); // pass nothing to prompt
                // if the choice choosen is not within 1-4 then show error
                // =====================================Filter Test For
                // Nicole===================

                // List<AEmployee> filter = EmployeeHandler.getAllUnsortedEmployees();
                // EmployeeFilter empFil = new EmployeeFilter(filter);
                // // List<AEmployee> retunedFilter =
                // // EmployeeFilter.filterEmployeesByBranch("NTU");
                // List<AEmployee> retunedFilter = EmployeeFilter.filterEmployeesByAgeRange(30,
                // 50);
                // // System.out.println(retunedFilter);
                // for (AEmployee test : retunedFilter) {
                // System.out.println(test.getName());
                // }

                // =========================================================================================
                if (choice > 4) {
                    System.out.println("Invalid Option");
                    this.navigate(0); // then navigate back to the first page again
                }
                // if not navigate to the next option staff chose
                this.navigate(choice);
                break;

            case 1: // login admin
                loggedIn = handleLogin(page);
                if (loggedIn = true) {
                    adminController = new AdminController(this.currentUser);
                    adminController.navigate(0);
                } else
                    this.navigate(0);
                break;

            case 2: // login manager
                loggedIn = handleLogin(page);
                if(loggedIn == true)
                {
                    managerController = new ManagerController(this.currentUser);
                    managerController.navigate(4);
                }
                else
                {
                    this.navigate(0);
                }
                break;

            case 3: // login staff
                loggedIn = handleLogin(page);
                if (loggedIn == true) {
                    // StaffRole staffRole = new StaffRole(currentUser.getName(),
                    // currentUser.getStaffID(), currentUser.getRole(), currentUser.getGender(),
                    // currentUser.getAge(), currentUser.getBranch(), currentUser.getPassword());
                    staffController = new StaffController(this.currentUser);
                    staffController.navigate(0);
                } else {
                    this.navigate(0);
                }
                break;

            case 4: // reset password
                // Login id
                // loginView.renderApp(1);
                String id = loginView.getInputString("Enter Staff ID: ");
                String oldPassword = loginView.getInputString("Enter Old Password:");
                boolean validAcc = authentication.checkAccExist(id, oldPassword);
                if (validAcc) {
                    String password = loginView.getInputString("Enter New Password: ");
                    String cfmPassword = loginView.getInputString("Confirm Password: ");
                    // Update staff list
                    boolean upToDate = reset.updatePass(id, password, cfmPassword);
                    loginView.updated(upToDate);
                } else {
                    loginView.displayInvalidAcc();
                    navigate(0);
                }
                break;

            case 5:
                System.exit(page);
                break;
        }

    }

    private boolean handleLogin(int page) {
        String staffRole = "";
        if (page == 1)
            staffRole = "A";
        else if (page == 2)
            staffRole = "M";
        else if (page == 3)
            staffRole = "S";
        String id = loginView.getInputString("Enter Staff ID:");
        // loginView.passwordPrompt();
        String password = loginView.getInputString("Enter Password: ");
        boolean auth = authentication.authenticate(password, id, staffRole);
        loginView.loggedInPrompt(auth);
        return auth;
    }
}
