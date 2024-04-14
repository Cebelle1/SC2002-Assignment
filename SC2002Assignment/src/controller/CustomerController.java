package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import controller.abstracts.AController;
import model.Branch;
import model.Order;
import view.BranchView;
import view.CustomerHomePageView;
import view.OrderMenuView;
import controller.OrderMenuController;
public class CustomerController extends AController {
    private CustomerHomePageView customerHomeView = new CustomerHomePageView();
    private BranchView branchV = new BranchView();
    private Order currentOrder;
    private List<Order> orders = new ArrayList<>();
    private List<Branch> branches; // Ensure that this list is populated with branches containing menu items
    private OrderMenuView orderMenuView = new OrderMenuView(this);
    private static int branchChoice;
    private Map<Branch, OrderMenuController> branchOrderMenuControllers = new HashMap<>();
    private OrderMenuController selectedOMC;
    public CustomerController(List<Branch> branches) {
        this.branches = branches;
        
    }

    @Override
    public void navigate(int page) {
        switch (page) {
            case 0: // Customer Main Menu
                customerHomeView.renderApp(0); // Default 0
                int choice = customerHomeView.getInputInt("", 3);
                this.navigate(choice);
                break;
            
            case 1: // Display Completed Orders
                selectedOMC = branchOrderMenuControllers.get(branches.get(branchChoice));
                if (selectedOMC == null) {
                    branchV.displayBranchError();
                    this.navigate(0);
                }
                selectedOMC.displayOrderStatus();
                this.navigate(0);
                break;
            case 2: // Edit Order
                selectedOMC = branchOrderMenuControllers.get(branches.get(branchChoice));
                if (selectedOMC == null) {
                    branchV.displayBranchError();
                    this.navigate(0);
                }
                selectedOMC.navigate(0);
                break;
            case 3: //Collect order by ID
                customerHomeView.collectOrder();
                int orderToCollect = customerHomeView.getInputInt("Enter Order ID to collect order: ");
                collectOrder(orderToCollect);
                break;
                
            case 10: // Select Branch [Startup should come here first]
                branchV.displayOpenBranch(branches, false);
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

    private void collectOrder(int orderID){
        Order order = Order.getOrderById(orderID);
        String currentBr = branches.get(branchChoice).getName();
        if (order != null) {
            if(!currentBr.equals(order.getBranchName())){
                System.out.println("Cannot collect order from another branch! Please switch branch");
                
            }else{
                order.markCompleted();
                System.out.printf("Order %d Status: %s\n", orderID, order.getOrderStatus());
            }
            customerHomeView.delay(3);
        } else {
            System.out.println("Order with ID " + orderID + " not found.");
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
