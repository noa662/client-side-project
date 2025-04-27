package data;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Scanner;

public abstract class Inquiry {

    static Integer nextCodeVal = 0;
    protected Integer code;
    protected String description;
    protected String className;
    protected LocalDateTime creationDate;
    protected LinkedList<String> listFiles = new LinkedList<>();

    public Integer getCode() {
        return code;
    }

    public LocalDateTime getCreationDate(){
        return creationDate;
    }

    public String getDescription(){
        return description;
    }

    public Inquiry() {
    }

    public void fillDataByUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("insert description:");
        this.description = scanner.nextLine();
        this.code = nextCodeVal++;
        this.creationDate = LocalDateTime.now();
        System.out.println("insert name of files or enter to end");
        while (true) {
            String file = scanner.nextLine();
            if (file.isEmpty())
                break;
            listFiles.add(file);
        }
    }

    public abstract void handling();
}
