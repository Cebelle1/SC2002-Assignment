package controller;
import java.util.List;
import java.util.Scanner;

import controller.abstracts.AController;
import model.Staff;
import model.StaffCategory;
import model.abstracts.AUser;
import view.LoginView;

public class LoginController extends AController{
    AUser currentUser;
    LoginView loginView = new LoginView(this);
    AuthenticationController authentication;
    private List<StaffCategory> staffs;
    private static boolean loggedIn;
<<<<<<< Updated upstream
=======
    ResetPassword reset;
    AdminController adminController;
    StaffController staffController;
    ManagerController managerController;
>>>>>>> Stashed changes

    public LoginController(List<StaffCategory> staffs){
        this.staffs = staffs;
        this.authentication = new AuthenticationController(this, staffs);
    }

    public AUser getCurrentUser(){
        return this.getCurrentUser();
    }

    /*public void setCurrentUser(Customer c){
        this.currentUser = c;
    }*/

    public void setCurrentUser(Staff s){
        this.currentUser = s;
    }

    //Navigation for staff
    //Choose staff option, S/M/A
    public void navigate (int page){
        switch(page){
            case 0:
                //System.out.println("LoginControllerTest");
                loginView.renderApp(0);    //default 0
                // then from LoginView it comes to this method
                int choice = super.getInputInt(""); // pass nothing to prompt
                // if the choice choosen is not within 1-4 then show error
                if(choice > 4){
                    System.out.println("Invalid Option");
                    this.navigate(0); // then navigate back to the first page again
                }
                // if not navigate to the next option staff chose
                this.navigate(choice);
                break;

            case 1: // login admin
                loggedIn = handleLogin(page);
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
                break;

            case 4: // reset password
<<<<<<< Updated upstream
=======
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
>>>>>>> Stashed changes
                break;

            case 5:
                System.exit(page);
                break;
        }

        
    }

    private boolean handleLogin(int page){
        //loginView.renderApp(1);
        String id = getInputString("Enter Staff ID:");
        //loginView.passwordPrompt();
        String password = getInputString("Enter Password: ");
        boolean auth = authentication.authenticate(password, id, page);
        loginView.loggedInPrompt(auth);
        return auth;
    }
}
