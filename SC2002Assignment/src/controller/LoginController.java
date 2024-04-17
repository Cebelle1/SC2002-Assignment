package controller;

import java.util.List;

import controller.abstracts.AController;
import model.EmployeeHandler;
import model.ResetPassword;
import model.abstracts.AEmployee;
import view.LoginView;

/**
 * The Login Controller class handles the Login features of the application
 * 
 * @author Sharmilla
 * @author Shu Fang
 * @version
 */
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

    /**
     * The LoginController constructor takes in a list of EmployeeHandler objects
     * and initializes the authentication controller.
     * 
     * @param allStaffList
     */
    public LoginController(List<EmployeeHandler> allStaffList) {
        this.authentication = new AuthenticationController(this, allStaffList); // Constructor Injection, tight coupling
                                                                                // bsince AuthC needs the dependencies
                                                                                // to func properly
        this.reset = new ResetPassword(allStaffList);
        this.allStaffList = allStaffList;
    }


    /**
     * Setter function to set the current user
     * @param user The logged in user
     */
    public void setCurrentUser(AEmployee user) {
        this.currentUser = user;
        // Set current user is only confirmed after loggin in (auth). So controller type
        // is not defined until then.
        // Yall can use polymorphism to define controller object.
    }

    /**
     * Getter function to get the current logged in user
     * @return The AEmployee object of the current logged in user
     */
    public AEmployee getCurrentUser() {
        // return this.getCurrentUser();
        return this.currentUser;
    }

    /**
     * Navigates to the specified case based on user input.
     * @param page The feature to navigate to.
     * The pages are:
     *              <ul>
     *                  <li>0: Displays login choices</li>
     *                  <li>1: Login Authentication Process for Admin Role </li>
     *                  <li>2: Login Authentication Process for Manager Role </li>
                        <li>3: Login Authentication Process for Staff Role </li>
                        <li>4: Reset Password </li>
     *              </ul>
     */
    public void navigate(int page) {
        switch (page) {
            case 0:
                loginView.renderApp(0); // default 0
                // then from LoginView it comes to this method
                int choice = loginView.getInputInt(""); // pass nothing to prompt

                if (choice > 4) {
                    System.out.println("Invalid Option");
                    this.navigate(0); // then navigate back to the first page again
                }
                // if not navigate to the next option staff chose
                this.navigate(choice);
                break;

            case 1: // login admin
                loggedIn = handleLogin(page);
                if (loggedIn == true) {
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
                    managerController.navigate(0);
                }
                else
                {
                    this.navigate(0);
                }
                break;

            case 3: // login staff
                loggedIn = handleLogin(page);
                if (loggedIn == true) {
                    staffController = new StaffController(this.currentUser);
                    staffController.navigate(0);
                } else {
                    this.navigate(0);
                }
                break;

            case 4: // reset password
                // Login id
                String id = loginView.getInputString("Enter Staff ID: ").trim();
                String oldPassword = loginView.getInputString("Enter Old Password:").trim();
                boolean validAcc = authentication.checkAccExist(id, oldPassword);
                if (validAcc) {
                    String password = loginView.getInputString("Enter New Password: ").trim(); //No trailing spaces allowed.
                    String cfmPassword = loginView.getInputString("Confirm Password: ").trim();
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

    /**
     * Handles the login feature
     * @param page Selected page represents the role
     * @return Whether the login is successful
     */
    private boolean handleLogin(int page) {
        String staffRole = "";
        if (page == 1)
            staffRole = "A";
        else if (page == 2)
            staffRole = "M";
        else if (page == 3)
            staffRole = "S";
        String id = loginView.getInputString("Enter Staff ID:").trim();
        String password = loginView.getInputString("Enter Password: ").trim();
        boolean auth = authentication.authenticate(password, id, staffRole);
        loginView.loggedInPrompt(auth);
        return auth;
    }
}
