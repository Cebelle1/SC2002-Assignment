package controller;

import java.util.List;

import model.EmployeeHandler;
import model.abstracts.AEmployee;

public class AuthenticationController {
    private int loginAttempt = 3;
    private LoginController loginC;
    private List<EmployeeHandler> roleCategories;
    private boolean cd = false;

    // constructor
    public AuthenticationController(LoginController lc, List<EmployeeHandler> roleCategories) {
        this.loginC = lc;
        this.roleCategories = roleCategories;
    }

    public boolean isUnderCooldown() {
        return this.cd;
    }

    // checking if the password and staffID matches with the data
    /*
     * public boolean authenticate(String password, String id, String staffRole){
     * for (RoleCategory roleCategory : roleCategories) {
     * List<AEmployee> employees = roleCategory.getAllEmployees();
     * for (AEmployee employee : employees) {
     * if (staffRole.equals(employee.getRole()) &&
     * id.equals(employee.getStaffID()) &&
     * password.equals(employee.getPassword())) {
     * // Set current user
     * loginC.setCurrentUser(employee);
     * return true;
     * }
     * }
     * }
     * return false; // no such staff located
     * }
     */

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
                    if (staffRole.equals(employee.getRole()) &&
                            id.equals(employee.getStaffID()) &&
                            password.equals(employee.getPassword())) {
                        return employee;
                    }
                } else { // Unknown staff role, for resetting pw
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
}
