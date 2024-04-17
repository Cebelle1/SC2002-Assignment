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

/**
 * The MenuDataManager handles the interaction between the application and the database.
 * It is responsible for adding, deleting and editing the menu textfile.
 * 
 * @author Tey Shu Fang
 * @version 1.0
 */


public class MenuDataManager {

    private static final String filePath = "SC2002Assignment/src/database/menu_list.txt";

    //=================== menu_list.txt ================================//
    
    /**
     * Adds new item to the menu textfile.
     * @param menuItem  The new item which consists of the item name, item description, item price, the branch it is adding to and the item category
     */

    // Add item
    public static void addItemToMenu(MenuItem menuItem){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))){
            String line = String.join("\t", menuItem.getRawName().trim(), menuItem.getDescription().trim(), Double.toString(menuItem.getPrice()).trim(), menuItem.getBranch().trim(), menuItem.getCategory().trim());
            bw.write(line);
            bw.newLine();
            }catch(IOException e){
            System.err.println("Error adding menu item to file: " + e.getMessage());
        }
    }

    /**
     * Removes existing item from the menu textfile.
     * @param menuItem  The new item which consists of the item name, item description, item price, the branch it is adding to and the item category
     */

    // Remove item
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

    /**
     * Edits the name of the existing item in the menu textfile.
     * @param menuItem  The new item which consists of the item name, item description, item price, the branch it is adding to and the item category
     * @param newName   The name of the existing item
     */

    // Edit name
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

    /**
     * Edits the price of the existing item in the menu textfile.
     * @param menuItem  The new item which consists of the item name, item description, item price, the branch it is adding to and the item category
     * @param price The price of the existing item
     */

    // Edit price
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

    /**
     * Edits the description of the existing item in the menu textfile.
     * @param menuItem  The new item which consists of the item name, item description, item price, the branch it is adding to and the item category
     * @param description   The description of the existing item
     */

    // Edit description
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
