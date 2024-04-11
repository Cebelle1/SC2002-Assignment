
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
                int choice  = super.getInputInt("");
                if(choice > 3){
                    System.out.println("Invalid Option");
                    this.renderApp(0);;
                }
                manCon.navigate(choice);
                break;

            case 1:
                // Edit Menu Options
                renderOption();
                int option  = super.getInputInt("");
                if(option > 4){
                    System.out.println("Invalid Option");
                    this.renderApp(1);;
                }
                manCon.editMenu(option);
                break;

            case 2:
                // What to edit
                renderEdit();
                int edit  = super.getInputInt("");
                if(edit > 4){
                    System.out.println("Invalid Option");
                    this.renderApp(2);;
                }
                manCon.editFeatures(edit);
                break;
        }
    }

    @Override
    public void renderChoice(){
        super.printBorder("Manager Home Page View");
        System.out.println("(1) Enter Staff mode");
        System.out.println("(2) Display Branch Staff List");
        System.out.println("(3) Edit Menu List");
    }

    public void renderOption(){
        super.printBorder("Edit Menu List");
        System.out.println("(1) Add new item");
        System.out.println("(2) Remove menu item");
        System.out.println("(3) Edit menu item");
        System.out.println("(4) Back to Manager Home Page");
    }

    public void renderEdit(){
        super.printBorder("What to edit");
        System.out.println("(1) Item name");
        System.out.println("(2) Item price");
        System.out.println("(3) Item description");
        System.out.println("(4) Back to Edit Menu List");
    }

    public void displayBranchStaff(List<AEmployee> employees){
        for(AEmployee person : employees){
            System.out.printf("%s ", person.getName());
            System.out.printf("%s", person.getRole());
            System.out.println();
        }
        super.delay(3);
    }
}
