package view.abstracts;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import view.interfaces.ViewInterface;

public abstract class ARenderView implements ViewInterface{
    Scanner sc;  

    public ARenderView(){
        sc = new Scanner(System.in);
    }

    protected void printBorder(String input){
        clearCLI();
        String space = String.format("%" + (99- input.length()) + "s", "");
        String halfSpace = String.format("%" + (99- input.length())/2 + "s", "");
        System.out.println(
                "╔════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ " +halfSpace + input + halfSpace + "║");
        System.out.println(
                "╚════════════════════════════════════════════════════════════════════════════════════════════════════╝");
    }

    protected void clearCLI(){
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception err) {
            System.out.println("Error clearing screen");
        }
    }

    public void delay(int sec) {
            for(int i=0; i<sec; i++){
                System.out.printf("Returning in %d second\n", sec-i);
                try {TimeUnit.SECONDS.sleep(1); 
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
    }

    protected void printDoubleUnderline(String input){
        String space = String.format("%" + (99- input.length()) + "s", "");
        String halfSpace = String.format("%" + (99- input.length())/2 + "s", "");
        System.out.println(input + space );
        System.out.println(
                "════════════════════════════════════════════════════════════════════════════════════════════════════");
    }
    
    protected void printSingleUnderline(String input){
        String space = String.format("%" + (99- input.length()) + "s", "");
        String halfSpace = String.format("%" + (99- input.length())/2 + "s", "");
        System.out.println(input + space );
        System.out.println(
                "_____________________________________________________________________________________________________");
    }
    
    protected void printSingleBorder(String input){
        String space = String.format("%" + (99- input.length()) + "s", "");
        String halfSpace = String.format("%" + (99- input.length())/2 + "s", "");
        System.out.println(
                "______________________________________________________________________________________________________");
        System.out.println("| " + input + space + "|");
        System.out.println(
                "|____________________________________________________________________________________________________|");
    }

    public int getInputInt(String prompt) {
        if (!prompt.isEmpty()) {
            System.out.println(prompt);
        }
        try {
            int input = sc.nextInt();
            sc.nextLine();
            return input;
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
           // Read a sentence
           String input = sc.nextLine();
           //input = sc.nextLine();
           return input;
        } catch (Exception e) {
            System.err.println("Error receiving input.");
            sc.next();
            return "";
        }
    }
}
