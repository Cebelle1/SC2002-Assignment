package model.menus;

import java.io.Serializable;
import java.util.Locale;

import model.Branch;
import model.Order;

public class MenuItem implements Serializable{
    private String name;
    private double price;
    private String branch;
    private SetMealCategory setMeal;
    private String category;
    private String comments;

    public MenuItem(String name, double price, String branch, String category) {
        this.name = name;
        this.price = price;
        this.branch = branch;
        this.category = category.trim();
        //===Basic Menu Items, non setmeal so setting to nulls
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

    public String getRawName(){
        return this.name;
    }

    public String getName(){
        return formatName(this.name);
    }

    public String getFormattedName(){
        return String.format("| %-15s", formatName(this.name));
    }

    public String getCategory(){
        return this.category.trim();
    }

    public String getBranch(){
        return this.branch;
    }

    public SetMealCategory getSetMeal(){
        return this.setMeal;
    }

    public String getComments(){
        return this.comments;
    }

    public void setComments(String comments){
        this.comments = comments;
    }

    private String formatName(String name) {
        String[] words = name.split(" ");

        StringBuilder formattedName = new StringBuilder();
        for (String word : words) {
            formattedName.append(word.substring(0, 1).toUpperCase(Locale.ENGLISH)); // Capitalize first letter
            formattedName.append(word.substring(1).toLowerCase(Locale.ENGLISH)); // Convert remaining letters to
                                                                                 // lowercase
            formattedName.append(" "); // Add space between words
        }

        return formattedName.toString();
    }
}
