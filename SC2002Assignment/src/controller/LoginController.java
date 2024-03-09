package controller;
import model.abstracts.AUser;
import view.LoginView;
import model.Staff;

public class LoginController {
    AUser currentUser;
    LoginView loginView = new LoginView(this);

    public LoginController(){

    }

    public AUser getCurrentUser(){
        return this.getCurrentUser();
    }

    /*public void setCurrentUser(Customer c){
        this.currentUser = c;
    }*/

    public void setCurrentUser(Staff s){
        this.currentUser = s;
    }

    //Navigation for staff
    //Choose staff option, S/M/A
    public void navigate (int page){
        switch(page){
            case 0:
                System.out.println("LoginControllerTest");
                loginView.render(0);    //default 0
                break;
            case 1:
                System.out.println("LC Test Case 1");
                break;
            case 3:
                System.exit(page);
                break;
        }
    }
}
