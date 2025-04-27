package data;

import java.time.LocalDateTime;
import java.util.List;

public class Question extends Inquiry implements IForSaving {

    public Question() {
        fillDataByUser();
    }

    @Override
    public void fillDataByUser() {
        className = this.getClass().getSimpleName();
        super.fillDataByUser();
        //System.out.println("Question Inquiry created successfully!");
    }

    @Override
    public void handling() {
        System.out.println(getClass().getSimpleName() + " Num." + code);
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
        return className + "," + code + "," + creationDate + "," + description;
    }

    @Override
    public void parseFromFile(List<String> str) {
        className = str.get(0);
        code = Integer.parseInt(str.get(1));
        nextCode(code);
        creationDate= LocalDateTime.parse(str.get(2));
        description = str.get(3);
    }

    public synchronized void nextCode(int code) {
        nextCodeVal = Math.max(nextCodeVal, code + 1);
    }
}


