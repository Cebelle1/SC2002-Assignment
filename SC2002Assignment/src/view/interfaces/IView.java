package view.interfaces;

public interface IView extends ViewHelperInterface, ViewInterface{
    void renderApp(int selection);      //For App Navigation (Should be in Controller?)
    void renderChoice();                //For printing statics, i.e Strings of choice description
    int getInputInt(String prompt);
    String getInputString(String prompt);
    void printBorder(String input);
    void clearCLI();
    void delay(int sec);
    void printDoubleUnderline(String input);
    void printSingleUnderline(String input);
    void printSingleBorder(String input);
}
