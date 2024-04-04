package model.menus;

import java.io.Serializable;

public class SetMealCategory implements Serializable{
    private MenuItem mainDish;
    private MenuItem sideDish;
    private MenuItem drink;

    public SetMealCategory(MenuItem mainDish, MenuItem sideDish, MenuItem drink) {
        this.mainDish = mainDish;
        this.sideDish = sideDish;
        this.drink = drink;
    }

    public MenuItem getMainDish() {
        return mainDish;
    }

    public MenuItem getSideDish() {
        return sideDish;
    }

    public MenuItem getDrink() {
        return drink;
    }
}
