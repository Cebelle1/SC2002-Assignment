package view.payments;

import java.util.List;

import view.abstracts.ARenderPayment;

public class PaymentView extends ARenderPayment {
    public void renderPayment(){
        System.out.println("Processing payment..");
        //confirmPayment();
    }

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
