package view;

import java.util.List;
import controller.CustomerController;
import model.Branch;
import model.menus.MenuItem;
import view.abstracts.ARenderView;

/**
 * The MenuView is a class in the View layer to display the menu of a branch.
 *  * This class extends the abstract base view class{@link ARenderView}
 * 
 * @author Loo Si Hui
 * @version 1.0
 */
public class MenuView extends ARenderView{
    CustomerController custCon;
    Branch selectedBranch;

    /**
     * The MenuView constructor takes in a CustomerController dependancy
     * @param controller
     */
    public MenuView(CustomerController controller){
        this.custCon = controller;
    }


    /**
     * Displays the menu by different categorys of Main, Drinks, and Sides
     * @param branchChoice The integer of the selected branch
     * @param branches The list of branches
     */
    public void displayOrganizedMenu(int branchChoice,List<Branch> branches){
        selectedBranch = branches.get(branchChoice);
        super.printBorder("Menu Items in " + selectedBranch.getName());
        displayMain();
        displayDrinks();
        displaySides();
    }

    /**
     * Displays the full menu without categorization
     * @param inputChoice The selected branch
     * @param branches The list of branches
     */
    public void displayMenu(int inputChoice, List<Branch> branches) {
        int index = 1;
        selectedBranch = branches.get(inputChoice);
        
        List<MenuItem> menu = selectedBranch.getMenu();

        super.printBorder("Menu Items in " + selectedBranch.getName());
        System.out.println("No.   | Name            |     Price     |   Description");
        System.out.println("--------------------------------------------------------");

        // Print menu items
        for (MenuItem item : menu) {
            // Format item name and price
            //String formattedName = formatName(item.getName());
            String formattedName = item.getFormattedName();
            String formattedPrice = item.getFormattedPriceStr();
            String descString = item.getDescription();
            System.out.printf("%-5d | %-15s | %13s | %s\n", index, formattedName, formattedPrice, descString);
            index++;
        }
    }

//=================Category=================//
    /**
     * Displays the menu by Drink Category
     */
    public void displayDrinks() {
        List<MenuItem> menu = selectedBranch.getMenu();
        int index = 1;
        super.printSingleBorder(("Category: Drinks"));
        System.out.println("No.   | Name            |     Price     |   Description");
        for (MenuItem item : menu) {
            if ("drink".equals(item.getCategory())) {
                //String formattedName = formatName(item.getName());
                String formattedName = item.getFormattedName();
                String formattedPrice = item.getFormattedPriceStr();
                String descString = item.getDescription();
                System.out.printf("%-5d | %-15s | %13s | %s\n", index, formattedName, formattedPrice, descString);
                index++;
            }
        }
    }
    
    /**
     * Displays the menu by Side Category
     */
    public void displaySides() {
        List<MenuItem> menu = selectedBranch.getMenu();
        int index = 1;
        super.printSingleBorder("Category: Sides");
        System.out.println("No.   | Name            |     Price     |   Description");
        for (MenuItem item : menu) {
            if ("side".equals(item.getCategory())) { 
                //String formattedName = formatName(item.getName());
                String formattedName =item.getFormattedName();
                String formattedPrice = item.getFormattedPriceStr();;
                String descString = item.getDescription();
                System.out.printf("%-5d | %-15s | %13s | %s\n", index, formattedName, formattedPrice, descString);
                index++;
            }
        }
    }

    /**
     * Display the menu by Main Category,
     * which is classified as any Menu Item that does not belong in Sides or Drinks
     */
    public void displayMain(){
        List<MenuItem> menu = selectedBranch.getMenu();
        int index = 1;
        super.printSingleBorder("Category: Mains");
        System.out.println("No.   | Name            |     Price     |   Description");
        for (MenuItem item : menu) {
            if (!"side".equals(item.getCategory()) && !"drink".equals(item.getCategory()) && !"set meal".equals(item.getCategory())) {//All but side and drink
                //String formattedName = formatName(item.getName());
                String formattedName = item.getFormattedName();
                String formattedPrice = item.getFormattedPriceStr();
                String descString = item.getDescription();
                System.out.printf("%-5d | %-15s | %13s | %s\n", index, formattedName, formattedPrice, descString);
                index++;
            }
        }
    }

    /**
     * Displays the menu by Main Category with Title Border
     */
    public void displayMains(){
        super.printBorder("Menu in " + selectedBranch.getName());
        displayMain();
    }


    @Override
    public void renderApp(int selection) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'renderApp'");
    }

    @Override
    public void renderChoice() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'renderChoice'");
    }
}
