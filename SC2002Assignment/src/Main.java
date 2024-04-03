import model.Order;

/**
 * This is the Main class of the app.
 * It is the entry point for the entire app.
 */
public class Main {
    public static void main(String[] args) {
        App FOMSApp = new App();
        FOMSApp.start();

        // Add shutdown hook to save orders for serialization!!!
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Save orders when the program exits
            Order.saveOrders(Order.getOrders());
        }));
    }

    
    
}

/*
 * Naming Conventions:
 * Package: lowercase
 * Class, Interface: UpperCamel
 * Method, Variable: lowerCamel
 * Constant, enum : UPPERCASE
 */
