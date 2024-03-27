package model.menus;

import java.io.Serializable;

public class MenuItem implements Serializable{
    private String name;
    private double price;
    private String category;
    private String branch;
    private SetMealCategory setMealCategory;
    
    public MenuItem(String name, double price, String branch, String category) {
        this.name = name;
        this.price = price;
        this.branch = branch;
        this.category = category;
    }

    public double getPrice(){
        return this.price;
    }

    public String getName(){
        return this.name;
    }

    public String getCategory(){
        return this.category;
    }

    public String getBranch(){
        return this.branch;
    }
}
