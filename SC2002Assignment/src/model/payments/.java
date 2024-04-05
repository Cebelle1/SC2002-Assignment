package model.payments;
import view.payments.PaymentView;
public class  implements IPaymentProcessor {
    public boolean payment(double amount) {
        System.out.println("Paid with IPaymentProcessor");
        System.out.println("Processing " + amount);
        //Just gonna use the PaymentView for now
        PaymentView pnv = new PaymentView();
        return pnv.confirmPayment();
    }
}
