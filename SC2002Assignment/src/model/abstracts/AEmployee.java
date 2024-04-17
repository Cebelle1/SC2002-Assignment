package model.abstracts;

/**
 * A parent class for individual roles.
 * Contextual codes utilizes Polymorphism (Factory Pattern) to identify which object is to be 
 * created during startup loading and uses AEmployee to
 * reference to the individual roles using AEmployee objects.
 */

 /**
 * The AEmployee is an abstract class representing an employee.
 * 
 * @author Loo Si Hui
 * @version 1.0
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
    /**
     * Constructs an instance of an employee.
     *
     * @param Name     The name of the employee.
     * @param StaffID  The staff ID of the employee.
     * @param Role     The role of the employee (S - Supervisor, M - Manager, A - Assistant).
     * @param Gender   The gender of the employee (M - Male, F - Female).
     * @param Age      The age of the employee in years.
     * @param Branch   The branch of the employee (NTU, JE, JP).
     * @param Password The initial password for the employee's login.
     */
    public AEmployee(String Name, String StaffID, String Role, String Gender, int Age, String Branch, String Password){
        this.Name = Name;
        this.StaffID = StaffID;
        this.Role = Role;
        this.Gender = Gender;
        this.Age = Age;
        this.Branch = Branch;
        this.Password = Password;
    }

    /**
     * Gets the name of the employee.
     *
     * @return The name of the employee.
     */
    public String getName(){
        return this.Name.trim();
    }

    /**
     * Gets the staff ID of the employee.
     *
     * @return The staff ID of the employee.
     */
    public String getStaffID(){
        return this.StaffID.trim();
    }

    /**
     * Gets the role of the employee.
     *
     * @return The role of the employee (S - Supervisor, M - Manager, A - Assistant).
     */
    public String getRole(){
        return this.Role.trim();
    }

     /**
     * Gets the gender of the employee.
     *
     * @return The gender of the employee either Male(M) or Female(F)
     */
    public String getGender(){
        return this.Gender.trim();
    }

    /**
     * Gets the age of the employee.
     *
     * @return The age of the employee in years.
     */
    public int getAge(){
        return this.Age;
    }

    /**
     * Gets the branch of the employee.
     *
     * @return The branch of the employee (NTU, JE, JP).
     */
    public String getBranch(){
        return this.Branch.trim();
    }

    /**
     * Gets the password for the employee's login.
     *
     * @return The password for the employee's login.
     */
    public String getPassword(){
        return this.Password.trim();
    }

    /**
     * Sets the password for the employee's login.
     *
     * @param pw The new password for the employee's login.
     */
    public void setPassword(String pw){
        this.Password = pw;
    }

}
