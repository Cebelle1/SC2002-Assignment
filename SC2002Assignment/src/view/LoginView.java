/**
 * The LoginView class in Java is responsible for rendering the login interface, prompting for
 * credentials, handling login status, and displaying messages related to login and password updates.
 * 
 * @author Loo Si Hui
 * @version 1.0
 */
package view;
import controller.LoginController;
import view.abstracts.ARenderView;

public class LoginView extends ARenderView{
    LoginController lc;

    /**
     * Constructs a new LoginView object with a reference to a LoginController.
     *
     * This constructor initializes a new instance of the LoginView class and associates
     * it with the provided LoginController object.
     *
     * @param lc The LoginController object to associate with the LoginView.
     */
     public LoginView(LoginController lc){
        super();
        this.lc = lc;
    }

    /**
     * Displays various options and performs corresponding actions based on the user's input.
     * 
     * The renderApp method renders different parts of the application based on the selection parameter.
     * 
     * @param selection The selection parameter determines which part of the application to render.
     *                  Valid values are:
     *                  <ul>
     *                      <li>0: Displays login choices for Admin, Manager, and Staff.</li>
     *                      <li>1: Prompts the user for credentials.</li>
     *                      <li>2: Navigates back to the reset password page.</li>
     *                      <li>4: Exits the system.</li>
     *                  </ul>
     */
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
                // Go back to the reset password page
                this.lc.navigate(4);
                break;

                
            case 4:
                this.lc.navigate(5); // go to login controller then system exit
                break;
        }
    }

    /**
     * The function `credentialPrompt` prompts the user to enter a Staff ID.
     */
    public void credentialPrompt() {
        System.out.print("Enter Staff ID: ");
    }


    /**
     * Checks if a user is authenticated and displays a message accordingly.
     * 
     * The loggedInPrompt method checks the authentication status of the user and prints a message
     * based on the value of the auth parameter.
     * 
     * @param auth A boolean indicating whether the user is authenticated.
     *             - If true, the user is logged in successfully.
     *             - If false, the user authentication failed, and they need to retry.
     */
    public void loggedInPrompt(boolean auth){
        if(auth){
            System.out.print("You are logged in! ");
            //renderApp(4); // for now do a system exit
        }
        else{
            System.out.print("Retry again! ");
            super.delay(3);
        }
    }


    /**
     * Checks if a password update was successful and navigates accordingly.
     * 
     * The updated method checks whether the password update was successful based on the value
     * of the upToDate parameter.
     * 
     * @param upToDate A boolean indicating whether the password update was successful.
     *                 - If true, the password was updated successfully.
     *                 - If false, the password does not match or the update was unsuccessful.
     */
    public void updated(boolean upToDate){
        if(upToDate){
            System.out.print("Password updated successfully ");
            this.lc.navigate(0); // goes back to loginController to prompt again
        }
        else{
            // Go back to the reset password page
            System.out.print("Password does not match or update unsuccessful ");
            renderApp(2);
        }
    }

    /**
     * Displays a message indicating an invalid StaffID or Password and pauses execution briefly.
     */
    public void displayInvalidAcc(){
        System.out.println("Invalid StaffID or Password!");
        super.delay(2);
    }


    /**
     * The `renderChoice` method in Java overrides the superclass method to print borders for the
     * "Login View".
     */
    @Override
    public void renderChoice(){
        super.printBorder("Login View");
    }
    
}
