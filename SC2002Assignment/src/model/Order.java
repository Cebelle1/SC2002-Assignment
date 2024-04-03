package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.menus.MenuItem;
import model.payments.IPaymentProcessor;

public class Order implements Serializable {
    private List<MenuItem> items = new ArrayList<>();   //Menu Items in a single order
    private List<Order> orders; //Running orders
    private static List<Order> confirmedOrders = new ArrayList<>(); //Confirmed orders
    private static Order currentOrder; //Current order
    private Branch branch;  //Branch selected
    private double total = 0;
    private String diningMode = "Unselected Dining Mode";   
    private OrderStatus status;
    private int orderID;
    private static int orderIDCounter = 0; // Temp counter for generating order IDs
    
    public enum OrderStatus {
        NEW,        //Created
        ORDERING,   //Selected Dining Mode
        PENDING,    //Checked Out
        PREPARING,  //Payment confirmed
        READY_TO_PICKUP,
        COMPLETED
    }

    public Order(){
        this.orders = new ArrayList<>();
    }
    public Order(Branch branch){
        this.orders = new ArrayList<>();
        this.branch = branch;
    }

    public void newOrder(Order order){
        order.orderID = ++orderIDCounter;
        orders.add(order);
        Order.currentOrder = order;
        Order.currentOrder.status = OrderStatus.NEW;
        order.total = 0;
    }

    public List<Order> getOrders() {    //Returns all existing orders
        return orders;
    }

    public static Order getCurrentOrder() {
        return currentOrder;
    }

    public int getOrderID(){
        return this.orderID;
    }

    public double getAmount(){
       return this.total;
    }

 //==========Order Items============//   
    public void addItem(MenuItem item) {
        System.out.println("Adding item");
        System.out.println(item.getName());
        items.add(item);
        total += item.getPrice();
    }

    public String removeItem(int removeItem) {
        String itemName = items.get(removeItem).getName();
        items.remove(removeItem);
        return itemName;
    }

    public List<MenuItem> getCurrentOrderItems() {
        return items;
    }
//============Dining Mode===============//

    public static boolean setDiningMode(int diningMode){
        if(currentOrder.getOrderStatus() != OrderStatus.NEW && currentOrder.getOrderStatus() != OrderStatus.ORDERING){
           return false;
        }   
        try{
            Order currentOrder = Order.getCurrentOrder();
            currentOrder.selectDiningMode(diningMode);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    private void selectDiningMode(int dineMode) {
        
        switch (dineMode) {
            case 1: //Dine In
                this.diningMode = "Dine In";
                break;
            case 2: // Take out
                this.diningMode = "Take Away";
                break;
            default:
                System.out.println("Invalid Dining Mode");
        }
    }

    public String getDiningMode(){
        return this.diningMode;
    }
//=============== Process Order============//
    public static void showReceipt(){

    }

    public void caculateAmount(){
        double payable = 0;
        for(MenuItem item: items){
                payable += item.getPrice() * item.getQty();
        }
        this.total = payable;
    }

    public static boolean checkout(){
            //Use displayCartItems() to display cartItems\
            if (currentOrder == null){
                System.out.println("No order found");
                return false;
            }
            currentOrder.checkoutOrder();
            currentOrder.caculateAmount();
            System.out.printf("Order Status Now: %s\n", currentOrder.getOrderStatus());
            return true;
        
    }

    private boolean checkoutOrder(){
        if (status == OrderStatus.NEW){
            status = OrderStatus.PENDING;
            return true; //Successful
        }else{
            return false; //No orders to Checkout
        }
    }

    public boolean confirmOrder() {
        if (status == OrderStatus.PENDING) {
            status = OrderStatus.PREPARING;
            confirmedOrders.add(this); // Add to confirmed orders list
            return true; //Payment confirmed
        }else{
            return false;
        }
    }

    public void markReady() {   //For STAFF
        if (status == OrderStatus.PREPARING) {
            status = OrderStatus.READY_TO_PICKUP;
        }
    }

    public void markCompleted() {   //For CUSTOMER
        if (status == OrderStatus.READY_TO_PICKUP) {
            status = OrderStatus.COMPLETED;
        }
    }

    public OrderStatus getOrderStatus(){
        return this.status;
    }
}
