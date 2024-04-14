package view;
import view.abstracts.ARenderView;
import java.util.Scanner;
import controller.CustomerController;
import model.Branch;
import java.util.List;
import model.Order;
import view.OrderMenuView;

public class CustomerHomePageView extends ARenderView{
    
    public CustomerHomePageView(){
    }

    public void collectOrder(){
        super.printBorder("Order Collection");
    }
    @Override
    public void renderApp(int selection){
        switch(selection){
            case 0: //Render the option
                renderChoice();
                break;
        }
    }

    @Override
    public void renderChoice(){
        super.printBorder("Customer Home Page View");
        System.out.println("(1) Check Order Status");
        System.out.println("(2) Place a New Order");
        System.out.println("(3) Collect Order By ID");
    }

}
