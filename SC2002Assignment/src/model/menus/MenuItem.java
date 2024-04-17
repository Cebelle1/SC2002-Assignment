package model.menus;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Locale;

/**
 * The MenuItem class is a class in the Model layer that represents a Menu Item.
 * MenuItem implements Serializable for Serialization of Orders
 * @author Loo Si Hui
 * @version 1.0
 */
public class MenuItem implements Serializable{
    private String name;
    private String description;
    private double price;
    private String branch;
    private SetMealCategory setMeal;    //Menu has a SetMeal
    private String category;
    private String comments;
    private int qty;

    /**
     * The MenuItem constructor takes in parameters that describe a single menu item
     * @param name 
     * @param description
     * @param price
     * @param branch
     * @param category
     */
    public MenuItem(String name, String description, double price, String branch, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.branch = branch;
        this.category = category.trim();
        //===Basic Menu Items, non setmeal so setting to nulls
        this.setMeal = null;
    }

    /**
     * Overloaded MenuItem constructor for Set Meal
     * @param name
     * @param description
     * @param price
     * @param branch
     * @param category
     * @param setMeal
     */
    public MenuItem(String name, String description, double price, String branch, String category, SetMealCategory setMeal) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.branch = branch;
        this.category = category;
        this.setMeal = setMeal;
    }

    /**
     * Getter function to get the description of the menu item
     * @return The description of the menu item
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * Setter function to set the description of the menu item
     * @param description
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Getter function to get the unit price of the menu item
     * @return The price of the menu
     */
    public double getPrice(){
        return this.price;
    }

    /**
     * Getter function to get the price in $%2.f format
     * @return The formatted price string
     */
    public String getFormattedPriceStr(){
        return formatPrice(this.price);
    }

    /**
     * Setter function to set the price of the menu item
     * @param price
     */
    public void setPrice(double price){
        this.price = price;
    }

    /**
     * Getter function to get the unformatted name of the menu item
     * @return The unformatted name of the menu item
     */
    public String getRawName(){
        return this.name;
    }

    /**
     * Setter function to set the raw name of them menu item
     * @param name
     */
    public void setRawName(String name){
        this.name = name;
    }

    /**
     * Getter function to get the formatted name of the menu item
     * @return The formatted name
     */
    public String getName(){
        return formatName(this.name);
    }

    /**
     * Getter function to get the formatted name with a fixed length of the menu item
     * @return The formatted name with fixed length
     */
    public String getFormattedName(){
        return String.format(" %-15s", formatName(this.name));
    }

    /**
     * Getter function to get the category of the menu item
     * @return The category string
     */
    public String getCategory(){
        return this.category.trim();
    }

    /**
     * Getter function to get the Branch of the menu item
     * @return The branch string
     */
    public String getBranch(){
        return this.branch;
    }

    /**
     * Getter function to get the set meal
     * @return The SetMealCategory object
     */
    public SetMealCategory getSetMeal(){
        return this.setMeal;
    }

    /**
     * Getter function to get the customization instruction
     * @return The customization instruction string
     */
    public String getComments(){
        return this.comments;
    }

    public void setComments(String comments){
        this.comments = comments;
    }

    /**
     * Setter function to set the quantity of the menu item
     * @param qty
     */
    public void setQty(int qty){
        this.qty = qty;
    }

    /**
     * Getter function to get the quantity of the menu item
     * @return The quantity of the menu item
     */
    public int getQty(){
        return this.qty;
    }

    /**
     * Formats the name of the menu item. 
     * Capitalizes the first letter and converts the remaining letter to lower case. Adds space in between words
     * @param name The name to be formatted
     * @return The formatted name
     */
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

    /**
     * Formats the price into $%.2f format
     * @param price The price to be formatted
     * @return The formatted price string
     */
    private String formatPrice(double price){
        return String.format(" $%.2f", price);
    }


    /**
     * Serialize individual attributes
     * @param out
     * @throws IOException
     */
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

    /**
     * Deserialize individual attributes
     * @param in
     * @throws IOException
     * @throws ClassNotFoundException
     */
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
