package view;

import view.abstracts.ARenderView;
import java.util.List;
import controller.AdminController;
import model.abstracts.AEmployee;

/** 
 * The AdminHomePageView class is for the admin home page, responsible for rendering various options and choices available to the admin user.
 * 
 * This class extends the abstract base view class{@link ARenderView}
 * 
 * @author Nicole
 * @version 1.0
 */

public class AdminHomePageView extends ARenderView {
    AdminController adminCon;


    /**
     * Constructs an AdminHomePageView object with the specified admin controller.
     *
     * @param adminController The admin controller. 
     */
    public AdminHomePageView(AdminController adminController/* , List<AEmployee> employees */) {
        this.adminCon = adminController;
    }


    /**
     * Navigates to the specified case based on user input.
     * @param selection The feature to navigate to.
     * The pages are:
     *              <ul>
     *                  <li>0: Admin Home Page View options </li>
     *                  <li>1: Selection to Edit Staff Accounts </li>
     *                  <li>2: Selection of filter to display Staff List </li>
                        <li>3: Add a new staff into staff list view</li>
                        <li>7: Renders the menu for managing branches.</li>
                        <li>10: Prints an error message for invalid integer input</li>
     *              </ul>
     */
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

    /**
     * Displays the available choices for the admin user.
     */
    public void renderChoice() {
        super.printBorder("Admin Home Page View");
        System.out.println("(1) Edit Staff Accounts");
        System.out.println("(2) Display Staff List");
        System.out.println("(3) Assign Managers");
        System.out.println("(4) Promotion");
        System.out.println("(5) Transfer Staff");
        System.out.println("(6) Edit Payment Method");
        System.out.println("(7) Manage Branch");
    }

    // =============Add Edit Staff List (1)========================================
    /**
     * Displays the options for editing staff accounts.
     */
    public void renderChoiceEdit() {
        super.printBorder("Edit Staff Accounts");
        System.out.println("(1) Add Staff Account");
        System.out.println("(2) Remove Staff Account");
        System.out.println("(3) Edit Staff Account");
    }

    /**
     * Add a new staff into staff list view
     */
    public void addStaffAccountDisplay() {
        super.printBorder("Edit Staff Accounts");
        System.out.println("Enter New Staff Info");
    }

    // ==================Display Staff List With filter(2)===========================
    /**
     * Displays the options for filtering and displaying staff lists.
     */
    public void renderChoiceFilterDisplay() {
        super.printBorder("Display Staff List ");
        System.out.println("Filter in terms of: ");
        System.out.println("(1) Branch");
        System.out.println("(2) Role");
        System.out.println("(3) Gender");
        System.out.println("(4) Age");
    }

    /**
     * Prints an error message for invalid branch input.
     */
    public void printemptyfilter() {
        System.out.println("Invalid branch, please enter again: ");
    }

    /**
     * Prints the filtered staff list.
     *
     * @param printfiltered The list of filtered staff members to print.
     */
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

    /**
     * Renders the options for managing branches.
     */
    public void renderManageBranch() {
        super.printBorder("Manage Branch");
        System.out.println("(1) Open a closed branch");
        System.out.println("(2) Close a opened branch");
        System.out.println("(3) Open a new branch");
    }

    /**
     * Displays the options for managing payment methods.
     */
    public void displayManagePayment(){
        super.printBorder("Manage Payment");
        System.out.println("(1) Add a new payment method");
        System.out.println("(2) Remove a payment method");
    }

    /**
     * Prints an error message for invalid integer input.
     */
    public void errorIntInput() {
        System.out.println("Invalid choice, please enter.");
        System.out.println(" ");
    }

}
