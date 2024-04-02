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
    private String diningMode = null;   
    private OrderStatus status;
    private int orderID;
    private static int orderIDCounter = 0; // Temp counter for generating order IDs
    
    public enum OrderStatus {
        PENDING,
        PREPARING,
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
        Order.currentOrder.status = OrderStatus.PENDING;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public static Order getCurrentOrder() {
        return currentOrder;
    }

    public int getOrderID(){
        return this.orderID;
    }

 //==========Order Items============//   
    public void addItem(MenuItem item) {
        System.out.println("Adding item");
        System.out.println(item.getName());
        items.add(item);
    }

    public String removeItem(int removeItem) {
        String itemName = items.get(removeItem).getName();
        items.remove(removeItem);
        return itemName;
    }

    public List<MenuItem> getCurrentOrderItems() {
        return items;
    }
//========================================//
    public void setDiningMode(int dineMode) {
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

//=============Set Meal Selection=================//
public MenuItem getSelectedItem(int menuIndex) {
    Branch selectedBranch = branch;
    if (menuIndex > selectedBranch.getMenu().size()) {
        System.out.println("Menu index out of range");
        return null;
    }
    return selectedBranch.getMenu().get(menuIndex);

}

public MenuItem getMainDish(int mainChoice) {
    List<MenuItem> mainDishes = branch.getMenu().stream()
                                    .filter(item -> "main".equals(item.getCategory()))
                                    .collect(Collectors.toList());
    if (mainChoice < 1 || mainChoice > mainDishes.size()) {
        System.out.println("Invalid main dish selection.");
        return null;
    }
    return mainDishes.get(mainChoice - 1); 
}

public MenuItem getDrink(int drinkChoice) {
    Branch selectedBranch = this.branch;
    List<MenuItem> menu = selectedBranch.getMenu();
    List<MenuItem> drinks = menu.stream()
                                    .filter(item -> item.getCategory() != "side" && item.getCategory() != "drink" && item.getCategory() != "set meal")
                                    .collect(Collectors.toList());
    
    if (drinkChoice < 1 || drinkChoice > drinks.size()) {
        System.out.println("Invalid main dish selection.");
        return null;
    }
    
    return drinks.get(drinkChoice - 1); 
}

public MenuItem getSide(int sideChoice) {
    Branch selectedBranch = branch;
    if (sideChoice > selectedBranch.getMenu().size()) {
        System.out.println("Menu index out of range");
        return null;
    }
    return selectedBranch.getMenu().get(sideChoice);
}

    //=========Payment Example, put here first. Using loose coupling, polymorphism=============//
    private IPaymentProcessor paymentProcessor;

    public Order(IPaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public void processPayment(double amount) {
        paymentProcessor.payment(amount);
    }

    //========== Process Order============//
    public void confirmOrder() {
        if (status == OrderStatus.PENDING) {
            status = OrderStatus.PREPARING;
            confirmedOrders.add(this); // Add to confirmed orders list
        }
    }

    public void markReady() {
        if (status == OrderStatus.PREPARING) {
            status = OrderStatus.READY_TO_PICKUP;
        }
    }

    public void markCompleted() {
        if (status == OrderStatus.READY_TO_PICKUP) {
            status = OrderStatus.COMPLETED;
        }
    }

    public OrderStatus getOrderStatus(){
        return this.status;
    }
}
