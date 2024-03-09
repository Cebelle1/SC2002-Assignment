package view.interfaces;

//Interface for all views
public interface ViewInterface {

    //Handles user input from the CLI
    String getUserInput(); 
    int getUserInput(int overloadInt);
    void render(int selection);
}
