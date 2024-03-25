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

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) 
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
}
