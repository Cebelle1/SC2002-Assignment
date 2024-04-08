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
import java.util.stream.Collectors;

import model.abstracts.AEmployee;
import model.menus.MenuItem;
import model.payments.IPaymentProcessor;

public class DataManager {

    public static List<Branch> loadMenuIntoBranches(String filePath) {
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

        // Load employees separately
        List<EmployeeHandler> employees = DataManager.loadStaff("staff_list_with_pw.txt");
       
        // Associate employees with branches
        for (Branch branch : branches) {
            String branchName = branch.getName();
            List<AEmployee> branchEmployees = employees.stream()
                                                    .flatMap(roleCategory -> roleCategory.getAllEmployeesByRole().stream())
                                                    .filter(employee -> employee.getBranch().equals(branchName))
                                                    .collect(Collectors.toList());
            branch.setEmployees(branchEmployees);
           
        }
        return branches;
    }

      // load the staff list here
    public static List<EmployeeHandler> loadStaff(String filePath) {
        Map<String, List<AEmployee>> staffMap = new HashMap<>();    //Employee sorted into their roles
        List<AEmployee> allEmployees = new ArrayList<>(); // Unsorted employee
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
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

    //===================staff_list_with_pw.txt================================//
     public static void updateFile(String filePath, String newPassword, String id){
        try(BufferedReader br = new BufferedReader(new FileReader(filePath));
            BufferedWriter bw = new BufferedWriter(new FileWriter("temp.txt"))){
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split("\t");
                if(parts.length == 7 && id.equals(parts[1])){
                    parts[6] = newPassword;
                    line = String.join("\t", parts);
                }
                bw.write(line);
                bw.newLine();
            }
        } catch(IOException e){
            System.err.println("Error updating the password: " + e.getMessage());
        }

        // Update the new staff list file
        File original = new File(filePath);
        File tempFile = new File("temp.txt");

        Path originalPath = Paths.get(original.getPath());
        Path tempPath = Paths.get(tempFile.getPath());

        try{
            Files.move(tempPath, originalPath, StandardCopyOption.REPLACE_EXISTING);
        } catch(IOException e){
            System.err.println("Error updating the file: " + e.getMessage());
        }
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

    //=================== menu_list.txt ================================//
    public static void addItemToMenu(MenuItem menuItem){
        String filePath = "menu_list.txt";
        try(BufferedReader br = new BufferedReader(new FileReader(filePath));
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))){
            String line;
            boolean add = false;
            while((line = br.readLine()) != null && add == false){
                String[] parts = line.split("\t");
                // Add
                if(parts.length == 4){
                    parts[0] = menuItem.getRawName();
                    parts[1] = Double.toString(menuItem.getPrice());
                    parts[2] = menuItem.getBranch();
                    parts[3] = menuItem.getCategory();
                    line = String.join("\t", parts);
                    add = true;
                }

                bw.write(line);
                bw.newLine();
            }
        } catch(IOException e){
            System.err.println("Error adding menu item to file: " + e.getMessage());
        }
    }

    public static void removeItemFromMenu(MenuItem menuItem){
        String filePath = "menu_list.txt";
        try(BufferedReader br = new BufferedReader(new FileReader(filePath));
            BufferedWriter bw = new BufferedWriter(new FileWriter("temp.txt"))){
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split("\t");
                 // Remove
                 if(parts.length == 4 && parts[0].equals(menuItem.getRawName())){
                    // Check the branch before removing as different branches may have same item
                    if(parts[2].equals(menuItem.getBranch())){
                        // Skip writing the line to the new file which will be updated later on
                        continue;
                    }
                }

                bw.write(line);
                bw.newLine();
            }
        } catch(IOException e){
            System.err.println("Error removing menu item from file: " + e.getMessage());
        }

        // Update the new menu list file
        File original = new File(filePath);
        File tempFile = new File("temp.txt");

        Path originalPath = Paths.get(original.getPath());
        Path tempPath = Paths.get(tempFile.getPath());

        try{
            Files.move(tempPath, originalPath, StandardCopyOption.REPLACE_EXISTING);
        } catch(IOException e){
            System.err.println("Error updating the file: " + e.getMessage());
        }

}

    public static void editItemName(String oldName, String newName){
        String filePath = "menu_list.txt";
        try(BufferedReader br = new BufferedReader(new FileReader(filePath));
            BufferedWriter bw = new BufferedWriter(new FileWriter("temp.txt"))){
                String line;
                while((line = br.readLine()) != null){
                    String[] parts = line.split("\t");
                    if(parts.length == 4 && parts[0].equals(oldName)){
                        parts[0] = newName;
                        line = String.join("\t", parts);
                    }

                    bw.write(line);
                    bw.newLine();
                }
        } catch(IOException e){
            System.err.println("Error updating the item name: " + e.getMessage());
        }

        // Update the new menu list file
        File original = new File(filePath);
        File tempFile = new File("temp.txt");

        Path originalPath = Paths.get(original.getPath());
        Path tempPath = Paths.get(tempFile.getPath());
        try{
            Files.move(tempPath, originalPath, StandardCopyOption.REPLACE_EXISTING);
        } catch(IOException e){
            System.err.println("Error updating the file: " + e.getMessage());
        }
    }

    public static void editItemPrice(String name, double price){
        String filePath = "menu_list.txt";
        try(BufferedReader br = new BufferedReader(new FileReader(filePath));
            BufferedWriter bw = new BufferedWriter(new FileWriter("temp.txt"))){
                String line;
                while((line = br.readLine()) != null){
                    String[] parts = line.split("\t");
                    if(parts.length == 4 && parts[0].equals(name)){
                        //parts[1] = Double.toString(price);
                        //parts[1] = "".valueOf(price);
                        //line = String.join("\t", parts);
                        StringBuffer sb = new StringBuffer();
                        sb.append(price);
                        parts[1] = sb.toString();
                        line = String.join("\t", parts);
                    }

                    bw.write(line);
                    bw.newLine();
                }
        } catch(IOException e){
            System.err.println("Error updating the item name: " + e.getMessage());
        }

        // Update the new staff list file
        File original = new File(filePath);
        File tempFile = new File("temp.txt");

        Path originalPath = Paths.get(original.getPath());
        Path tempPath = Paths.get(tempFile.getPath());

        try{
            Files.move(tempPath, originalPath, StandardCopyOption.REPLACE_EXISTING);
        } catch(IOException e){
            System.err.println("Error updating the file: " + e.getMessage());
        }
    }

}