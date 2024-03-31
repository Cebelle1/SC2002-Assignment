package view;
import controller.LoginController;
import view.abstracts.RenderView;

public class LoginView extends RenderView{
    LoginController lc;
    // constructor
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
                System.out.println("Choose Staff Option");
                System.out.println("(1) Login as Admin");
                System.out.println("(2) Login as Manager");
                System.out.println("(3) Login as Staff");
                System.out.println("(4) Reset Password");
                break;
            case 1:
                credentialPrompt();
                break;

            case 2:
                // Go back to the login page
                this.lc.navigate(0);
                break;

                
            case 4:
                this.lc.navigate(5); // go to login controller then system exit
                break;
        }
    }


    //@Override
    public String credentialPrompt() {

        System.out.print("Enter Staff ID: ");
        return "123";  //Use the same method to read the password so that it can be hidden when entered    
    }

    public String passwordPrompt(){
        System.out.print("Enter Password: ");
        return "123";
    }

    public void loggedInPrompt(boolean auth){
        
        if(auth){
            System.out.print("You are logged in! ");
            renderApp(4); // for now do a system exit
        }
        else{
            System.out.print("Retry again! ");
            //this.lc.navigate(0); // goes back to loginController to prompt again
        }
    }

    // Reset password
    public String newPassword(){
        System.out.print("Enter New Password: ");
        return "123";
    }

    public String cfmNewPassword(){
        System.out.print("Confirm Password: ");
        return "123";
    }

    public void updated(boolean upToDate){
        if(upToDate){
            System.out.print("Password updated successfully ");
            this.lc.navigate(0); // goes back to loginController to prompt again
        }
        else{
            // Go back to the LoginView
            System.out.print("Password does not match ");
            renderApp(2);
        }
    }


    @Override
    public void renderChoice(){
        // to print the borders -> overide the superclass method
        super.printBorder("Login View");
    }
    
}
