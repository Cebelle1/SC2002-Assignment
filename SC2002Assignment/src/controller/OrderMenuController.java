package controller;

import java.util.List;

import controller.abstracts.AController;
import model.Branch;
import model.FilterOrder;
import model.Order;
import model.menus.MenuHandler;
import model.payments.PaymentMethodFactory;
import view.MenuView;
import view.OrderMenuView;
import view.OrderView;

/**
 * OrderMenuController class handles the interaction between Customer, Order, and Menu Items
 * 
 * @author Loo Si Hui
 * @version 1.0
 */
public class OrderMenuController extends AController {
    private List<Branch> branches;
    private OrderMenuView omv;
    private int branchChoice;
    //private List<Order> orders; // List to store orders
    private Order orders;
    private CustomerController cC;
    private MenuHandler menuHandler;
    private MenuView menuV = new MenuView(cC);
    private OrderView orderV = new OrderView();

    /**
     * OrderMenuController takes in a CustomerController and initializers a new Order and a Menu Handler
     * 
     * @param cC
     */
    public OrderMenuController(CustomerController cC) {
        this.cC = cC;
        this.branches = cC.getCurrentBranch();
        this.omv = cC.getCurOMV();
        this.branchChoice = cC.getBranchChoice();
        this.orders = new Order(branches.get(branchChoice));
        this.menuHandler = new MenuHandler(this, branches, branchChoice, orders, this.omv);
    }

  /**
     * Navigates to the specified case based on user input.
     * @param page The feature to navigate to.
     * The pages are:
     *              <ul>
     *                  <li>0: Displays Order Menu View.</li>
     *                  <li>1: Displays the Menu of the Branch </li>
     *                  <li>2: Invokes the feature to Edit Cart</li>
                        <li>3: Invokes the feature to select Dining Mode</li>
                        <li>4: Invokes the feature to Checkout</li>
                        <li>5: Invokes the feature to Pay</li>
                        <li>6: Invokes the feature to print Receipt</li>
                        <li>8: Return back to Customer Home Page View</li>
     *              </ul>
     */
    @Override
    public void navigate(int page) {
        switch (page) {
            case 0:
                omv.renderApp(0);
                int choice = omv.getInputInt("");
                navigate(choice);
                break;
            case 1:
                // Display menu
                menuV.displayOrganizedMenu(branchChoice, branches);
                omv.exitPrompt();
                navigate(0);
                break;
            case 2:
                // Edit cart
                omv.renderApp(2);
                int editCartChoice = omv.getInputInt("Edit Cart Choice:", 2);
                menuHandler.editCart(editCartChoice);
                navigate(0);
                break;
            case 3: //Dining Mode
                omv.renderApp(3);
                int diningMode = omv.getInputInt("Select dining mode: ", 2);
                boolean orderingStatus = Order.setDiningMode(diningMode);
                if (!orderingStatus){
                    System.out.println("No items in cart or order is already being processed!");
                    omv.delay(2);
                }
                navigate(0);
                break;
            case 4: //Checkout
                boolean checkedOut = Order.checkout();
                if(!checkedOut){
                    System.out.println("No dining mode selected, please select a dining mode first!");
                }else{
                    orderV.displayCheckout(Order.getCurrentOrder());
                }
                omv.exitPrompt();

                navigate(0);
                break;
            case 5: //Pay
                boolean paid = PaymentMethodFactory.handlePayment(orders);
                //Individual error prints inside handlePayment, if no new implementation change back to void.
                if(!paid){
                    omv.delay(2);
                } else{
                    omv.delay(2);
                }
                navigate(0);
                break;
            case 6: //Receipt
                //Order.showReceipt();
                if(Order.showReceipt() == false){
                    omv.getInputString("Please make payment first! ");
                    this.navigate(0);
                }
                //omv.renderApp(6);
                omv.exitPrompt();
                break;
            case 7: //Collect Order - Shifted to CustomerHPV
                omv.renderApp(7);
                
                
                break;
            case 8:
                // Navigate back to main menu
                cC.navigate(0);
                break;
        }
    }

    /**
     * Function to invoke the display of the cart items
     *
     */
    public void displayCartItems() {
        orderV.chooseDisplayCurrentOrder(this.orders);
        omv.getInputString("Enter a key to exit"); // just a wait for enter
    }

    /**
     * Function to invoke and display the Order Status
     */
    public void displayOrderStatus(){
        List<Order> completedOrders = FilterOrder.filterOrderByBranch(orders.getBranchName());
        // Make sure it is within the range of the confirmed orders from all branches
        int orderID = omv.getInputInt("Enter Order ID to check status",Order.getConfirmedOrders().size())-1;
        // Ensuring the user selected the right branch they made the order in
        orderV.chooseDisplayCompleteOrderStatus(completedOrders, orderID);
        omv.exitPrompt();
        return;
    }

    /**
     * Getter function to get the current instance of OrderMenuView
     * @return The current instance of OrderMenuView
     */
    public OrderView getOMV(){
        return this.orderV;
    }

    /**
     * Getter function to get the Menu View associated with the current instance
     * @return The menu View associated with the current instance
     */
    public MenuView getMV(){
        return this.menuV;
    }


}
