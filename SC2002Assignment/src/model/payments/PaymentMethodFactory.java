package model.payments;

import java.lang.reflect.InvocationTargetException;
import model.Order;
import model.Order.OrderStatus;
import model.PaymentDataManager;
import view.payments.PaymentView;
import java.util.List;

/**
 * The PaymentMethodFactory class handles the payment features.
 * It is responsible for creating the payment processor instance based on the user selection.
 * 
 * @author Loo Si Hui
 * @version 1.0
 */
public class PaymentMethodFactory {

    /**
     * Dynamically handles payment for an order with state checks.
     * @param orders
     * @return Whether payment has been successfully made
     */
    public static boolean handlePayment(Order orders) {
        if (orders.getOrders().isEmpty()) {
            System.out.println("No orders to pay, please checkout first!");
            return false;
        }

        Order currentOrder = Order.getCurrentOrder();
        double amount = currentOrder.getAmount();
        if (currentOrder.getOrderStatus() != OrderStatus.PENDING) {
            System.out.println("Please checkout first!");
            return false;
        }

        PaymentView pnv = new PaymentView();
        int paymentMode = pnv.getPaymentMode(PaymentDataManager.readPaymentMethods());

        IPaymentProcessor paymentProcessor = null;
        try {
            // Dynamically create payment processor instance based on user selection
            String className = getPaymentProcessorClassName(paymentMode);
            paymentProcessor = PaymentMethodFactory.createPaymentMethod(className);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }

        boolean paid = paymentProcessor.payment(amount);

        if (!paid) {
            System.out.println("Canceling payment");
            pnv.delay(1);
            return false;
        } else {
            pnv.delay(1, "Processing Payment...");
            currentOrder.confirmOrder();
            System.out.printf("Order Status Now: %s\n", currentOrder.getOrderStatus());
            return true;
        }
    }

    /**
     * Getter function to get the payment processor class name
     * @param paymentMode
     * @return Payment processor class name
     */
    private static String getPaymentProcessorClassName(int paymentMode) {
        List<String> paymentMethods = PaymentDataManager.readPaymentMethods();
        if (paymentMode >= 1 && paymentMode <= paymentMethods.size()) {
            return "model.payments."+paymentMethods.get(paymentMode - 1);
        } else {
            throw new IllegalArgumentException("Invalid payment method selected.");
        }
    }

    /**
     * Handles the dynamic creation of new payment method
     * @param type
     * @return IPaymentProcessor object of the newly created payment
     */
    public static IPaymentProcessor createPaymentMethod(String type) {
        try {
            Class<?> paymentMethodClass = Class.forName(type);  //Create instance of object dynamically
            Object paymentMethodObject = paymentMethodClass.getDeclaredConstructor().newInstance();
            if (paymentMethodObject instanceof IPaymentProcessor) {
                return (IPaymentProcessor) paymentMethodObject;
            } else {
                throw new IllegalArgumentException("Class does not implement IPaymentProcessor: " + type);
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException
                | InvocationTargetException | NoSuchMethodException e) {
            // throw new IllegalArgumentException("Invalid payment method type: " + type, e);
            GenerateNewPayment.createPaymentClass(type);
        }
        return null;
    }
}
