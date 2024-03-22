package view;
import view.abstracts.RenderView;
import java.util.Scanner;
import controller.LoginController;

public class LoginView extends RenderView{
    LoginController lc;
    public LoginView(LoginController lc){
        super();
        //super(lc);
        this.lc = lc;
    }

    @Override
    public void renderApp(int selection){
        renderChoice();
        switch(selection){
            case 0:
                //this should just show "Log in as Admin/Staff/Manager"
                System.out.println("Choose staff option");
                break;
            case 1: 
                credentialPrompt();
                break;
            case 2:
                this.lc.navigate(3);
                break;

        }
    }

    

    //@Override
    public String credentialPrompt() {
        System.out.print("Enter Password: ");
        return "123";  //Use the same method to read the password so that it can be hidden when entered    

    }

    @Override
    public void renderChoice(){
        super.printBorder("Login View");
        System.out.println("(1) Choose Staff Option");
    }
    
}
