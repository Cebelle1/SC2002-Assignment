package model;
import model.abstracts.AEmployee;
import java.util.List;

/**
 * The ResetPassword handles the interaction between the application and the database.
 * It is responsible for updating the passwords of the existing staff in the staff textfile.
 * 
 * @author Tey Shu Fang
 * @version 1.0
 */

public class ResetPassword {

    private List<EmployeeHandler> staffs;
    private static final String filePath = "SC2002Assignment/src/database/staff_list_with_pw.txt";
    private static final String defaultPassword = "password";

    /**
     * The ResetPassword constructor takes in a list of staffs.
     * @param staffs The list of staffs
     */

    // Constructor
    public ResetPassword(List<EmployeeHandler> staffs){
        this.staffs = staffs;
    }

    /**
     * Checks if the new password is updated to the database.
     * It is responsible for checking if the new password matches the confirm password and the new password cannot be the default password.
     * 
     * @param id    The staff ID
     * @param newPassword   The new password that the staff wants
     * @param cfmNewPassword    Confirm the password
     * @return A boolean to indicate whether the password is updated to the staff textfile
     */

    public boolean updatePass(String id, String newPassword, String cfmNewPassword) {
        // Passwords match
        if (newPassword.equals(cfmNewPassword)) {
            if(!cfmNewPassword.equals(defaultPassword)){
                for (EmployeeHandler roleCategory : staffs) {
                    List<AEmployee> employees = roleCategory.getAllEmployeesByRole(); // Retrieve the list of employees in this category
                    for (AEmployee employee : employees) {
                        // Access properties of each employee
                        if (id.equals(employee.getStaffID())) {
                            employee.setPassword(cfmNewPassword);
                        }
                    }
                }
                // Update the file
                EmployeeDataManager.updateFile(filePath, cfmNewPassword, id);
                return true;
            }
        }
        // Passwords are different or New password is "password"
        return false;
    }

}
