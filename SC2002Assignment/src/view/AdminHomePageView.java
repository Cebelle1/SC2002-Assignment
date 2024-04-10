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

            case 10: // error handling
                errorintinput();
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

    /*
     * MAKE CHANGES HERE
     * public void printAllStaff(List<AEmployee> employees) {
     * System.out.println("Role Gender Branch  Age Name");
     * for (AEmployee test : employees) {
     * // for now print age with name!Later can change to printing all attribute of
     * // employee
     * System.out.println(test.getRole() + " " + test.getGender() + " " +
     * test.getBranch() + " " + test.getAge()
     * + " " + test.getName()
     * + " " + test.getRole());
     * }
     * }
     */

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

    // ==================Display Staff List With filter
    // (2)===========================
    public void renderChoiceFilterDisplay() {
        super.printBorder("Display Staff List");
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
        System.out.println("Name    StaffID    Role     Gender    Age    Branch     Password");
        for (AEmployee test : printfiltered) {
            // for now print age with name!Later can change to printing all attribute of
            // employee
            System.out.println(test.getName() + " " + test.getStaffID() + " " + test.getRole() + " " + test.getGender()
                    + " " + test.getAge()
                    + " " + test.getPassword());

        }
        super.delay(10);
    }

    public void errorintinput() {
        System.out.println("Invalid choice, please enter a valid number");
        System.out.println(" ");
    }

}
