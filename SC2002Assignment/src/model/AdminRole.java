package model;

import model.abstracts.AEmployee;

public class AdminRole extends AEmployee {
    
    public AdminRole(String Name, String StaffID, String Role, String Gender,int Age, String Branch, String Password){
        super(Name, StaffID, Role, Gender,Age, Branch, Password);
    }
    
    //Add your individual role method here like edit staffacc, display staff list etc
}
