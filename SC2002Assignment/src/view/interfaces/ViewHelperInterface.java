package view.interfaces;

/**
 * Defines methods for interacting with the view in a user interface.
 * 
 * @author Loo Si Hui
 * @version 1.0
 */


public interface ViewHelperInterface {
    /**
     * Prints a border with the given input string.
     * @param input The input string used to generate the border.
     */
    void printBorder(String input);
    
    /**
     * Clears the command-line interface (CLI).
     * This method is used to clear the output on the CLI.
     */
    void clearCLI();
    
    /**
     * Delays execution for the specified number of seconds.
     * @param sec The number of seconds to delay execution.
     */
    void delay(int sec);
    
    /**
     * Prints a double underline with the given input string.
     * @param input The input string to underline.
     */
    void printDoubleUnderline(String input);
    
    /**
     * Prints a single underline with the given input string.
     * @param input The input string to underline.
     */
    void printSingleUnderline(String input);
    
    /**
     * Prints a single border around the given input string.
     * @param input The input string to enclose in a border.
     */
    void printSingleBorder(String input);

    /**
     * Prompts the user for an integer input and returns it.
     * @param prompt The prompt message to display to the user.
     * @return The integer inputted by the user.
     */
    int getInputInt(String prompt);
    
    /**
     * Prompts the user for a string input and returns it.
     * @param prompt The prompt message to display to the user.
     * @return The string inputted by the user.
     */
    String getInputString(String prompt);
    
}
