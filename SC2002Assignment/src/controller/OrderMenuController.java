package controller;

import java.util.ArrayList;
import java.util.List;

import controller.abstracts.AController;
import model.Branch;
import model.Order;
import model.menus.MenuItem;
import view.OrderMenuView;

public class OrderMenuController extends AController {
    private List<Branch> branches;
    private OrderMenuView omv;
    private int branchChoice;
    private List<Order> orders; // List to store orders
    private CustomerController cC;

    public OrderMenuController(CustomerController cC) {
        this.cC = cC;
        this.branches = cC.getCurrentBranch();
        this.omv = cC.getCurOMV();
        this.branchChoice = cC.getBranchChoice();
        this.orders = new ArrayList<>(); // Initialize the list of orders
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
                displayMenu();
                break;
            case 2:
                // Edit cart
                omv.renderApp(2);
                int editCartChoice = getInputInt("Edit Cart Choice:");
                editCart(editCartChoice);
                break;
            case 3:
                omv.renderApp(3);
                int diningMode = getInputInt("Select Dining Mode");
                Order currentOrder = orders.get(orders.size() - 1);
                currentOrder.setDiningMode(diningMode);
                break;
            case 8:
                // Navigate back to main menu
                cC.navigate(0);
                break;
        }
    }

    private void displayMenu() {
        if (branchChoice >= 0 && branchChoice < branches.size()) {
            omv.displayMenu(branchChoice, branches);
        } else {
            System.out.println("Invalid branch selection.");
        }
    }

    private void editCart(int choice) {
        switch (choice) {
            case 1:
                // Add item to cart
                addItemToCart();
                break;
            case 2:
                // Edit items in cart
                editOrder();    //To edit other orders
                break;
            case 3:
                // Remove item from cart
                removeItemFromCart();
                break;
            case 4:
                createNewOrder();
                this.navigate(2);
                break;
        }
    }

    private void addItemToCart() {
        if (branchChoice >= 0 && branchChoice < branches.size()) {
            if(orders.size() < 1){
                createNewOrder();
            }
            omv.displayMenu(branchChoice, branches);
            int menuItemIndex = getInputInt("Select menu item for Order " + (orders.size() - 1) + ":") - 1;
            try {
                MenuItem selectedItem = omv.getSelectedItem(menuItemIndex);
                if (!orders.isEmpty()) {
                    // Add the selected item to the current order
                    Order currentOrder = orders.get(orders.size() - 1);
                    currentOrder.addItem(selectedItem);
                    this.navigate(0);
                } else {
                    System.out.println("No order created yet. Please create a new order.");
                    this.navigate(2);
                }
            } catch (Exception e) {
                System.out.println("Invalid menu item selection.");
                addItemToCart();
            }
        } else {
            omv.displayEmptyOrderListError();
            this.navigate(2);
        }
    }

    private void removeItemFromCart(){
        if (branchChoice >= 0 && branchChoice < branches.size() && orders.size() > 0) {
            omv.displayAllOrder(this);
            int editChoice = getInputInt("Select which Order to remove item from:")-1;
            Order currentOrder = orders.get(editChoice);
            omv.displayOrderList(orders, editChoice);
            int removeItem = getInputInt("Select which Item to remove from order:")-1;
            String removedItem = currentOrder.removeItem(removeItem);
            omv.displayRemoved(removedItem);
            
            this.navigate(0);
        } else {
            omv.displayEmptyOrderListError();
            this.navigate(2);
        }
    }

    private void editOrder(){

    }

    private void createNewOrder() {
        // Create a new order and add it to the list of orders
        Order newOrder = new Order();
        orders.add(newOrder);
        System.out.println("New order created.");
    }

    public void displayCurrentOrders() {
        omv.chooseDisplayCurrentOrder(this);
        String exit = getInputString("Enter a key to exit");  //just a wait for enter
    
    }

    public List<Order> getOrders(){
        return orders;
    }


}
