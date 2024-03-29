package view.abstracts;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import view.interfaces.ViewInterface;

public abstract class RenderView implements ViewInterface{

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

    protected void delay(int sec) {
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
}
