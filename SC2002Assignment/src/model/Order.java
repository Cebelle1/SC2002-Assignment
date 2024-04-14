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
import view.ReceiptView;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L; // Unique identifier for serialization
    private static final String ORDERS_FILE = "SC2002Assignment/src/database/orders_serialize.txt"; // File name for storing orders
    
    private static List<Order> confirmedOrders = deserializeConfirmedOrders();
    private static Order currentOrder; //Current order
    
    private static int orderIDCounter = confirmedOrders.size(); // Temp counter for generating order IDs
    
    private Branch branch;  //Branch selected
    private String branchName;
    private List<Order> orders = new ArrayList<>(); //Running orders
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
        //orders = new ArrayList<>();
        orderIDCounter = confirmedOrders.size();    //indexing might cause crashes, note
        //this.branch = branch;
        this.branchName = branch.getName();
        this.orderID = ++orderIDCounter;
        this.total =0;
        this.status = OrderStatus.NEW;
        currentOrder = this;
        orders.add(this);
        addShutdownSerialize();
    }

    public List<Order> getOrders() {    //Returns all existing orders
        return orders;
    }

    public String getBranchName(){
        return this.branchName.trim();
    }

    public static List<Order> getConfirmedOrders(){
        List<Order> ordersInOpenedBranch = new ArrayList<>();
        List<Branch> openedBranches = Branch.getOpenBranches();
        
        // Extract the names of open branches
        List<String> openBranchNames = openedBranches.stream()
                                                    .map(Branch::getName)
                                                    .collect(Collectors.toList());

        // Filter orders based on open branch names
        for (Order order : confirmedOrders) {
            if (openBranchNames.contains(order.getBranchName())) {
                ordersInOpenedBranch.add(order);
            }
        }
        return ordersInOpenedBranch;

        //return confirmedOrders;
    }

    public static Order getCurrentOrder() {
        return currentOrder;
    }

    public int getOrderID(){
        return this.orderID;
    }

    public static Order getOrderById(int orderId){
        for (Order order : confirmedOrders) {
            if (order.getOrderID() == orderId) {
                return order;
            }
        }
        return null; // Order not found
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
    // public static boolean showReceipt(){
    //     //Shows only the latest paid order
    //     int lastIndex = confirmedOrders.size() - 1;
    //     Order currentOrder = confirmedOrders.get(lastIndex);

    //     if(currentOrder.getOrderStatus() != OrderStatus.PREPARING) return false;

    //     ReceiptView.printReciept(currentOrder);

    //     return true;

    // }

    public static Order showReceipt(){
        //Shows only the latest paid order
        int lastIndex = confirmedOrders.size() - 1;
        Order currentOrder = confirmedOrders.get(lastIndex);

        if(currentOrder.getOrderStatus() != OrderStatus.PREPARING) return null;

        //ReceiptView.printReciept(currentOrder);

        return currentOrder;

    }

    public void caculateAmount(){
        double payable = 0;
        for(MenuItem item: items){
                payable += item.getPrice() * item.getQty();
        }
        this.total = payable;
    }

    //=============Receipt View dummy
    public double getSubtotal(){
        return 1.2;
    }

    public double getTax(){
        return 1.2;
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
            confirmedOrders.add(currentOrder); // Add to confirmed orders list
            currentOrder = null; //Reset current order to allow customer to make another order
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
        }else{
            System.out.println("Order not ready for pickup");
        }
    }

    public OrderStatus getOrderStatus(){
        return this.status;
    }

    public void serializeConfirmedOrders() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ORDERS_FILE))) {
            oos.writeObject(confirmedOrders); // Serialize only confirmed orders
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {   //Serialize
        out.defaultWriteObject(); 

        out.writeInt(orderID);
        out.writeObject(branchName);
        out.writeDouble(total);
        out.writeObject(items);
        out.writeObject(status);
        out.writeObject(diningMode);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); // Deserialize 

        orderID = in.readInt();
        //branch = (Branch) in.readObject();
        branchName = (String) in.readObject();
        total = in.readDouble();
        items = (List<MenuItem>) in.readObject();
        status = (OrderStatus) in.readObject();
        diningMode = (String) in.readObject();
    }

    public static List<Order> deserializeConfirmedOrders() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ORDERS_FILE))) {
            List<Order> des = (List<Order>) ois.readObject();
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
