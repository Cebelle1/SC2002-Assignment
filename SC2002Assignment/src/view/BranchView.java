package view;

import model.Branch;
import view.abstracts.ARenderView;

import java.util.List;

public class BranchView extends ARenderView {

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
            if (branch.getOperation() == true) {
                System.out.println("(" + (i + 1) + ") " + branches.get(i).getName());
                i++;
            }
        }

    }

    // should be usable by employee only
    public int displayAllBranch(List<Branch> branches) { // pass in the full branch here
        super.printBorder("Logged in as Employee");
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
