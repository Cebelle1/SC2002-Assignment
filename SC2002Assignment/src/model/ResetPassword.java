package model;
import model.abstracts.AEmployee;
import java.util.List;

public class ResetPassword {

    private List<EmployeeHandler> staffs;

    // Constructor
    public ResetPassword(List<EmployeeHandler> staffs){
        this.staffs = staffs;
    }

    public boolean updatePass(String id, String newPassword, String cfmNewPassword) {
        // Passwords match
        if (newPassword.equals(cfmNewPassword)) {
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
            EmployeeDataManager.updateFile("staff_list_with_pw.txt", cfmNewPassword, id);
            return true;
        }
        // Passwords are different
        return false;
    }

}
