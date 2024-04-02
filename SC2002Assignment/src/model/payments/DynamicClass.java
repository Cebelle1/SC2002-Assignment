package model.payments;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class DynamicClass {


    public static IPaymentProcessor createPaymentClass(String paymentMethodName) throws ClassNotFoundException {
        // Step 1: Define a Template
        String paymentMethodTemplate = "package model.payments;\n" +
                "public class %s implements IPaymentProcessor {\n" +
                "    public void processPayment() {\n" +
                "        // Add payment processing logic here\n" +
                "    }\n" +
                "}\n";

        // Step 2: Generate Java Source Code
        String paymentMethodSource = String.format(paymentMethodTemplate, paymentMethodName);

        // Step 3: Write Source Code to File
        String filePath = "src/model/payments/" + paymentMethodName + ".java";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(paymentMethodSource);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // Step 4: Compile the Source Code
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int compilationResult = compiler.run(null, null, null, filePath);
        if (compilationResult == 0) {
            System.out.println("Compilation successful.");

            // Step 5: Load and Use the New Payment Method
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
