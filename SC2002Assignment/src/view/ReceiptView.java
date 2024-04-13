package view;

import java.util.List;

import model.Order;
import model.menus.MenuItem;
import model.menus.SetMealCategory;

public class ReceiptView {

    public void printReciept() {

        Order orders = Order.getCurrentOrder();
        List<MenuItem> items = orders.getCurrentOrderItems();

        System.out.println("+-------------------------------------+");
        System.out.println("|                                     |");
        System.out.println("|                FOMS                 |");
        System.out.println("|                                     |");
        System.out.println("|              SINGAPORE              |");
        System.out.println("|           (+65) 6875 6431           |");
        System.out.println("|                                     |");
        System.out.printf("|       OrderID :  %-9s                     |", orders.getOrderID());
        for(MenuItem item : items){
            System.out.printf("|        %-15s      %%-7s     $%-15.2f |\n ", item.getName(), item.getQty(), item.getPrice()*item.getQty()); 
            if ("set meal".equals(item.getCategory())) {
                SetMealCategory setMeal = (SetMealCategory) item.getSetMeal();
                System.out.printf("|%8s > Main: %s                    |\n", "", setMeal.getMainDish().getName());
                System.out.printf("|%8s > Side: %s                    |\n", "", setMeal.getSideDish().getName());
                System.out.printf("|%8s > Drink: %s                   |\n", "", setMeal.getDrink().getName());
            }
            System.out.printf("|%8s > Comments: %s                    |\n", "", item.getComments());
        }
        System.out.printf("|     Total:                                |", orders.getAmount());
        System.out.println("|                                     |");
        System.out.println("|             THANK YOU!              |");
        System.out.println("+-------------------------------------+");
    }

}
