package model.payments;
import model.interfaces.PaymentProcessor;

public class CreditCardPayment implements PaymentProcessor {
    public void payment(double amount) {
        System.out.println("Paid with Credit Card");
    }
}