package view;

import view.abstracts.RenderView;

import java.util.List;
import java.util.Scanner;

import controller.AdminController;
import model.Branch;
import model.menus.MenuItem;

public class AdminHomePageView extends RenderView {
    AdminController adminCon;

    public AdminHomePageView(AdminController adminController) {
        this.adminCon = adminController;
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
                renderChoiceDisplay();
                break;
        }
    }

    public void renderChoice() {
        super.printBorder("Admin Home Page View");
        System.out.println("(1) Edit Staff Accounts");
        System.out.println("(2) Display All Staff List");
        System.out.println("(3) Assign Managers");
        System.out.println("(4) Promotion");
        System.out.println("(5) Transfer Staff");
        System.out.println("(6) Edit paymnet method");
        System.out.println("(7) Manage Branch");
    }

    public void renderChoiceEdit() {
        super.printBorder("Edit Staff Accounts");
        System.out.println("Select Branch staff is from: ");
        System.out.println("(1) JP");
        System.out.println("(2) JE");
        System.out.println("(3) NTU");
    }

    public void renderChoiceDisplay() {
        super.printBorder("Display Staff List");
        System.out.println("Filter in terms of: ");
        System.out.println("(1) Branch");
        System.out.println("(2) Role");
        System.out.println("(3) Gender");
        System.out.println("(4) Age");
    }

    // public void printFilterStaff(String ){

    // }

    // }

}
