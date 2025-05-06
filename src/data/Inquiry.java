package data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Scanner;

public abstract class Inquiry implements Serializable, data.IForSaving{

    protected Integer code;
    protected String description;
    protected LocalDateTime creationDate;
    protected  String className;

    public void fillDataByUser(String description,LocalDateTime creationDate){

       this.description=description;
       this.creationDate=creationDate;
    }

    public void fillDataByUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("insert description:");
        this.description = scanner.nextLine();
        this.creationDate = LocalDateTime.now();
    }

    public Inquiry(String description){

        fillDataByUser(description,LocalDateTime.now());
        this.className=this.getClass().getName();
    }
    public Inquiry(){};

    public abstract void handling();

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
