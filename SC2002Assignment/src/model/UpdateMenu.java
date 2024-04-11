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

    private static String filePath = "menu_list.txt";

    //=================== menu_list.txt ================================//
    public static void addItemToMenu(MenuItem menuItem){
        //String filePath = "menu_list.txt";
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))){
            String line = String.join("\t", menuItem.getRawName(), menuItem.getDescription(), Double.toString(menuItem.getPrice()), menuItem.getBranch(), menuItem.getCategory());
            bw.write(line);
            bw.newLine();
            }catch(IOException e){
            System.err.println("Error adding menu item to file: " + e.getMessage());
        }
    }

    public static void removeItemFromMenu(MenuItem menuItem){
        //String filePath = "menu_list.txt";
        try(BufferedReader br = new BufferedReader(new FileReader(filePath));
            BufferedWriter bw = new BufferedWriter(new FileWriter("temp.txt"))){
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split("\t");
                 // Remove
                 if(parts.length == 5 && parts[0].equals(menuItem.getRawName())){
                    // Check the branch before removing as different branches may have same item
                    if(parts[3].equals(menuItem.getBranch())){
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
        //String filePath = "menu_list.txt";
        try(BufferedReader br = new BufferedReader(new FileReader(filePath));
            BufferedWriter bw = new BufferedWriter(new FileWriter("temp.txt"))){
                String line;
                while((line = br.readLine()) != null){
                    String[] parts = line.split("\t");
                    if(parts.length == 5 && parts[0].equals(oldName)){
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
        //String filePath = "menu_list.txt";
        try(BufferedReader br = new BufferedReader(new FileReader(filePath));
            BufferedWriter bw = new BufferedWriter(new FileWriter("temp.txt"))){
                String line;
                while((line = br.readLine()) != null){
                    String[] parts = line.split("\t");
                    if(parts.length == 5 && parts[0].equals(name)){
                        StringBuffer sb = new StringBuffer();
                        sb.append(price);
                        parts[2] = sb.toString();
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

    public static void editItemDescription(String name, String description){
        //String filePath = "menu_list.txt";
        try(BufferedReader br = new BufferedReader(new FileReader(filePath));
            BufferedWriter bw = new BufferedWriter(new FileWriter("temp.txt"))){
                String line;
                while((line = br.readLine()) != null){
                    String[] parts = line.split("\t");
                    if(parts.length == 5 && parts[0].equals(name)){
                        parts[1] = description;
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
