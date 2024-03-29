import java.util.List;
import java.util.Scanner;

import controller.CustomerController;
import controller.LoginController;
import model.Branch;
import model.DataLoader;
import model.StaffCategory;

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
        List<Branch> branches = DataLoader.loadBranches("menu_list.txt");
        List<StaffCategory> staffs = DataLoader.loadStaff("staff_list_with_pw.txt"); 
        // Debug

        // Controllers
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                CustomerController cc = new CustomerController(branches);
                cc.navigate(10); // Immediately select branch
                break;
            case 2:
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
