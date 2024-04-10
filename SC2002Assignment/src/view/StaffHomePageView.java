package view;

import controller.StaffController;
import view.abstracts.ARenderView;

public class StaffHomePageView extends ARenderView{

    StaffController staffC;

    public StaffHomePageView(StaffController staffC){
        this.staffC = staffC;
    }

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
                System.out.println("Details of Current Order");
                break;

            // processing orders
            case 3:
                System.out.println("Process Order: ");
                System.out.println("(1) Select Order ");
                System.out.println("(2) Update order status ");
                System.out.println("(3) Back to Staff Main View");
                break;
            case 4:
                break;
        }
    }

    public void displayingOrderView(int orderID){
        System.out.printf("Order ID: %d\n", orderID); // print the orderID
        System.out.printf("   Name - Quantity\n");
        System.out.printf(" ---------------------------------------\n");

    }

    @Override
    public void renderChoice(){
        // to print the borders -> override the superclass method
        super.printBorder("Staff Main Page");
    }

}
