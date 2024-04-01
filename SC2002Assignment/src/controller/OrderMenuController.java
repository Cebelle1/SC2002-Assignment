package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import controller.CustomerController;
import controller.abstracts.AController;
import model.Branch;
import model.menus.MenuItem;
import model.menus.SetMealCategory;
import view.OrderMenuView;
import model.Order;

public class OrderMenuController extends AController {
    private List<Branch> branches;
    private OrderMenuView omv;
    private int branchChoice;
    //private List<Order> orders; // List to store orders
    private Order orders;
    private CustomerController cC;

    public OrderMenuController(CustomerController cC) {
        this.cC = cC;
        this.branches = cC.getCurrentBranch();
        this.omv = cC.getCurOMV();
        this.branchChoice = cC.getBranchChoice();
        //this.orders = new Order()ArrayList<>(); // Initialize the list of orders
        this.orders = new Order(branches.get(branchChoice));
    }

    @Override
    public void navigate(int page) {
        switch (page) {
            case 0:
                omv.renderApp(0);
                int choice = getInputInt("");
                navigate(choice);
                break;
            case 1:
                // Display menu
                omv.displayOrganizedMenu(branchChoice, branches);
                String retChocie = getInputString("Press any key to return");
                navigate(0);
                break;
            case 2:
                // Edit cart
                omv.renderApp(2);
                int editCartChoice = getInputInt("Edit Cart Choice:");
                editCart(editCartChoice);
                navigate(0);
                break;
            case 3: //Dining Mode
                omv.renderApp(3);
                int diningMode = getInputInt("Select dining mode: ");
                Order currentOrder = Order.getCurrentOrder();
                currentOrder.setDiningMode(diningMode);
                navigate(0);
                break;
            case 4: //Checkout
                checkout();
                navigate(0);
                break;
            case 5: //Pay
                break;
            case 6:
                omv.renderApp(6);
                // System.out.println("printreceipt");
                break;
            case 8:
                // Navigate back to main menu
                cC.navigate(0);
                break;
        }
    }

    private void displayMenu() {    //Display the non-organized menu
        if (branchChoice >= 0 && branchChoice < branches.size()) {
            omv.displayMenu(branchChoice, branches);
        } else {
            System.out.println("Invalid branch selection.");
        }
    }

    private void editCart(int choice) {
        switch (choice) {
            case 1:
                addItemToCart();
                break;
            case 2:
                //To implement
                editOrder(); 
                break;
            case 3:
                
                removeItemFromCart();
                break;
            case 4:
                createNewOrder();
                this.navigate(2);
                break;
        }
    }

    //Might atomize this later on
    private void addItemToCart() {
        if (branchChoice < 0 || branchChoice > branches.size()-1){
            omv.displayEmptyOrderListError();
            this.navigate(2);
        }

        if (orders.getOrders().size() < 1) {
            createNewOrder();
        }

        Order currentOrder = Order.getCurrentOrder();
        omv.displayMenu(branchChoice, branches);    //shift to BranchView
        
        int menuItemIndex = getInputInt("Select menu item for Order " + (orders.getOrders().size()) + ":") - 1;
        try {
            MenuItem selectedItem = getSelectedItem(menuItemIndex);
            if (!orders.getOrders().isEmpty()) {
                if ("set meal".equals(selectedItem.getCategory())) {    //If set meal, proceed to select meal items
                    handleSetMeal(currentOrder, selectedItem);
                }else{
                    //Customize & Select Qty
                    String comments = customizeItem();
                    selectedItem.setComments(comments);
                    int qty = selectQty();
                    selectedItem.setQty(qty);
                    currentOrder.addItem(selectedItem);
                }   
                this.navigate(0);
            } else {
                System.out.println("No order created yet. Please create a new order.");
                this.navigate(2);
            }
        } catch (Exception e) {
            System.out.println("Invalid menu item selection.");
            addItemToCart();
        }
    
    }

    private void removeItemFromCart() {
        if (branchChoice >= 0 && branchChoice < branches.size() && orders.getOrders().size() > 0) {
            omv.displayAllOrder(orders);
            int editChoice = getInputInt("Select which Order to remove item from:") - 1;
            Order currentOrder = Order.getCurrentOrder();
            omv.displayOrderList(orders, editChoice);
            int removeItem = getInputInt("Select which Item to remove from order:") - 1;
            String removedItem = currentOrder.removeItem(removeItem);
            omv.displayRemoved(removedItem);

            this.navigate(0);
        } else {
            omv.displayEmptyOrderListError();
            this.navigate(2);
        }
    }
    private void editOrder() {

    }

    private void createNewOrder() {
        // Create a new order and add it to the list of orders
        Order newOrder = new Order();
        orders.newOrder(newOrder);
        System.out.println("New order created.");
    }

    public void displayCartItems() {
        omv.chooseDisplayCurrentOrder(this.orders, this);
        String exit = getInputString("Enter a key to exit"); // just a wait for enter
    }

    public void displayOrderStatus(){
        int orderID = getInputInt("Enter Order ID to check status")-1;
        omv.chooseDisplayOrderStatus(this.orders, orderID);
        String exit = getInputString("Press any key to exit");
        return;
    }

    private void checkout(){
        //Use displayCartItems() to display cartItems
        omv.displayCheckout(this.orders);
        
        List<Order> allOrders = orders.getOrders();
        for(Order o: allOrders){
            o.confirmOrder();
            System.out.printf("Order Status Now: %s\n", o.getOrderStatus());
        }
        String exit = getInputString("Enter a key to exit");
    }

//================For Add Items to Cart================================
    private void handleSetMeal(Order currentOrder, MenuItem selectedItem){
        omv.displayMains();
        int mainChoice = getInputInt("Select 1 Main for : " + selectedItem.getName());
        MenuItem selectedMain = getMainDish(mainChoice);

        omv.displayDrinks(); 
        int drinkChoice = getInputInt("Select 1 Drink:");
        MenuItem selectedDrink = getDrink(drinkChoice);

        omv.displaySides();
        int sideChoice = getInputInt("Select 1 Side:");
        MenuItem selectedSide = getSide(sideChoice);
        
        //Customize?
        String comments = customizeItem();
        int qty = selectQty();
        //Create a MenuItem Set Meal Object
        MenuItem mealItem = new MenuItem("Combo Meal", 10.99, branches.get(branchChoice).getName(),"set meal", new SetMealCategory(selectedMain, selectedSide, selectedDrink));
        mealItem.setComments(comments);
        mealItem.setQty(qty);
        currentOrder.addItem(mealItem);
    }

//==================Ad-hoc functions for easier code readability=======================

    private String customizeItem(){
        //Customize?
        omv.displayCustomizeChoice();
        String comments;
        int customizeChoice = getInputInt("Customize Order?");
        if(customizeChoice == 1){
            comments = getInputString("Enter customization instructions:");
        }else{
            comments = "None";
        }
        return comments;
    }

    private int selectQty(){
        System.out.println("Select qty for this item");
        int qty = getInputInt("Qty: ");
        return qty;
    }

//=================================================

//===============Return the menu item selected, by categories==================//   
    public MenuItem getSelectedItem(int menuIndex) {
        Branch selectedBranch = branches.get(branchChoice);
        if (menuIndex > selectedBranch.getMenu().size()) {
            System.out.println("Menu index out of range");
            return null;
        }
        return selectedBranch.getMenu().get(menuIndex);

    }

    public MenuItem getMainDish(int mainChoice) {
        Branch selectedBranch = branches.get(branchChoice);
        //Correct format of category comparison
        List<MenuItem> mainDishes = selectedBranch.getMenu().stream()
                                        .filter(item -> !item.getCategory().equals("side") && !item.getCategory().equals("drink") && !item.getCategory().equals("set meal"))
                                        .collect(Collectors.toList());

        

        while(mainChoice < 0 || mainChoice > mainDishes.size()){
            mainChoice = getInputInt("Invalid main dish selection, please select again");
        }
        System.out.printf("Main Selected: %s\n", mainDishes.get(mainChoice-1).getName());
        return mainDishes.get(mainChoice - 1); 
    }

    public MenuItem getDrink(int drinkChoice) {
        Branch selectedBranch = branches.get(branchChoice);
        List<MenuItem> menu = selectedBranch.getMenu();
        List<MenuItem> drinks = menu.stream()
                                        .filter(item -> item.getCategory().equals("drink"))
                                        .collect(Collectors.toList());
        
        while(drinkChoice < 0 || drinkChoice > drinks.size()){
            drinkChoice = getInputInt("Invalid drink selection, please select again");
        }
        System.out.printf("Drink Selected: %s\n", drinks.get(drinkChoice-1).getName());
        return drinks.get(drinkChoice - 1); 
    }

    public MenuItem getSide(int sideChoice) {
        Branch selectedBranch = branches.get(branchChoice);
        List<MenuItem> menu = selectedBranch.getMenu();
        List<MenuItem> sides = menu.stream()
                                        .filter(item -> item.getCategory().equals("side"))
                                        .collect(Collectors.toList());

        while(sideChoice < 0 || sideChoice > sides.size()){
            sideChoice = getInputInt("Invalid side dish selection, please select again");
        }
        System.out.printf("Side Selected: %s\n", sides.get(sideChoice-1).getName());
        return sides.get(sideChoice - 1); 
    }


}
