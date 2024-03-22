package controller;

import java.util.ArrayList;
import java.util.List;

import controller.abstracts.AController;
import model.Branch;
import model.menus.MenuItem;
import model.DataLoader;
import model.Order;

public class OrderMenuController extends AController{
    private List<Branch> branches;

    public OrderMenuController(){
        branches = new ArrayList<>();
        //loadMenuItemsIntoBranches();
    }

    /*private void loadMenuItemsIntoBranches() {
        // Load menu items from file
        List<MenuItem> menuItems = DataLoader.loadBranches("menu.txt"); // Adjust file path as needed

        
        // Iterate over menu items and add them to appropriate branches
        for (MenuItem menuItem : menuItems) {
            boolean branchExists = false;
            // Check if the branch already exists
            for (Branch branch : branches) {
                if (branch.getName().equals(menuItem.getBranch())) {
                    branch.addMenuItem(menuItem);
                    branchExists = true;
                    break;
                }
            }
            // If the branch doesn't exist, create a new branch and add menu item
            if (!branchExists) {
                Branch newBranch = new Branch(menuItem.getBranch(), new ArrayList<>());
                newBranch.addMenuItem(menuItem);
                branches.add(newBranch);
            }
        }
    }*/

    @Override
    public void navigate(int page) {
        
    }
    
}
