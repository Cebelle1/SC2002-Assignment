package model.payments;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import model.Order;
import model.Order.OrderStatus;
import view.payments.PayNowView;

public class PaymentMethodFactory {

    public static boolean handlePayment(Order orders){
        if(orders.getOrders().size() < 1){  //empty order
            System.out.println("No orders to pay, please checkout first!");
            return false;
        }

        Order currentOrder = Order.getCurrentOrder();
        double amount = currentOrder.getAmount();
        if(currentOrder.getOrderStatus() != OrderStatus.PENDING){   //Status sequence check
            System.out.println("Please checkout first!");
            return false;
        }

        IPaymentProcessor paymentProcessor = null;
        //Using PayNowView for now for Views
        PayNowView pnv = new PayNowView();
        pnv.renderApp(0);
        int paymentMode = pnv.getInputInt("Select payment method: ");
        switch(paymentMode){
            case 1:
                paymentProcessor = PaymentMethodFactory.createPaymentMethod("model.payments.DebitCardPayment");
                break;
            case 2:
                paymentProcessor = PaymentMethodFactory.createPaymentMethod("model.payments.CreditCardPayment");
                break;
            case 3:
                paymentProcessor = PaymentMethodFactory.createPaymentMethod("model.payments.PayNowPayment");
        }

        boolean paid = paymentProcessor.payment(amount);
        
        if(!paid){
            System.out.println("Canceling payment");
            pnv.delay(1);
            return false;
        }else{
            pnv.delay(1, "Processing Payment...");
            currentOrder.confirmOrder();
            System.out.printf("Order Status Now: %s\n", currentOrder.getOrderStatus());
            return true;
        }
        
        /*List<Order> allOrders = orders.getOrders();
        for(Order o: allOrders){
            o.confirmOrder();
            System.out.printf("Order Status Now: %s\n", o.getOrderStatus());
        }*/
    }

    public static IPaymentProcessor createPaymentMethod(String type) {
        try {
            Class<?> paymentMethodClass = Class.forName(type);
            Object paymentMethodObject = paymentMethodClass.getDeclaredConstructor().newInstance();
            if (paymentMethodObject instanceof IPaymentProcessor) {
                return (IPaymentProcessor) paymentMethodObject;
            } else {
                throw new IllegalArgumentException("Class does not implement IPaymentProcessor: " + type);
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalArgumentException("Invalid payment method type: " + type, e);
        } 
    }
}
