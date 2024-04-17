package view;

import java.util.List;

import model.Order;
import model.menus.MenuItem;
import model.menus.SetMealCategory;
import view.abstracts.ARenderView;


/**
 * ReceiptView prints the receipt of the Order
 * 
 * This class extends the abstract base view class{@link ARenderView}
 * 
 * @author Loo Si Hui, Sharmilla, Shu Fang
 * @version 1.0
 */

public class ReceiptView extends ARenderView{

    /**
     * Print the reciept with the order info.
     * @param order
     */
    public static void printReciept(Order order) {

        List<MenuItem> items = order.getCurrentOrderItems();
        System.out.println("+-------------------------------------+-------------------------------------+");
        System.out.println("|                                                                           |");
        System.out.println("|                                   FOMS                                    |");
        System.out.println("|                                                                           |");
        System.out.println("|                             Orchard, SINGAPORE                            |");
        System.out.println("|                              (+65) 6875 6431                              |");
        System.out.println("|                                                                           |");
        System.out.println("|  OrderID.   | Name            |   Qty   |    Unit Price    |  Total Price |");
        System.out.println("|-------------------------------------|-------------------------------------|");
        for (MenuItem item : items) {
            System.out.printf("| %-9s  | %-15s | %-7s | $%-15.2f | $%-13.2f |\n", 
                order.getOrderID(), 
                item.getFormattedName(), 
                item.getQty(), 
                item.getPrice(),
                item.getPrice() * item.getQty());
            if ("set meal".equals(item.getCategory())) {
                SetMealCategory setMeal = (SetMealCategory) item.getSetMeal();
                System.out.printf("| %8s > Main: %-53s |\n", "", setMeal.getMainDish().getName());
                System.out.printf("| %8s > Side: %-53s |\n", "", setMeal.getSideDish().getName());
                System.out.printf("| %8s > Drink: %-53s |\n", "", setMeal.getDrink().getName());
            }
            System.out.printf("| %8s > Comments: %-53s |\n", "", item.getComments());
        }
        System.out.println("|-------------------------------------|-------------------------------------|");
        System.out.printf("|                                                       Subtotal: $%-10.2f  |\n", order.getSubtotal());
        System.out.printf("|                                                       Total: $%-10.2f     |\n", order.getAmount());
        System.out.println("|                                     |                                     |");
        System.out.println("|                                 THANK YOU!                                |");
        System.out.println("+-------------------------------------+-------------------------------------+");



    }

    /**
     * Renders the application interface.
     *
     * @param selection The user's selection.
     * @throws UnsupportedOperationException if the method is unimplemented.
     */
    @Override
    public void renderApp(int selection) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'renderApp'");
    }

    /**
     * Renders the user's choices.
     *
     * @throws UnsupportedOperationException if the method is unimplemented.
     */
    @Override
    public void renderChoice() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'renderChoice'");
    }

}
