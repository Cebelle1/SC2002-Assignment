package model;
import java.util.List;

public class StaffCategory{
    
    private String Role;
    private List<Staff> staff;

    // Constructor
    public StaffCategory(String Role, List<Staff> staff) {
        this.Role = Role;
        this.staff = staff;
    }

    public List<Staff> getStaff() {
        return staff;
    }

    public String getRole() {
        return Role;
    }

    public void addStaff(Staff staffName){
        staff.add(staffName);
    }

}
