package controller;
import java.util.List;

import model.Staff;
import model.StaffCategory;

public class AuthenticationController {
    int loginAttempt = 3;
    LoginController loginC;
    private List<StaffCategory> staffs;
    private String role;
    boolean cd = false;

    // constructor
    public AuthenticationController(LoginController loginC, List<StaffCategory> staffs){
        this.loginC = loginC;
        this.staffs = staffs;
    }

    public boolean isUnderCooldown(){
        return this.cd;
    }

    // staffRole: 3 -> Staff login, 2 -> Manager, 1 -> Admin
    // checking if the password and staffID matches with the data
    public boolean authenticate(String password, String id, int staffRole){

        for (int i = 0; i < staffs.size(); i++) {
            StaffCategory staffCategory = staffs.get(i);
            String role = staffCategory.getRole(); // Retrieve the role of the staff category
            List<Staff> staffList = staffCategory.getStaff(); // Retrieve the list of staff members in this category
            
            System.out.println("Role: " + role);
            for (Staff staff : staffList){
                // Access properties of each staff member

                // admin
                if(staffRole == 1 && role.equals("A")){
                    if(id.equals(staff.getStaffID())&& password.equals(staff.getPassword())){
                        // set current user
                        loginC.setCurrentUser(staff);
                        return true;
                    }
                }

                // manager
                if(staffRole == 2 && role.equals("M")){
                    if(id.equals(staff.getStaffID())&& password.equals(staff.getPassword())){
                        // set current user
                        loginC.setCurrentUser(staff);
                        return true;
                    }
                }

                // staff
                if(staffRole == 3 && role.equals("S")){
                    if(id.equals(staff.getStaffID())&& password.equals(staff.getPassword())){
                        // set current user
                        loginC.setCurrentUser(staff);
                        return true;
                    }
                }
            }
        }
        return false; // no such staff located
    }

}