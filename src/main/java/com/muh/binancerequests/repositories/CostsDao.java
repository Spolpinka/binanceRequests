package com.muh.binancerequests.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.muh.binancerequests.model.TimeCost;
import com.muh.binancerequests.service.FileService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.ArrayList;

@Repository
public class CostsDao {

    private static int lastId = 0;
    private final FileService fileService;

    private Map<Integer, TimeCost> timeCosts = new TreeMap<>();

    public CostsDao(FileService fileService) {
        this.fileService = fileService;
    }

    public Map<Integer, TimeCost> getTimeCosts() {
        return timeCosts;
    }


    public void add(Double cost, String symbol) {
        timeCosts.put(lastId, new TimeCost(cost, symbol));
        lastId++;
        saveToFile();
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
            DataFile dataFile = new DataFile(lastId, timeCosts);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            String s = objectMapper.writeValueAsString(dataFile);
            fileService.saveToFile(s);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile() {
        try {
            String json = fileService.readFromFile();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            DataFile dataFile = objectMapper.readValue(fileService.readFromFile(),
                    new TypeReference<>() {
                    });
            lastId = dataFile.lastId;
            timeCosts = dataFile.timeCosts;


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public TreeMap<LocalDateTime, Double> getMapForChart() {
        Collection <TimeCost> list = timeCosts.values();
        TreeMap<LocalDateTime, Double> result = new TreeMap<>();

        for (TimeCost bean :
                list) {
            result.put(bean.getTime(), bean.getCost());
        }

        return result;
    }

    public List<Double> getMonthCourses(int month) {
        Collection <TimeCost> list = timeCosts.values();
        List<Double> result = new ArrayList<>();

        for (TimeCost bean :
                list) {
            if (bean.getTime().getMonthValue() == month) {
                result.add(bean.getCost());
            }
        }

        return result;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class DataFile {
        private int lastId;
        private Map<Integer, TimeCost> timeCosts;
    }
}
