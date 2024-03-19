package view.abstracts;
import java.util.Scanner;

import view.interfaces.ViewInterface;

public abstract class RenderView implements ViewInterface{

    protected void printBorder(String input){
        clearCLI();
        String space = String.format("%" + (99- input.length()) + "s", "");
        System.out.println(
                "╔════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ " + input + space + "║");
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

    public int getInputInt(){
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter StaffID:");
            //auth
            return scanner.nextInt();   
        } catch (Exception e) {
            System.err.println("Error reading input.");
            return -1;
        }
    }

    public String getInputString(){
        return "1"; // TODO: Implement proper user input
    }
}
