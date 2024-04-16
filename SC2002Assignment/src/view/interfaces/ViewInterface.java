package view.interfaces;

/**
 * A contract for views in the application to implement.
 * 
 * @author Loo Si Hui
 * @version 1.0
 */

 public interface ViewInterface {

    /**
     * Renders the application based on the provided selection.
     * @param selection The selection made by the user for navigation.
     *                  This method is typically used for app navigation.
     */
    void renderApp(int selection);
    
    /**
     * Renders the choices available to the user.
     * This method is used for printing static strings describing choices.
     */
    void renderChoice();
    
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

