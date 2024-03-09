import controller.LoginController;
import view.OrderView;
import java.util.Scanner;

public class App {
    Scanner sc = new Scanner(System.in);
    public void start(){
        System.out.println("HelloWorld");
        System.out.println("Select Mode:");
        System.out.println("1 - Customer, 2 - Staff");
        int choice = sc.nextInt();
        switch(choice){
            case 1: 
                OrderView ov = new OrderView();
                break;
            case 2:
                LoginController lc = new LoginController();
                lc.navigate(0);
                break;
        }
        
    }
}
