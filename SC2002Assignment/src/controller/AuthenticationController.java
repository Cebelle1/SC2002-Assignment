package controller;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import model.EmployeeHandler;
import model.abstracts.AEmployee;

/**
 * AuthenticationController class handles the authentication of the logins
 * 
 * @author Loo Si Hui
 * @version 1.0
 */
public class AuthenticationController {
    private static final int maxAttempt = 3;
    private int loginAttempt;
    private LoginController loginC;
    private List<EmployeeHandler> roleCategories;
    private boolean cd = false;
    private static final String defaultPassword = "password";

    /**
     * AuthenticationController constructor receives the login controller and the
     * list of employee handlers as dependies, and initializes the login attempt to 0.
     * 
     * @param lc
     * @param roleCategories
     */
    public AuthenticationController(LoginController lc, List<EmployeeHandler> roleCategories) {
        this.loginC = lc;
        this.roleCategories = roleCategories;
        this.loginAttempt = 0;
    }

    /**
     * A method to check if system is under cooldown due to
     * user exceeding maximum incorrect login attempts
     * 
     * @return A boolean to indicate whether system is under cooldown
     */
    public boolean isUnderCooldown() {
        return this.cd;
    }

    /**
     * Uses the checkAccData to attempt to login
     * @param password
     * @param id
     * @param staffRole
     * @return A boolean to indicate whether authentication for login was successful
     */
    public boolean authenticate(String password, String id, String staffRole) {
        AEmployee authEmployee = checkAccData(password, id, staffRole);
        if (authEmployee != null) {
            loginC.setCurrentUser(authEmployee);
            return true;
        }
        return false;

    }

    /**
     * Authentication functions that checks received input with database records to match.
     * Authentication also checks if user is on cooldown and if user is a first time login.
     * @param password
     * @param id
     * @param staffRole
     * @return AEmployee object of the logged in user
     */
    //Base Function
    private AEmployee checkAccData(String password, String id, String staffRole) {
        if(getCd()){
            System.out.println("On cooldown, please wait to try again");
            return null;
        }
        for (EmployeeHandler roleCategory : roleCategories) { // one piece to a row of roleCategories
            List<AEmployee> employees = roleCategory.getAllEmployeesByRole(); 
            for (AEmployee employee : employees) {
                if (staffRole != "") { // If known staffRole, for login Auth
                    // Retrieve the staff
                    if (staffRole.equals(employee.getRole()) &&
                        id.equals(employee.getStaffID()) &&
                        password.equals(employee.getPassword())) {
                            // First time login
                            if(checkFirstLogin(employee)){
                                System.out.println("Please reset password!");
                                // Navigate to reset password page
                                loginC.navigate(4);
                            }
                        return employee;
                    }

                }  
                else { // Unknown staff role, for resetting pw
                    if (id.equals(employee.getStaffID()) &&
                            password.equals(employee.getPassword())) {
                        return employee;
                    }
                }
            }
        }
        // Wrong Attempt
        if(++loginAttempt >= maxAttempt){
            System.out.println("Max login attempts reached, please wait to try again.");
            setCd(true);
            
        }
        return null; // no such staff located
    }


    /**
     * Uses the checkAccData to check whether an account exists in the database
     * 
     * @param id
     * @param password
     * @return A boolean that indicates existence of account
     */
    public boolean checkAccExist(String id, String password) {
        AEmployee authEmployee = checkAccData(password, id, "");
        if (authEmployee != null) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether user is logging in for the first time
     * @param user
     * @return A boolean to indicate whether user is logging in for the first time
     */
    private boolean checkFirstLogin(AEmployee user){
        // First time login
        if(user.getPassword().equals(defaultPassword)){
            return true;
        }
        // Not first time
        return false;
    }

    /**
     * A setter for the cooldown boolean
     * @param cd
     */
    private void setCd(boolean cd){
        this.cd = cd;
        if(cd == true){
            startCdTimer();
        }
        
    }

    /**
     * Start the cool down timer of 10 seconds
     */
    private void startCdTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // This code runs after the cooldown period (30 seconds in this case)
                System.out.println("Cooldown completed!");
                setCd(false);
            }
        }, 10000); // 30 seconds in milliseconds (30,000 milliseconds = 30 seconds)
    }

    /**
     * Getter for the cool down
     * @return The boolean that indicates whether system is under cooldown
     */
    private boolean getCd(){
        return this.cd;
    }
}
