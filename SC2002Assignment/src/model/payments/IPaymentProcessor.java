package model.payments;

public interface IPaymentProcessor {
    boolean payment(double amount);
}
