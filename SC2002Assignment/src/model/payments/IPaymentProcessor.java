package model.payments;

/**
 * The IPaymentProcessor is a class in the Mode layer.
 * It is part of the Factory Design Pattern for creating and using the new payments
 * 
 * @author Loo Si Hui
 * @version 1.0
 */
public interface IPaymentProcessor {
    boolean payment(double amount);
}
