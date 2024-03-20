package view;
import view.abstracts.RenderView;
import java.util.Scanner;
import controller.CustomerController;

public class CustomerHomerPageView extends RenderView{
    Scanner sc = new Scanner(System.in);
    CustomerController custCon;
    
    public CustomerHomerPageView(CustomerController custController){
        this.custCon=custController;

    }


    @Override
    public void renderApp(int selection){
        switch(selection){
            case 0: //Render the option
                renderChoice();
                break;
            case 1:
                displayBranch();
                break;
        }
    }

    @Override
    public void renderChoice(){
        super.printBorder("Customer Home Page View");
        System.out.println("(1) Select Branch");
        System.out.println("(2) Check Order Status");
        System.out.println("(3) Place a New Order");
    }

    public void displayBranch(){
        System.out.println("dispplay branch");
    }
}
