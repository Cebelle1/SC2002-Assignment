package controller;

import java.util.List;

import controller.abstracts.AController;
import model.Branch;
import model.ManagerRole;
import model.abstracts.AEmployee;
import model.menus.MenuItem;
import view.ManagerHomePageView;

public class ManagerController extends AController {
    
    MenuItem menuItem;
    AEmployee user;
    private ManagerRole manager;
    private ManagerHomePageView managerView;
    Branch area;

    // Constructor
    public ManagerController(AEmployee user){
        this.user = user;
        // Create the ManagerRole object once the Manager logs in successfully
        this.manager = new ManagerRole(user.getName(), user.getStaffID(), user.getRole(), user.getGender(), user.getAge(), user.getBranch(), user.getPassword());
        this.managerView = new ManagerHomePageView(this);
        this.area = manager.loadBranches();
    }


    public void navigate(int page) {
        switch (page) {
            case 0:
                // Manager Main Page
                // Load the branch when the manager logs in successfully such that all methods in the ManagerRole can access the same branch
                //area = manager.loadBranches();
                //System.out.println(area.getName());
                managerView.renderApp(0);
                int choice  = managerView.getInputInt("");
                if(choice > 3){
                    System.out.println("Invalid Option");
                    this.navigate(0);
                }
                this.navigate(choice);
                break;

            case 1:
                // Everything a staff is able to do
                // Create the staff controller only before navigating to the staff mode
                StaffController staffcon = new StaffController(this.user);
                staffcon.navigate(0);
                break;    

            case 2:
                // Display Branch Staff List
                List<AEmployee> employees = manager.retrieveBranchStaff(area);
                managerView.displayBranchStaff(employees);
                this.navigate(0);
                break;

            case 3:
                // Edit Menu Item
                managerView.renderApp(1);
                int option  = managerView.getInputInt("");
                if(option > 4){
                    System.out.println("Invalid Option");
                    // Go back to Edit menu list
                    managerView.renderApp(1);
                }
                editMenu(option);
                break;
        }
    }

    public void editMenu(int selection){
        boolean found = false;
        switch(selection){
            case 1:
                // Add new item
                String newItem = managerView.getInputString("Enter the name of new item: ");
                found = manager.itemAvailable(newItem, area);
                // Item not in the menu list
                if(found == false)
                {
                    String comment = managerView.getInputString("Enter the item description: ");
                    double cost = managerView.getInputDouble("Enter the price of the item: ");
                    String category = managerView.getInputString("Enter the category it belongs to: ");
                    String branch = area.getName();
                    MenuItem menuItem = new MenuItem(newItem, comment, cost, branch, category);
                    manager.addItem(menuItem, area);
                    this.navigate(3);
                }
                // Item in menu list
                else
                {
                    // Set flag back to false in case it was true in other cases
                    //found = false;
                    this.navigate(3);
                }
                break;

            case 2:
                // Remove menu item
                String itemToRemove = managerView.getInputString("Enter the item name to remove: ");
                found = manager.itemAvailable(itemToRemove, area);
                if(found == true)
                {
                    manager.removeItem(itemToRemove, area);
                    this.navigate(3);
                }
                else
                {
                    //found = false;
                    this.navigate(3);
                }
               
                break;

            case 3:
                // Edit menu item name
                managerView.renderApp(2);
                int option  = managerView.getInputInt("");
                if(option > 3){
                    System.out.println("Invalid Option");
                    // Go back to Edit menu list
                    managerView.renderApp(2);
                }
                editFeatures(option);
                break;

            case 4:
                // Go back to Manager Home Page
                this.navigate(0);
                break;
        }
    }

    public void editFeatures(int choice){
        boolean found = false;
        switch(choice){
            case 1: 
                // Edit item name
                String oldName = managerView.getInputString("Enter old name: ").toLowerCase();
                found = manager.itemAvailable(oldName, area);
                if(found == true)
                {
                    String editName = managerView.getInputString("Enter new name: ");
                    manager.editName(oldName, editName, area);
                    this.editMenu(3);
                }
                else
                {
                    managerView.displayNotFound();
                    this.editMenu(3);
                }
                break;

            case 2:
                // Edit item price
                String name = managerView.getInputString("Enter item name: ").toLowerCase();
                found = manager.itemAvailable(name, area);
                if(found == true)
                {
                    double cost = managerView.getInputDouble("Enter new price: "); 
                    manager.editPrice(name, cost, area);
                    this.editMenu(3);
                }
                else
                {
                    managerView.displayNotFound();
                    this.editMenu(3);
                }
                break;

            case 3:
                // Edit item description
                String itemName = managerView.getInputString("Enter item name: ").toLowerCase();
                found = manager.itemAvailable(itemName, area);
                if(found == true)
                {
                    String note = managerView.getInputString("Enter new description: "); 
                    manager.editDescription(itemName, note, area);
                    this.editMenu(3);
                }
                else
                {
                    managerView.displayNotFound();
                    this.editMenu(3);
                }
                break;

            case 4:
                // Go back to Edit Menu List
                this.navigate(3);
                break;
        }
    }
}
