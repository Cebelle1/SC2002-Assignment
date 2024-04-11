package view;

import java.util.List;
import java.util.Locale;

import controller.CustomerController;
import model.Branch;
import model.menus.MenuItem;
import view.abstracts.ARenderView;

public class MenuView extends ARenderView{
    CustomerController custCon;
    Branch selectedBranch;

    public MenuView(CustomerController controller){
        this.custCon = controller;
    }


    public void displayOrganizedMenu(int branchChoice,List<Branch> branches){
        selectedBranch = branches.get(branchChoice);
        super.printBorder("Menu Items in " + selectedBranch.getName());
        displayMain();
        displayDrinks();
        displaySides();
    }

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
