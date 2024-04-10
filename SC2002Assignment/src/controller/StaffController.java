package controller;

import java.util.List;

import controller.abstracts.AController;
import model.Order;
import model.StaffRole;
import model.abstracts.AEmployee;
import view.StaffHomePageView;

public class StaffController extends AController{

    private StaffHomePageView staffView;
    private List<Order> orders;
    private StaffRole staffRole;
    AEmployee user;
    int orderid = -1;

    public StaffController(AEmployee user){
        staffRole =  new StaffRole(user.getName(), user.getStaffID(), user.getRole(), user.getGender(), user.getAge(), user.getBranch(), user.getPassword());
        staffView = new StaffHomePageView(this);
        this.user = user;
    }

    public void navigate(int page){

        switch(page){

            // main staff page
            case 0:
                staffView.renderApp(0); // displays what the staff can do
                int choice = staffView.getInputInt(""); // gets the input
                if(choice > 4){
                    System.out.println("Invalid Option");
                    this.navigate(0);
                }
                this.navigate(choice);
                break;

            // Display new orders
            case 1:
                staffView.renderApp(1);
                staffRole.displayOrders(); // get the order in the branch the staff is working at
                staffView.getInputString("\n\nPress any key to go back to Staff Main Menu");
                this.navigate(0);
                break;

            // View Details
            case 2:
                int orderID = staffView.getInputInt("Type in the OrderID:"); // gets the OrderId
                // Error Handling -> checking if the OrderID exists
                if(!staffRole.checkOrderID(orderID)){
                    System.out.println("\nPlease enter a valid Order ID");
                    staffView.delay(1);
                    this.navigate(2); // request for valid Order ID again
                }
                else{
                    staffView.renderApp(2);
                    staffView.displayingOrderView(orderID);
                    staffRole.viewDetails(orderID);
                    staffView.getInputString("\n\nPress any key to go back to Staff Main Menu");
                    this.navigate(0);
                }
                break;

            // Process orders main page
            case 3:
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
                        System.out.println("Please enter a valid Order ID");
                        staffView.delay(1);
                    }
                    this.navigate(3);
                }
                else{
                    // processsed the order status to 'Ready to collect'
                    if(staffRole.processOrder(orderid)){
                        staffView.getInputString("\nPress any key to go back to Staff Main Menu");
                        staffView.delay(1);
                        this.navigate(3);
                    }
                }
                break;

            // exit
            case 4:
                break;

        }
    }
    
}
