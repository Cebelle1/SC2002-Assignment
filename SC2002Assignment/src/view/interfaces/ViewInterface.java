package view.interfaces;

//Interface for all views
public interface ViewInterface {

    void renderApp(int selection);      //For App Navigation (Should be in Controller?)
    void renderChoice();                //For printing statics, i.e Strings of choice description
    int getInputInt(String prompt);
    String getInputString(String prompt);
}
