package view;

import view.abstracts.ARenderView;

import java.util.List;
import java.util.Scanner;

import controller.AdminController;
import model.Branch;
//import model.abstracts.AEmployee;
import model.abstracts.AEmployee;

public class AdminHomePageView extends ARenderView {
    AdminController adminCon;
    // private List<AEmployee> employees;

    public AdminHomePageView(AdminController adminController/* , List<AEmployee> employees */) {
        this.adminCon = adminController;
        // this.employees = employees;
    }

    @Override
    public void renderApp(int selection) {
        switch (selection) {
            case 0:
                renderChoice();
                break;
            case 1:
                renderChoiceEdit();
                break;
            case 2:
                renderChoiceFilterDisplay();
                break;
            case 3:
                addStaffAccountDisplay();
                break;
            case 7:
                renderManageBranch();

            case 10: // error handling
                errorIntInput();
                break;

        }
    }

    public void renderChoice() {
        super.printBorder("Admin Home Page View");
        System.out.println("(1) Edit Staff Accounts");
        System.out.println("(2) Display Staff List");
        System.out.println("(3) Assign Managers");
        System.out.println("(4) Promotion");
        System.out.println("(5) Transfer Staff");
        System.out.println("(6) Edit paymnet method");
        System.out.println("(7) Manage Branch");
    }

    // =============Add Edit Staff List (1)========================================
    public void renderChoiceEdit() {
        super.printBorder("Edit Staff Accounts");
        System.out.println("(1) Add Staff Account");
        System.out.println("(2) Remove Staff Account");
        System.out.println("(3) Edit Staff Account");
    }

    public void addStaffAccountDisplay() {
        super.printBorder("Edit Staff Accounts");
        System.out.println("Enter New Staff Info");
    }

    // ==================Display Staff List With
    // filter(2)===========================
    public void renderChoiceFilterDisplay() {
        super.printBorder("Display Staff List ");
        System.out.println("Filter in terms of: ");
        System.out.println("(1) Branch");
        System.out.println("(2) Role");
        System.out.println("(3) Gender");
        System.out.println("(4) Age");
    }

    public void printemptyfilter() {
        System.out.println("Invalid branch, please enter again: ");
    }

    // display filtered by (branch,role,gender.age)
    public void printFilterStaff(List<AEmployee> printfiltered) {
        if (printfiltered == null || printfiltered.isEmpty()) {
            System.out.println("No data to display.");
            return;
        }

        // Headers
        String[] headers = { "Name", "StaffID", "Role", "Gender", "Age", "Branch", "Password" };

        // Determine maximum width for each column
        int[] maxWidths = new int[headers.length];
        for (int i = 0; i < headers.length; i++) {
            maxWidths[i] = headers[i].length(); // Initialize with header length
        }

        for (AEmployee emp : printfiltered) {
            maxWidths[0] = Math.max(maxWidths[0], emp.getName().length());
            maxWidths[1] = Math.max(maxWidths[1], emp.getStaffID().length());
            maxWidths[2] = Math.max(maxWidths[2], emp.getRole().length());
            maxWidths[3] = Math.max(maxWidths[3], emp.getGender().length());
            maxWidths[4] = Math.max(maxWidths[4], String.valueOf(emp.getAge()).length());
            maxWidths[5] = Math.max(maxWidths[5], emp.getBranch().length());
            maxWidths[6] = Math.max(maxWidths[6], emp.getPassword().length());
        }

        // Print header row
        for (int i = 0; i < headers.length; i++) {
            System.out.print(String.format("%-" + (maxWidths[i] + 2) + "s", headers[i]));
        }
        System.out.println();

        // Print row separator
        for (int width : maxWidths) {
            for (int i = 0; i < width + 2; i++) {
                System.out.print("-");
            }
            System.out.print(" ");
        }
        System.out.println();

        // Print each row of data
        for (AEmployee emp : printfiltered) {
            System.out.print(String.format("%-" + (maxWidths[0] + 2) + "s", emp.getName()));
            System.out.print(String.format("%-" + (maxWidths[1] + 2) + "s", emp.getStaffID()));
            System.out.print(String.format("%-" + (maxWidths[2] + 2) + "s", emp.getRole()));
            System.out.print(String.format("%-" + (maxWidths[3] + 2) + "s", emp.getGender()));
            System.out.print(String.format("%-" + (maxWidths[4] + 2) + "d", emp.getAge()));
            System.out.print(String.format("%-" + (maxWidths[5] + 2) + "s", emp.getBranch()));
            System.out.print(String.format("%-" + (maxWidths[6] + 2) + "s", emp.getPassword()));
            System.out.println();
        }
        super.exitPrompt();
    }

    public void renderManageBranch() {
        super.printBorder("Manage Branch");
        System.out.println("(1) Open a closed branch");
        System.out.println("(2) Close a opened branch");
        System.out.println("(3) Open a new branch");
    }

    public void displayManagePayment(){
        super.printBorder("Manage Payment");
        System.out.println("(1) Add a new payment method");
        System.out.println("(2) Remove a payment method");
    }

    public void errorIntInput() {
        System.out.println("Invalid choice, please enter a valid number");
        System.out.println(" ");
    }

}
