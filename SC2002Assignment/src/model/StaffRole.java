package model;

import java.util.List;

import model.abstracts.AEmployee;
import model.menus.MenuItem;
import model.menus.SetMealCategory;

public class StaffRole extends AEmployee{

    private List<Order> orders;
    //private static final String ORDERS_FILE = "SC2002Assignment/src/database/orders_serialize.txt"; // File name for storing orders

    
    public StaffRole(String Name, String StaffID, String Role, String Gender,int Age, String Branch, String Password){
        super(Name, StaffID, Role, Gender,Age, Branch, Password);
        orders = FilterOrder.filterOrderByBranch(this.getBranch());
    }

    // Just displaying the order numbers
    public boolean displayOrders(){

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

        // no orders
        if(orders.isEmpty()){
            return false;
        }

        for(Order order : orders){
            List<MenuItem> items = order.getCurrentOrderItems();
            if(orderID == (order.getOrderID())){
                for(MenuItem item : items){
                    System.out.printf("%-9s  | %-15s | %-7s |\n",orderID ,item.getName(), item.getQty()); // get the menu name and qty
                    if ("set meal".equals(item.getCategory())) {
                        SetMealCategory setMeal = (SetMealCategory) item.getSetMeal(); // type casting
                        System.out.printf("%8s > Main: %s\n", "", setMeal.getMainDish().getName());
                        System.out.printf("%8s > Side: %s\n", "", setMeal.getSideDish().getName());
                        System.out.printf("%8s > Drink: %s\n", "", setMeal.getDrink().getName());
                    }
                    System.out.printf("%8s > Comments: %s\n" , "",item.getComments()); // get the comments
                }
            }
        }

        return true;
    }

    // process orders
    public boolean processOrder(int orderID){

        for(Order order : orders){
            if(orderID == (order.getOrderID())){
                order.markReady();
                order.serializeConfirmedOrders(); // Serialize the updated order
                System.out.printf("Order " + orderID + " is now ready-to-pickup");
                return true;
            }

        }
        return false;
    }

    // check if such orderID exists first
    public boolean checkOrderID(int orderID){

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
