package view;

import java.util.List;
import java.util.Locale;

import controller.CustomerController;
import controller.OrderMenuController;
import model.Branch;
import model.Order;
import model.menus.MenuItem;
import view.abstracts.RenderView;
import model.Branch;
import view.Receipt;

public class OrderMenuView extends RenderView {
    CustomerController custCon;
    Branch selectedBranch;

    Receipt receipt = new Receipt();

    public OrderMenuView(CustomerController controller) {
        this.custCon = controller;
    }

    public void displayMenu(int inputChoice, List<Branch> branches) {
        int index = 1;
        selectedBranch = branches.get(inputChoice);
        List<MenuItem> menu = selectedBranch.getMenu();

        super.printBorder("Menu Items in " + selectedBranch.getName());
        System.out.println("No.   | Name            |     Price     |");
        System.out.println("--------------------------------");

        // Print menu items
        for (MenuItem item : menu) {
            // Format item name and price
            String formattedName = formatName(item.getName());
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

    public MenuItem getSelectedItem(int menuIndex) {
        if (menuIndex > selectedBranch.getMenu().size()) {
            System.out.println("Menu index out of range");
            return null;
        }
        return selectedBranch.getMenu().get(menuIndex);

    }

    public void displayEditCart() {
        super.printBorder("Edit Cart");
        System.out.println("(1) Add Items");
        System.out.println("(2) Edit Orders");
        System.out.println("(3) Remove Item");
        System.out.println("(4) Make a new order");
    }

    public void chooseDisplayCurrentOrder(OrderMenuController omc) {
        super.printBorder("Order Status");

        displayAllOrder(omc);

        // Prompt user to select an order
        int selectedOrderIndex = omc.getInputInt("Enter the order number:") - 1;
        if (selectedOrderIndex < 0 || selectedOrderIndex >= omc.getOrders().size()) {
            System.out.println("Invalid order number.");
            return;
        }

        displayOrderList(omc.getOrders(), selectedOrderIndex);
    }

    public void displayAllOrder(OrderMenuController omc) {
        List<Order> orders = omc.getOrders(); // Assuming you have access to OrderMenuController and its orders
        if (orders.isEmpty()) {
            System.out.println("No orders available.");
            return;
        }
        int index = 1;
        for (Order order : orders) {
            System.out.println(index + ". Order " + index);
            index++;
        }
    }

    public void displayOrderList(List<Order> orders, int selectedOrderIndex) {
        // Display the selected order's items
        Order selectedOrder = orders.get(selectedOrderIndex);
        List<MenuItem> items = selectedOrder.getCurrentOrder();
        if (items.isEmpty()) {
            System.out.println("No items in this order.");
        } else {
            super.printBorder(selectedOrder.getDiningMode() + " Order");
            //System.out.println();
            System.out.println("Items in selected order:");
            int index = 1;
            for (MenuItem item : items) {
                String formattedName = formatName(item.getName());
                System.out.println(String.format("%-5s", index) + formattedName);
                index++;
            }
        }
    }

    public void displayEmptyOrderListError() {
        System.out.println("Please create an order using (4) before adding menu items.");
        delay(2);
    }

    public void displayRemoved(String removedItem) {
        removedItem = formatName(removedItem);
        System.out.printf("Remove %s from order\n", removedItem);
        delay(2);

    }

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

                // payment, no need to simulate, maybe +feature of payment into total sales
                // print receipt with order ID
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
        System.out.println("(7) Collect Food)");
        System.out.println("(8) Return to Customer Main Menu");
    }
}