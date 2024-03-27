package controller;

import java.util.ArrayList;
import java.util.List;
import controller.CustomerController;
import controller.abstracts.AController;
import model.Branch;
import model.menus.MenuItem;
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
        this.orders = new Order();
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
                int diningMode = getInputInt("Select dining mode: ");
                Order currentOrder = orders.getOrders().get(orders.getOrders().size() - 1);
                currentOrder.setDiningMode(diningMode);
                navigate(0);
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
                editOrder(); // To edit other orders
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
            if (orders.getOrders().size() < 1) {
                createNewOrder();
            }
            omv.displayMenu(branchChoice, branches);
            int menuItemIndex = getInputInt("Select menu item for Order " + (orders.getOrders().size() - 1) + ":") - 1;
            try {
                MenuItem selectedItem = omv.getSelectedItem(menuItemIndex);
                if (!orders.getOrders().isEmpty()) {
                    // Add the selected item to the current order
                    Order currentOrder = orders.getOrders().get(orders.getOrders().size() - 1);
                    
                    /*if (selectedItem.getCategory().equals("Set Meal")) {
                        // Prompt the user to choose drinks and sides
                        System.out.println("Choose your drink:");
                        omv.displayDrinks(); // Method to display drinks
                        int drinkChoice = getInputInt("Enter the drink number:");
                        MenuItem selectedDrink = getDrink(drinkChoice);
            
                        System.out.println("Choose your side:");
                        displaySides(); // Method to display sides
                        int sideChoice = getInputInt("Enter the side number:");
                        MenuItem selectedSide = getSide(sideChoice);
            
                        // Add the selected drink and side to the current order
                        Order currentOrder = orders.getOrders().get(orders.getOrders().size() - 1);
                        currentOrder.addItem(selectedDrink);
                        currentOrder.addItem(selectedSide);
                    }*/
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

    private void removeItemFromCart() {
        if (branchChoice >= 0 && branchChoice < branches.size() && orders.getOrders().size() > 0) {
            omv.displayAllOrder(orders);
            int editChoice = getInputInt("Select which Order to remove item from:") - 1;
            Order currentOrder = orders.getOrders().get(orders.getOrders().size() - 1);
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

    public void displayCurrentOrders() {
        omv.chooseDisplayCurrentOrder(this.orders, this);
        String exit = getInputString("Enter a key to exit"); // just a wait for enter

    }

   

}
