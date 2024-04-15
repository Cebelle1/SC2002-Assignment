package model.payments;

import java.lang.reflect.InvocationTargetException;

import model.BranchDataManager;
import model.Order;
import model.Order.OrderStatus;
import view.payments.PaymentView;
import java.util.List;

public class PaymentMethodFactory {

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
        int paymentMode = pnv.getPaymentMode(BranchDataManager.readPaymentMethods());

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

    private static String getPaymentProcessorClassName(int paymentMode) {
        List<String> paymentMethods = BranchDataManager.readPaymentMethods();
        if (paymentMode >= 1 && paymentMode <= paymentMethods.size()) {
            return "model.payments."+paymentMethods.get(paymentMode - 1);
        } else {
            throw new IllegalArgumentException("Invalid payment method selected.");
        }
    }

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
