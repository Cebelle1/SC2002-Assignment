package controller;

import java.util.ArrayList;
import java.util.List;
import controller.CustomerController;
import controller.abstracts.AController;
import model.Branch;
import model.menus.MenuItem;
import view.OrderMenuView;
import model.Order;

public class OrderMenuController extends AController{
    private List<Branch> branches;
    private OrderMenuView  omv;
    private int branchChoice;
    private Order currentOrder;
    private CustomerController cC;

    //public OrderMenuController(OrderMenuView omV, Order order, int branch, List<Branch> branches){
    public OrderMenuController(CustomerController cC){
        this.cC = cC;
        this.branches = cC.getCurrentBranch();
        //loadMenuItemsIntoBranches();
        this.omv = cC.getCurOMV();
        this.branchChoice = cC.getBranchChoice();
        System.out.printf("OMV choice: %d", branchChoice);
        
        currentOrder = cC.getCurOrder();
    }

    @Override
    public void navigate(int page) {
        switch(page){
            case 0:
                omv.renderApp(0);
                int choice = getInputInt("");
                navigate(choice);
                break;
            case 1:
               //organize
                break;
            case 2:
            //edit
                omv.renderApp(2);
                int editCartChoice = getInputInt("Edit Cart Choice:");
                this.editCart(editCartChoice);
                break;
            case 3:
                
                break;
            case 8:
                cC.navigate(0);
                break;
        }
    }

    private void editCart(int choice){
        switch(choice){
            case 1: //Add
                //Should be able to make multiple orders. Each order have multiple items.
                if (branchChoice >= 0 && branchChoice < branches.size()) {
                    omv.displayMenu(branchChoice, branches);
                } else {
                    
                    System.out.println("Invalid branch selection.");
                    System.out.println(branchChoice);
                }
                int menuItemIndex = super.getInputInt("Select menu item:") - 1;
                try{
                    MenuItem selectedItem = omv.getSelectedItem(menuItemIndex);
                    currentOrder.addItem(selectedItem);
                    this.navigate(0);
                } catch(Exception e){
                    this.navigate(3);
                };
                break;
            case 2: //Edit items
                break;
            case 3: //Remove item
                break;
        }
    }
    
}
