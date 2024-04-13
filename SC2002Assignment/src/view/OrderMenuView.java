package view;

import controller.CustomerController;
import model.Branch;
import view.abstracts.ARenderView;

public class OrderMenuView extends ARenderView {
    CustomerController custCon;
    Branch selectedBranch;
    ReceiptView receipt = new ReceiptView();

    public OrderMenuView(CustomerController controller) {
        this.custCon = controller;
    }

    public void displayEditCart() {
        super.printBorder("Edit Cart");
        System.out.println("(1) Add Items");
        System.out.println("(2) Remove Item");
        //System.out.println("(3) Make a new order");   //New order should be auto
    }

//=============Error Handling===================//
    public void displayEmptyOrderListError() {
        System.out.println("Order is empty, please add items to cart.");
        delay(2);
    }

    public void displayRemoved(String removedItem) {
        removedItem = super.formatName(removedItem);
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
            case 4: // Check Out
                super.printBorder("Checkout");
                break;
            case 5: // Pay for Order (Pay multiple orders)
                
                break;
            case 6:
                receipt.printReciept();
                break;
            case 7:
                super.printBorder("Collect Order");
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