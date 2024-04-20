
package view;

import java.util.List;

import controller.ManagerController;
import model.abstracts.AEmployee;
import view.abstracts.ARenderView;

/**
 * ManagerHomePageView class is responsible for rendering the manager view, prompting
 * the manager to select the options he/she wants to display.
 * 
 * This class extends the abstract base view class{@link ARenderView}
 * 
 * @author Tey Shu Fang
 * @version 1.0
 */

public class ManagerHomePageView extends ARenderView{
    
    ManagerController manCon;

    /**
     * Constructs a new ManagerHomePageView object with ManagerController dependency.
     * 
     * @param managerCon The ManagerController object to associate with the ManagerHomePageView.
     */

    public ManagerHomePageView(ManagerController managerCon){
        this.manCon = managerCon;
    }

    /**
     * Overrided method to display various options and navigates to the specified case based on manager input.
     * 
     * The renderApp method renders different aspects of the application based on the selection parameter.
     * 
     * @param selection The view to navigate to.
     *                  The valid selection are:
     *                  <ul>
     *                      <li>0: Invoke the features in the Manager Main Page.</li>
     *                      <li>1: Invoke the features to perform the tasks of the staff.</li>
     *                      <li>2: Invoke the features to process an order.</li>
     *                      <li>3: Invoke the features to edit the menu textfile.<li>
     *                      <li>4: Invoke the features to edit the particular item.</li>
     *                  </ul>
     */

    @Override
    public void renderApp(int selection){
        switch(selection){
            case 0:
                // Manager Main Page
                renderChoice();
                break;

            case 1:
                // Staff Mode
                renderStaffChoice();
                break;

            case 2:
                // Process Order
                renderProcessChoice();  
                break;

            case 3:
                // Edit Menu Options
                renderOption();
                break;

            case 4:
                // What to edit
                renderEdit();
                break;
        }
    }

    /**
     * A private method to print the title in the Manager Home Page to prevent modifications.
     */

    private void managerHeading(){
        super.printBorder("Manager Home Page");
    }

    /**
     * Overrided method to print the options in the Manager Main Page.
     */

    @Override
    public void renderChoice(){
        managerHeading();
        System.out.println("(1) Enter Staff mode");
        System.out.println("(2) Display Branch Staff List");
        System.out.println("(3) Edit Menu List");
    }

    /**
     * Method to print the options of what the staff can do and to navigate back to the Manager Main Page.
     */

    public void renderStaffChoice(){
        super.printBorder("Staff Home Page");
        System.out.println("(1) Display new orders");
        System.out.println("(2) View Details");
        System.out.println("(3) Process Orders");
        System.out.println("(4) Back to Manager Home Page");
    }

    /**
     * Method to print the options for processing an order and to navigate back to the Staff Home Page.
     */

    public void renderProcessChoice(){
        super.printBorder("Staff Home Page");
        System.out.println("Process Order: ");
        System.out.println("(1) Select Order ");
        System.out.println("(2) Back to Staff Home Page");
    }

    /**
     * Method to print the options for editing the menu textfile and to navigate back to the Manager Home Page.
     */

    public void renderOption(){
        managerHeading();
        System.out.println("Edit Menu List: ");
        System.out.println("(1) Add new item");
        System.out.println("(2) Remove menu item");
        System.out.println("(3) Edit menu item");
        System.out.println("(4) Back to Manager Home Page");
    }

    /**
     * Method to print the options for modifying a particular item and to navigate back to editing the menu textfile.
     */

    public void renderEdit(){
        managerHeading();
        System.out.println("What to edit: ");
        System.out.println("(1) Item name");
        System.out.println("(2) Item price");
        System.out.println("(3) Item description");
        System.out.println("(4) Back to Edit Menu List");
    }

    /**
     * Method to print the message when displaying new orders.
     */

    public void displayOrder(){
        System.out.println("\nDisplaying new orders: ");
    }

    /**
     * Method to print the headings when displaying the order details.
     */

    public void orderView(){
        System.out.println("\nOrderID.   | Name            |   Qty   |");
    }

    /**
     * Method to display the list of staffs and their roles from the branch that the manager is in.
     * 
     * @param employees The list of staffs in the same branch as the manager.
     */

    public void displayBranchStaff(List<AEmployee> employees){
        for(AEmployee person : employees){
            System.out.printf("%s ", person.getName());
            System.out.printf("%s", person.getRole());
            System.out.println();
        }
        exitPrompt();
    }

    /**
     * Method to display the message when an item is not found
     * and delay for 3 seconds before returning back to edit the menu textfile.
     */

    public void displayNotFound(){
        System.out.println("Item not found");
        super.delay(3);
    }
}
