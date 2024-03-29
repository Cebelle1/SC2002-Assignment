package model;
import model.abstracts.AUser;

public class Staff extends AUser {
    
    public Staff(String Name, String StaffID, String Role, String Gender, String Branch, String Password){
        super(Name, StaffID, Role, Gender, Branch, Password);
    }

}
