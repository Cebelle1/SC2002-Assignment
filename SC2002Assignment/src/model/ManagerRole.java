package model;

import java.util.List;

import model.abstracts.AEmployee;
import model.menus.MenuItem;

/**
 * The ManagerRole contains the methods to handle the responsibilities of the manager in the ordering system.
 * It is responsible for retrieving the list of staffs supervised by the manager and updates the menu textfile.
 * It extends the StaffRole model class {@link StaffRole}.
 * 
 * @author Tey Shu Fang
 * @version 1.0
 */

public class ManagerRole extends StaffRole{ 

    private List<Branch> branches;

    /**
     * The constructor for ManagerRole takes in the Staff information
     * which calls the superclass constructor of StaffRole class.
     * 
     * @param Name The name of the staff.
     * @param StaffID The unique identifier of each employee in the Organisation.
     * @param Role The role the staff plays in the Organisation. In this case, the manager.
     * @param Gender The gender of the staff.
     * @param Age How old is the staff.
     * @param Branch The division the manager is supervising.
     * @param Password The unqiue phrase or word to login to the system.
     * 
     */

    public ManagerRole(String Name, String StaffID, String Role, String Gender,int Age, String Branch, String Password){
        super(Name, StaffID, Role, Gender,Age, Branch, Password);
    }

    /**
     * Abstract the branch that the manager is supervising from the list of branches.
     * 
     * @return The branch that the manager resides in.
     */

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

    /**
     * Retrieve the list of staffs that are working in the same branch as the manager.
     * 
     * @return The list of staffs that is working in the same branch.
     */

    public List<AEmployee> retrieveBranchStaff(Branch branch){
        // Retrieve the employees from the branch
        return branch.getEmployees();
    }

    // -------------------- Edit menu items ----------------------------

    /**
     * Method that is called from the ManagerController to add the new item to the menu textfile based on the particular branch,
     * so that the customer have more options to choose from.
     * 
     * @param itemName The new item which consists of the item name, item description, item price,
     *                 the branch it is adding to and the item category.
     * @param selectedBranch The branch which the manager is working at.
     */

    // Add menu item 
    public void addItem(MenuItem itemName, Branch selectedBranch){
        selectedBranch.addMenuItem(itemName);
        // Update the menu list
        MenuDataManager.addItemToMenu(itemName);
        System.out.println("New item added to menu!");
    }

    /**
     * Method that is called from the ManagerController to remove the existing item from the menu textfile
     * based on the particular branch.
     * 
     * @param itemName The name of the existing item.
     * @param selectedBranch The branch which the manager is working at.
     */

    // Remove menu item
    public void removeItem(String itemName, Branch selectedBranch){
        List<MenuItem> menu = selectedBranch.getMenu();
        for(MenuItem item : menu){
            if(item.getRawName().toLowerCase().trim().equals(itemName.toLowerCase().trim()) && item.getBranch().trim().equals(selectedBranch.getName().trim())){
                selectedBranch.removeMenuItem(item);
                // Update the menu list
                MenuDataManager.removeItemFromMenu(item);
                System.out.println("Item removed!");
                break;
            }
        }
    }

    /**
     * Method that is called from the ManagerController to edit the name of the existing item from the menu textfile
     * based on the particular branch.
     * 
     * @param oldName The current name of the existing item in the menu textfile.
     * @param itemName The new name to be updated to the menu textfile.
     * @param selectedBranch The branch which the manager is working at.
     */

    // Edit item name
    public void editName(String oldName, String itemName, Branch selectedBranch){
        List<MenuItem> menu = selectedBranch.getMenu();
        for(MenuItem item : menu){
            if(oldName.toLowerCase().trim().equals(item.getRawName().toLowerCase().trim()) && item.getBranch().trim().equals(selectedBranch.getName().trim()))
            {
                // Edit name
                MenuDataManager.editItemName(item, itemName);
                item.setRawName(itemName);
                System.out.println("Item name edited");
                break;
            }
        }
    }

    /**
     * Method that is called from the ManagerController to edit the price of the existing item from the menu textfile
     * based on the particular branch.
     * 
     * @param itemName The name of the existing item in the menu textfile.
     * @param price The new price to be updated to the menu textfile.
     * @param selectedBranch The branch which the manager is working at.
     */

    // Edit item price
    public void editPrice(String itemName, double price, Branch selectedBranch){
        List<MenuItem> menu = selectedBranch.getMenu();
        for(MenuItem item : menu){
            if(item.getRawName().toLowerCase().trim().equals(itemName.toLowerCase().trim()) && item.getBranch().trim().equals(selectedBranch.getName().trim())){

                // Edit price
                MenuDataManager.editItemPrice(item, price);
                item.setPrice(price);
                System.out.println("Price edited");
                break;
            }
        }
    }

    /**
     * Method that is called from the ManagerController to edit the description of the existing item from the menu textfile
     * based on the particular branch.
     * 
     * @param name The name of the existing item in the menu textfile.
     * @param description The new description of the item to be updated to the menu textfile.
     * @param selectedBranch The branch which the manager is working at.
     */

    // Edit item description
    public void editDescription(String name, String description, Branch selectedBranch){
        List<MenuItem> menu = selectedBranch.getMenu();
        for(MenuItem item : menu){
            if(item.getRawName().toLowerCase().trim().equals(name.toLowerCase().trim()) && item.getBranch().trim().equals(selectedBranch.getName().trim())){

                // Edit descirption
                MenuDataManager.editItemDescription(item, description);
                item.setDescription(description);
                System.out.println("Description edited");
                break;
            }
        }
    }

    /**
     * Checks if the item to be updated is already in the menu textfile before adding, removing or editing the item.
     * 
     * @return A boolean that indicates whether the item is available or not in the menu textfile.
     *         - true if the item is in the menu textfile.
     *         - false if the item is not in the menu textfile.
     */

    // Check if item is in the menu list
    public boolean itemAvailable(String itemName, Branch selectedBranch){
        List<MenuItem> menu = selectedBranch.getMenu();
        // New branch, means the menu is empty
        if(menu == null){
            // Item not in the list, just add new item
            return false;
        }
        for(MenuItem item : menu){
            if(item.getRawName().toLowerCase().trim().equals(itemName.toLowerCase().trim()) && item.getBranch().trim().equals(selectedBranch.getName().trim()))
            {
                // Item is in the list
                return true;
            }
        }
        // Item not in the list
        return false;
    }
}
