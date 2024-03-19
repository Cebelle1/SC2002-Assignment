package controller;
import view.CustomerHomerPageView;

public class CustomerController {
    CustomerHomerPageView customerHomeView = new CustomerHomerPageView(this);

    public CustomerController(){

    }

    public void navigate (int page){
        switch(page){
            case 0:
                System.out.println("Customer Home Page Test");
                customerHomeView.renderApp(0);    //default 0
                break;
            
            case 3:
                System.exit(page);
                break;
        }
    }

}
