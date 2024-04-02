package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import controller.CustomerController;
import controller.abstracts.AController;
import model.Branch;
import model.menus.MenuItem;
import model.menus.SetMealCategory;
import model.payments.IPaymentProcessor;
import model.payments.PaymentMethodFactory;
import view.OrderMenuView;
import view.payments.PayNowView;
import model.Order;
import model.menus.MenuHandler;

public class OrderMenuController extends AController {
    private List<Branch> branches;
    private OrderMenuView omv;
    private int branchChoice;
    //private List<Order> orders; // List to store orders
    private Order orders;
    private CustomerController cC;
    private MenuHandler menuHandler;

    public OrderMenuController(CustomerController cC) {
        this.cC = cC;
        this.branches = cC.getCurrentBranch();
        this.omv = cC.getCurOMV();
        this.branchChoice = cC.getBranchChoice();
        this.orders = new Order(branches.get(branchChoice));
        this.menuHandler = new MenuHandler(this, branches, branchChoice, orders, this.omv);
    }

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
                omv.displayOrganizedMenu(branchChoice, branches);
                String retChocie = omv.getInputString("Press any key to return");
                navigate(0);
                break;
            case 2:
                // Edit cart
                omv.renderApp(2);
                int editCartChoice = omv.getInputInt("Edit Cart Choice:");
                menuHandler.editCart(editCartChoice);
                navigate(0);
                break;
            case 3: //Dining Mode
                omv.renderApp(3);
                int diningMode = omv.getInputInt("Select dining mode: ");
                Order currentOrder = Order.getCurrentOrder();
                currentOrder.setDiningMode(diningMode);
                navigate(0);
                break;
            case 4: //Checkout
                checkout();
                navigate(0);
                break;
            case 5: //Pay
                payment();
                
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

//=========================================
    public void displayCartItems() {
        omv.chooseDisplayCurrentOrder(this.orders, this);
        String exit = omv.getInputString("Enter a key to exit"); // just a wait for enter
    }

    public void displayOrderStatus(){
        int orderID = omv.getInputInt("Enter Order ID to check status")-1;
        omv.chooseDisplayOrderStatus(this.orders, orderID);
        String exit = omv.getInputString("Press any key to exit");
        return;
    }

    private void checkout(){
        //Use displayCartItems() to display cartItems
        omv.displayCheckout(this.orders);
        
        List<Order> allOrders = orders.getOrders();
        for(Order o: allOrders){
            //o.confirmOrder();
            System.out.printf("Order Status Now: %s\n", o.getOrderStatus());
        }
        String exit = omv.getInputString("Enter a key to exit");
    }

//======================================================

    private void payment(){
        IPaymentProcessor paymentProcessor = null;
        //Using PayNowView for now for Views
        PayNowView pnv = new PayNowView();
        pnv.renderApp(0);
        int paymentMode = pnv.getInputInt("Select payment method: ");
        switch(paymentMode){
            case 1:
                paymentProcessor = PaymentMethodFactory.createPaymentMethod("model.payments.DebitCardPayment");
                
                break;
            case 2:
                paymentProcessor = PaymentMethodFactory.createPaymentMethod("model.payments.CreditCardPayment");
                break;
            case 3:
                paymentProcessor = PaymentMethodFactory.createPaymentMethod("model.payments.PayNowPayment");
        }
        paymentProcessor.payment(3.2);
        pnv.delay(1);

        List<Order> allOrders = orders.getOrders();
        for(Order o: allOrders){
            o.confirmOrder();
            System.out.printf("Order Status Now: %s\n", o.getOrderStatus());
        }
        String exit = omv.getInputString("Enter a key to exit");
    }

}
