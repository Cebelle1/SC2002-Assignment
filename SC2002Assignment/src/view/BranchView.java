package view;

import java.util.List;

import model.Branch;
import view.abstracts.ARenderView;

/** (NOT DONE PLEASE COMPLETE)
 * The BranchView class is a class that is used to display information related to the Branch
 * 
 * @author Nicole
 * @version 1.0
 */
public class BranchView extends ARenderView {

    /**
     * Used to display Branches that are currently open
     * @param branches List of open branches
     * @param employee False indicates Customer, True indiciates Employee
     */
    // pass in only the opened branches
    public void displayOpenBranch(List<Branch> branches, boolean employee) { // employee=0 > customer, employee=1 >
                                                                             // employee
        if (!employee) {
            super.printBorder("Logged in as Customer > Select Branch");
        } else {
            super.printBorder("Logged in as Employee");
        }

        int i = 0;
        for (Branch branch : branches) {
           
            System.out.println("(" + (i + 1) + ") " + branch.getName());
            i++;
            
        }

    }

    public int displayAllBranch(List<Branch> branches) { // pass in the full branch here
        int i = 0;
        for (Branch branch : branches) {
            if (branch.getOperation() == true || branch.getOperation() == false) {
                System.out.println("(" + (i + 1) + ") " + branches.get(i).getName());
                i++;
            }
        }
        return i;
    }

    
    public void displayBranchError() {
        System.out.println("Please select branch first");
        delay(2);
    }

    @Override
    public void renderApp(int selection) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'renderApp'");
    }

    @Override
    public void renderChoice() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'renderChoice'");
    }
}
