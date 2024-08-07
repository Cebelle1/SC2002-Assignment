import java.util.List;
import java.util.Scanner;

import controller.CustomerController;
import controller.LoginController;
import model.Branch;
import model.BranchDataManager;
import model.EmployeeHandler;

/**
 * The App class initiates the application start up
 * 
 * @author Loo Si Hui
 * @version 1.0
 */
public class App {
    Scanner sc = new Scanner(System.in);

    public App(){};

    /**
     * Boot up 
     */
    public void start() {
        printAppTitle();
        renderChoice();
        renderApp(0);

    }

    /**
     * Displays the App Title
     */
    private static final void printAppTitle() {

        System.out.println("╔══════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                     ███████╗ ██████╗ ███╗   ███╗███████╗                     ║");
        System.out.println("║                     ██╔════╝██╔═══██╗████╗ ████║██╔════╝                     ║");
        System.out.println("║                     █████╗  ██║   ██║██╔████╔██║███████╗                     ║");
        System.out.println("║                     ██╔══╝  ██║   ██║██║╚██╔╝██║╚════██║                     ║");
        System.out.println("║                     ██║     ╚██████╔╝██║ ╚═╝ ██║███████║                     ║");
        System.out.println("║                     ╚═╝      ╚═════╝ ╚═╝     ╚═╝╚══════╝                     ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════════════╝");

    }

    /**
     * Selects the app to be rendered
     * @param selection Logging in as Customer or Employee. 1 for Customer, 2 for Employee
     */
    // Standalone, not inherited from RenderView.
    public void renderApp(int selection) {
        List<Branch> branches = BranchDataManager.loadMenuIntoBranches();
        List<EmployeeHandler> staffs = BranchDataManager.loadStaff();
        
        BranchDataManager.loadStaffIntoBranch(branches, staffs);
        BranchDataManager.loadQuotaNStatus(branches);

        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                // Constructor Injection
                // Tight coupling, dependency is fundamental to the operation of object and will
                // not change during its lifetime.
                CustomerController cc = new CustomerController(Branch.getOpenBranches()); 
                cc.navigate(10); // Immediately select branch
                start();    //FOR RETURNING FROM CUSTOMER TO SWITCH TO STAFF
                break;
            case 2:
                LoginController lc = new LoginController(staffs);
                lc.navigate(0);
                break;
        }
    }

    /**
     * Displays the Main Menu, prompts for Login choice
     */
    public void renderChoice() {
        String input = "Main Menu > Log in as?";
        String space = String.format("%" + (99 - input.length()) + "s", "");
        System.out.println(
                "╔════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ " + input + space + "║");
        System.out.println(
                "╚════════════════════════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println("(1) Customer");
        System.out.println("(2) Staff");
    }
}
