package model;
import java.util.List;

import model.menus.*;

public class Branch {

    private String name;
    private List<MenuItem> menu;

    public Branch(String name, List<MenuItem> menu) {
        this.name = name;
        this.menu = menu;
    }

    public List<MenuItem> getMenu() {
        return menu;
    }

    public String getName() {
        return name;
    }

    public void addMenuItem(MenuItem menuItem){
        menu.add(menuItem);
    }

    
}
