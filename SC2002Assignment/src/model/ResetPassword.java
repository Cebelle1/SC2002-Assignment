package model;
import model.abstracts.AEmployee;
import java.util.List;

public class ResetPassword {

    private List<EmployeeHandler> staffs;
    private static final String filePath = "SC2002Assignment/src/database/staff_list_with_pw.txt";
    private static final String defaultPassword = "password";

    // Constructor
    public ResetPassword(List<EmployeeHandler> staffs){
        this.staffs = staffs;
    }

    public boolean updatePass(String id, String newPassword, String cfmNewPassword) {
        // Passwords match
        if (newPassword.trim().equals(cfmNewPassword.trim())) {
            if(!cfmNewPassword.trim().equals(defaultPassword)){
                for (EmployeeHandler roleCategory : staffs) {
                    List<AEmployee> employees = roleCategory.getAllEmployeesByRole(); // Retrieve the list of employees in this category
                    for (AEmployee employee : employees) {
                        // Access properties of each employee
                        if (id.trim().equals(employee.getStaffID().trim())) {
                            employee.setPassword(cfmNewPassword.trim());
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
