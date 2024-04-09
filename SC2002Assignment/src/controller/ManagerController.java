package controller;

import controller.abstracts.AController;
import model.Branch;
import model.EmployeeFilter;
import model.EmployeeHandler;
import model.ManagerRole;
import model.abstracts.AEmployee;
import model.menus.MenuItem;
import view.ManagerHomePageView;

import java.util.List;

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
    }


    public void navigate(int page) {
        switch (page) {
            case 0:
                // Manager Main Page
                // Load the branch when the manager logs in successfully such that all methods in the ManagerRole can access the same branch
                area = manager.loadBranches();
                System.out.println(area.getName());
                managerView.renderApp(0);
                int choice  = managerView.getInputInt("");
                if(choice > 4){
                    System.out.println("Invalid Option");
                    this.navigate(0);
                }
                this.navigate(choice);
                break;

            case 1:
                // Everything a staff is able to do
                // Create the staff controller only before navigating to the staff mode
                StaffController staffcon = new StaffController(user);
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
                if(option > 3){
                    System.out.println("Invalid Option");
                    // Go back to Edit menu list
                    managerView.renderApp(1);
                }
                editMenu(option);
                break;
        }
    }

    public void editMenu(int selection){
        switch(selection){
            case 1:
                // Add new item
                //menuItem = promptInput();
                String newItem = managerView.getInputString("Enter the name of new item: ");
                double cost = Double.parseDouble(managerView.getInputString("Enter the price of the item: "));
                String category = managerView.getInputString("Enter the category it belongs to: ");
                String branch = area.getName();
                MenuItem menuItem = new MenuItem(newItem, cost, branch, category);
                manager.addItem(menuItem, area);
                //managerView.renderApp(2);
                this.navigate(3);
                break;

            case 2:
                // Remove menu item
                //menuItem = promptInput();
                String itemToRemove = managerView.getInputString("Enter the item name to remove: ");
                manager.removeItem(itemToRemove, area);
                //managerView.renderApp(2);
                this.navigate(3);
                break;

            case 3:
                // Edit menu item name
                // String oldName = managerView.getInputString("Enter old name: ");
                // String editName = managerView.getInputString("Enter new name: ");
                // manager.editItemName(oldName, editName, area);
                // this.navigate(3);
                managerView.renderApp(2);
                int option  = managerView.getInputInt("");
                if(option > 3){
                    System.out.println("Invalid Option");
                    // Go back to Edit menu list
                    managerView.renderApp(2);
                }
                editFeatures(option);
                break;
        }
    }

    public void editFeatures(int choice){
        switch(choice){
            case 1: 
                // Edit item name
                String oldName = managerView.getInputString("Enter old name: ");
                String editName = managerView.getInputString("Enter new name: ");
                manager.editItemName(oldName, editName, area);
                this.navigate(3);
                break;

            case 2:
                // Edit item price
                String name = managerView.getInputString("Enter item name: ");
                double cost = Double.parseDouble(managerView.getInputString("Enter the new price: ")); 
                manager.editItemPrice(name, cost, area);
                this.navigate(3);
                break;
        }
    }




}
