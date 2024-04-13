package controller;

import java.util.List;

import model.EmployeeHandler;
import model.abstracts.AEmployee;

public class AuthenticationController {
    private int loginAttempt = 3;
    private LoginController loginC;
    private List<EmployeeHandler> roleCategories;
    private boolean cd = false;
    private static final String defaultPassword = "password";

    // constructor
    public AuthenticationController(LoginController lc, List<EmployeeHandler> roleCategories) {
        this.loginC = lc;
        this.roleCategories = roleCategories;
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

    private AEmployee checkAccData(String password, String id, String staffRole) {
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
}
