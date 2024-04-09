package view;
import view.abstracts.ARenderView;
import java.util.Scanner;
import controller.CustomerController;
import model.Branch;
import java.util.List;
import model.Order;
import view.OrderMenuView;

public class CustomerHomePageView extends ARenderView{
    CustomerController custCon;
    
    public CustomerHomePageView(CustomerController custController){
        this.custCon=custController;
    }

    @Override
    public void renderApp(int selection){
        switch(selection){
            case 0: //Render the option
                renderChoice();
                
                break;
            //case 1:
              //  displayBranch();
                
                //break;
        }
    }

    @Override
    public void renderChoice(){
        super.printBorder("Customer Home Page View");
        System.out.println("(1) Check Order Status");
        System.out.println("(2) Place a New Order");
    }

    public void displayBranch(List<Branch> branches){
        super.printBorder("Logged in as Customer > Select Branch");
        for (int i = 0; i < branches.size(); i++) {
            System.out.println("(" + (i + 1) + ") " + branches.get(i).getName());
        }
        
    }


    public void displayBranchError(){
        System.out.println("Please select branch first");
        delay(2);
    }
}
