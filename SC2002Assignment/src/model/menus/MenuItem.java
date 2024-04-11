package model.menus;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Locale;

import model.Branch;
import model.Order;

public class MenuItem implements Serializable{
    private String name;
    private String description;
    private double price;
    private String branch;
    private SetMealCategory setMeal;    //Menu has a SetMeal
    private String category;
    private String comments;
    private int qty;

    public MenuItem(String name, String description, double price, String branch, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.branch = branch;
        this.category = category.trim();
        //===Basic Menu Items, non setmeal so setting to nulls
        this.setMeal = null;
    }

    public MenuItem(String name, String description, double price, String branch, String category, SetMealCategory setMeal) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.branch = branch;
        this.category = category;
        this.setMeal = setMeal;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }


    public double getPrice(){
        return this.price;
    }

    public String getFormattedPriceStr(){
        return formatPrice(this.price);
    }

    public void setPrice(double price){
        this.price = price;
    }

    public String getRawName(){
        return this.name;
    }

    public void setRawName(String name){
        this.name = name;
    }

    public String getName(){
        return formatName(this.name);
    }

    public String getFormattedName(){
        return String.format(" %-15s", formatName(this.name));
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

    public void setQty(int qty){
        this.qty = qty;
    }

    public int getQty(){
        return this.qty;
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

    private String formatPrice(double price){
        return String.format(" $%.2f", price);
    }


    // Implement custom serialization and deserialization methods
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(name);
        out.writeObject(description);
        out.writeDouble(price);
        out.writeObject(branch);
        out.writeObject(setMeal);
        out.writeObject(category);
        out.writeObject(comments);
        out.writeInt(qty);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        description =  (String) in.readObject();
        price = in.readDouble();
        branch = (String) in.readObject();
        setMeal = (SetMealCategory) in.readObject();
        category = (String) in.readObject();
        comments = (String) in.readObject();
        qty = in.readInt();
    }
}
