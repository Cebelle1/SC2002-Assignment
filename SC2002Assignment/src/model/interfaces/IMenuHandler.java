package model.interfaces;
import model.Branch;
import model.Order;
import model.menus.MenuItem;
import java.util.List;

public interface IMenuHandler {
    void displayMenu(List<Branch> branches);
    void addItemToCart(Order order, MenuItem menuItem);
    void removeItemFromCart(Order order, int itemIndex);
    void createNewOrder(Order order);
}
