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
                int editCartChoice = omv.getInputInt("Edit Cart Choice:", 2);
                menuHandler.editCart(editCartChoice);
                navigate(0);
                break;
            case 3: //Dining Mode
                omv.renderApp(3);
                int diningMode = omv.getInputInt("Select dining mode: ", 2);
                boolean orderingStatus = Order.setDiningMode(diningMode);
                if (!orderingStatus){
                    System.out.println("No orders created, please add items to cart first!");
                    omv.delay(2);
                }
                navigate(0);
                break;
            case 4: //Checkout
                boolean checkedOut = Order.checkout();
                if(!checkedOut){
                    System.out.println("No dining mode selected, please select a dining mode first!");
                }else{
                    omv.displayCheckout(this.orders);
                }
                omv.delay(2);
                
                navigate(0);
                break;
            case 5: //Pay
                //payment();
                double amount = 3.20;   //Test dummy
                boolean paid = PaymentMethodFactory.handlePayment(orders, amount);
                //Individual error prints inside handlePayment, if no new implementation change back to void.
                if(!paid){
                    omv.delay(2);   
                } else{
                    omv.delay(2);
                }
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
        omv.getInputString("Enter a key to exit"); // just a wait for enter
    }

    public void displayOrderStatus(){
        int orderID = omv.getInputInt("Enter Order ID to check status")-1;
        omv.chooseDisplayOrderStatus(this.orders, orderID);
        omv.getInputString("Press any key to exit");
        return;
    }

    


}
