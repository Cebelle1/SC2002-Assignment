package view.abstracts;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
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

    protected void delay(int sec) {
            for(int i=0; i<sec; i++){
                System.out.printf("Returning in %d second\n", sec-i);
                try {TimeUnit.SECONDS.sleep(1); 
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
            
       
    }

    
}
