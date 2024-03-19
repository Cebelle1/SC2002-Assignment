import controller.LoginController;
import controller.CustomerController;
import java.util.Scanner;

public class App{
    Scanner sc = new Scanner(System.in);
    public void start(){
        printAppTitle();
        renderChoice();
        renderApp(0);
        
        
    }

    private static final void printAppTitle(){
        /*System.out.println("╔════════════════════════════════════════════════════════════════════════════════════════════════════════╗");                                                 
        System.out.println("║        CCCCCCCCCCCCC               AAA               MMMMMMMM               MMMMMMMM   SSSSSSSSSSSSSSS║");
        System.out.println("║     CCC::::::::::::C              A:::A              M:::::::M             M:::::::M SS:::::::::::::::S║");
        System.out.println("║   CC:::::::::::::::C             A:::::A             M::::::::M           M::::::::MS:::::SSSSSS::::::S║║");
        System.out.println("║  C:::::CCCCCCCC::::C            A:::::::A            M:::::::::M         M:::::::::MS:::::S     SSSSSSS║");
        System.out.println("║ C:::::C       CCCCCC           A:::::::::A           M::::::::::M       M::::::::::MS:::::S            ║");
        System.out.println("║C:::::C                        A:::::A:::::A          M:::::::::::M     M:::::::::::MS:::::S            ║");
        System.out.println("║C:::::C                       A:::::A A:::::A         M:::::::M::::M   M::::M:::::::M S::::SSSS         ║");
        System.out.println("║C:::::C                      A:::::A   A:::::A        M::::::M M::::M M::::M M::::::M  SS::::::SSSSS    ║");
        System.out.println("║C:::::C                     A:::::A     A:::::A       M::::::M  M::::M::::M  M::::::M    SSS::::::::SS  ║");
        System.out.println("║C:::::C                    A:::::AAAAAAAAA:::::A      M::::::M   M:::::::M   M::::::M       SSSSSS::::S ║");
        System.out.println("║C:::::C                   A:::::::::::::::::::::A     M::::::M    M:::::M    M::::::M            S:::::S║");
        System.out.println("║ C:::::C       CCCCCC    A:::::AAAAAAAAAAAAA:::::A    M::::::M     MMMMM     M::::::M            S:::::S║");
        System.out.println("║  C:::::CCCCCCCC::::C   A:::::A             A:::::A   M::::::M               M::::::MSSSSSSS     S:::::S║");
        System.out.println("║   CC:::::::::::::::C  A:::::A               A:::::A  M::::::M               M::::::MS::::::SSSSSS:::::S║");
        System.out.println("║     CCC::::::::::::C A:::::A                 A:::::A M::::::M               M::::::MS:::::::::::::::SS ║");
        System.out.println("║        CCCCCCCCCCCCCAAAAAAA                   AAAAAAAMMMMMMMM               MMMMMMMM SSSSSSSSSSSSSSS   ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════════════════════╝");    */
        System.out.println("╔═══════════════════════════════════════════════════════════════════╗");                 
        System.out.println("║        █████████    █████████   ██████   ██████  █████████        ║");
        System.out.println("║        ███░░░░░███  ███░░░░░███ ░░██████ ██████  ███░░░░░███      ║");
        System.out.println("║       ███     ░░░  ░███    ░███  ░███░█████░███ ░███    ░░░       ║");
        System.out.println("║      ░███          ░███████████  ░███░░███ ░███ ░░█████████       ║");
        System.out.println("║      ░███          ░███░░░░░███  ░███ ░░░  ░███  ░░░░░░░░███      ║");
        System.out.println("║      ░░███     ███ ░███    ░███  ░███      ░███  ███    ░███      ║");
        System.out.println("║       ░░█████████  █████   █████ █████     █████░░█████████       ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════╝");                                                                                                                                                                                                                  
    }

    //Standalone, not inherited from RenderView.
    public void renderApp(int selection) {

        //Controllers
        int choice = sc.nextInt();
        switch(choice){
            case 1: 
                CustomerController cc = new CustomerController();
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
        String space = String.format("%" + (99- input.length()) + "s", "");
        System.out.println(
                "╔════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ " + input + space + "║");
        System.out.println(
                "╚════════════════════════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println("(1) Customer");
        System.out.println("(2) Staff");
    }   
}
