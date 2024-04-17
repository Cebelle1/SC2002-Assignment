package view;

import controller.CustomerController;
import model.Branch;
import view.abstracts.ARenderView;

/**
 * The OrderMenuView is a class in the View layer that displays the Order Menu View
 * This class extends the abstract base view class{@link ARenderView}
 * @author Loo Si Hui
 * @version 1.0
 */
public class OrderMenuView extends ARenderView {
    CustomerController custCon;
    Branch selectedBranch;
    ReceiptView receipt = new ReceiptView();

    /**
     * The OrderMenuView constructor takes in a CustomerController object and
     * initializes the custCon field.
     * 
     * @param controller
     */
    public OrderMenuView(CustomerController controller) {
        this.custCon = controller;
    }

    /**
     * Displays the Edit Cart options
     */
    public void displayEditCart() {
        super.printBorder("Edit Cart");
        System.out.println("(1) Add Items");
        System.out.println("(2) Remove Item");
        //System.out.println("(3) Make a new order");   //New order should be auto
    }

    //=============Error Handling===================//
    /** 
     * Displays an error message if the order is empty for 2 seconds
     * 
     */
    public void displayEmptyOrderListError() {
        System.out.println("Order is empty, please add items to cart.");
        delay(2);
    }

    /**
     * Displays the name of the item removed
     * @param removedItem Name of the item removed
     */
    public void displayRemoved(String removedItem) {
        removedItem = super.formatName(removedItem);
        System.out.printf("Remove %s from order\n", removedItem);
        delay(2);
    }

    /**
     * Displays an error messay for 2 seconds
     * @param prompt The prompt to be printed
     */
    public void displayError(String prompt){    //General Error
        delay(2, prompt);
    }

//==================Overrides====================//
    /**
     * Displays various options and performs corresponding actions based on the user's input.
     * 
     * The renderApp method renders different parts of the application based on the selection parameter.
     * 
     * @param selection The selection parameter determines which part of the application to render.
     *                  Valid values are:
     *                  <ul>
     *                      <li>0: Displays Order Menu View choices</li>
     *                      <li>2: Displays Edit Cart</li>
     *                      <li>4: Prints the Title Border for Checkout</li>
     *                  </ul>
     */
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
                //receipt.printReciept();
                break;
            //case 7:   //Shifted to CustomerHPV
                //super.printBorder("Collect Order");
                //break;
        }
    }

    /**
     * Displays the Over Menu View choices
     */
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