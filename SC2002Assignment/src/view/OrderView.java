package view;

import java.util.List;

import model.Order;
import model.menus.MenuItem;
import model.menus.SetMealCategory;
import view.abstracts.ARenderView;

public class OrderView extends ARenderView{

    //Choose an order to display
    public void chooseDisplayCurrentOrder(Order orders) {
        super.printBorder("Order Status");

        if(!displayAllOrder(orders)){
            return;
        }
        // Prompt user to select an order
        int selectedOrderIndex = getInputInt("Enter the order number:") - 1;
        if (selectedOrderIndex < 0 || selectedOrderIndex >= orders.getOrders().size()) {
            System.out.println("Invalid order number.");
            return;
        }

        displayOrderList(orders, selectedOrderIndex);
    }

    //Display all orderID, no menuItems
    public boolean displayAllOrder(Order ordersM) {
        List<Order> orders = ordersM.getOrders(); // Assuming you have access to OrderMenuController and its orders
        if (orders.isEmpty()) {
            System.out.println("No orders available.");
            return false;
        }
        int index = 1;
        for (Order order : orders) {
            System.out.println(index + ". Order " + index);
            index++;
        }
        return true;
    }

    //General display for MenuItems for a single order by inputing OrderID
    public void displayOrderList(Order orders, int selectedOrderIndex) {
        Order selectedOrder = orders.getOrders().get(selectedOrderIndex);
        List<MenuItem> items = selectedOrder.getCurrentOrderItems();
        if (items.isEmpty()) {
            System.out.println("No items in this order.");
        } else {
            super.printBorder(selectedOrder.getDiningMode() + " Order " + selectedOrderIndex+1);
            System.out.println("OrderID.   | Name            |   Qty   |    Unit Price    |    Total Price ");
            for (MenuItem item : items) {
                System.out.printf("%-9s  %-15s | %-7s | $%-15.2f | $%-10.2f\n", 
                    selectedOrder.getOrderID(), 
                    item.getFormattedName(), 
                    item.getQty(), 
                    item.getPrice(),
                    item.getPrice() * item.getQty());
                if ("set meal".equals(item.getCategory())) {
                    SetMealCategory setMeal = (SetMealCategory) item.getSetMeal();
                    System.out.printf("%8s > Main: %s\n", "", setMeal.getMainDish().getName());
                    System.out.printf("%8s > Side: %s\n", "", setMeal.getSideDish().getName());
                    System.out.printf("%8s > Drink: %s\n", "", setMeal.getDrink().getName());

                    
                }
                System.out.printf("%8s > Comments: %s\n", "", item.getComments());
                //System.out.println("         > Comments: " + item.getComments());
            }
        }
    }

    //Display the order status of a single order
    public void chooseDisplayOrderStatus(Order ordersM, int orderID){
        List<Order> orders = ordersM.getOrders(); // Assuming you have access to OrderMenuController and its orders
        if (orders.isEmpty()) {
            System.out.println("No orders available.");
        } else if (orderID < 0 || orderID > orders.size()-1) {
            System.out.println("Invalid order ID.");
        } else {
            Order order = orders.get(orderID);
            super.printDoubleUnderline("OrderID | Status");
            System.out.printf("%-8d | %s\n", order.getOrderID(), order.getOrderStatus());
        }
    }

    public void chooseDisplayCompleteOrderStatus(List<Order> completedOrder, int orderID){

        /*for(Order order : completedOrder){
            if(order.getOrderID() == (orderID)){
                System.out.println(order.getOrderStatus());
            }
        }*/
        Order selectedOrder = completedOrder.get(orderID);
        if(selectedOrder == null){
            System.out.println("Order is Null");
        }
        else{
            System.out.println("Order is " + selectedOrder.getOrderStatus());
        }

    }

    public void displayCustomizeChoice(){
        System.out.println("Do you wish to customize your order?");
        System.out.println("(1) Yes\n(2) No");
    }

    //================Checkout, Payement, Print Receipt Prints==================//
    public void displayCheckout(Order orders){
        super.printDoubleUnderline("Checking Out All Orders");
        for(int i=0; i<orders.getOrders().size(); i++){
            displayOrderList(orders, i);
        }
    }
    
    @Override
    public void renderApp(int selection) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'renderApp'");
    }

    @Override
    public void renderChoice() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'renderChoice'");
    }
    
}
