package model.menus;

import model.Branch;
import model.Order;
import model.Order.OrderStatus;
import model.menus.MenuItem;
import view.OrderMenuView;

import java.util.List;
import java.util.stream.Collectors;

import controller.OrderMenuController;
import model.interfaces.IMenuHandler;

public class MenuHandler {
    private List<Branch> branches;
    private int branchChoice;
    private OrderMenuView omv;
    private OrderMenuController omc;
    private Order orders;

    
    public MenuHandler(OrderMenuController omc, List<Branch> branches, int branchChoice, Order orders, OrderMenuView omv){
        this.omc = omc;
        this.branches = branches;
        this.branchChoice = branchChoice;
        this.orders = orders;
        this.omv = omv;
    }

    public void editCart(int choice) {
        switch (choice) {
            case 1:
                addItemToCart();
                break;
            case 2:
                removeItemFromCart();
                break;
            case 3:
                createNewOrder();
                omc.navigate(2);
                break;
        }
    }
    
    public void displayMenu(List<Branch> branches) { //Display the non-organized menu
        if (branchChoice >= 0 && branchChoice < branches.size()) {
            omv.displayMenu(branchChoice, branches);
        } else {
            System.out.println("Invalid branch selection.");
        }
    }


    //Might atomize this later on
    private void addItemToCart() {
        if (branchChoice < 0 || branchChoice > branches.size()-1){
            omv.displayEmptyOrderListError();
            omc.navigate(2);
        }

        if (orders.getOrders().size() < 1) {
            createNewOrder();
        }

        Order currentOrder = Order.getCurrentOrder();

        //If already checked out, i.e process status >= PENDING, cannot add. But still can add if just selected dining mode
        if(currentOrder.getOrderStatus() != OrderStatus.NEW && currentOrder.getOrderStatus() != OrderStatus.ORDERING){
            omv.displayError("Order has been checked out or is being processed, no further changes should be made");
            omc.navigate(2);
        }   

        omv.displayMenu(branchChoice, branches);    //shift to BranchView
        
        int menuItemIndex = omv.getInputInt("Select menu item for Order " + (orders.getOrders().size()) + ":") - 1;
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
                omc.navigate(0);
            } else {
                System.out.println("No order created yet. Please create a new order.");
                omc.navigate(2);
            }
        } catch (Exception e) {
            System.out.println("Invalid menu item selection.");
            addItemToCart();
        }
    
    }

    private void removeItemFromCart() {
        if (branchChoice >= 0 && branchChoice < branches.size() && orders.getOrders().size() > 0) {
            //omv.displayAllOrder(orders);
            //int editChoice = omv.getInputInt("Select which Order to remove item from:") - 1;
            Order currentOrder = Order.getCurrentOrder();
            if(currentOrder.getOrderStatus() != OrderStatus.NEW && currentOrder.getOrderStatus() != OrderStatus.ORDERING){
                omv.displayError("Order has been checked out or is being processed, no further changes should be made");
                omc.navigate(2);
            }   
            
            omv.displayOrderList(orders, currentOrder.getOrderID()-1);    //display current order menu items
            if(currentOrder.getCurrentOrderItems().size() < 1){
                omv.displayError("No items to remove");
                omc.navigate(2);
            }
            int removeItem = omv.getInputInt("Select which Item to remove from order:") - 1;
            String removedItem = currentOrder.removeItem(removeItem);
            omv.displayRemoved(removedItem);

            omc.navigate(0);
        } else {
            omv.displayEmptyOrderListError();
            omc.navigate(2);
        }
    }

    private void createNewOrder() {
        // Create a new order and add it to the list of orders
        Order newOrder = new Order();
        orders.newOrder(newOrder);
        System.out.println("New order created.");
    }

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
            mainChoice = omv.getInputInt("Invalid main dish selection, please select again");
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
            drinkChoice = omv.getInputInt("Invalid drink selection, please select again");
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
            sideChoice = omv.getInputInt("Invalid side dish selection, please select again");
        }
        System.out.printf("Side Selected: %s\n", sides.get(sideChoice-1).getName());
        return sides.get(sideChoice - 1); 
    }

//==========================Set Meal==================
    private void handleSetMeal(Order currentOrder, MenuItem selectedItem){
        omv.displayMains();
        int mainChoice = omv.getInputInt("Select 1 Main for : " + selectedItem.getName());
        MenuItem selectedMain = getMainDish(mainChoice);

        omv.displayDrinks(); 
        int drinkChoice = omv.getInputInt("Select 1 Drink:");
        MenuItem selectedDrink = getDrink(drinkChoice);

        omv.displaySides();
        int sideChoice = omv.getInputInt("Select 1 Side:");
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
        int customizeChoice = omv.getInputInt("Customize Order?");
        if(customizeChoice == 1){
            comments = omv.getInputString("Enter customization instructions:");
        }else{
            comments = "None";
        }
        return comments;
    }

    private int selectQty(){
        System.out.println("Select qty for this item");
        int qty = omv.getInputInt("Qty: ");
        return qty;
    }
}
