package controller;

import java.sql.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import model.EmployeeHandler;
import model.abstracts.AEmployee;

public class AuthenticationController {
    private static final int maxAttempt = 3;
    private int loginAttempt;
    private LoginController loginC;
    private List<EmployeeHandler> roleCategories;
    private boolean cd = false;
    private static final String defaultPassword = "password";

    // constructor
    public AuthenticationController(LoginController lc, List<EmployeeHandler> roleCategories) {
        this.loginC = lc;
        this.roleCategories = roleCategories;
        this.loginAttempt = 3;
    }

    public boolean isUnderCooldown() {
        return this.cd;
    }


    public boolean authenticate(String password, String id, String staffRole) {
        AEmployee authEmployee = checkAccData(password, id, staffRole);
        if (authEmployee != null) {
            loginC.setCurrentUser(authEmployee);
            return true;
        }
        return false;

    }

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
                    // Wrong Attempt
                    if(++loginAttempt >= maxAttempt){
                        System.out.println("Max login attempts reached, please wait to try again.");
                        setCd(true);
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
        return null; // no such staff located
    }

    public boolean checkAccExist(String id, String password) {
        AEmployee authEmployee = checkAccData(password, id, "");
        if (authEmployee != null) {
            return true;
        }
        return false;
    }

    private boolean checkFirstLogin(AEmployee user){
        // First time login
        if(user.getPassword().equals(defaultPassword)){
            return true;
        }
        // Not first time
        return false;
    }

    private void setCd(boolean cd){
        this.cd = cd;
        if(cd == true){
            startCdTimer();
        }
        
    }
    private void startCdTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // This code runs after the cooldown period (30 seconds in this case)
                System.out.println("Cooldown completed!");
                // Perform any action you want after the cooldown period
            }
        }, 10000); // 30 seconds in milliseconds (30,000 milliseconds = 30 seconds)
    }

    private boolean getCd(){
        return this.cd;
    }
}
