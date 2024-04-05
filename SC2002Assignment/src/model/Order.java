package model;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.menus.MenuItem;
import model.payments.IPaymentProcessor;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L; // Unique identifier for serialization
    private static final String ORDERS_FILE = "orders_serialize.txt"; // File name for storing orders
    
    private static List<Order> confirmedOrders = deserializeConfirmedOrders();
    private static Order currentOrder; //Current order
    
    private static int orderIDCounter = 0; // Temp counter for generating order IDs
    
    private Branch branch;  //Branch selected
    private List<Order> orders; //Running orders
    private double total = 0;
    private List<MenuItem> items = new ArrayList<>();   //Menu Items in a single order
    private int orderID;
    private OrderStatus status;
    private String diningMode = "Unselected Dining Mode";  

    public enum OrderStatus {
        NEW,        //Created
        ORDERING,   //Selected Dining Mode
        PENDING,    //Checked Out
        PREPARING,  //Payment confirmed
        READY_TO_PICKUP,    
        COMPLETED
    }

    public Order(){
        orders = new ArrayList<>();
        addShutdownSerialize();
    }

    public Order(Branch branch){
        orders = new ArrayList<>();
        orderIDCounter = orders.size();
        this.branch = branch;
        addShutdownSerialize();
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

    public static List<Order> getConfirmedOrders(){
        return confirmedOrders;
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
        this.status = OrderStatus.ORDERING;
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
            boolean checkedOut = currentOrder.checkoutOrder();
            if(checkedOut){
                currentOrder.caculateAmount();
                System.out.printf("Order Status Now: %s\n", currentOrder.getOrderStatus());
                return true;
            }
            return false;
            
            
        
    }

    private boolean checkoutOrder(){
        if (status == OrderStatus.ORDERING){
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
            //currentOrder = null; perhaps
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

    //====================Serialization TESTING IN PROGRESS========================

    public void serializeConfirmedOrders() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ORDERS_FILE))) {
            oos.writeObject(confirmedOrders); // Serialize only confirmed orders
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Order> deserializeConfirmedOrders() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ORDERS_FILE))) {
            List<Order> des = (List<Order>) ois.readObject();
            System.out.println(des);
            System.out.println(des.get(1).getCurrentOrderItems());
            return des; // Deserialize the list of confirmed orders
        } catch (IOException | ClassNotFoundException e) {
            if (e instanceof EOFException) {
                System.out.println("No confirmed orders to load.");
            } else {
                e.printStackTrace();
            }
            return new ArrayList<>();
        }
    }

    private void addShutdownSerialize() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            serializeConfirmedOrders(); // Serialize only confirmed orders
        }));
    }
    
    

}
