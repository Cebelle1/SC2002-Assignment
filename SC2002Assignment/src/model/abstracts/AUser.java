package model.abstracts;

/**
 * Abstract Class for Actors
 */
public abstract class AUser {
    private final String Name;
    private final String StaffID;
    private final String Role;      // S/M/A
    private final String Gender;    // M/F 
    private final String Branch;    // NTU/JE/JP
    private String Password;        // Password can be changed

    //Create Staff Login Instance
    public AUser(String Name, String StaffID, String Role, String Gender, String Branch){
        this.Name = Name;
        this.StaffID = StaffID;
        this.Role = Role;
        this.Gender = Gender;
        this.Branch = Branch;
    }

    /* Getter Methods */
    public String getName(){
        return this.Name;
    }

    public String getStaffID(){
        return this.StaffID;
    }

    public String getRole(){
        return this.Role;
    }

    public String getGender(){
        return this.Gender;
    }

    public String getBranch(){
        return this.Branch;
    }
}
