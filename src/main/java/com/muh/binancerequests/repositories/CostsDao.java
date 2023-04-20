package com.muh.binancerequests.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.muh.binancerequests.model.TimeCost;
import com.muh.binancerequests.service.FileService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CostsDao {

    private static int lastId = 0;
    private final FileService fileService;

    private List<TimeCost> timeCosts = new ArrayList<>();

    public CostsDao(FileService fileService) {
        this.fileService = fileService;
    }

    public List<TimeCost> getAllList() {
        return timeCosts;
    }

    public void add(Double cost) {
        timeCosts.add(new TimeCost(cost));
    }

    @PostConstruct
    void init() {
        try {
            readFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        try {
            DataFile dataFile = new DataFile(lastId+1, timeCosts);
            String s = new ObjectMapper().writeValueAsString(dataFile);
            fileService.saveToFile(s);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile() {
        try {
            String json = fileService.readFromFile();
            DataFile dataFile = new ObjectMapper().readValue(fileService.readFromFile(),
                    new TypeReference<>() {
                    });
            lastId = dataFile.lastId;
            timeCosts = dataFile.timeCosts;


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class DataFile {
        private int lastId;
        private List<TimeCost> timeCosts;
    }
}
