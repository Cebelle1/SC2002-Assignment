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

public class BranchDataManager {

    public static List<Branch> loadMenuIntoBranches() {
        final String MENU_LIST = "menu_list.txt";
        Map<String, List<MenuItem>> branchMenuMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(MENU_LIST))) {
            // Read the headings first
            br.readLine();
            String line;
            // Start reading from the second line of the text file
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t"); // Assuming tab-separated values
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

    public static void loadQuotaNStatus(List<Branch> branches) {
        String filePath = "branch_list.txt";
        List<Branch> openBranches = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip the header line
            br.readLine();
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
                    
                        boolean canOpen = branch.getEmployees().size() >= staffQuota;
                        branch.setOperation(canOpen);
                        
                        if (canOpen) {
                            openBranches.add(branch);
                        }
                    } else {
                        // Create a new branch
                        System.out.println("Unknown branch in branch.txt, does not match with the ones in menu_list");
                    }
                }
            }
            Branch.setOpenBranches(openBranches);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

      // load the staff list here
    public static List<EmployeeHandler> loadStaff() {
        final String STAFF_LIST = "staff_list_with_pw.txt";
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

    private static String filePath = "branch_list.txt";

    public static void updateBranchList(Branch branch) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
             BufferedWriter bw = new BufferedWriter(new FileWriter("temp_branch.txt"))) {
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
        File tempFile = new File("temp_branch.txt");

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

    

//===================Payment.txt================================//
    public static void appendPaymentMethod(String paymentMethod) {
        String fileName = "payments.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(paymentMethod + "\n");
            System.out.println("Payment method appended successfully.");
        } catch (IOException e) {
            System.err.println("Error appending payment method to file: " + e.getMessage());
        }
    }

    //Not tested
    public static void removePaymentMethod(String paymentMethodToRemove) {
        String fileName = "payments.txt";
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            if (lines.remove(paymentMethodToRemove)) {
                Files.write(Paths.get(fileName), lines);
                System.out.println("Payment method removed successfully.");
            } else {
                System.out.println("Payment method not found in file.");
            }
        } catch (IOException e) {
            System.err.println("Error removing payment method from file: " + e.getMessage());
        }
    }

    public static List<String> readPaymentMethods() {
        String fileName = "payments.txt";
        List<String> paymentMethods = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                paymentMethods.add(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return paymentMethods;
    }

    
}