package model.payments;
import view.payments.PaymentView;

/**
 * The Maybank class is a class generated by the GeneratePayment class as
 * a demonstraction of the dynamic payment creation. {@link GenerateNewPayment}
 * @author Loo Si HUi
 * @version 1.0
 */
public class Maybank implements IPaymentProcessor {
    public boolean payment(double amount) {
        System.out.println("Paying with Maybank");
        System.out.printf("Processing $%.2f", amount);
        // Just gonna use the PaymentView for now
        PaymentView pnv = new PaymentView();
        return pnv.confirmPayment();
    }
}
