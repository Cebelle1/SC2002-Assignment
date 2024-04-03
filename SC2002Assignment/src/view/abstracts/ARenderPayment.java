package view.abstracts;
import java.util.Scanner;

import model.payments.IPaymentProcessor;
import model.payments.PaymentMethodFactory;

public abstract class ARenderPayment extends ARenderView{
    public abstract void renderPayment();

    public boolean confirmPayment(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Confirm Payment? (y/n)");
        String choice = sc.next();
        if(choice.equalsIgnoreCase("y")){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void renderApp(int selection) {
        super.clearCLI();
        super.printBorder("Payment Options");
        System.out.println("(1) Debit Card");
        System.out.println("(2) Credit Card");
        System.out.println("(3) PayNow");
    }

    @Override
    public void renderChoice() {
        
        
    }

}
