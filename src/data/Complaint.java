package data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Complaint extends Inquiry implements IForSaving {

    private String assignedBranch;
    Scanner scanner = new Scanner(System.in);

    public String getAssignedBranch(){
        return assignedBranch;
    }

    public Complaint() {
        super();
        fillDataByUser();
    }

    @Override
    public void fillDataByUser() {
        className = this.getClass().getSimpleName();
        super.fillDataByUser();
        System.out.println("insert name of assigned branch:");
        this.assignedBranch = scanner.nextLine();
        //System.out.println("Complaint Inquiry created successfully!");
    }

    @Override
    public void handling() {
        System.out.println(getClass().getSimpleName() + " Num." + code + " assigned to branch: " + assignedBranch);
    }

    @Override
    public String getFolderName() {
        return getClass().getSimpleName();
    }

    @Override
    public String getFileName() {
        return code.toString();
    }

    @Override
    public String getData() {
        return className + "," + code + "," + creationDate + "," + description + "," + assignedBranch;
    }

    @Override
    public void parseFromFile(List<String> str) {
        className = str.get(0);
        code = Integer.parseInt(str.get(1));
        creationDate= LocalDateTime.parse(str.get(2));
        description = str.get(3);
        assignedBranch=str.get(4);
    }


}
