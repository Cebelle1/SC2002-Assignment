package model.payments;

import view.payments.PayNowView;

public class PayNowPayment implements IPaymentProcessor {
    public void payment(double amount) {
        System.out.printf("Please scan the QR code to pay $%f\n", amount);
        PayNowView pnv = new PayNowView();
        pnv.renderPayment();
    }
    
}