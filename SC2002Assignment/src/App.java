import java.util.List;
import java.util.Scanner;

import controller.CustomerController;
import controller.LoginController;
import model.Branch;
import model.DataManager;
import model.EmployeeHandler;
import model.Order;


public class App {
    Scanner sc = new Scanner(System.in);
    private DataFiles dataFiles;
    
    public enum DataFiles{
        MENU_LIST("menu_list.txt"),
        BRANCH_LIST("branch_list.txt"),
        STAFF_LIST("staff_list_with_pw.txt");

        private final String fileName;
        DataFiles(String fileName) {
            this.fileName = fileName;
        }
    }

    public App(){};

    public void start() {
        printAppTitle();
        renderChoice();
        renderApp(0);

    }

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

    // Standalone, not inherited from RenderView.
    public void renderApp(int selection) {
        //Text file dataset loads
        List<Branch> branches = DataManager.loadMenuIntoBranches(DataFiles.MENU_LIST.fileName);
        List<EmployeeHandler> staffs = DataManager.loadStaff(DataFiles.STAFF_LIST.fileName);
        //Serialization loads
        Order.loadOrders();

        // Controllers
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                // Constructor Injection
                // Tight coupling, dependency is fundamental to the operation of object and will
                // not change during its lifetime.
                CustomerController cc = new CustomerController(branches); 
                cc.navigate(10); // Immediately select branch
                start();    //FOR RETURNING FROM CUSTOMER TO SWITCH TO STAFF
                break;
            case 2:
                // Setter Injection
                // Dependencies may need to change (staffs) due to modifying of txt files.
                LoginController lc = new LoginController(staffs);
                lc.navigate(0);
                break;
        }
    }

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
