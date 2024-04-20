package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.abstracts.AController;
import model.Branch;
import model.Order;
import view.BranchView;
import view.CustomerHomePageView;
import view.OrderMenuView;

/**
 * The CustomerController handles the actions of the Customer
 * 
 * This class extends the abstract base model class {@link AController}.
 * 
 * @author Loo Si Hui
 * @version 1.0
 */

public class CustomerController extends AController {
    private CustomerHomePageView customerHomeView = new CustomerHomePageView();
    private BranchView branchV = new BranchView();
    private Order currentOrder;
    private List<Branch> branches; // Ensure that this list is populated with branches containing menu items
    private OrderMenuView orderMenuView = new OrderMenuView(this);
    private static int branchChoice;
    private Map<Branch, OrderMenuController> branchOrderMenuControllers = new HashMap<>();
    private OrderMenuController selectedOMC;

    /**
     * CustomerController constructor receives a list of branches as dependency
     * @param branches
     */
    public CustomerController(List<Branch> branches) {
        this.branches = branches;
        
    }

    /**
     * Navigates to the specified case based on user input.
     * @param page The feature to navigate to.
     * The pages are:
     *              <ul>
     *                  <li>0: Customer Main Menu.</li>
     *                  <li>1: Checks Order Status by OrderID</li>
     *                  <li>2: Go to Order Menu Page</li>
                        <li>3: Collect Order by OrderID</li>
                        <li>10: Invokes the branch selection</li>
     *              </ul>
     */
    @Override
    public void navigate(int page) {
        switch (page) {
            case 0: // Customer Main Menu
                customerHomeView.renderApp(0); // Default 0
                int choice = customerHomeView.getInputInt("", 3);
                this.navigate(choice);
                break;
            
            case 1: // Check Order Status by ID
                selectedOMC = branchOrderMenuControllers.get(branches.get(branchChoice));
                if (selectedOMC == null) {
                    branchV.displayBranchError();
                    this.navigate(0);
                }
                selectedOMC.displayOrderStatus();
                this.navigate(0);
                break;
            case 2: // Make New Order
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

    /**
     * Allows Customer to collect order by passing in the OrderID
     * Function will print prompts if Customer entered the wrong OrderID or if the OrderID is not from the current branch
     * @param orderID
     */
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


    /**
     * Getter functino to get the list of branch
     * @return The list of branch
     */
    public List<Branch> getCurrentBranch() {
        return branches;
    }

    /**
     * Getter function to get the associated OrderMenuView to this controller instance
     * @return The OrderMenuView associated with this controller instance
     */
    public OrderMenuView getCurOMV() {
        return orderMenuView;
    }

    /**
     * Getter function to get the branch Customer selected
     * @return The branch Customer selected
     */
    public int getBranchChoice() {
        return branchChoice;
    }

    /**
     * Getter function to get the current order associated with this controller instance
     * @return The current order associated with this controller instance
     */
    public Order getCurOrder() {
        return currentOrder;
    }

    //================== Helper Methods for User Input Handling =================//

    /**
     * Adhoc helper method to handle the input of the branch choice
     * @return The valid branch choice
     */
    private int handleBranchInput(){
        int branchChoice = -1;
        while(branchChoice < 0 || branchChoice > branches.size()-1){
            branchChoice = customerHomeView.getInputInt("Select branch:") - 1;
        }
        return branchChoice;
    }
}
