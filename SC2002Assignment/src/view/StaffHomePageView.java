package view;

import controller.StaffController;
import view.abstracts.ARenderView;

public class StaffHomePageView extends ARenderView{

    StaffController staffC;

    public StaffHomePageView(StaffController staffC){
        this.staffC = staffC;
    }

    @Override
    public void renderApp(int selection) {

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
                System.out.println("Details of Current Orders");
                break;

            // processing orders
            case 3:
                System.out.println("Process Order: ");
                System.out.println("(1) Select Order ");
                System.out.println("(2) Update order status ");
                break;
            case 4:
                break;
        }
    }


    @Override
    public void renderChoice(){
        // to print the borders -> override the superclass method
        super.printBorder("Staff View");
    }
}
