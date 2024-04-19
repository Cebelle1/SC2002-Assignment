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

}

