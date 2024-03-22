package view;
import view.abstracts.RenderView;
import java.util.Scanner;
import controller.CustomerController;
import model.Branch;
import java.util.List;
import model.Order;
import view.OrderMenuView;

public class CustomerHomePageView extends RenderView{
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
        System.out.println("(1) Select Branch");
        System.out.println("(2) Check Order Status");
        System.out.println("(3) Place a New Order");
    }

    public void displayBranch(List<Branch> branches){
        for (int i = 0; i < branches.size(); i++) {
            System.out.println("(" + (i + 1) + ") " + branches.get(i).getName());
        }
        
    }

    public void checkOrderStatus(OrderMenuView omv, Order order){
        omv.displayCurrentOrder(order);
    }
}
