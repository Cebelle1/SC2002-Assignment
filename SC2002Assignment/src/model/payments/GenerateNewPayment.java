package model.payments;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import model.DataManager;

public class GenerateNewPayment {

    public static IPaymentProcessor createPaymentClass(String paymentMethodName){
        String className = IPaymentProcessor.class.getSimpleName();

        // Step 1: Define a Template
        String paymentMethodTemplate = "package model.payments;\n" +
        "import view.payments.PaymentView;\n" +
        "public class %s implements IPaymentProcessor {\n" +
        "    public boolean payment(double amount) {\n" +
        "        System.out.println(\"Paid with %s\");\n" +
        "        System.out.printf(\"Processing\", amount);\n" +
        "        // Just gonna use the PaymentView for now\n" +
        "        PaymentView pnv = new PaymentView();\n" +
        "        return pnv.confirmPayment();\n" +
        "    }\n" +
        "}\n";

        // Step 2: Generate Java Source Code
        String paymentMethodSource = String.format(paymentMethodTemplate, paymentMethodName, className);

        // Step 3: Write Source Code to File
        String filePath = "SC2002Assignment/src/model/payments/" + paymentMethodName + ".java";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(paymentMethodSource);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        //Step 4: Update Payment.txt file
        DataManager.appendPaymentMethod(paymentMethodName);

        // Step 5: Compile the Source Code
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int compilationResult = compiler.run(null, null, null, filePath);
        if (compilationResult == 0) {
            System.out.println("Compilation successful.");
            
            // Step 6: Load and Use the New Payment Method
            try {
                Class<?> paymentMethodClass = Class.forName("model.payments." + paymentMethodName);
                IPaymentProcessor paymentMethod = (IPaymentProcessor) paymentMethodClass.getDeclaredConstructor().newInstance();
                return paymentMethod;
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Compilation failed.");
            return null;
        }
        return null;
    }

    public static void createPaymentView(String paymentViewName){
        // Define a Template for Payment View
        String paymentViewTemplate = "package view.payments;\n" +
                "import view.abstracts.ARenderPayment;\n" +
                "public class %s extends ARenderPayment {\n" +
                "    public void renderPayment() {\n" +
                "        System.out.println(\"Paying with <new payment>\");\n" +
                "    }\n" +
                "}\n";

        // Generate Java Source Code for Payment View
        String paymentViewSource = String.format(paymentViewTemplate, paymentViewName);

        // Write Source Code to File
        String filePath = "src/view/payments/" + paymentViewName + ".java";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(paymentViewSource);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Compile the Source Code
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int compilationResult = compiler.run(null, null, null, filePath);
        if (compilationResult == 0) {
            System.out.println("Compilation successful.");
        } else {
            System.out.println("Compilation failed.");
        }
    }
    
}
