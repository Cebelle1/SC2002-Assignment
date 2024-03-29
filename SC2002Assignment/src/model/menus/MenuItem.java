package model.menus;

import java.io.Serializable;

import model.Branch;
import model.Order;

public class MenuItem implements Serializable{
    private String name;
    private double price;
    private String branch;
    private SetMealCategory setMeal;
    private String category;

    public MenuItem(String name, double price, String branch, String category) {
        this.name = name;
        this.price = price;
        this.branch = branch;
        this.category = category;
        this.setMeal = null;
    
    }

    public MenuItem(String name, double price, String branch, String category, SetMealCategory setMeal) {
        this.name = name;
        this.price = price;
        this.branch = branch;
        this.category = category;
        this.setMeal = setMeal;
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

    public SetMealCategory getSetMeal(){
        return this.setMeal;
    }
}
