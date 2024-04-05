package model;

import model.abstracts.AEmployee;

public class ManagerRole extends StaffRole{ 
    public ManagerRole(String Name, String StaffID, String Role, String Gender,int Age, String Branch, String Password){
        super(Name, StaffID, Role, Gender,Age, Branch, Password);
    }

    //Add your individual role method here like perform whatever staff can do, actlly alr inherited
    //for display branch staff, you can use smth like "Branch selectedBranch = branches.get(branchChoice);", tho currently the staff and manager arritbute still not added
    //if you need the OrderStatus.STATUS, import model.Order.OrderStatus;
}
