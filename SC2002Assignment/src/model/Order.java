package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import model.menus.MenuItem;
import view.ReceiptView;

/**
 * The Order class is a class in the Model layer that represents an Order instance. The Order class implements Serializable for serialization features
 * 
 * @author Loo Si Hui
 * @version 1.0
 */
public class Order implements Serializable {
    private static final long serialVersionUID = 1L; // Unique identifier for serialization
    private static final String ORDERS_FILE = "SC2002Assignment/src/database/orders_serialize.txt"; // File name for storing orders
    private static final long CHECK_INTERVAL_MS = 30000; // 1 minute interval

    private static List<Order> confirmedOrders = deserializeConfirmedOrders();
    private static Order currentOrder; //Current order
    private static int orderIDCounter = confirmedOrders.size(); // Temp counter for generating order IDs
    
    private String branchName;
    private List<Order> orders = new ArrayList<>(); //Running orders
    private double total = 0;
    private List<MenuItem> items = new ArrayList<>();   //Menu Items in a single order
    private int orderID;
    private OrderStatus status;
    private String diningMode = "Unselected Dining Mode";
    private LocalDateTime readyToPickupTime; // Timestamp when order was marked as "Ready to Pickup"
    private static Timer timer = new Timer(); // Initialise the timer

    /**
     * The OrderStatus enumeration represents the states of an Order.
     * Statuses are:
     *                  <ul>
     *                      <li>NEW: Newly initialized order.</li>
     *                      <li>ORDERING: Selected Dining Mode</li>
     *                      <li>PENDING: Checked Out</li>
     *                      <li>PREPARING: Paid for order</li>
     *                      <li>READY_TO_PICKUP: Staff has processed order</li>
     *                      <li>COMPLETED: Customer has collected order</li>
     *                      <li>UNCOLLECTED: Customer has not collected order for a specified time</li>
     *                  </ul>
     */
    public enum OrderStatus {
        NEW,        //Created
        ORDERING,   //Selected Dining Mode
        PENDING,    //Checked Out
        PREPARING,  //Payment confirmed
        READY_TO_PICKUP,    //Staff processed
        COMPLETED,
        UNCOLLECTED  // order not collected for a certain time being
    }

    /**
     * The base constructor for Order class
     */
    public Order(){   //Not used, dont use.
    }

    /**
     * The overloaded constructor takes in a branch and creates a new order
     * It also registers a shutdown hook for Serialization
     * @param branch The branch that the order is being created in.
     */
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

    /**
     * Getter function to return orders that has not been paid
     * @return List of orders that has not been paid
     */
    public List<Order> getOrders() {    //Returns orders that has not been paid
        return orders;
    }

    /**
     * Getter function to get the Branch name the order was made in
     * @return The branch name the order was made in
     */
    public String getBranchName(){
        return this.branchName.trim();
    }

    /**
     * Getter function to get all confirmedOrders, including previous sessions.
     * Confirmed Orders are serializable and act as a bridge between Customer Orders and Staff Processing Orders
     * @return The list of confirmed orders
     */
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

    /**
     * Getter function to get the current order 
     * @return The current order
     */
    public static Order getCurrentOrder() {
        return currentOrder;
    }

    /**
     * Getter function to get the OrderID
     * @return The OrderID
     */
    public int getOrderID(){
        return this.orderID;
    }

    /**
     * Getter function to get order by OrderID
     * @param orderId
     * @return The order with the OrderID
     */
    public static Order getOrderById(int orderId){
        for (Order order : confirmedOrders) {
            if (order.getOrderID() == orderId) {
                return order;
            }
        }
        return null; // Order not found
    }

    /**
     * Getter function to get the payable of the current order
     * @return The amount payable of the current order
     */
    public double getAmount(){
       return this.total;
    }

 //==========Order Items============//   
    /**
     * Adds item to the current order
    * @param item Menu Item to add to the order
    */
    public void addItem(MenuItem item) {
        System.out.println("Adding item");
        System.out.println(item.getName());
        items.add(item);
        total += item.getPrice();
    }

    /**
     * Removes item from the current order
     * @param removeItem Menu ITem to remove from the order
     * @return The name of the item removed
     */
    public String removeItem(int removeItem) {
        String itemName = items.get(removeItem).getName();
        items.remove(removeItem);
        return itemName;
    }

    /**
     * Getter function to get the menu items of the current order
     * @return List of Menu Items in the current order
     */
    public List<MenuItem> getCurrentOrderItems() {
        return items;
    }
//============Dining Mode===============//

    /**
     * Setter function to set the dining mode of the order. Also checks whether the order is in the correct state to set the dining mode
     * Setter Injection as dining mode is selected after creation of order
     * @param diningMode
     * @return Whether dining mode is successfully set
     */
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

    /**
     * Sets the dining mode of the order
     * @param dineMode 1 for "Dine In", 2 for "Take Away"
     */
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

    /**
     * Getter function to get the dining mode of the order
     * @return The dining mode of the order
     */
    public String getDiningMode(){
        return this.diningMode;
    }
//=============== Process Order============//
    /**
     * Shows the receipt of the latest paid order
     * Also checks for order order state to be paid
     * @return Whether showing receipt is successful
     */
    public static boolean showReceipt(){
        //Shows only the latest paid order
        int lastIndex = confirmedOrders.size() - 1;
        Order currentOrder = confirmedOrders.get(lastIndex);

        if(currentOrder.getOrderStatus() != OrderStatus.PREPARING) return false;

        ReceiptView.printReciept(currentOrder);

        return true;

    }

    /**
     * Calculates the order's payable amount and updates the total attribute
     */
    public void caculateAmount(){
        double payable = 0;
        for(MenuItem item: items){
                payable += item.getPrice() * item.getQty();
        }
        this.total = payable;
    }

    //=============Receipt View dummy
    /**
     * Getter function to get the dummy subtotal of the order
     * @return The placeholder of subtotal of the order
     */
    public double getSubtotal(){
        return 1.2;
    }

    /**
     * Getter function to get the dummy tax of the order
     * @return The placeholder of subtotal of the order
     */
    public double getTax(){
        return 1.2;
    }

    /**
     * Checks out the order
     * @return Whether order has been successfully checked out
     */
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

    /**
     * Checks the order state for checkout process
     * @return Whether the order can be check out
     */
    private boolean checkoutOrder(){
        if (status == OrderStatus.ORDERING){
            status = OrderStatus.PENDING;
            return true; //Successful
        }else{
            return false; //No orders to Checkout
        }
    }

    /**
     * Sets the status of the order to PREPARING after payment
     * 
     * @return Whether order has been confirmed
     */
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

    /**
     * Setter for the order status to READY_TO_PICKUP
     * 
     * Tracks the time elapsed since the order was marked as "Ready to Pickup"
     */
    public void markReady() {   //For STAFF
        if (status == OrderStatus.PREPARING) {
            status = OrderStatus.READY_TO_PICKUP;
            // tracking time elapsed
            readyToPickupTime = LocalDateTime.now(); // Set the timestamp
        }
    }

    /**
     * Setter for the order status to COMPLETED
     */
    public void markCompleted() {   //For CUSTOMER
        if (status == OrderStatus.READY_TO_PICKUP) {
            status = OrderStatus.COMPLETED;
        }else{
            System.out.println("Order not ready for pickup");
        }
    }

    /**
     * Getter function to get the order status
     * @return The order status
     */
    public OrderStatus getOrderStatus(){
        return this.status;
    }

//=====================Serialization=================================
    /**
     * Serialization method to serialize confirm orders
     */
    public void serializeConfirmedOrders() {
        Serializer.serializeConfirmedOrders(confirmedOrders, ORDERS_FILE);
    }

    /**
     * Serialize the individual attributes
     * @param out
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream out) throws IOException {   //Serialize
        out.defaultWriteObject(); 

        out.writeInt(orderID);
        out.writeObject(branchName);
        out.writeDouble(total);
        out.writeObject(items);
        out.writeObject(status);
        out.writeObject(diningMode);
        out.writeObject(readyToPickupTime);
    }   

    /**
     * Deserialize the individual attributes
     * @param in
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); // Deserialize 

        orderID = in.readInt();
        //branch = (Branch) in.readObject();
        branchName = (String) in.readObject();
        total = in.readDouble();
        items = (List<MenuItem>) in.readObject();
        status = (OrderStatus) in.readObject();
        diningMode = (String) in.readObject();
        readyToPickupTime = (LocalDateTime) in.readObject();
    }

    /**
     * Deserialization method to deserialize confirm orders
     * @return
     */
    public static List<Order> deserializeConfirmedOrders() {
        return Serializer.deserializeConfirmedOrders(ORDERS_FILE);
    }

    /**
     * Shut down hook to intiate Serialization
     */
    private void addShutdownSerialize() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            serializeConfirmedOrders(); // Serialize only confirmed orders
        }));
    }

    //===================================Timer=====================================
    /**
     * Calls the timer every 1 minute to check for Uncollected Orders
     */
    static {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkUncollectedOrders(); // Periodically check for uncollected orders
            }
        }, 0, CHECK_INTERVAL_MS); // interval is 1 min
    }

    /**
     * Checks for uncollected orders and cancels them if they exceed the specified timeframe.
     */
    public static void checkUncollectedOrders() {
        // filter orders by READY_TO_PICKUP
        List<Order> readyToPickupOrders = FilterOrder.filterOrderByOrderStatus(OrderStatus.READY_TO_PICKUP);

        // check the current time
        LocalDateTime currentTime = LocalDateTime.now();
        for (Order order : readyToPickupOrders) {
            // pickupDeadline is 3 min after order is ready to collect
            if(order.readyToPickupTime != null ){
                LocalDateTime pickupDeadline = order.readyToPickupTime.plusMinutes(1);
                if (currentTime.isAfter(pickupDeadline)) {
                    OrderStatus status = order.getOrderStatus();
                    if (status == OrderStatus.READY_TO_PICKUP) {
                        order.status = OrderStatus.UNCOLLECTED;
                    }
                }
            }
        }
    }
    
}
