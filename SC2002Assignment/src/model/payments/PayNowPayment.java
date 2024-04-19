package model.payments;
import view.payments.PaymentView;
/**
 * The PayNowPayment class is one of the default payment class created statically.
 * This class implements the IPaymentProcessor to fufil the Factory Pattern Design
 * @author Loo Si HUi
 * @version 1.0
 */
public class PayNowPayment implements IPaymentProcessor {
    public boolean payment(double amount) {
        System.out.println("Paid with IPaymentProcessor");
        System.out.printf("Processing $%.2f\n", amount);
        //Just gonna use the PaymentView for now
        PaymentView pnv = new PaymentView();
        return pnv.confirmPayment();
    }
}
