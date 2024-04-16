package model;

import java.util.List;

import model.Order.OrderStatus;
import model.abstracts.AEmployee;
import model.menus.MenuItem;
import model.menus.SetMealCategory;

/**
 * StaffRole class contains the methods to handle staff responsibility in the ordering system.
 * This class extends the abstract base model class {@link AEmployee}.
 *
 * @author Sharmilla
 * @version 1.0
 */
public class StaffRole extends AEmployee{

    private List<Order> orders = FilterOrder.filterOrderByBranch(this.getBranch());

    /**
     * The constructor for StaffRole class which takes in the Staff information
     * which calls the base constructor of AEmployee class.
     * 
     * @param Name The name of the staff.
     * @param StaffID The unique identifier of each employee in the Organisation.
     * @param Role The role the staff plays in the Organisation.
     * @param Gender The gender of the staff.
     * @param Age The number of years the employee has lived??
     * @param Branch The division the staff is working at.
     * @param Password The unqiue phrase or word to login to the system.
     * 
     */
    public StaffRole(String Name, String StaffID, String Role, String Gender,int Age, String Branch, String Password){
        super(Name, StaffID, Role, Gender,Age, Branch, Password);
    }

    /**
     * Displays the new orders via their unique order ID and returns true if the order is in the branch, and false if the order is not in the branch.
     * 
     * @return true if the order is new and in the same branch as the staff is working at, false otherwise
     */
    public boolean displayOrders(){

        // no orders
        if(orders.isEmpty()){
            return false;
        }

        // get the filtered list from FilterOrder
        List<Order> filterList = FilterOrder.filterOrderByOrderBranchAndStatus(this.getBranch(), OrderStatus.PREPARING);

        for(Order order : filterList){
                System.out.printf("Order ID: %d\n", order.getOrderID()); // print the orderID
        }
        return true;
    }

    /**
     * View the details of one new order ID selected by the staff.
     * Displays the full order of that order ID with the item name and the comments from customers,
     * @param orderID a unique identifier for each order
     * @return true if the order is new and in the same branch as the staff is working at, false otherwise
     */
    public boolean viewDetails(int orderID){

        // no orders
        if(orders.isEmpty()){
            return false;
        }

        // get the filtered list from FilterOrder
        List<Order> filterList = FilterOrder.filterOrderByOrderBranchAndStatus(this.getBranch(), OrderStatus.PREPARING);

        for(Order order : filterList){
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

    /**
     * Processes the new orders from the customers
     * @param orderID a unique identifier for each order
     * @return true if the order is in the same branch, false otherwise
     */
    public boolean processOrder(int orderID){

        for(Order order : orders){
            if(orderID == (order.getOrderID())){
                System.out.printf("Order " + orderID + " has been changed from " + order.getOrderStatus());
                order.markReady();
                order.serializeConfirmedOrders(); // Serialize the updated order
                System.out.printf(" to " + order.getOrderStatus());
                return true;
            }
        }
        return false;
    }

    /**
     * Ensures that the orderID is in the same branch.
     * @param orderID a unique identifier for each order
     * @return true if the order is in the same branch, false if the order is not in the same branch or if it is empty
     */
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
