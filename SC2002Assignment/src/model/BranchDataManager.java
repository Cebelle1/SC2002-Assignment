package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import model.abstracts.AEmployee;
import model.menus.MenuItem;

/**
 * The BranchDataManager handles the interaction between the application and the database.
 * It is responsible for reading and writing data to the database textfiles.
 * 
 * @author Loo Si Hui
 * @version 1.0
 */
public class BranchDataManager {
    private static final String rootPath = "SC2002Assignment/src/database/";
    private static final String menuListTxt = "menu_list.txt";
    private static final String branchListTxt = "branch_list.txt";
    private static final String staffListTxt = "staff_list_with_pw.txt";
    private static final String tempBranchTxt = "temp_branch.txt";

    /**
     * Loads the menu list from the textfile into the application.
     * Initializes Branches with the menu list loaded
     * @return The list of Branches initialized
     */
    public static List<Branch> loadMenuIntoBranches() {
        final String MENU_LIST = rootPath+ menuListTxt;
        Map<String, List<MenuItem>> branchMenuMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(MENU_LIST))) {
            // Read the headings first
            br.readLine();
            String line;
            // Start reading from the second line of the text file
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t"); // Tab-separated values
                if (parts.length == 5) {
                    String name = parts[0];
                    String description = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    String branch = parts[3];
                    String category = parts[4];
                    MenuItem item = new MenuItem(name, description, price, branch, category);
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

    /**
     * Updates the employees dependencies in the individual Branches
     * @param branches
     * @param employees
     */
    public static void loadStaffIntoBranch(List<Branch> branches, List<EmployeeHandler> employees){
        // Associate employees with branches
        for (Branch branch : branches) {
            String branchName = branch.getName();
            List<AEmployee> branchEmployees = employees.stream()
                                                    .flatMap(roleCategory -> roleCategory.getAllEmployeesByRole().stream())
                                                    .filter(employee -> employee.getBranch().equals(branchName))
                                                    .collect(Collectors.toList());
            branch.setEmployees(branchEmployees);
        }
    }

    /**
     * Loads the quota and status of the branches from the branch textfile into the application.
     * Updates the quota and operation of the individual Branches
     * @param branches
     */
    public static void loadQuotaNStatus(List<Branch> branches) {
        final String filePath = rootPath + branchListTxt;
        List<Branch> openBranches = new ArrayList<>();
        List<String> updatedLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String header = br.readLine(); // Read the header line
            updatedLines.add(header); // Add the header line to the updated lines list
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 4) {
                    String name = parts[0];
                    String location = parts[1];
                    int staffQuota = Integer.parseInt(parts[2]);
                    boolean isOpen = parts[3].equalsIgnoreCase("open");
    
                    // Find the branch by name
                    Optional<Branch> optionalBranch = branches.stream()
                            .filter(branch -> branch.getName().equals(name))
                            .findFirst();
    
                    if (optionalBranch.isPresent()) {
                        Branch branch = optionalBranch.get();
                        branch.setLocation(location);
                        branch.setStaffQuota(staffQuota);
                        
                        boolean canOpen = branch.canOpenBranch();
                        boolean newStatus = isOpen || canOpen; // Determine the new status
    
                        if (newStatus) {    //isOpen = previous stat, canOpen = recalculate
                            openBranches.add(branch);
                        }
    
                        // Update the status in the line before adding it to the updated lines list
                        parts[3] = newStatus ? "open" : "close";
                        
                    } else {
                        // Create a new branch
                        System.out.printf("Branch does not have menu items added: %s", parts[0]);
                        //System.out.println("Unknown branch in branch.txt, does not match with the ones in menu_list");
                        parts[3] = "close";
                        Branch newBranch = new Branch(name, null);
                        
                    }
                    updatedLines.add(String.join("\t", parts));
                }
            }
            // Write the updated lines back to the file
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                for (String updatedLine : updatedLines) {
                    bw.write(updatedLine);
                    bw.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Branch.setOpenBranches(openBranches);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    
    /**
     * Loads the employee from the staff textfile and categorizes them based on their roles.
     * Returns a list of role categories with corresponding employees.
     * 
     * @return The list of EmployeeHandler objects categories into roles
     */
    public static List<EmployeeHandler> loadStaff() {
        final String STAFF_LIST = rootPath + staffListTxt;
        Map<String, List<AEmployee>> staffMap = new HashMap<>();    //Employee sorted into their roles
        List<AEmployee> allEmployees = new ArrayList<>(); // Unsorted employee
        try (BufferedReader br = new BufferedReader(new FileReader(STAFF_LIST))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t"); // Assuming tab-separated values
                if (parts.length == 7) {
                    String name = parts[0];
                    String staffID = parts[1];
                    String role = parts[2];
                    String gender = parts[3];
                    int age = Integer.parseInt(parts[4].trim());
                    String branch = parts[5];
                    String password = parts[6];

                    AEmployee employee = null;
                    if (role.equals("S")) {
                        employee = new StaffRole(name, staffID, role, gender, age, branch, password);
                    } else if (role.equals("M")) {
                        employee = new ManagerRole(name, staffID, role, gender, age, branch, password);
                    } else if (role.equals("A")){
                        employee = new AdminRole(name, staffID, role, gender, age, branch, password);
                    }

                    // Add the employee to the respective role category in the map
                    if (!staffMap.containsKey(role)) {
                        staffMap.put(role, new ArrayList<>());
                    }
                    staffMap.get(role).add(employee);
                    allEmployees.add(employee);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Save the list of all employees, unsorted
        EmployeeHandler.setAllEmployees(allEmployees);

        // Convert the map into a list of role categories
        List<EmployeeHandler> roleCategories = new ArrayList<>();
        for (Map.Entry<String, List<AEmployee>> entry : staffMap.entrySet()) {
            String roleName = entry.getKey();
            List<AEmployee> employees = entry.getValue();
            EmployeeHandler roleCategory = new EmployeeHandler(roleName,employees);
            roleCategories.add(roleCategory);
        }
        return roleCategories;
    }

    
    /**
     * Updates the branch textfile
     * @param branch
     */
    public static void updateBranchList(Branch branch) {
        String filePath = rootPath + branchListTxt;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
             BufferedWriter bw = new BufferedWriter(new FileWriter(rootPath + tempBranchTxt))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 4 && parts[0].equals(branch.getName())) {
                    // Update the operation status
                    parts[3] = branch.getOperation() ? "open" : "close";
                    line = String.join("\t", parts);
                }
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error updating the branch list file: " + e.getMessage());
        }

        // Replace the original file with the updated one
        File original = new File(filePath);
        File tempFile = new File(rootPath + tempBranchTxt);

        Path originalPath = Paths.get(original.getPath());
        Path tempPath = Paths.get(tempFile.getPath());

        try {
            Files.move(tempPath, originalPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error replacing the branch list file: " + e.getMessage());
        }
        List<Branch> openBranches = Branch.getOpenBranches();
        openBranches.remove(branch); // Remove the branch from the list
        Branch.setOpenBranches(openBranches);
    }

}