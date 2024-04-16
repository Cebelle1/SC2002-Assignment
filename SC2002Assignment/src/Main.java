/**
 * The Main class in Java instantiates and starts an App object, handling any exceptions that may
 * occur.
 * 
 * @author Loo Si Hui
 * @version 1.0
 */

public class Main {
    public static void main(String[] args) {
        try{
            App FOMSApp = new App();
            FOMSApp.start();
        }catch(Exception e){
            System.out.println("Thank for using FOMs!");
            e.printStackTrace();
        }
    }
}

/*
 * Naming Conventions:
 * Package: lowercase
 * Class, Interface: UpperCamel
 * Method, Variable: lowerCamel
 * Constant, enum : UPPERCASE
 */
