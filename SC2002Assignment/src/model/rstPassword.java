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
import java.util.List;

import controller.LoginController;

public class rstPassword {

    private List<StaffCategory> staffs;
    LoginController login;

    // Constructor
    public rstPassword(LoginController loginC, List<StaffCategory> staffs){
        this.login = loginC;
        this.staffs = staffs;
    }

    public boolean updatePass(String id, String newPassword, String cfmNewPassword) {
        
        List<Staff> staffList = null;

        // Passwords match
        if(newPassword.equals(cfmNewPassword))
        {
            for (int i = 0; i < staffs.size(); i++) {
                StaffCategory staffCategory = staffs.get(i);
                staffList = staffCategory.getStaff(); // Retrieve the list of staff members in this category
                
                for (Staff staff : staffList){
                    // Access properties of each staff member
                    if(id.equals(staff.getStaffID())){
                        staff.setPassword(cfmNewPassword);
                    }
                }
            }
            // Update
            updateFile("staff_list_with_pw.txt", cfmNewPassword, id, staffList);
            return true;
        }
        // Passwords are different
        return false;
    }

    public static void updateFile(String filePath, String newPassword, String id, List<Staff> staffList){
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
            e.printStackTrace();
        }

        // Update the new staff list file
        File original = new File(filePath);
        File tempFile = new File("temp.txt");

        Path originalPath = Paths.get(original.getPath());
        Path tempPath = Paths.get(tempFile.getPath());

        try{
            Files.move(tempPath, originalPath, StandardCopyOption.REPLACE_EXISTING);
            // Delete temp file
            Files.delete(tempPath);
        } catch(IOException e){
            e.getMessage();
        }
    }
}
