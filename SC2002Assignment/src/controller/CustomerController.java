package controller;

import java.util.List;
import controller.abstracts.AController;
import model.Branch;
import model.Order;
import model.menus.MenuItem;
import view.CustomerHomePageView;
import view.OrderMenuView;

public class CustomerController extends AController {
    private CustomerHomePageView customerHomeView = new CustomerHomePageView(this);
    private Order currentOrder;
    private List<Branch> branches; // Ensure that this list is populated with branches containing menu items
    private OrderMenuView orderMenuView = new OrderMenuView(this);
    private static int branchChoice;

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
                this.navigate(0);
                
                break;
            case 2: //Display Current ORder
                //orderMenuView.displayCurrentOrder(currentOrder);
                customerHomeView.checkOrderStatus(orderMenuView, currentOrder);
                break;
            case 3: //Add Item to Order
                if (branchChoice >= 0 && branchChoice < branches.size()) {
                    orderMenuView.displayMenu(branchChoice, branches);
                } else {
                    System.out.println("Invalid branch selection.");
                }
                int menuItemIndex = super.getInputInt("Select menu item:") - 1;
                try{
                    MenuItem selectedItem = orderMenuView.getSelectedItem(menuItemIndex);
                    currentOrder.addItem(selectedItem);
                    this.navigate(0);
                } catch(Exception e){
                    this.navigate(3);
                };
                
                break;
            case 9:
                System.exit(page);
                break;
            default:
                System.out.println("Invalid page.");
        }
    }
}

