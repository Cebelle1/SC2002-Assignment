package view;
import view.interfaces.ViewInterface;
import java.util.Scanner;

public class OrderView implements ViewInterface{
    Scanner sc = new Scanner(System.in);
    public OrderView(){
        System.out.println("Logged in as Customer!");

    }


    @Override
    public void render(int selection){
        
    }

    @Override   //for String userInput
    public String getUserInput(){
        return "1"; // TODO: Implement proper user input
    }

    @Override   //for int userInput
    public int getUserInput(int overloadInt){
        return 1;
    }
}
