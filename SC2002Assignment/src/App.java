import java.util.List;
import java.util.Scanner;

import controller.CustomerController;
import controller.LoginController;
import model.Branch;
import model.DataManager;
import model.EmployeeHandler;
import model.EmployeeHandler;

public class App {
    Scanner sc = new Scanner(System.in);

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
        List<Branch> branches = DataManager.loadBranches("menu_list.txt");
        List<EmployeeHandler> staffs = DataManager.loadStaff("staff_list_with_pw.txt");
        // Debug

        // Controllers
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                //Constructor Injection
                //Tight coupling, dependency is fundamental to the operation of object and will not change during its lifetime.
                CustomerController cc = new CustomerController(branches);
                cc.navigate(10); // Immediately select branch
                break;
            case 2:
                //Setter Injection
                //Dependencies may need to change (staffs) due to modifying of txt files.
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
