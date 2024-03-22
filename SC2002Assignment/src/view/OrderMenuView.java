package view;
import java.util.List;
import java.util.Locale;
import controller.CustomerController;
import model.Order;
import model.menus.MenuItem;
import view.abstracts.RenderView;
import model.Branch;

public class OrderMenuView extends RenderView{
    CustomerController custCon;
    Branch selectedBranch;

    public OrderMenuView(CustomerController controller) {
        this.custCon = controller;
    }

    public void displayMenu(int inputChoice, List<Branch> branches) {
        int index = 1;
        selectedBranch = branches.get(inputChoice);
        List<MenuItem> menu = selectedBranch.getMenu();
        
        super.printBorder("Menu Items in " + selectedBranch.getName());
        System.out.println("No.   | Name            |     Price     |");
        System.out.println("--------------------------------");
    
        // Print menu items
        for (MenuItem item : menu) {
            // Format item name and price
            String formattedName = formatName(item.getName());
            String formattedPrice = String.format("| $%.2f", item.getPrice());

            // Print formatted item with index
            System.out.println(String.format("%-5s", index) + formattedName + formattedPrice + " |");
            index++;
        }
    }

    private String formatName(String name) {
        // Split the name into words
        String[] words = name.split(" ");
    
        // Capitalize the first letter of each word
        StringBuilder formattedName = new StringBuilder();
        for (String word : words) {
            formattedName.append(word.substring(0, 1).toUpperCase(Locale.ENGLISH)); // Capitalize first letter
            formattedName.append(word.substring(1).toLowerCase(Locale.ENGLISH));    // Convert remaining letters to lowercase
            formattedName.append(" "); // Add space between words
        }
    
        return String.format("| %-15s", formattedName.toString());
    }

    public void displayCurrentOrder(Order order) {
        super.printBorder("Order Status");
        List <MenuItem> items = order.getCurrentOrder();
        int index = 1;
        for(MenuItem mItem : items){
            String formattedName = formatName(mItem.getName());
            System.out.println(String.format("%-5s", index)+formattedName);
            index++;
        }
    }

    public MenuItem getSelectedItem(int menuIndex) {
        if(menuIndex > selectedBranch.getMenu().size()){
            System.out.println("Menu index out of range");
            return null;
        }
        return selectedBranch.getMenu().get(menuIndex);

    }

    @Override
    public void renderApp(int selection) {
    }
        

    @Override
    public void renderChoice() {
       
    }
}