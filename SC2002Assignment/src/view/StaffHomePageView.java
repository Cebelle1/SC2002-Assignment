package view;

import controller.StaffController;
import view.abstracts.ARenderView;

/**
 * StaffHomePageView class is responsible for rendering the staff view, prompting
 * the staff to select the options he/she wants to display
 * .
 * This class extends the abstract base view class{@link ARenderView}
 * 
 * @author Sharmilla
 * @version 1.0
 */
public class StaffHomePageView extends ARenderView{

    StaffController staffC;

    /**
     * Constructs a new StaffHomePageView object with reference to a StaffController.
     * 
     * This constructor initializes a new instance of the StaffHomePageView class and associates
     * it with the provided StaffController object.
     * 
     * @param staffC The StaffController object to associate with the StaffHomePageView.
     */
    public StaffHomePageView(StaffController staffC){
        this.staffC = staffC;
    }

    /**
     * Displays various options and navigates to the specified case based on user input.
     * 
     * The renderApp method renders different aspects of the application based on the selection parameter.
     * 
     * @param selection The view to navigate to.
     *                  The valid selection are:
     *                  <ul>
     *                      <li>0: Displays three options for staff. </li>
     *                      <li>1: Displays the new orders. </li>
     *                      <li>2: Prompts the user to enter the orderID. </li>
     *                      <li>3: Displays the processing options for staff.li>
     *                      <li>5: Exits the system. </li>
     *                  </ul>
     */
    @Override
    public void renderApp(int selection){

        renderChoice();

        switch(selection){

            case 0:
                System.out.println("Select one option ");
                System.out.println("(1) Display new orders ");
                System.out.println("(2) View Details");
                System.out.println("(3) Process Orders");
                break;

            // Display new orders
            case 1:
                System.out.println("Displaying New Orders: ");
                break;

            // view details of orders
            case 2:
                System.out.println("View Details of:");
                System.out.println("Please enter OrderID");
                break;

            // processing orders
            case 3:
                System.out.println("Process Order: ");
                System.out.println("(1) Select Order ");
                System.out.println("(2) Back to Staff Home Page");
                break;

            case 5:
                break;
        }
    }

    /**
     * An interface for the staff to view the Order ID, ordered items and quantity clearly.
     * 
     * @param orderID a unique identifier for each order
     */
    public void displayingOrderView(int orderID){
        System.out.println("OrderID.   | Name            |   Qty   |");
    }

    /**
     * The `renderChoice` method in Java overrides the superclass method 
     * to print borders for the "Staff Home Page".
     */
    @Override
    public void renderChoice(){
        // to print the borders -> override the superclass method
        super.printBorder("Staff Home Page");
    }

}
