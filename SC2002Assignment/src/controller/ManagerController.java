package controller;

import java.util.List;

import controller.abstracts.AController;
import model.Branch;
import model.ManagerRole;
import model.abstracts.AEmployee;
import model.menus.MenuItem;
import view.ManagerHomePageView;

/**
 * The ManagerController is responsible for handling the actions of the manager.
 * This class extends the abstract base controller class{@link AController}
 * 
 * @author Tey Shu Fang
 * @version 1.0
 */


public class ManagerController extends AController {
    
    MenuItem menuItem;
    AEmployee user;
    private ManagerRole manager;
    private ManagerHomePageView managerView;
    Branch area;
    int orderNo = -1;

    /**
     * ManagerController constructor receives the current manager as dependency.
     * @param user The manager that is logged in.
     */

    // Constructor
    public ManagerController(AEmployee user){
        this.user = user;
        // Create the ManagerRole object once the Manager logs in successfully
        this.manager = new ManagerRole(user.getName(), user.getStaffID(), user.getRole(), user.getGender(), user.getAge(), user.getBranch(), user.getPassword());
        this.managerView = new ManagerHomePageView(this);
        this.area = manager.loadBranches();
    }

    
    /**
     * Navigates to the specified case based on manager input.
     * @param page The feature to navigate to.
     *             The pages are:
     *             <ul>
     *                 <li>0: Displays Manager choices in Manager Home Page View.</li>
     *                 <li>1: Invokes the features for performing the task of the staff.</li>
     *                 <li>2: Display Branch Staff List.</li>
     *                 <li>3: Invokes the features for editing the menu list.</li>
     *             </ul>
     */

    public void navigate(int page) {
        switch (page) {
            case 0:
                // Manager Main Page
                managerView.renderApp(0);
                int choice = managerView.getInputInt("");
                if(choice > 3){
                    System.out.println("Invalid Option");
                    this.navigate(0);
                }
                this.navigate(choice);
                break;

            case 1:
                // Everything a staff is able to do
                managerView.renderApp(1);
                int task  = managerView.getInputInt("");
                if(task > 4){
                    System.out.println("Invalid Option");
                    this.navigate(1);
                }
                this.staffJob(task);
                break;    

            case 2:
                // Display Branch Staff List
                List<AEmployee> employees = manager.retrieveBranchStaff(area);
                managerView.displayBranchStaff(employees);
                this.navigate(0);
                break;

            case 3:
                // Edit Menu Item
                managerView.renderApp(3);
                int option  = managerView.getInputInt("");
                if(option > 4){
                    System.out.println("Invalid Option");
                    // Go back to Edit menu list
                    this.navigate(3);
                }
                this.editMenu(option);
                break;
        }
    }

    
    /**
     * Navigates to the specified case based on which task the manager wants to do as a staff.
     * @param task The feature to navigate to.
     *             The tasks are:
     *             <ul>
     *                 <li>1: Displays new orders.</li>
     *                 <li>2: View the order details based on orderID.</li>
     *                 <li>3: Invokes the features for processing an order.</li>
     *                 <li>4: Navigate back to Manager Home Page.</li>
     *             </ul>
     */

    public void staffJob(int task){
        switch(task){
            case 1:
                // Display new orders
                managerView.displayOrder();
                manager.displayOrders();
                managerView.exitPrompt();
                this.navigate(1);
                break;

            case 2:
                // View details
                int orderNum = managerView.getInputInt("Please enter OrderID"); // gets the OrderId
                if(!manager.checkOrderID(orderNum))
                {
                    System.out.println("\nPlease enter a valid Order ID");
                    managerView.delay(1);
                    this.navigate(1); // request for valid Order ID again
                }
                else{
                    managerView.orderView();
                    manager.viewDetails(orderNum);
                    managerView.exitPrompt();
                    this.navigate(1);
                }
                break;

            case 3:
                // Process order
                managerView.renderApp(2);
                int stage  = managerView.getInputInt("");
                if(stage > 3){
                    System.out.println("Invalid Option");
                    // Go back to process order page
                    this.staffJob(3);
                }
                this.processOption(stage);
                break;

            // Navigate back to Manager Home Page
            case 4:
                this.navigate(0);
                break;
        }
    }

    /**
     * Navigates to the specified case based on the steps to process an order.
     * @param choice The feature to navigate to.
     *               The choices are:
     *               <ul>
     *                   <li>1: Select the order to process.</li>
     *                   <li>2: Update the order status.</li>
     *                   <li>3: Navigate back to the Staff Home Page.</li>
     *               <ul>
     */

    public void processOption(int choice){
        switch(choice){
            case 1:
                // Select Order
                orderNo = managerView.getInputInt("Please enter OrderID"); // gets the OrderId
                if(!manager.checkOrderID(orderNo)){
                    System.out.println("You've entered an invalid OrderID");
                }
                else{
                    System.out.println("Order selected");
                }
                managerView.delay(1);
                this.staffJob(3);
                break;
            
            case 2:
                // Update order status
                if(orderNo < 0){
                    System.out.println("Please select your orderID first");
                    managerView.delay(1);
                    this.staffJob(3);
                }
                // Process to Completed
                if(manager.processOrder(orderNo)){
                    managerView.exitPrompt();
                    this.staffJob(3);
                }
                else{
                    System.out.println("Error processing order!");
                    managerView.exitPrompt();
                    this.staffJob(3);
                }
                break;

            case 3:
                // Back to Staff Home Page
                this.navigate(1);
                break;
        }
    }

    /**
     * Navigates to the specified case based on what the manager wants to do with the menu list.
     * @param selection The feature to navigate to.
     *                  The selections are:
     *                  <ul>
     *                      <li>1: Add a new item.</li>
     *                      <li>2: Remove an existing item.</li>
     *                      <li>3: Invokes the features of the item to edit.</li>
     *                      <li>4: Navigate back to Manager Home Page.</li>
     *                  </ul>
     */

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
                    this.navigate(3);
                }
               
                break;

            case 3:
                // Edit menu item name
                managerView.renderApp(4);
                int option  = managerView.getInputInt("");
                if(option > 4){
                    System.out.println("Invalid Option");
                    // Go back to Edit choice
                    this.editMenu(3);
                }
                this.editFeatures(option);
                break;

            case 4:
                // Go back to Manager Home Page
                this.navigate(0);
                break;
        }
    }

    /**
     * Navigates to the specified case based on what the manager wants to modify to the existing menu item.
     * @param choice The feature to navigate to.
     *               The choices are:
     *               <ul>
     *                   <li>1: Name of the item.</li>
     *                   <li>2: Price of the item.</li>
     *                   <li>3: Description of the item.</li>
     *                   <li>4: Navigate back to Edit Menu List.</li>
     *               </ul>
     */

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
