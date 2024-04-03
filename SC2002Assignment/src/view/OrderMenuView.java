package view;

import java.util.List;
import java.util.Locale;

import controller.CustomerController;
import controller.OrderMenuController;
import model.Branch;
import model.Order;
import model.abstracts.AEmployee;
import model.menus.MenuItem;
import model.menus.SetMealCategory;
import model.payments.IPaymentProcessor;
import model.payments.PaymentMethodFactory;
import view.abstracts.ARenderView;

public class OrderMenuView extends ARenderView {
    CustomerController custCon;
    Branch selectedBranch;

    ReceiptView receipt = new ReceiptView();

    public OrderMenuView(CustomerController controller) {
        this.custCon = controller;
    }

    public void displayOrganizedMenu(int branchChoice,List<Branch> branches){
        selectedBranch = branches.get(branchChoice);
        super.printBorder("Menu Items in " + selectedBranch.getName());
        displayMain();
        displayDrinks();
        displaySides();
    }

    public void displayMenu(int inputChoice, List<Branch> branches) {
        int index = 1;
        selectedBranch = branches.get(inputChoice);
        //For Shufang's reference
        /*List<AEmployee> employees = selectedBranch.getEmployees();
        //System.out.println(employees);
        for(AEmployee employee : employees){
            System.out.println(employee.getName());
        }
        delay(2);*/
        
        List<MenuItem> menu = selectedBranch.getMenu();

        super.printBorder("Menu Items in " + selectedBranch.getName());
        System.out.println("No.   | Name            |     Price     |");
        System.out.println("--------------------------------");

        // Print menu items
        for (MenuItem item : menu) {
            // Format item name and price
            //String formattedName = formatName(item.getName());
            String formattedName = item.getFormattedName();
            String formattedPrice = String.format("| $%.2f", item.getPrice());
            // Print formatted item with index
            System.out.println(String.format("%-5s", index) + formattedName + formattedPrice + " |");
            index++;
        }
    }

    private String formatName(String name) {
        // Split the name into words
        String[] words = name.split(" ");

        // Capitalize the first letter of each word
        StringBuilder formattedName = new StringBuilder();
        for (String word : words) {
            formattedName.append(word.substring(0, 1).toUpperCase(Locale.ENGLISH)); // Capitalize first letter
            formattedName.append(word.substring(1).toLowerCase(Locale.ENGLISH)); // Convert remaining letters to
                                                                                 // lowercase
            formattedName.append(" "); // Add space between words
        }

        return String.format("| %-15s", formattedName.toString());
    }

    

    public void displayEditCart() {
        super.printBorder("Edit Cart");
        System.out.println("(1) Add Items");
        System.out.println("(2) Remove Item");
        //System.out.println("(3) Make a new order");   //New order should be auto
    }

//=================Category=================//
    public void displayDrinks() {
        List<MenuItem> menu = selectedBranch.getMenu();
        int index = 1;
        super.printSingleBorder(("Category: Drinks"));
        System.out.println("No.   | Name            |     Price     |");
        for (MenuItem item : menu) {
            if ("drink".equals(item.getCategory())) {
                //String formattedName = formatName(item.getName());
                String formattedName = item.getFormattedName();
                String formattedPrice = String.format("| $%.2f", item.getPrice());
                System.out.println(String.format("%-5s", index) + formattedName + formattedPrice + " |");
                index++;
            }
        }
    }
    
    public void displaySides() {
        List<MenuItem> menu = selectedBranch.getMenu();
        int index = 1;
        super.printSingleBorder("Category: Sides");
        System.out.println("No.   | Name            |     Price     |");
        for (MenuItem item : menu) {
            if ("side".equals(item.getCategory())) { 
                //String formattedName = formatName(item.getName());
                String formattedName =item.getFormattedName();
                String formattedPrice = String.format("| $%.2f", item.getPrice());
                System.out.println(String.format("%-5s", index) + formattedName + formattedPrice + " |");
                index++;
            }
        }
    }

    public void displayMain(){
        List<MenuItem> menu = selectedBranch.getMenu();
        int index = 1;
        super.printSingleBorder("Category: Mains");
        System.out.println("No.   | Name            |     Price     |");
        for (MenuItem item : menu) {
            if (!"side".equals(item.getCategory()) && !"drink".equals(item.getCategory()) && !"set meal".equals(item.getCategory())) {//All but side and drink
                //String formattedName = formatName(item.getName());
                String formattedName = item.getFormattedName();
                String formattedPrice = item.getFormattedPriceStr();
                System.out.println(String.format("%-5s", index) + formattedName + formattedPrice + " |");
                index++;
            }
        }
    }

    public void displayMains(){
        super.printBorder("Menu in " + selectedBranch.getName());
        displayMain();
    }
    

//================Order==================//
    //Choose an order to display
    public void chooseDisplayCurrentOrder(Order orders) {
        super.printBorder("Order Status");

        if(!displayAllOrder(orders)){
            return;
        }
        // Prompt user to select an order
        int selectedOrderIndex = getInputInt("Enter the order number:") - 1;
        if (selectedOrderIndex < 0 || selectedOrderIndex >= orders.getOrders().size()) {
            System.out.println("Invalid order number.");
            return;
        }

        displayOrderList(orders, selectedOrderIndex);
    }

    //Display all orderID, no menuItems
    public boolean displayAllOrder(Order ordersM) {
        List<Order> orders = ordersM.getOrders(); // Assuming you have access to OrderMenuController and its orders
        if (orders.isEmpty()) {
            System.out.println("No orders available.");
            return false;
        }
        int index = 1;
        for (Order order : orders) {
            System.out.println(index + ". Order " + index);
            index++;
        }
        return true;
    }

    //General display for MenuItems for a single order by inputing OrderID
    public void displayOrderList(Order orders, int selectedOrderIndex) {
        // Display the selected order's items
        //Order selectedOrder = orders.get(selectedOrderIndex);
        Order selectedOrder = orders.getOrders().get(selectedOrderIndex);
        List<MenuItem> items = selectedOrder.getCurrentOrderItems();
        if (items.isEmpty()) {
            System.out.println("No items in this order.");
        } else {
            super.printBorder(selectedOrder.getDiningMode() + " Order " + selectedOrderIndex+1);
            System.out.println("OrderID.   | Name            |   Qty   |    Unit Price    |    Total Price ");
            int index = 1;
            for (MenuItem item : items) {
                System.out.printf("%-9s  %-15s | %-7s | $%-15.2f | $%-10.2f\n", 
                    selectedOrderIndex+1, 
                    item.getFormattedName(), 
                    item.getQty(), 
                    item.getPrice(),
                    item.getPrice() * item.getQty());
                if ("set meal".equals(item.getCategory())) {
                    SetMealCategory setMeal = (SetMealCategory) item.getSetMeal();
                    System.out.printf("%8s > Main: %s\n", "", setMeal.getMainDish().getName());
                    System.out.printf("%8s > Side: %s\n", "", setMeal.getSideDish().getName());
                    System.out.printf("%8s > Drink: %s\n", "", setMeal.getDrink().getName());

                    
                }
                System.out.printf("%8s > Comments: %s\n", "", item.getComments());
                //System.out.println("         > Comments: " + item.getComments());
                index++;
            }
        }
    }

    //Display the order status of a single order
    public void chooseDisplayOrderStatus(Order ordersM, int orderID){
        List<Order> orders = ordersM.getOrders(); // Assuming you have access to OrderMenuController and its orders
        if (orders.isEmpty()) {
            System.out.println("No orders available.");
        } else if (orderID < 0 || orderID > orders.size()-1) {
            System.out.println("Invalid order ID.");
        } else {
            Order order = orders.get(orderID);
            super.printDoubleUnderline("OrderID | Status");
            System.out.printf("%-8d | %s\n", order.getOrderID(), order.getOrderStatus());
        }

    }

    public void displayCustomizeChoice(){
        System.out.println("Do you wish to customize your order?");
        System.out.println("(1) Yes\n(2) No");
    }

//================Checkout, Payement, Print Receipt Prints==================//
    public void displayCheckout(Order orders){
        super.printDoubleUnderline("Checking Out All Orders");
        for(int i=0; i<orders.getOrders().size(); i++){
            displayOrderList(orders, i);
        }
    }
//=============Error Handling===================//
    public void displayEmptyOrderListError() {
        System.out.println("Order is empty, please add items to cart.");
        delay(2);
    }

    public void displayRemoved(String removedItem) {
        removedItem = formatName(removedItem);
        System.out.printf("Remove %s from order\n", removedItem);
        delay(2);
    }

    public void displayError(String prompt){    //General Error
        delay(2, prompt);
    }

//==================Overrides====================//
    @Override
    public void renderApp(int selection) {
        switch (selection) {
            case 0:
                renderChoice();
                break;
            case 1:
                // Organize display of menu items
                break;
            case 2:
                // Add, edit, delete menu items
                displayEditCart();

                break;
            case 3:
                super.printBorder("Dining Mode");
                System.out.println("Dining Mode:");
                System.out.println("(1) Dine In");
                System.out.println("(2) Take Out");
                break;
            case 4: // Check Out (Complete placing a single order)
                // check out means make payment?
                // select dine in mode
                super.printBorder("Checkout");
                break;
            case 5: // Pay for Order (Pay multiple orders)
                
                break;
            case 6:
                receipt.printReciept();

                break;
            case 7:
                // collect food, change status from ready to pick up to completed
                break;
        }
    }

    @Override
    public void renderChoice() {
        super.printBorder("Order Menu View");
        System.out.println("(1) Organize Menu");
        System.out.println("(2) Edit Cart");
        System.out.println("(3) Take-away or Dine-in");
        System.out.println("(4) Check Out");
        System.out.println("(5) Pay");
        System.out.println("(6) Print Receipt");
        System.out.println("(7) Collect Food");
        System.out.println("(8) Return to Customer Main Menu");
    }
}