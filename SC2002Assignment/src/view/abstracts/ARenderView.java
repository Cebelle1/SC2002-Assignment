/**
 * @author Loo Si Hui
 * @version 1.0
 * The `ARenderView` class is an abstract class in Java that provides methods for rendering views with
 * borders, delays, input handling, and text formatting.
 */
package view.abstracts;

import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import view.interfaces.ViewHelperInterface;
import view.interfaces.ViewInterface;

public abstract class ARenderView implements ViewInterface, ViewHelperInterface{
    Scanner sc;

    /** 
     * The `public ARenderView()` constructor in the `ARenderView` class initializes a new `Scanner`
     * object `sc` 
    */
    public ARenderView() {
        sc = new Scanner(System.in);
    }

    /**
     * Prints a string surrounded by a double-lined border 
     * 
     * @param input The input string displayed in the middle of the border.
     */
    @Override
    public void printBorder(String input) {
        clearCLI();
        String space = String.format("%" + (99 - input.length()) + "s", "");
        String halfSpace = String.format("%" + (99 - input.length()) / 2 + "s", "");
        System.out.println(
                "╔════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ " + halfSpace + input + halfSpace + "║");
        System.out.println(
                "╚════════════════════════════════════════════════════════════════════════════════════════════════════╝");
    }

    /**
     * Uses a ProcessBuilder to clear the command line interface screen
     * by executing the "cls" command.
     */
    @Override
    public void clearCLI() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception err) {
            System.out.println("Error clearing screen");
        }
    }

    /**
     * Prints a countdown message for the specified number of seconds
     * before returning.
     * 
     * @param sec The number of seconds to delay.
     */
    public void delay(int sec) {
        for (int i = 0; i < sec; i++) {
            System.out.printf("Returning in %d second\n", sec - i);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Overloaded delay function to print only the string prompt without printing countdown seconds, 
     * 
     * @param sec    The number of seconds to delay.
     * @param prompt The prompt to be printed before the delay.
     */
    public void delay(int sec, String prompt) { // Overload, delay function without printing countdown, but just a
                                                // single string prompt
        System.out.println(prompt);
        for (int i = 0; i < sec; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    /**
     * Prints a string with a double underline below it.
     * 
     * @param input The string to be printed above the double underlines
     */
    @Override
    public void printDoubleUnderline(String input) {
        String space = String.format("%" + (99 - input.length()) + "s", "");
        String halfSpace = String.format("%" + (99 - input.length()) / 2 + "s", "");
        System.out.println(input + space);
        System.out.println(
                "════════════════════════════════════════════════════════════════════════════════════════════════════");
    }

    /**
     * Prints a string with a double underline below it.
     * 
     * @param input The string to be printed above the double underlines
     */
    public void printSingleUnderline(String input) {
        String space = String.format("%" + (99 - input.length()) + "s", "");
        String halfSpace = String.format("%" + (99 - input.length()) / 2 + "s", "");
        System.out.println(input + space);
        System.out.println(
                "_____________________________________________________________________________________________________");
    }

    /**
     * Implements from the interface to print a string with a double underline below it.
     * 
     * @param input The string to be printed above the double underlines
     */
    @Override
    public void printSingleBorder(String input) {
        String space = String.format("%" + (99 - input.length()) + "s", "");
        String halfSpace = String.format("%" + (99 - input.length()) / 2 + "s", "");
        System.out.println(
                "______________________________________________________________________________________________________");
        System.out.println("| " + input + space + "|");
        System.out.println(
                "|____________________________________________________________________________________________________|");
    }


    /**
     * Reads an integer input from the user and returns it.
     * 
     * @param prompt A prompt message to display to the user before input.
     * @return The integer inputted by the user.
     */
    public int getInputInt(String prompt) {
        if (!prompt.isEmpty()) {
            System.out.println(prompt);
        }
        try {
            int input = sc.nextInt();
            sc.nextLine();
            return input;
        } catch (Exception e) {
            System.err.println("Please enter an integer.");
            sc.nextLine(); // Consume invalid input to prevent infinite loop
            return -1;
        }
    }

    /**
     * Overloadded function that reads an integer input from the user,
     * checks if its less than maximum allowed value, then returns it.
     * 
     * @param prompt A prompt message to display to the user before input.
     * @param max The upper bound of the range of values the user should enter
     * @return The integer inputted by the user.
     */

    public int getInputInt(String prompt, int max) { // Overload to control selection range
        if (!prompt.isEmpty()) {
            System.out.println(prompt);
        }
        try {
            int input = sc.nextInt();
            sc.nextLine();
            while (input > max) {
                System.out.println("Selection out of range please try again");
                input = sc.nextInt();
                sc.nextLine();
            }
            return input;

        } catch (Exception e) {
            System.err.println("Please enter an integer.");
            sc.nextLine(); // Consume invalid input to prevent infinite loop
            return -1;
        }
    }


    /**
     * Reads a string input from the user and returns it.
     * 
     * @param prompt A prompt message to display to the user before input.
     * @return The string inputted by the user.
     */
    public String getInputString(String prompt) {
        if (!prompt.isEmpty()) {
            System.out.println(prompt);
        }
        try {
            // Read a sentence
            String input = sc.nextLine();
            // input = sc.nextLine();
            return input;
        } catch (Exception e) {
            System.err.println("Error receiving input.");
            sc.next();
            return "";
        }
    }

    /**
     * Reads an double input from the user and returns it.
     * 
     * @param prompt A prompt message to display to the user before input.
     * @return The double inputted by the user.
     */    
    public double getInputDouble(String prompt) {
        if (!prompt.isEmpty()) {
            System.out.println(prompt);
        }
        try {
            double input = sc.nextDouble();
            sc.nextLine();
            return input;

        } catch (Exception e) {
            System.err.println("Please enter an integer/double.");
            sc.nextLine(); // Consume invalid input to prevent infinite loop
            return -1;
        }
    }


    /**
     * Formats the string passed in by capitalizing the first letter and
     * convert the remaining letters to lowercase, then returns it
     * 
     * @param name The string to be converted
     * @return The formmated string
     */
    protected String formatName(String name) {
        // Split the name into words
        String[] words = name.split(" ");

        // Capitalize the first letter of each word
        StringBuilder formattedName = new StringBuilder();
        for (String word : words) {
            formattedName.append(word.substring(0, 1).toUpperCase(Locale.ENGLISH)); // Capitalize first letter
            formattedName.append(word.substring(1).toLowerCase(Locale.ENGLISH)); // Convert remaining letters to
                                                                                 // lowercase
            formattedName.append(" "); // Add space between words
        }

        return String.format("| %-15s", formattedName.toString());
    }


    /**
     * Prints a exit prompt message and waits for a user input
     */    
    public void exitPrompt() {
        System.out.println("\nPress Enter key to exit...");

        try {
            System.in.read(); // Waits for user input
        } catch (Exception e) {
            System.err.println("An I/O error occurred: " + e.getMessage());
        }
    }

}
