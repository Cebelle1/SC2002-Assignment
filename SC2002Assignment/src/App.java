import controller.LoginController;
import model.Branch;
import controller.CustomerController;
import model.DataLoader;
import java.util.List;
import java.util.Scanner;

import javax.xml.crypto.Data;

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
        // Debug

        // Controllers
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                CustomerController cc = new CustomerController(branches);
                cc.navigate(0);
                break;
            case 2:
                LoginController lc = new LoginController();
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
