package model;

import java.util.List;

import model.abstracts.AEmployee;

public class StaffRole extends AEmployee{

    private Order orders = new Order();
    private List<Order> order;
    
    public StaffRole(String Name, String StaffID, String Role, String Gender,int Age, String Branch, String Password){
        super(Name, StaffID, Role, Gender,Age, Branch, Password);
    }

    // display new orders -> only the PREPARING one
    public List<Order> displayOrders(){
        return order;
    }

    // view details
    public List<Order> viewDetails(){
        return order;
    }

    // process orders
    public List<Order> processOrder(){
        // call markReady() in Order.java after processing the order
        return order;
    }

    // filter orders by branch
    public List<Order> filterOrdersByBranch(){
        List<Order> order = orders.getOrders(); // get the orders
        for(int i=0;i<order.size();i++){ // iterate through the list
            System.out.printf("%s",order.get(i));
           // if(getBranch().equals(orders.Branch)){

            //}
        }
        return order;
    }




    //add your staff responsibility here, i.e display new order, view detail process order. 
    //for display new order, i am working on the visiblity of the orders. 
}
