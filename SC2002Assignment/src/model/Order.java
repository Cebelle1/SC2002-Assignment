package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import model.menus.MenuItem;

public class Order implements Serializable{
    private List<MenuItem> items = new ArrayList<>();
    private double total = 0;

    public void addItem(MenuItem item) {
        items.add(item);
        
    }

    public List<MenuItem> getCurrentOrder() {
        return items;
    }
}
