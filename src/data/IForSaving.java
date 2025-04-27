package data;

import java.util.List;

public interface IForSaving {

    String getFolderName();

    String getFileName();

    String getData();

    void parseFromFile(List<String> values);

}
