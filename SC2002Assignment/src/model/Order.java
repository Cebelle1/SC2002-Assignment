package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import model.menus.MenuItem;

public class Order implements Serializable{
    private List<MenuItem> items = new ArrayList<>();
    private double total = 0;
    private String diningMode = null;

    public void addItem(MenuItem item) {
        items.add(item);
        
    }

    public List<MenuItem> getCurrentOrder() {
        return items;
    }

    public void setDiningMode(int dineMode){
        switch(dineMode){
            case 1: //Take out
                this.diningMode="Take Away";
                break;
            case 2: //Dine in
                this.diningMode="Dine In";
                break;
            default:
                System.out.println("Invalid Dining Mode");
        }
    }
}
