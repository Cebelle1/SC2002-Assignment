package model;
import model.payments.*;
import model.abstracts.AEmployee;

public class AdminRole extends AEmployee {
    private PaymentMethodFactory paymentMethodFactory = new PaymentMethodFactory();

    public AdminRole(String Name, String StaffID, String Role, String Gender,int Age, String Branch, String Password){
        super(Name, StaffID, Role, Gender,Age, Branch, Password);
    }
    
    //Add your individual role method here like edit staffacc, display staff list etc


    //Not tested, designing.
    public void addPaymentMethod(String type) {
        IPaymentProcessor paymentProcessor = PaymentMethodFactory.createPaymentMethod("model.payments.PayNowPayment");
    }
}
