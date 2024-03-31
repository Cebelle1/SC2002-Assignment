package model;
import model.abstracts.AEmployee;

public class StaffRole extends AEmployee {
    
    public StaffRole(String Name, String StaffID, String Role, String Gender,int Age, String Branch, String Password){
        super(Name, StaffID, Role, Gender,Age, Branch, Password);
    }

    //add your staff responsibility here, i.e display new order, view detail process order. 
    //for display new order, i am working on the visiblity of the orders. 
}
