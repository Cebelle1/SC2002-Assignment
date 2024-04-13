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
import java.util.List;

import model.abstracts.AEmployee;

public class EmployeeDataManager {

    // ===================staff_list_with_pw.txt================================//

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
            System.err.println("Error updating the password: " + e.getMessage());
        }

        // Update the new staff list file
        File original = new File(filePath);
        File tempFile = new File("temp.txt");

        Path originalPath = Paths.get(original.getPath());
        Path tempPath = Paths.get(tempFile.getPath());

        try {
            Files.move(tempPath, originalPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error updating the file: " + e.getMessage());
        }
    }

    // ======================================ADMIN  EDITS===========================================
    public static int addNewStaffAccount(AEmployee newAEmployee) {
        String filePath = "staff_list_with_pw.txt";
        List<String> lines = new ArrayList<>(); // Store file lines
        boolean staffExists = false; // Flag to check if the staff already exists

        // Step 1: Read the existing content into 'lines'
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
                // Optional: Check if the staff already exists based on a unique identifier,
                // e.g., StaffID
                String[] parts = line.split("\t");
                if (parts.length > 1 && parts[1].equals(newAEmployee.getStaffID())) {
                    staffExists = true; // Staff exists, maybe update this record instead of adding a new one
                    System.out.println("StaffID already exist");
                    return -1;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading staff file: " + e.getMessage());
            return -2; // Exit the method in case of read error
        }

        // Step 2: Add new staff details if not already existing, then write back to the
        // file
        if (!staffExists) {
            // Construct the line for the new staff member
            String newLine = String.join("\t", newAEmployee.getName(), newAEmployee.getStaffID(),
                    newAEmployee.getRole(), newAEmployee.getGender(), Integer.toString(newAEmployee.getAge()),
                    newAEmployee.getBranch(), newAEmployee.getPassword());

            // Add the new staff details to 'lines'
            lines.add(newLine);
        }

        // Write the updated 'lines' back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String updatedLine : lines) {
                bw.write(updatedLine);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing staff file: " + e.getMessage());
        }
        return 0;

    }

    public static void removeStaffAccount(String staffNameToRemove) {
        String filePath = "staff_list_with_pw.txt";
        List<String> lines = new ArrayList<>(); // Store file lines
        boolean staffFound = false; // Flag to check if the staff was found and removed

        // Step 1: Read the existing content and filter out the staff to remove
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                // Check if the staffID matches the one to remove
                if (parts.length > 1 && parts[0].equals(staffNameToRemove)) {
                    staffFound = true; // Mark that the staff was found
                } else {
                    lines.add(line); // Add line to 'lines' if it's not the staff to remove
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading staff file: " + e.getMessage());
            return; // Exit the method in case of read error
        }

        // If the staff was found and 'lines' has been updated, write it back to the
        // file
        if (staffFound) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                for (String updatedLine : lines) {
                    bw.write(updatedLine);
                    bw.newLine();
                }
            } catch (IOException e) {
                System.err.println("Error writing staff file: " + e.getMessage());
            }
        } else {
            System.out.println("Staff member with ID " + staffNameToRemove + " not found.");
        }
    }

    // For Promotion staff -> branch manager
    public static void promoteStaffToManager(String staffnameToPromote) {
        String filePath = "staff_list_with_pw.txt";
        boolean isPromoted = false;
        boolean nonExistent = true;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
                BufferedWriter bw = new BufferedWriter(new FileWriter("temp1.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length > 1 && parts[0].equals(staffnameToPromote)) {
                    nonExistent = false;
                    if (parts[2].equals("M")) {
                        System.out.println("Staff " + staffnameToPromote + " is already a Manager");
                    } else {
                        parts[2] = "M"; // Promote to Manager and continues to copy the rest into the temp file
                        line = String.join("\t", parts);
                        isPromoted = true;
                    }
                }
                bw.write(line);
                bw.newLine();
            }
            if (nonExistent) {
                System.out.println("Staff " + staffnameToPromote + " does not exist");
            }
        } catch (IOException e) {
            System.err.println("Error updating the item name: " + e.getMessage());
        }

        // Replace the original file with the updated one
        try {
            Files.move(Paths.get("temp1.txt"), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            if (isPromoted) {
                System.out.println("Staff " + staffnameToPromote + " is promoted to a Manager");
            }
        } catch (IOException e) {
            System.err.println("Error updating the file: " + e.getMessage());
        }
    }

//==================Assign Managers to each branch with quota constraints====================

public static void assignManagerToBranch(String staffName,String branchToAssignTo) {
    String filePath = "staff_list_with_pw.txt";
    boolean isAssigned = false;
    boolean nonExistent = true;
    try (BufferedReader br = new BufferedReader(new FileReader(filePath));
            BufferedWriter bw = new BufferedWriter(new FileWriter("temp1.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("\t");
            if (parts.length > 1 && parts[0].equals(staffName)) {
                nonExistent = false;
                if (parts[5].equals(branchToAssignTo)) {
                    System.out.println("Staff " + staffName + " is already a Manager in "+ branchToAssignTo);
                } else {
                    parts[5] = branchToAssignTo; // Assign manager to new branch 
                    line = String.join("\t", parts);
                    isAssigned = true;
                }
                
            }
            bw.write(line);
            bw.newLine();
        
        }
        if (nonExistent) {
            System.out.println("Staff " + staffName + " does not exist");
        }
    } catch (IOException e) {
        System.err.println("Error updating the item name: " + e.getMessage());
    }

    // Replace the original file with the updated one
    try {
        Files.move(Paths.get("temp1.txt"), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        if (isAssigned) {
            System.out.println("Staff " + staffName + " is assigned to branch "+ branchToAssignTo);
        }
    } catch (IOException e) {
        System.err.println("Error updating the file: " + e.getMessage());
    }
}


public static boolean checkifManager(String nameOfStaff) {
    String filePath = "staff_list_with_pw.txt";
    boolean managerCheck = true;
    boolean staffExists = false;
    try (BufferedReader br = new BufferedReader(new FileReader(filePath));
            BufferedWriter bw = new BufferedWriter(new FileWriter("temp1.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("\t");{
            if (parts.length > 1 && parts[0].equals(nameOfStaff)); 
            staffExists = true;
                if( parts[2].equals("S")) {
                    managerCheck = false; 
                }                  
            }
            bw.write(line);
            bw.newLine();
        }
    } catch (IOException e) {
        System.err.println("Error updating the item name: " + e.getMessage());
    }

    // Replace the original file with the updated one
    try {
        Files.move(Paths.get("temp1.txt"), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
        System.err.println("Error updating the file: " + e.getMessage());
    }
    return managerCheck && staffExists;
}




//================== Transfer a staff/manager amongst branches==========================
    //check if the person is existent in the list 
    public static boolean checkifStaffExists(String nameOfStaff) {
        String filePath = "staff_list_with_pw.txt";
        boolean existent = false;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
                BufferedWriter bw = new BufferedWriter(new FileWriter("temp1.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length > 1 && parts[0].equals(nameOfStaff)) {
                    existent = true;                   
                }
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error updating the item name: " + e.getMessage());
        }

        // Replace the original file with the updated one
        try {
            Files.move(Paths.get("temp1.txt"), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error updating the file: " + e.getMessage());
        }
        return existent;
    }
    

}
