package model;
import java.util.List;
import java.util.stream.Collectors;

import model.abstracts.AEmployee;
import model.menus.*;

public class Branch {
    private String name;
    private List<MenuItem> menu;
    private List<AEmployee> employees;

    public Branch(String name, List<MenuItem> menu) {
        this.name = name;
        this.menu = menu;
    }

    public List<MenuItem> getMenu() {
        return this.menu;
    }
    
    public List<MenuItem> getMenu(String category) {
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

    public void setEmployees(List<AEmployee> branchAEmployees){
        this.employees = branchAEmployees;
    }
    
}
