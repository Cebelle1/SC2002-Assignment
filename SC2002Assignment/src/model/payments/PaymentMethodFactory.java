package model.payments;

import java.lang.reflect.InvocationTargetException;

//Not Tested, still designing
public class PaymentMethodFactory {

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
