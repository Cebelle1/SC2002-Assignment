package model.payments;

import view.payments.PaymentView;

public class DebitCardPayment implements IPaymentProcessor {

    public boolean payment(double amount) {
        System.out.println("Paying with Debit Card");
        
        //Just gonna use the PayNowView for now
        PaymentView pnv = new PaymentView();
        System.out.println("Processing");
        return pnv.confirmPayment();
    }
}