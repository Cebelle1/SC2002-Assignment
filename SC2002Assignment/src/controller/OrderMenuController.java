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
import view.ReceiptView;

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
                Order currentOrder = Order.showReceipt();
                if(currentOrder == null){
                    omv.getInputString("Please make payment first! ");
                    this.navigate(0);
                }
                else{
                    ReceiptView.printReciept(currentOrder);
                }
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

    public void displayCartItems() {
        orderV.chooseDisplayCurrentOrder(this.orders);
        omv.getInputString("Enter a key to exit"); // just a wait for enter
    }

    public void displayOrderStatus(){
        // the confirmedOrders is getting all the confirmedorders from all branches
        // in StaffRole.java I did a filterOrderByBranch(), so maybe you want to use that?
        List<Order> comepletedOrders = FilterOrder.filterOrderByBranch(this.orders.getBranchName());
        //int orderID = omv.getInputInt("Enter Order ID to check status",comepletedOrders.size())-1;
        int orderID = omv.getInputInt("Enter Order ID to check status",Order.getConfirmedOrders().size())-1;
        orderV.chooseDisplayCompleteOrderStatus(comepletedOrders, orderID);
        //orderV.chooseDisplayCompleteOrderStatus(Order.getConfirmedOrders(), orderID);
        //int orderID = omv.getInputInt("Enter Order ID to check status",Order.getConfirmedOrders().size())-1;
        //omv.chooseDisplayOrderStatus(this.orders, orderID);
        //orderV.chooseDisplayCompleteOrderStatus(Order.getConfirmedOrders(), orderID);
        omv.exitPrompt();
        return;
    }

    public OrderView getOMV(){
        return this.orderV;
    }

    public MenuView getMV(){
        return this.menuV;
    }


}
