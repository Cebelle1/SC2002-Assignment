package model;

import java.util.ArrayList;
import java.util.List;

import model.Order.OrderStatus;


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

    // Filter by Order status
    public static List<Order> filterOrderByOrderStatus(OrderStatus status){

         // get the confirmed orders
        List<Order> confirmedOrders = Order.getConfirmedOrders();

        // create a new array list to store the orders by order status
        List<Order> ordersByStatus = new ArrayList<>();

        // iterate through the orders to get the right orders depending on status
        for(Order order : confirmedOrders){

                if(order.getOrderStatus() != status){
                    ordersByStatus.add(order);
                }
        }
        return ordersByStatus;

    }
    
}