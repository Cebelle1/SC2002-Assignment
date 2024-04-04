import model.Order;

/**
 * This is the Main class of the app.
 * It is the entry point for the entire app.
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
