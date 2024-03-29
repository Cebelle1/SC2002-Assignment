package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.menus.MenuItem;

public class DataLoader {

    public static List<Branch> loadBranches(String filePath) {
        Map<String, List<MenuItem>> branchMenuMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                
                String[] parts = line.split("\t"); // Assuming tab-separated values
                if (parts.length == 4) {
                    String name = parts[0];
                    double price = Double.parseDouble(parts[1]);
                    String branch = parts[2];
                    String category = parts[3];
                    MenuItem item = new MenuItem(name, price, branch, category);
                    // Add the menu item to the respective branch in the map
                    if (!branchMenuMap.containsKey(branch)) {
                        branchMenuMap.put(branch, new ArrayList<>());
                    }
                    branchMenuMap.get(branch).add(item);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convert the map into a list of branches
        List<Branch> branches = new ArrayList<>();
        for (Map.Entry<String, List<MenuItem>> entry : branchMenuMap.entrySet()) {
            String branchName = entry.getKey();
            List<MenuItem> menuItems = entry.getValue();
            Branch branch = new Branch(branchName, menuItems);
            branches.add(branch);
        }

        return branches;
    }

      // load the staff list here
    public static List<StaffCategory> loadStaff(String filePath) {
        Map<String, List<Staff>> staffMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                
                String[] parts = line.split("\t"); // Assuming tab-separated values
                if (parts.length == 7) {
                    String name = parts[0];
                    String staffID = parts[1];
                    String role = parts[2];
                    String gender = parts[3];
                    Double age = Double.parseDouble(parts[4]);
                    String branch = parts[5];
                    String password = parts[6];
                    // age is not passed in as of yet -> if it is needed can just pass in
                    Staff staff = new Staff(name, staffID, role, gender, branch, password);
                    // Add the staff list to the respective role in the map
                    if (!staffMap.containsKey(role)) {
                        staffMap.put(role, new ArrayList<>());
                    }
                    staffMap.get(role).add(staff);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convert the map into a list of staffs
        List<StaffCategory> staffs = new ArrayList<>();
        for (Map.Entry<String, List<Staff>> entry : staffMap.entrySet()) {
            String roleName = entry.getKey();
            List<Staff> Staff = entry.getValue();
            StaffCategory role = new StaffCategory(roleName, Staff);
            staffs.add(role);
        }

        return staffs;
    }
}