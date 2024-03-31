package controller;

import java.util.List;

import controller.abstracts.AController;
import view.AdminHomePageView;

public class AdminController extends AController {
    private AdminHomePageView adminHomePageView = new AdminHomePageView(this);

    public AdminController() {
    
    }

    public void navigate(int page) {
        switch (page) {
            case 0:
                adminHomePageView.renderApp(0);
                int choice = super.getInputInt("");
                if (choice > 7) { // HARDCODED, CHANGE IF NEEDED
                    System.out.println("Invalid Option");
                    this.navigate(0);
                }
                this.navigate(choice);
                break;

            case 1: // Edit Staff Accounts
                adminHomePageView.renderApp(1);
                int choiceEdit = super.getInputInt("");
                filterNavigate(choiceEdit);             
                break;

            case 2: // Display All Staff List in term of branch, role, gender, age
                adminHomePageView.renderApp(2);
                int choiceDisplay = super.getInputInt("");

            case 3: // Assign Managers
                break;

            case 4: // Promotion
                break;

            case 5: // Transfer Staff
                break;

            case 6: // Edit paymnet method
                break;

            case 7: // Manage Branch
                break;

        }
    }


    public void filterNavigate(int num){
        switch(num){
            case 1: //filter by branch
                

            case 2: //filter by role

            case 3: //filter by gender

            case 4: //filter by age


        }

    }

}