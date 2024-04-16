/**
 * The abstract class AController defines a constructor and an abstract method navigate for navigating
 * to different pages.
 * 
 * @author Loo Si Hui
 * @version 1.0
 */
package controller.abstracts;

/**
 * Abstract base class for controllers.
 * This class defines common functionality for controllers in the application.
 */
public abstract class AController {
    
    /**
     * Constructs a new instance of AController.
     * This constructor is provided for subclasses.
     */
    public AController() {}
    
    /**
     * Navigates to the specified page.
     * Subclasses must implement this method to define the navigation behavior.
     * @param page The page number or identifier to navigate to.
     */
    public abstract void navigate(int page);
}
