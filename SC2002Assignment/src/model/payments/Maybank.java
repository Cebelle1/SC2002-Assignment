package model.payments;
import view.payments.PaymentView;
public class Maybank implements IPaymentProcessor {
    public boolean payment(double amount) {
        System.out.println("Paying with Maybank");
        System.out.printf("Processing $%.2f", amount);
        // Just gonna use the PaymentView for now
        PaymentView pnv = new PaymentView();
        return pnv.confirmPayment();
    }
}