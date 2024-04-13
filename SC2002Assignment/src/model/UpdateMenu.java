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
import model.menus.MenuItem;

public class UpdateMenu {

    private static final String filePath = "SC2002Assignment/src/database/menu_list.txt";

    //=================== menu_list.txt ================================//
    public static void addItemToMenu(MenuItem menuItem){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))){
            String line = String.join("\t", menuItem.getRawName().trim(), menuItem.getDescription().trim(), Double.toString(menuItem.getPrice()).trim(), menuItem.getBranch().trim(), menuItem.getCategory().trim());
            bw.write(line);
            bw.newLine();
            }catch(IOException e){
            System.err.println("Error adding menu item to file: " + e.getMessage());
        }
    }

    public static void removeItemFromMenu(MenuItem menuItem){
        try(BufferedReader br = new BufferedReader(new FileReader(filePath));
            BufferedWriter bw = new BufferedWriter(new FileWriter("temp.txt"))){
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split("\t");
                 // Remove
                 if(parts.length == 5 && parts[0].trim().equals(menuItem.getRawName().trim())){
                    // Check the branch before removing as different branches may have same item
                    if(parts[3].trim().equals(menuItem.getBranch().trim())){
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

    

    public static void editItemName(MenuItem menuItem, String newName){
        try(BufferedReader br = new BufferedReader(new FileReader(filePath));
            BufferedWriter bw = new BufferedWriter(new FileWriter("temp.txt"))){
                String line;
                while((line = br.readLine()) != null){
                    String[] parts = line.split("\t");
                    if(parts.length == 5 && parts[0].toLowerCase().trim().equals(menuItem.getRawName().toLowerCase().trim()) && parts[3].trim().equals(menuItem.getBranch().trim())){
                        parts[0] = newName.trim();
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

    public static void editItemPrice(MenuItem menuItem, double price){
        try(BufferedReader br = new BufferedReader(new FileReader(filePath));
            BufferedWriter bw = new BufferedWriter(new FileWriter("temp.txt"))){
                String line;
                while((line = br.readLine()) != null){
                    String[] parts = line.split("\t");
                    if(parts.length == 5 && parts[0].toLowerCase().trim().equals(menuItem.getRawName().toLowerCase().trim()) && parts[3].trim().equals(menuItem.getBranch().trim())){
                        StringBuffer sb = new StringBuffer();
                        sb.append(price);
                        parts[2] = sb.toString().trim();
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

    public static void editItemDescription(MenuItem menuItem, String description){
        try(BufferedReader br = new BufferedReader(new FileReader(filePath));
            BufferedWriter bw = new BufferedWriter(new FileWriter("temp.txt"))){
                String line;
                while((line = br.readLine()) != null){
                    String[] parts = line.split("\t");
                    if(parts.length == 5 && parts[0].toLowerCase().trim().equals(menuItem.getRawName().toLowerCase().trim()) && parts[3].trim().equals(menuItem.getBranch().trim())){
                        parts[1] = description.trim();
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
}
