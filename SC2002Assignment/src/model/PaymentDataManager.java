package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * The PaymentDataManager handles the interaction between the application and the database.
 * It is responsible for reading and writing data to the database textfiles.
 * @author Loo Si Hui
 * @version 1.0
 */
public class PaymentDataManager {
    private static final String rootPath = "SC2002Assignment/src/database/";
    private static final String paymentTxt = "payments.txt";

    /**
     * Adds a new payment method into the payment textfile
     * @param paymentMethod
     */
    public static void appendPaymentMethod(String paymentMethod) {

        
        final String fileName = rootPath + paymentTxt;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(paymentMethod + "\n");
            System.out.println("Payment method appended successfully.");
        } catch (IOException e) {
            System.err.println("Error appending payment method to file: " + e.getMessage());
        }
    }

    /**
     * Remove a payment method from the payment textfile
     * @param paymentMethodToRemove
     */
    public static void removePaymentMethod(String paymentMethodToRemove) {
        String fileName = rootPath + paymentTxt;
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            if (lines.remove(paymentMethodToRemove)) {
                Files.write(Paths.get(fileName), lines);
                System.out.println("Payment method removed successfully.");
            } else {
                System.out.println("Payment method not found in file.");
            }
        } catch (IOException e) {
            System.err.println("Error removing payment method from file: " + e.getMessage());
        }
    }

    /**
     * Loads the payment methods into the application from the payment textfile
     * @return The list of names of the Payment methods
     */
    public static List<String> readPaymentMethods() {
        final String fileName = rootPath + paymentTxt;
        List<String> paymentMethods = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                paymentMethods.add(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return paymentMethods;
    }

}
