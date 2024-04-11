package model;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.abstracts.AEmployee;
import model.menus.*;

public class Branch {
    private String name;
    private List<MenuItem> menu;
    private List<AEmployee> employees;
    private static List<Branch> allBranches = new ArrayList<>();
    private static List<Branch> openBranches = new ArrayList<>();
    private boolean isOpen;
    private int staffQuota;
    private String location;
    
    public Branch(String name, List<MenuItem> menu) {
        this.name = name;
        this.menu = menu;
        allBranches.add(this);
    }

    public static List<Branch> getAllBranches() {
        return allBranches;
    }

    public static void setOpenBranches(List<Branch> branches){
        openBranches = branches;
    }
    
    public static List<Branch> getOpenBranches(){
        System.out.println(openBranches);
        return openBranches;
    }

    public static List<Branch> getClosedBranches(){
        List<Branch> closedBranches = new ArrayList<>(allBranches);
        closedBranches.removeAll(openBranches);
        return closedBranches;
    }

    public List<MenuItem> getMenu() {
        return this.menu;
    }
    
    public List<MenuItem> getMenu(String category) {    //Overload
        return menu.stream()
                   .filter(item -> category.equals(item.getCategory()))
                   .collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public void addMenuItem(MenuItem menuItem){
        menu.add(menuItem);
    }

    public void removeMenuItem(MenuItem menuItem){
        menu.remove(menuItem);
    }

    public void setEmployees(List<AEmployee> branchAEmployees){
        this.employees = branchAEmployees;
    }

    public List<AEmployee> getEmployees(){
        return this.employees;
    }

    public void loadStaff(List<AEmployee> staff) {
        this.employees = staff.stream()
                              .filter(employee -> employee.getBranch().equals(this.name))
                              .collect(Collectors.toList());
    }

    public void loadMenu(List<MenuItem> menuItems) {
        this.menu = menuItems.stream()
                            .filter(item -> item.getBranch().equals(this.name))
                            .collect(Collectors.toList());
    }

    public void setStaffQuota(int staffQuota){
        this.staffQuota = staffQuota;
    }

    public int getStaffQuota(){
        return this.staffQuota;
    }

    public void setOperation(boolean isOpen){
        this.isOpen = isOpen;
    }

    public boolean getOperation(){
        return this.isOpen;
    }

    public void setLocation(String location){
        this.location = location;
    }
    
}
