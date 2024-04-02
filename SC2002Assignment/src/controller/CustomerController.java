package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import controller.abstracts.AController;
import model.Branch;
import model.Order;
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
    private Map<Branch, OrderMenuController> branchOrderMenuControllers = new HashMap<>();

    public CustomerController(List<Branch> branches) {
        this.branches = branches;
        
    }

    @Override
    public void navigate(int page) {
        switch (page) {
            case 0: // Customer Main Menu
                customerHomeView.renderApp(0); // Default 0
                int choice = customerHomeView.getInputInt("");
                if(choice > 5){ //HARDCODED, CHANGE IF NEEDED
                    System.out.println("Invalid Option");
                    this.navigate(0);
                }
                this.navigate(choice);
                break;
            
            case 1: // Display Current Orders
                OrderMenuController selectedOMC = branchOrderMenuControllers.get(branches.get(branchChoice));
                if (selectedOMC == null) {
                    customerHomeView.displayBranchError();
                    this.navigate(0);
                }
                selectedOMC.displayOrderStatus();
                this.navigate(0);
                break;
            case 2: // Edit Order
                selectedOMC = branchOrderMenuControllers.get(branches.get(branchChoice));
                if (selectedOMC == null) {
                    customerHomeView.displayBranchError();
                    this.navigate(0);
                }
                selectedOMC.navigate(0);
                break;
            //======Case 3 only for Debugging Purposes, to switch from Customer to Staff=====
            case 3:
                
                return;
                
            case 9:
                System.exit(page);
                break;
            case 10: // Select Branch [Startup should come here first]
                customerHomeView.displayBranch(branches);
                branchChoice = handleBranchInput();
                Branch selectedBranch = branches.get(branchChoice);
                OrderMenuController omc = branchOrderMenuControllers.get(selectedBranch);
                if (omc == null) {
                    omc = new OrderMenuController(this);
                    branchOrderMenuControllers.put(selectedBranch, omc);
                    currentOrder = new Order(selectedBranch);
                }
                this.navigate(0);
                break;
            default:
                System.out.println("Invalid page.");
                this.navigate(0);
                break;
        }
    }

    public List<Branch> getCurrentBranch() {
        return branches;
    }

    public OrderMenuView getCurOMV() {
        return orderMenuView;
    }

    public int getBranchChoice() {
        return branchChoice;
    }

    public Order getCurOrder() {
        return currentOrder;
    }

    //================== Helper Methods for User Input Handling =================//
    private int handleBranchInput(){
        int branchChoice = -1;
        while(branchChoice < 0 || branchChoice > branches.size()-1){
            branchChoice = customerHomeView.getInputInt("Select branch:") - 1;
        }
        return branchChoice;
    }
}
