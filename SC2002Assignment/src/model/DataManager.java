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

public class DataManager {

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

        // Load employees separately
        List<EmployeeHandler> employees = DataManager.loadStaff(filePath);

        // Associate employees with branches
        for (Branch branch : branches) {
            String branchName = branch.getName();
            List<AEmployee> branchEmployees = employees.stream()
                    .flatMap(roleCategory -> roleCategory.getAllUnsortedEmployees().stream())
                    .filter(employee -> employee.getBranch().equals(branchName))
                    .collect(Collectors.toList());
            branch.setEmployees(branchEmployees);
        }
        return branches;
    }

    // load the staff list here
    public static List<EmployeeHandler> loadStaff(String filePath) {
        Map<String, List<AEmployee>> staffMap = new HashMap<>(); // Employee sorted into their roles
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
                    } else if (role.equals("A")) {
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
        // Save the list of all employees, unsorted
        EmployeeHandler.setAllEmployees(allEmployees);

        // Convert the map into a list of role categories
        List<EmployeeHandler> roleCategories = new ArrayList<>();
        for (Map.Entry<String, List<AEmployee>> entry : staffMap.entrySet()) {
            String roleName = entry.getKey();
            List<AEmployee> employees = entry.getValue();
            EmployeeHandler roleCategory = new EmployeeHandler(roleName, employees);
            roleCategories.add(roleCategory);
        }
        // System.out.print(roleCategories.getClass());

        // for (EmployeeHandler count : roleCategories) {
        // System.out.print(count.getAllEmployeesByRole());
        // List<AEmployee> test1 = count.getAllEmployeesByRole();
        // for (AEmployee test : test1) {
        // System.out.println(test.getStaffID());
        // }
        // }
        // List<AEmployee> test =

        return roleCategories;

    }

    public static void updateFile(String filePath, String newPassword, String id) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
                BufferedWriter bw = new BufferedWriter(new FileWriter("temp.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 7 && id.equals(parts[1])) {
                    parts[6] = newPassword;
                    line = String.join("\t", parts);
                }
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Update the new staff list file
        File original = new File(filePath);
        File tempFile = new File("temp.txt");

        Path originalPath = Paths.get(original.getPath());
        Path tempPath = Paths.get(tempFile.getPath());

        try {
            Files.move(tempPath, originalPath, StandardCopyOption.REPLACE_EXISTING);
            // Delete temp file
            Files.delete(tempPath);
        } catch (IOException e) {
            e.getMessage();
        }
    }
}