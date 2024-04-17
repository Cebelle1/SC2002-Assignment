
package view.abstracts;
import java.util.Scanner;

/**
 * The ARenderPayment class is an abstract class that extends ARenderView{@link ARenderView}
 * Provides methods for rendering payment options and confirming payment in a Java application.
 * 
 * @author Loo Si Hui
 * @version 1.0
 */
public abstract class ARenderPayment extends ARenderView{
    public abstract void renderPayment();

    /**
     * Prompts the user to confirm a payment.
     * 
     * @return A true if input is 'y'
     */
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

   /**
    * The `renderApp` function displays payment options for the user to choose from 
    * 
    * @param selection 
    */
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
