package model;

import java.util.List;

import model.abstracts.AEmployee;
import model.menus.MenuItem;


public class ManagerRole extends StaffRole{ 

    private List<Branch> branches;

    public ManagerRole(String Name, String StaffID, String Role, String Gender,int Age, String Branch, String Password){
        super(Name, StaffID, Role, Gender,Age, Branch, Password);
    }

    // To load the Branch data
    public Branch loadBranches(){
        branches = Branch.getAllBranches();
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

    // -------------------- Edit menu items ----------------------------

    // Add menu item 
    public void addItem(MenuItem itemName, Branch selectedBranch){
        selectedBranch.addMenuItem(itemName);
        // Update the menu list
        UpdateMenu.addItemToMenu(itemName);
        System.out.println("New item added to menu!");
    }

    // Remove menu item
    public void removeItem(String itemName, Branch selectedBranch){
        List<MenuItem> menu = selectedBranch.getMenu();
        for(MenuItem item : menu){
            if(item.getRawName().toLowerCase().equals(itemName.toLowerCase()) && item.getBranch().equals(selectedBranch.getName())){
                selectedBranch.removeMenuItem(item);
                // Update the menu list
                UpdateMenu.removeItemFromMenu(item);
                System.out.println("Item removed!");
                break;
            }
        }
    }

    // Edit item name
    public void editName(String oldName, String itemName, Branch selectedBranch){
        List<MenuItem> menu = selectedBranch.getMenu();
        for(MenuItem item : menu){
            if(oldName.toLowerCase().equals(item.getRawName().toLowerCase()) && item.getBranch().equals(selectedBranch.getName()))
            {
                // Edit name
                UpdateMenu.editItemName(item, itemName);
                item.setRawName(itemName);
                System.out.println("Item name edited");
                break;
            }
        }
    }


    // Edit item price
    public void editPrice(String itemName, double price, Branch selectedBranch){
        List<MenuItem> menu = selectedBranch.getMenu();
        for(MenuItem item : menu){
            if(item.getRawName().toLowerCase().equals(itemName.toLowerCase()) && item.getBranch().equals(selectedBranch.getName())){

                // Edit price
                UpdateMenu.editItemPrice(item, price);
                item.setPrice(price);
                System.out.println("Price edited");
                break;
            }
        }
    }

    // Edit item description
    public void editDescription(String name, String description, Branch selectedBranch){
        List<MenuItem> menu = selectedBranch.getMenu();
        for(MenuItem item : menu){
            if(item.getRawName().toLowerCase().equals(name.toLowerCase()) && item.getBranch().equals(selectedBranch.getName())){

                // Edit descirption
                UpdateMenu.editItemDescription(item, description);
                item.setDescription(description);
                System.out.println("Description edited");
                break;
            }
        }
    }

    // Check if item is in the menu list
    public boolean itemAvailable(String itemName, Branch selectedBranch){
        List<MenuItem> menu = selectedBranch.getMenu();
        for(MenuItem item : menu){
            item.getRawName().toLowerCase();
            itemName.toLowerCase();
            if(item.getRawName().toLowerCase().equals(itemName.toLowerCase()) && item.getBranch().equals(selectedBranch.getName()))
            //if(item.getRawName().equals(itemName) && item.getBranch().equals(selectedBranch.getName()))
            {
                // System.out.println("Item exists");
                return true;
            }
        }
        // Item not in the list
        // System.out.println("Item not in menu list!");
        return false;
    }
}
