package view;
import view.interfaces.ViewInterface;
import java.util.Scanner;
import controller.LoginController;

public class LoginView implements ViewInterface{
    LoginController lc;
    public LoginView(LoginController lc){
        //super(lc);
        //this.lc = lc;
    }

    @Override
    public void render(int selection){
        switch(selection){
            case 1: 
                credentialPrompt();
                break;
            case 2:
                this.lc.navigate(3);
                break;
        }
    }

    @Override
    public String getUserInput() {
        try (Scanner scanner = new Scanner(System.in)) {
            return scanner.nextLine().trim();   
        } catch (Exception e) {
            System.err.println("Error reading input.");
            return null;
        }
    }

    @Override
    public int getUserInput(int overloadInt){
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter StaffID:");
            //auth
            return scanner.nextInt();   
        } catch (Exception e) {
            System.err.println("Error reading input.");
            return -1;
        }
    }

    //@Override
    public String credentialPrompt() {
        System.out.print("Enter Password: ");
        return getUserInput();  //Use the same method to read the password so that it can be hidden when entered    

    }
    
}
