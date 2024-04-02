package model.payments;

import view.payments.PayNowView;

public class CreditCardPayment implements IPaymentProcessor {
    public void payment(double amount) {
        System.out.println("Paid with Credit Card");

        //Just gonna use the PayNowView for now
        PayNowView pnv = new PayNowView();
        System.out.println("Processing");
        pnv.confirmPayment();
    }
}