
package view;

import java.util.List;

import controller.ManagerController;
import model.abstracts.AEmployee;
import view.abstracts.ARenderView;

public class ManagerHomePageView extends ARenderView{
    
    ManagerController manCon;

    public ManagerHomePageView(ManagerController managerCon){
        this.manCon = managerCon;
    }

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

    private void managerHeading(){
        super.printBorder("Manager Home Page");
    }

    @Override
    public void renderChoice(){
        managerHeading();
        System.out.println("(1) Enter Staff mode");
        System.out.println("(2) Display Branch Staff List");
        System.out.println("(3) Edit Menu List");
    }

    public void renderStaffChoice(){
        super.printBorder("Staff Home Page");
        System.out.println("(1) Display new orders");
        System.out.println("(2) View Details");
        System.out.println("(3) Process Orders");
        System.out.println("(4) Back to Manager Home Page");
    }

    public void renderProcessChoice(){
        super.printBorder("Staff Home Page");
        System.out.println("Process Order: ");
        System.out.println("(1) Select Order ");
        System.out.println("(2) Update order status ");
        System.out.println("(3) Back to Staff Home Page");
    }

    public void renderOption(){
        managerHeading();
        System.out.println("Edit Menu List: ");
        System.out.println("(1) Add new item");
        System.out.println("(2) Remove menu item");
        System.out.println("(3) Edit menu item");
        System.out.println("(4) Back to Manager Home Page");
    }

    public void renderEdit(){
        managerHeading();
        System.out.println("What to edit: ");
        System.out.println("(1) Item name");
        System.out.println("(2) Item price");
        System.out.println("(3) Item description");
        System.out.println("(4) Back to Edit Menu List");
    }

    public void displayOrder(){
        System.out.println("\nDisplaying new orders: ");
    }

    public void orderView(){
        System.out.println("\nOrderID.   | Name            |   Qty   |");
    }

    public void displayBranchStaff(List<AEmployee> employees){
        for(AEmployee person : employees){
            System.out.printf("%s ", person.getName());
            System.out.printf("%s", person.getRole());
            System.out.println();
        }
        exitPrompt();
    }

    public void displayNotFound(){
        System.out.println("Item not found");
        super.delay(3);
    }
}
