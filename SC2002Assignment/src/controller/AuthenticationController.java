//Hello
package controller;

public class AuthenticationController {
    int loginAttempt = 3;
    LoginController loginC;
    boolean cd;

    public AuthenticationController(LoginController loginC){
        this.loginC = loginC;
    }

    public boolean isUnderCooldown(){
        return this.cd;
    }

    public boolean authenticate(String password){
        if(this.isUnderCooldown() == false) {
            //Auth logic here
            return true; //to add another if-else
        }else {
            return false;
        }
    }
    
}
