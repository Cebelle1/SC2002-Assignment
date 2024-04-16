package controller;

import controller.abstracts.AController;
import model.StaffRole;
import model.abstracts.AEmployee;
import view.StaffHomePageView;

/**
 * StaffController class responsible for handling staff-related functionality.
 * This class extends the abstract base controller class{@link AController}
 * 
 * @author Sharmilla
 * @version 1.0
 */

public class StaffController extends AController{

    private StaffHomePageView staffView;
    private StaffRole staffRole;
    AEmployee user;
    int orderid = -1;

    /**
     * Constructs a new StaffController with the specified user.
     * @param user The user logged in as a staff
     */

    public StaffController(AEmployee user){
        staffRole =  new StaffRole(user.getName(), user.getStaffID(), user.getRole(), user.getGender(), user.getAge(), user.getBranch(), user.getPassword());
        staffView = new StaffHomePageView(this);
        this.user = user;
    }

    /**
     * Navigates to the specified case based on user input.
     * @param page The feature to navigate to.
     * The pages are:
     *              <ul>
     *                  <li>0: Displays Staff choices in Staff Home Page View.</li>
     *                  <li>1: Invokes the feature to display new orders from customers </li>
     *                  <li>2: Invokes the feature to view more details of a specific orderID </li>
                        <li>3: Invokes the feature to process a particular order </li>
                        <li>4: Invokes the feature to exit the StaffController </li>
     *              </ul>
     */

    public void navigate(int page){

        switch(page){

            // main staff page
            case 0:
                staffView.renderApp(0); // displays what the staff can do
                int choice = staffView.getInputInt(""); // gets the input
                // Error handling
                if(choice > 3){
                    System.out.println("Invalid Option");
                    this.navigate(0);
                }
                this.navigate(choice);
                break;

            case 1:  // Display new orders
                staffView.renderApp(1);
                if(!staffRole.displayOrders()){
                    System.out.println("There are no orders to display");
                }// get the order in the branch the staff is working at
                staffView.exitPrompt();
                this.navigate(0);
                break;

            case 2: // View Details
                int orderID = staffView.getInputInt("Please enter OrderID"); // gets the OrderId

                // Error Handling -> checking if the OrderID exists
                if(!staffRole.checkOrderID(orderID)){
                    System.out.println("\nPlease enter a valid Order ID");
                    staffView.delay(1);
                    this.navigate(0); // request for valid Order ID again
                }

                else{
                    staffView.renderApp(2);
                    staffView.displayingOrderView(orderID);
                    staffRole.viewDetails(orderID);
                    staffView.exitPrompt();
                    this.navigate(0);
                }
                break;

            case 3: // Process orders main page
                staffView.renderApp(3);
                int select = staffView.getInputInt(""); // gets the input
                if(select == 3){
                    this.navigate(0);
                }
                // selected the second option without selecting the orderid
                else if(select == 2 && orderid < 0){
                    System.out.println("Please select your orderID first");
                    staffView.delay(1);
                    this.navigate(3);
                }
                else if (select == 1){
                    orderid = staffView.getInputInt("Type in the orderID: "); // gets the input
                    // Error Handling -> checking if the OrderID exists
                    if(!staffRole.checkOrderID(orderid)){
                        System.out.println("You've entered an invalid OrderID");
                        staffView.delay(1);
                    }
                    this.navigate(3);
                }
                else{
                    // process the order status to 'Ready to collect'
                    if(staffRole.processOrder(orderid)){
                        staffView.exitPrompt();
                        this.navigate(3);
                    }
                    else{
                        System.out.println("Error processing order!");
                        staffView.exitPrompt();
                        this.navigate(0);
                    }
                }
                break;
                
            case 4: // exit
                break;

        }
    }
    
}
