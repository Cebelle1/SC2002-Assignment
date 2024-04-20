/**
 * The `CustomerHomePageView` class extends an abstract class and provides methods for rendering the
 * customer home page view and collecting orders.
 * 
 * @author Loo Si Hui
 * @version 1.0
 */
package view;
import view.abstracts.ARenderView;

public class CustomerHomePageView extends ARenderView{
    
    /**
     * The default constructor for CustomerHomePageView
     */
    public CustomerHomePageView(){
    }

    /**
     * Prints the title border for order collection page
     */
    public void collectOrder(){
        super.printBorder("Order Collection");
    }

    /**
     * Overrided method for rendering the customer home page view
     *
     * @param selection Selects the page to render from the switch case
    
     * 
     */
    @Override
    public void renderApp(int selection){
        switch(selection){
            case 0: //Render the option
                renderChoice();
                break;
        }
    }

    /**
     * Overrided method for rendering the available choice for Customer login\
     * Valid values are:
     *    <ul>
     *       <li>0: Check Order Status using OrderID.</li>
     *       <li>1: Place a New Order.</li>
     *       <li>2: Collect ORder by OrderID.</li>
     *    </ul>
     */
    @Override
    public void renderChoice(){
        super.printBorder("Customer Home Page View");
        System.out.println("(1) Check Order Status");
        System.out.println("(2) Place a New Order");
        System.out.println("(3) Collect Order By ID");
    }

}
