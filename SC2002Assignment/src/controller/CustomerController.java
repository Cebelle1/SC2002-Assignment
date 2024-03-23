package controller;

import java.util.ArrayList;
import java.util.List;
import controller.abstracts.AController;
import model.Branch;
import model.Order;
import model.menus.MenuItem;
import view.CustomerHomePageView;
import view.OrderMenuView;
import controller.OrderMenuController;

public class CustomerController extends AController {
    private CustomerHomePageView customerHomeView = new CustomerHomePageView(this);
    private Order currentOrder;
    private List<Order> orders = new ArrayList<>();
    private List<Branch> branches; // Ensure that this list is populated with branches containing menu items
    private OrderMenuView orderMenuView = new OrderMenuView(this);
    private static int branchChoice;
    private OrderMenuController omC;

    public CustomerController(List<Branch> branches) {
        this.branches = branches;
        currentOrder = new Order();
        
    }

    @Override
    public void navigate(int page) {
        switch (page) {
            case 0: //Customer Main Menu
                customerHomeView.renderApp(0); // Default 0
                int choice = super.getInputInt("");
                this.navigate(choice);
                break;
            case 1: //Select Branch
                customerHomeView.displayBranch(branches);
                branchChoice = super.getInputInt("Select branch:")-1;
                //omC = new OrderMenuController(orderMenuView, currentOrder, branchChoice, branches);
                omC = new OrderMenuController(this);
                this.navigate(0);
                
                break;
            case 2: //Display Current ORder
                if(omC == null){    //Error handling
                    customerHomeView.displayBranchError();
                    this.navigate(0);
                }
                omC.displayCurrentOrders();
                this.navigate(0);
                break;
            case 3: //Edit Order
                if(omC == null){    //Error Handling
                    customerHomeView.displayBranchError();
                    this.navigate(0);
                }
                omC.navigate(0);

                break;
            case 9:
                System.exit(page);
                break;
            default:
                System.out.println("Invalid page.");
        }
    }

    public List<Branch> getCurrentBranch(){
        return branches;
    }

    public OrderMenuView getCurOMV(){
        return orderMenuView;
    }

    public int getBranchChoice(){
        return branchChoice;
    }

    public Order getCurOrder(){
        return currentOrder;
    }
}

