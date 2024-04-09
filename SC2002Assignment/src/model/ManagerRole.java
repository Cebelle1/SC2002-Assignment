package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.abstracts.AEmployee;
import model.menus.MenuItem;
import model.Branch;
import model.DataManager;


public class ManagerRole extends StaffRole{ 

    private List<Branch> branches;

    public ManagerRole(String Name, String StaffID, String Role, String Gender,int Age, String Branch, String Password){
        super(Name, StaffID, Role, Gender,Age, Branch, Password);
    }

    // To load the Branch data
    public Branch loadBranches(){
        branches = DataManager.loadMenuIntoBranches("menu_list.txt");
        for(Branch selectedBranch : branches)
        {
            if(selectedBranch.getName().equals(this.getBranch()))
            {
                return selectedBranch;
            }
        }
        return null;
    }

    //Add your individual role method here like perform whatever staff can do, actlly alr inherited
    //for display branch staff, you can use smth like "Branch selectedBranch = branches.get(branchChoice);", tho currently the staff and manager arritbute still not added
    //if you need the OrderStatus.STATUS, import model.Order.OrderStatus;

    public List<AEmployee> retrieveBranchStaff(Branch branch){
        // Retrieve the employees from the branch
        return branch.getEmployees();

    }

    // -------------------- Edit menu.txt ----------------------------

    // Add menu item 
    public void addItem(MenuItem itemName, Branch selectedBranch){
        //System.out.println(selectedBranch.getName());
        selectedBranch.addMenuItem(itemName);
        // Update the menu list
        DataManager.addItemToMenu(itemName);
        System.out.println("New item added to menu");
    }

    // Remove menu item
    public void removeItem(String itemName, Branch selectedBranch){
        List<MenuItem> menu = selectedBranch.getMenu();
        for(MenuItem item : menu){
            if(item.getRawName().equals(itemName)){
                selectedBranch.removeMenuItem(item);
                DataManager.removeItemFromMenu(item);
                System.out.println("Item removed");
                break;
            }
        }
    }

    // Edit item name
    public void editItemName(String oldName, String itemName, Branch selectedBranch){
        List<MenuItem> menu = selectedBranch.getMenu();
        for(MenuItem item : menu){
            // Check if item was in the list before editing
            if(oldName.equals(item.getRawName()))
            {
                // Edit name
                DataManager.editItemName(oldName, itemName);
                System.out.println("Item name edited");
                break;
            }
        }
    }


    // Edit item price
    public void editItemPrice(String itemName, double price, Branch selectedBranch){
        List<MenuItem> menu = selectedBranch.getMenu();
        for(MenuItem item : menu){
            if(item.getRawName().equals(itemName)){

                // Edit price
                DataManager.editItemPrice(itemName, price);
                System.out.println("Price edited");
                break;
            }
        }
    }
}
