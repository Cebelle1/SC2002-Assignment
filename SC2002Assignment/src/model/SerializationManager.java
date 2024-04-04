package model;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

//Might not use a Manager.. just serialize in the class to simplify object passing.
public class SerializationManager {
    
    //====================Serialization TESTING IN PROGRESS========================
   /* public void saveOrders() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ORDERS_FILE))) {
            oos.writeObject(orders); // Serialize the list of orders
            System.out.println("Saving");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @SuppressWarnings("unchecked")
    public static List<Order> loadOrders() {
        List<Order> orders = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ORDERS_FILE))) {
            orders = (List<Order>) ois.readObject(); // Deserialize the list of orders
        } catch (IOException | ClassNotFoundException e) {
            if (e instanceof EOFException) { //Account for empty file
                System.out.println("No orders found in the file.");
            } else {
                // Other IO or serialization/deserialization errors
                e.printStackTrace();
            }
        }
        return orders;
    }*/
}
