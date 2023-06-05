package com.muh.binancerequests.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileService {
    @Value("${path.to.data.file}")
    private String dataFilePath;

    @Value("${name.of.data.file}")
    private String dataFileName;


    /*public String readFromFile() {
        try {
            return Files.readString(Path.of(dataFilePath, dataFileName));
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }*/

    /*public boolean saveToFile(String json) {
        try {
            if (cleanDataFile()) {
                Files.writeString(Path.of(dataFilePath, dataFileName), json);
            } else {
                System.out.println("Проблема с очищением файла записи");
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }*/

    public boolean cleanDataFile() {
        try {
            Path path = Path.of(dataFilePath, dataFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
