package model.payments;
import view.payments.PaymentView;

/**
 *  A payment option of using Debit Card to pay for the order. 
 *  This class implements the IPaymentProcessor to fufil the Factory Pattern Design

 *  @author  Loo Si Hui
 *  @version 1.0
 */

public class DebitCardPayment implements IPaymentProcessor {

    /**
     * Function to handle payment
     * @return A boolean to confirm or cancel payment
     */
    public boolean payment(double amount) {
        System.out.println("Paying with Debit Card");
        
        //Just gonna use the PayNowView for now
        PaymentView pnv = new PaymentView();
        System.out.println("Processing");
        return pnv.confirmPayment();
    }
}