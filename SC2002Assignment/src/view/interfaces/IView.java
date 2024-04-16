package view.interfaces;

/**
 * Interface representing a view in a user interface.
 * This interface combines functionality from {@link ViewHelperInterface} and {@link ViewInterface}.
 */
public interface IView extends ViewHelperInterface, ViewInterface{
    /**
     * Renders the application based on the provided selection.
     * This method is typically used for app navigation.
     * @param selection The selection made by the user for navigation.
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
    
    // Inherited methods from ViewHelperInterface and ViewInterface
    // These methods are already documented in the respective interfaces,
    // so we use {@inheritDoc} to inherit their documentation.
    // These comments serve as placeholders for the inherited documentation.
    
    /** {@inheritDoc} */
    void printBorder(String input);
    
    /** {@inheritDoc} */
    void clearCLI();
    
    /** {@inheritDoc} */
    void delay(int sec);
    
    /** {@inheritDoc} */
    void printDoubleUnderline(String input);
    
    /** {@inheritDoc} */
    void printSingleUnderline(String input);
    
    /** {@inheritDoc} */
    void printSingleBorder(String input);
}

