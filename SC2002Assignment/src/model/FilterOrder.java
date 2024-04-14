package model;

import java.util.ArrayList;
import java.util.List;


// SRP
public class FilterOrder {

    public  static List<Order> filterOrderByBranch(String branch){
        // get the confirmed orders
        List<Order> confirmedOrders = Order.getConfirmedOrders();

        // create a new array list to store the orders by staff's branch
        List<Order> ordersByBranch = new ArrayList<>();

        // iterate through the orders to get the orders from the right branch
        for(Order order : confirmedOrders){
                if(order.getBranchName().equals(branch)){
                    ordersByBranch.add(order);
                }
        }
        return ordersByBranch;
    }
    
}