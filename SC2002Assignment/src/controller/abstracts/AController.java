package controller.abstracts;
import java.util.Scanner;

public abstract class AController {
    Scanner sc;  

    public AController(){
        sc = new Scanner(System.in);
    }
    public abstract void navigate(int page);

    public int getInputInt(String prompt) {
        if (!prompt.isEmpty()) {
            System.out.println(prompt);
        }
        try {
            return sc.nextInt();
        } catch (Exception e) {
            System.err.println("Please enter an integer.");
            sc.nextLine(); // Consume invalid input to prevent infinite loop
            return -1;
        }
    }

    public String getInputString(String prompt){
        if (!prompt.isEmpty()) {
            System.out.println(prompt);
        }
        try {
            return sc.next();
        } catch (Exception e) {
            System.err.println("Error receiving input.");
            sc.next();
            return "";
        }
    }

}
