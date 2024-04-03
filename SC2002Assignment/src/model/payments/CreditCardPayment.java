package model.payments;

import view.payments.PayNowView;

public class CreditCardPayment implements IPaymentProcessor {
    public boolean payment(double amount) {
        System.out.println("Paid with Credit Card");

        //Just gonna use the PayNowView for now
        PayNowView pnv = new PayNowView();
        System.out.println("Processing");
        return pnv.confirmPayment();
    }
}