package model;

import java.util.List;

import model.abstracts.AEmployee;
import model.menus.MenuItem;
import model.menus.SetMealCategory;
import view.OrderView;

public class StaffRole extends AEmployee{

    private Order orders = new Order();
    private List<Order> order;
    private OrderView orderV = new OrderView();
    
    public StaffRole(String Name, String StaffID, String Role, String Gender,int Age, String Branch, String Password){
        super(Name, StaffID, Role, Gender,Age, Branch, Password);
    }

    // display new orders -> only the PREPARING one
    public List<Order> displayOrders(){
        return order;
    }

    // view details
    public List<Order> viewDetails(){
        return order;
    }

    // process orders
    public List<Order> processOrder(){
        // call markReady() in Order.java after processing the order
        return order;
    }

    // filter orders by branch
    public List<Order> filterOrdersByBranch(){
        List<Order> confirmedOrders = Order.getConfirmedOrders(); // get the orders
        // Iterate over each order and print its details
        for (Order order : confirmedOrders) {
            List<MenuItem> items= order.getCurrentOrderItems();
            System.out.printf("Order ID: %d ", order.getOrderID());
            for(MenuItem item : items){
                System.out.println(item.getName());
                if ("set meal".equals(item.getCategory())) {
                    SetMealCategory setMeal = (SetMealCategory) item.getSetMeal();
                    System.out.printf("%8s > Main: %s\n", "", setMeal.getMainDish().getName());
                    System.out.printf("%8s > Side: %s\n", "", setMeal.getSideDish().getName());
                    System.out.printf("%8s > Drink: %s\n", "", setMeal.getDrink().getName());

                    
                }
            }
            
        }
        return order;
    }

    //add your staff responsibility here, i.e display new order, view deta2il process order. 
    //for display new order, i am working on the visiblity of the orders. 
}
