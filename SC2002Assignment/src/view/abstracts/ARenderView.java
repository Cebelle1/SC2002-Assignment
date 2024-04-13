package view.abstracts;

import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import view.interfaces.ViewInterface;

public abstract class ARenderView implements ViewInterface {
    Scanner sc;

    public ARenderView() {
        sc = new Scanner(System.in);
    }

    protected void printBorder(String input) {
        clearCLI();
        String space = String.format("%" + (99 - input.length()) + "s", "");
        String halfSpace = String.format("%" + (99 - input.length()) / 2 + "s", "");
        System.out.println(
                "╔════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ " + halfSpace + input + halfSpace + "║");
        System.out.println(
                "╚════════════════════════════════════════════════════════════════════════════════════════════════════╝");
    }

    protected void clearCLI() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception err) {
            System.out.println("Error clearing screen");
        }
    }

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

    protected void printDoubleUnderline(String input) {
        String space = String.format("%" + (99 - input.length()) + "s", "");
        String halfSpace = String.format("%" + (99 - input.length()) / 2 + "s", "");
        System.out.println(input + space);
        System.out.println(
                "════════════════════════════════════════════════════════════════════════════════════════════════════");
    }

    protected void printSingleUnderline(String input) {
        String space = String.format("%" + (99 - input.length()) + "s", "");
        String halfSpace = String.format("%" + (99 - input.length()) / 2 + "s", "");
        System.out.println(input + space);
        System.out.println(
                "_____________________________________________________________________________________________________");
    }

    protected void printSingleBorder(String input) {
        String space = String.format("%" + (99 - input.length()) + "s", "");
        String halfSpace = String.format("%" + (99 - input.length()) / 2 + "s", "");
        System.out.println(
                "______________________________________________________________________________________________________");
        System.out.println("| " + input + space + "|");
        System.out.println(
                "|____________________________________________________________________________________________________|");
    }

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

    public void exitPrompt() {
        System.out.println("\nPress Enter key to exit...");

        try {
            System.in.read(); // Waits for user input
        } catch (Exception e) {
            System.err.println("An I/O error occurred: " + e.getMessage());
        }
    }

}
