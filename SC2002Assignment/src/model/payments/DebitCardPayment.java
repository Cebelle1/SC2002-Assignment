package model.payments;

import view.abstracts.ARenderPayment;
import view.payments.PayNowView;

public class DebitCardPayment implements IPaymentProcessor {

    public void payment(double amount) {
        System.out.println("Paying with Debit Card");
        
        //Just gonna use the PayNowView for now
        PayNowView pnv = new PayNowView();
        System.out.println("Processing");
        pnv.confirmPayment();
    }
}