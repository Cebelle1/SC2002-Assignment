package view.payments;

import java.util.List;

import view.abstracts.ARenderPayment;

/** 
 * The PaymentView class is a view class for handling payment-related functionalities.
 * 
 * This class extends the abstract base view class{@link ARenderPayment}
 * 
 * @author Loo Si Hui
 * @version 1.0
 */
public class PaymentView extends ARenderPayment {

    /**
     * Renders the payment process.
     */
    public void renderPayment(){
        System.out.println("Processing payment..");
    }

    /**
     * Prints a QR code.
     */
    public void printQR(){
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("░░▓▒▒▒▒▓▒▒░▒▒▒▒░▓▓░░▒░░▒░░▓░▓▓▓▒░▒▒▒▓▒▒▒▒░▓▒▒▒▒▓░░");
        System.out.println("░░▓░▓▓░▓░▒░▒▒▓▓▒▓▓▒▓▓▓░▓▒▒▒▒▒▒▒░░▒▒▒▓░▒▒▒░▓░▓▓░▓░░");
        System.out.println("░░▓▒▒▒▒▓▒▒▒▒▒▒░▓▒░▓▒▒▒▒▓░░▓░▒▓▒▒░▒▒▒▒▒▒▓░░▓▒▒▒▒▓░░");
        System.out.println("░░░░▒▒░▒░░▒▓▒▓▓▓▒▓▓▓▒░▒▓▓▓▓▒▒▒▒▓▒▒▓▒░▓▓▓▒▒░▒▓░▒▒░░");
        System.out.println("░░▓▒▓▒▓▒▓▓▓▒▒▒▓░▒▒░▓░▒▒░▒▒▒░▓▓▒▒▓▒▒░░░▓░▒▒▓▒▒▒▒▒░░");
        System.out.println("░░▒▒▓▓░▒▒▓▒▒▒░▒▓▒▒▓▒▓▓▓▒▒▒▒▒▒░░▓▒░▒░░▒▓▒░▒░░▒▒▒▓░░");
        System.out.println("░░▒▒▒▒░▒▒░░▓▒▓▓▒▒▒▒░▒▒▒▒▓▒▒░▒▒░▒░░░▓░▒▓▒▒▒░▓▒░▒▒░░");
        System.out.println("░░▓░▒▓▒▒▓▒░▒▒▒▒▒▒▒▒▓▓▒▒▒▒▒▓░▒▒░░▒▓▒▒▒▓▓▓▒░░▓▒░▒▒░░");
        System.out.println("░░▒▓▒▒▒▒▓░░░▒▒▒▒▒▒▒▓▒▒▒▒▓▒▒▓▒▒░░▒░▒▒░▒▓░▒▓▒▒▓▒▒░░░");
        System.out.println("░░░▒▒▒▒▒▓▓▓▒▒▒▓▓▒▒▒▒▒░▓░▒░▒░░░░░▓░░▒▒▒▓░░▓▓▒▒▒▒░░░");
        System.out.println("░░▒░▓▓▓▒▓▒░░▓░▓▓░▒▒▒▒▓▓▓░▓░░░░░░░░▓▒▒▒▒▓▒▓▒▓▓▒░░░░");
        System.out.println("░░▒▒▒▓░▒▒▒▒▓▓▒▒▒▒▒▒▒░▒▒▒▒▓▒▒░▒░░▒▒▒▒░▓▓▒▒▒▒░▓▒▒▒░░");
        System.out.println("░░▓▒▒▒▓▒▒▓▒▒▓▒░▒▒▒▒▒▓▒▒▒▒▒▓▒▓▓▓░▒▓▓▓▒▒▓▒▒▓▒▒▓░░▒░░");
        System.out.println("░░▓▒▒▒░▒▒▒▒░░░▒▓▓▒░░▒░▒▒▒▒░░▒░░░▒░▒▒░░▒░░▒▓▓▒▒▓░░░");
        System.out.println("░░░▒▒▒▒▒▒▓▓▒▓▒▒▒▓▒▒▒▒░▒▒▓▒▒▓▓▒░▒▓▒▓▒░▒░░▒▓▓▓▓▒░▒░░");
        System.out.println("░░▒▒▓▓▒▒▒▒▓▒▒░▒▒▒▒░▒▒▒▒░▓▒▓▒▒▒▒▓▒░▒▒▒░▒▓░▒░▓▒▒░░░░");
        System.out.println("░░▒░▒▒▓▒░▒▓▒░▒▒▓░░▒░▓▒▓▒▒▓░▒▓░▒▓▓▒▒▒░▓░▓▓▒▒▒░▒▓▓░░");
        System.out.println("░░▒▓▒▒▒▒▒▓▒░▒▒▓▓░▒▒▒▒▒▓░░▒▓▓▓░▒▒▒░░▒░▒▓▒░░░▓▒▒▒▒░░");
        System.out.println("░░▒▓▓▒▓▒▓▒▒░▒▒░▓▒▒▒▓▒▒▓▒░░▒▓▒▒▒▓▒▒▒▒▒▓▒░░▒▓▓▒▒▒▒░░");
        System.out.println("░░░░░░░▒▒▒▓▒▒░▒▓▒▓▒▓▒░░▒▒▒▓▓▓▒░▒░░▒▒░░▓░░▓▓▓▒░▓▒░░");
        System.out.println("░░▓▒▒▒▒▓░▒▓▒▒▒▒▒▒▒░▒▒▒▓▓░░▓▒▓▒▒░▒▓▒▓▒▒▓▒▒▒▒░▓░░▒░░");
        System.out.println("░░▓░▓▓░▓░▒▒▓▒▒▒▒▒▒▒▓▓▒▒▓▒▒▓▒▒▒▓▓▒░▒▒░▒▓▒▒▓▓▓▓▒░▓░░");
        System.out.println("░░▓▒▒▒▒▓░░░▓░▒▓▒▒▒░▓▓▒▓░▒▒▒▓░░▒▓▒▒▒▒░░▒▒▒▒░▓▒▒▓▒░░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
    }

     /**
     * Gets the user's choice of payment mode.
     *
     * @param paymentMethods The list of available payment methods.
     * @return The user's selected payment mode.
     */
    public int getPaymentMode(List<String> paymentMethods){
        if (paymentMethods.isEmpty()) {
            System.out.println("No payment methods available.");
            return 0;
        }

        System.out.println("Select payment method:");
        for (int i = 0; i < paymentMethods.size(); i++) {
            System.out.printf("(%d) %s\n", i + 1, paymentMethods.get(i));
        }

        int userInput;
        while (true) {
            userInput = getInputInt("Enter your choice: ");
            if (userInput < 1 || userInput > paymentMethods.size()) {
                System.out.println("Invalid choice. Please try again.");
            } else {
                break;
            }
        }
        return userInput;
    }

     /**
     * Gets the user's choice of payment method name.
     *
     * @param paymentMethods The list of available payment methods.
     * @return The user's selected payment method name.
     */
    public String getPaymentName(List<String> paymentMethods) {
        if (paymentMethods.isEmpty()) {
            System.out.println("No payment methods available.");
            return null;
        }
    
        System.out.println("Select payment method:");
        for (int i = 0; i < paymentMethods.size(); i++) {
            System.out.printf("(%d) %s\n", i + 1, paymentMethods.get(i));
        }
    
        int userInput;
        while (true) {
            userInput = getInputInt("Enter your choice: ");
            if (userInput < 1 || userInput > paymentMethods.size()) {
                System.out.println("Invalid choice. Please try again.");
            } else {
                break;
            }
        }
        return paymentMethods.get(userInput - 1);
    }
}
