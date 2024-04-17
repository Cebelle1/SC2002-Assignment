package model.menus;

import java.io.Serializable;
/**
 * The SetMealCategory is a class in the Model layer to represent a set meal category.
 * SetMealCategory implements Serializable for Serialization of Orders
 * 
 * @author Loo Si Hui
 * @version 1.0
 */
public class SetMealCategory implements Serializable{
    private MenuItem mainDish;
    private MenuItem sideDish;
    private MenuItem drink;

    /**
     * The SetMealCategory constructor takes in 3 MenuItems and sets them to the mainDish, sideDish and drink
     *
     * @param mainDish
     * @param sideDish
     * @param drink
     */
    public SetMealCategory(MenuItem mainDish, MenuItem sideDish, MenuItem drink) {
        this.mainDish = mainDish;
        this.sideDish = sideDish;
        this.drink = drink;
    }

    /**
     * Getter function to get the Main Dish of the SetMealCategory
     *
     * @return MenuItem object
     */
    public MenuItem getMainDish() {
        return mainDish;
    }

    /**
     * Getter function to get the Side Dish of the SetMealCategory
     *
     * @return MenuItem object
     */
    public MenuItem getSideDish() {
        return sideDish;
    }

    /**
     * Getter function to get the Drink of the SetMealCategory
     *
     * @return MenuItem object
     */
    public MenuItem getDrink() {
        return drink;
    }
}
