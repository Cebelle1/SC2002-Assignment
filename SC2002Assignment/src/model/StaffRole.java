package model;

import java.util.ArrayList;
import java.util.List;

import controller.StaffController;
import model.abstracts.AEmployee;
import model.menus.MenuItem;
import model.menus.SetMealCategory;
import view.OrderView;

public class StaffRole extends AEmployee{

    //private Order orders = new Order();
    private List<Order> orders;
    private OrderView orderV = new OrderView();
    private StaffController staffc;
    
    public StaffRole(String Name, String StaffID, String Role, String Gender,int Age, String Branch, String Password){
        super(Name, StaffID, Role, Gender,Age, Branch, Password);
    }

    // filter orders by branch -> (1) displaying orders
    // Just displaying the order numbers
    public boolean displayOrders(){
        orders = filterOrderByBranch();

        // no orders
        if(orders.isEmpty()){
            return false;
        }

        for(Order order : orders){
            System.out.printf("Order ID: %d\n", order.getOrderID()); // print the orderID
        }
        return true;
    }


    // view details
    public boolean viewDetails(int orderID){
        orders = filterOrderByBranch();

        // no orders
        if(orders.isEmpty()){
            return false;
        }

        for(Order order : orders){
            List<MenuItem> items = order.getCurrentOrderItems();
            if(orderID == (order.getOrderID())){
                for(MenuItem item : items){
                    System.out.printf("\n%2s %s - %d\n", "",item.getName(), item.getQty()); // get the menu name and qty
                    if ("set meal".equals(item.getCategory())) {
                        SetMealCategory setMeal = (SetMealCategory) item.getSetMeal(); // type casting
                        System.out.printf("%8s > Main: %s\n", "", setMeal.getMainDish().getName());
                        System.out.printf("%8s > Side: %s\n", "", setMeal.getSideDish().getName());                            
                        System.out.printf("%8s > Drink: %s\n", "", setMeal.getDrink().getName());   
                    }
                    System.out.printf("%2s Order customizations: %s" , "",item.getComments()); // get the comments
                }
            }
        }

        return true;
    }

    // process orders
    public boolean processOrder(int orderID){
        // call markReady() in Order.java after processing the order
        orders = filterOrderByBranch();
        
        // no orders
        if(orders.isEmpty()){
            return false;
        }
        
        for(Order order : orders){
            if(order.getOrderID() == orderID){
                System.out.println(order.getOrderStatus());
                // that particular order will be marked as ready
                order.markReady();
                System.out.println(order.getOrderStatus());
                return true;
            }
        }
        return false;
    }


    // Filter orders by branch
    private  List<Order> filterOrderByBranch(){

        // get the confirmed orders
        List<Order> confirmedOrders = Order.getConfirmedOrders();

        // get the branch staff is working at
        String branch = this.getBranch();

        // create a new array list to store the orders by staff's branch
        List<Order> ordersByBranch = new ArrayList<>();

        // iterate through the orders to get the orders from the right branch
        for(Order orders : confirmedOrders){
            List<MenuItem> items = orders.getCurrentOrderItems();
            // need to get the specific branch staff is working at
                for(MenuItem item : items){
                    if(item.getBranch().equals(branch)){
                        ordersByBranch.add(orders);
                        break;
                    }
                }
        }
        return ordersByBranch;
    }

    // check if such orderID exists first
    public boolean checkOrderID(int orderID){

        orders = filterOrderByBranch();

        // no orders
        if(orders.isEmpty()){
            return false;
        }

        for(Order order : orders){
            if(orderID == order.getOrderID()){
                return true;
            }
        }

        return false; //default

    }

}
