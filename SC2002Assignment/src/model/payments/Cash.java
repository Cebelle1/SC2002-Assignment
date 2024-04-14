package model.payments;

import view.payments.PaymentView;

public class Cash implements IPaymentProcessor{
    PaymentView pv = new PaymentView();
    @Override
    public boolean payment(double amount) {
        System.out.printf("Amount to be paid %.2f\n", amount);
        double cash = pv.getInputDouble("Enter cash amount given:");
        while(cash < amount){
            cash = pv.getInputDouble("Insufficient cash, please enter again:");
        }
        System.out.printf("Change: $%.2f\n", cash - amount);
        return true;
    }
    
}
