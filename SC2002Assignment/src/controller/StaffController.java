package controller;

import java.util.List;

import controller.abstracts.AController;
import model.Order;
import model.StaffRole;
import model.abstracts.AEmployee;
import view.StaffHomePageView;

public class StaffController extends AController{

    private StaffHomePageView staffView = new StaffHomePageView(this);
    private List<Order> orders;
    private StaffRole staffRole;

    public StaffController(AEmployee user){
        staffRole =  new StaffRole(user.getName(), user.getStaffID(), user.getRole(), user.getGender(), user.getAge(), user.getBranch(), user.getPassword());
    }

    public void navigate(int page){

        switch(page){

            // gets the stuff input
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
                staffRole.filterOrdersByBranch(); // get the order in the branch the staff is working at
                // display orders -> PREPARING ORDER STATUS ONLY
                // go to staff model
                break;

            // View Details
            case 2:
                int orderID = staffView.getInputInt("Type in the OrderID:"); // gets the OrderId
                staffView.renderApp(2);
                break;

            // Process orders
            case 3:
                staffView.renderApp(3);
                int select = staffView.getInputInt(""); // gets the input
                if(select > 3){
                    System.out.println("Invalid Option");
                    this.navigate(3);
                }
                // process the orders according to the choice

                break;

            // exit
            case 4:
                break;

        }
    }
    
}
