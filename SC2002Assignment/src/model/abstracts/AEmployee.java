package model.abstracts;


/**
 * A parent class for individual roles.
 * Contextual codes utilizes Polymorphism (Factory Pattern) to identify which object is to be 
 * created during startup loading and uses AEmployee to
 * reference to the individual roles using AEmployee objects.
 */
public abstract class AEmployee {
    private final String Name;
    private final String StaffID;
    private final String Role;      // S/M/A
    private final String Gender;    // M/F 
    private final int  Age;          // years
    private final String Branch;    // NTU/JE/JP
    private String Password;        // Password can be changed

    //Create Staff Login Instance
    public AEmployee(String Name, String StaffID, String Role, String Gender, int Age, String Branch, String Password){
        this.Name = Name;
        this.StaffID = StaffID;
        this.Role = Role;
        this.Gender = Gender;
        this.Age = Age;
        this.Branch = Branch;
        this.Password = Password;
    }

    public String getName(){
        return this.Name.trim();
    }

    public String getStaffID(){
        return this.StaffID.trim();
    }

    public String getRole(){
        return this.Role.trim();
    }

    public String getGender(){
        return this.Gender.trim();
    }

    public int getAge(){
        return this.Age;
    }

    public String getBranch(){
        return this.Branch.trim();
    }

    public String getPassword(){
        return this.Password.trim();
    }

    public void setPassword(String pw){
        this.Password = pw;
    }
}
