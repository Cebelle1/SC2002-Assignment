package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.abstracts.AEmployee;
import model.menus.*;

/**
 * The Branch class is a class in the Model layer that represents a branch of the restaurant.
 * 
 * @author Loo Si Hui
 * @version 1.0
 */
public class Branch {
    private String name;
    private List<MenuItem> menu;
    private List<AEmployee> employees;
    private static List<Branch> allBranches = new ArrayList<>();
    private static List<Branch> openBranches = new ArrayList<>();
    private boolean isOpen;
    private int staffQuota;
    private String location;
    private int managerCount;
    
    /**
     * The Branch constructor takes in a name and a list of menu items and creates a new branch with the given name and menu items.
     * The class also keeps track of all the branches in the restaurant.
     * 
     * @param name
     * @param menu
     */
    public Branch(String name, List<MenuItem> menu) {
        this.name = name;
        this.menu = menu;
        allBranches.add(this);
    }

    /**
     * Getter function to get the name of the Branch
     * @return The name of the Branch
     */
    public String getName() {
        return name;
    }
//===================Get Branches====================

    /**
     * Getter function to get all Branches
     * @return A list of all Branches
     */
    public static List<Branch> getAllBranches() {
        return allBranches;
    }

    /**
     * Setter function to set the List of Branches that are currently open
     * Setter Injection is used as Admin is able to update dependency
     * @param branches
     */
    public static void setOpenBranches(List<Branch> branches){
        openBranches = branches;
    }
    
    /**
     * Getter function to get the list of Branches that are currently open
     * @return The list of Branches that are currently open
     */
    public static List<Branch> getOpenBranches(){
        return openBranches;
    }

    /**
     * Getter function to get the list of Branches that are currently closed
     * @return The list of Branches that are currently closed
     */
    public static List<Branch> getClosedBranches(){
        List<Branch> closedBranches = new ArrayList<>(allBranches);
        closedBranches.removeAll(openBranches);
        return closedBranches;
    }

//=============Branch Operation Quota Requirements==================

    /**
     * A checker function to check if Branch object meets the constraints
     * of setting its operation to open
     * @return A boolean indicating whether the Branch meets the constraints of opening for operation
     */
    public boolean canOpenBranch(){
        return staffQuota() && managerQuota();
    }

    /**
     * A checker function to check if Branch object meets the staff quota constraint
     * @return The boolean to indicate whether staff quota is met
     */
    public boolean staffQuota(){
        if(this.getEmployees().size() < this.staffQuota) return false;
        return true;
    }

    /**
     * A checker function to check if Branch object meets the manager quota constraint
     * @return The boolean to indicate whether manager quota is met
     */
    public boolean managerQuota(){
        int staffRoleSize = this.getEmployees().size() - this.managerCount;        
        //im using guard clause here to reduce if-else statements
        if(staffRoleSize < 5 && this.managerCount <1) return false;
        if(staffRoleSize > 4 && staffRoleSize < 9 && this.managerCount <2) return false;
        if(staffRoleSize > 8 && staffRoleSize < 16 && this.managerCount < 3) return false;
        return true;
    }

    /**
     * Getter function to get the managers in the Branch
     * @return The number of manager in the Branch
     */
    public int getManagerCount(){
        return this.managerCount;
    }
//==================Branch Menu===================

    /**
     * Getter function to get the menu of the Branch
     * @return The list of MenuItems in the Branch
     */
    public List<MenuItem> getMenu() {
        return this.menu;
    }
    
    /**
     * Overloadded getter function to get the menu of the branch in a certain category
     * @param category
     * @return The list of MenuItems in the Branch in the category
     */
    public List<MenuItem> getMenu(String category) {    //Overload
        return menu.stream()
                   .filter(item -> category.equals(item.getCategory()))
                   .collect(Collectors.toList());
    }

    /**
     * Adds to the menu of the Branch
     * @param menuItem
     */
    public void addMenuItem(MenuItem menuItem){
        if(menu == null){
            // Initialize the menu list
            menu = new ArrayList<>();
        }
        menu.add(menuItem);
    }

    /**
     * Removes a menu item from the branch
     * @param menuItem
     */
    public void removeMenuItem(MenuItem menuItem){
        menu.remove(menuItem);
    }

//==================Employees====================

    /**
     * Setter function to set the employees in Branch
     * Setter Injection is used as Admin is able to update dependency
     * This function also updates the manager count and is invoked during startup only
     * @param branchAEmployees
     */
    public void setEmployees(List<AEmployee> branchAEmployees){
        this.employees = branchAEmployees;

        int managers = 0;
        for(AEmployee employee : branchAEmployees){
            if("M".equals(employee.getRole())){
                managers++;
            }
        }
        this.managerCount = managers;
    }

    /**
     * Getter function to get the list of employees in the Branch
     * @return The list of employees in the Branch
     */
    public List<AEmployee> getEmployees(){
        return this.employees;
    }


//==========Getters and Mutators===================

    /**
     * Setter function to set the employee quota of the Branch
     * @param staffQuota
     */
    public void setStaffQuota(int staffQuota){
        this.staffQuota = staffQuota;
    }

    /**
     * Getter function to get the employee quota of the Branch
     * @return The minimum amount of employee in a Branch to open for operation
     */
    public int getStaffQuota(){
        return this.staffQuota;
    }

    /**
     * Setter function to open or close a Branch
     * @param isOpen
     */
    public void setOperation(boolean isOpen){
        this.isOpen = isOpen;
    }

    /**
     * Getter function to get the operation of the Branch
     * @return The boolean indicating if Branch is open 
     */
    public boolean getOperation(){
        return this.isOpen;
    }

    /**
     * Setter function to get the location of the Branch
     * @param location
     */
    public void setLocation(String location){
        this.location = location;
    }
}