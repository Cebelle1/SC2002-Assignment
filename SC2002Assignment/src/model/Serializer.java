package model;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Serializer handles the algorithm for Serilization
 * @author Loo Si Hui
 * @version 1.0
 */
public class Serializer {
    /**
     * Handles the Serializing of the object into the Serialization text file
     * 
     * @param object Object to be Serialized
     * @param filename Serialized byte stream to be saved in
     */

    public static void serializeConfirmedOrders(List<Order> confirmedOrders, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(confirmedOrders); // Serialize only confirmed orders
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the Deserializing of the object from the Serialization text file
     * @param filename Serialized byte stream to be read from
     * @return List of Orders that are confirmed
     */
    public static List<Order> deserializeConfirmedOrders(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            List<Order> des = (List<Order>) ois.readObject();
            return des; // Deserialize the list of confirmed orders
        } catch (IOException | ClassNotFoundException e) {
            if (e instanceof EOFException) {
                System.out.println("No confirmed orders to load.");
            } else {
                e.printStackTrace();
            }
            return new ArrayList<>();
        }
    }
}