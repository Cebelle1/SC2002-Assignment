package controller;
import view.CustomerHomerPageView;

public class CustomerController {
    CustomerHomerPageView customerHomeView = new CustomerHomerPageView(this);

    public CustomerController(){

    }

    public void navigate (int page){
        switch(page){
            case 0:
                customerHomeView.renderApp(0);    //default 0
                //get input
                
                break;
            case 1:
                customerHomeView.renderApp(1); 
                System.out.println("Cust Controller");
                break;
            case 3:
                System.exit(page);
                break;
        }
    }

}
