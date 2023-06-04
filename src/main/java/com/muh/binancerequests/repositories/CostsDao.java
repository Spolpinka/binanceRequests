package com.muh.binancerequests.repositories;

import com.muh.binancerequests.model.TimeCost;
import com.muh.binancerequests.service.FileService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class CostsDao {

    @Value("${user}")
    private String user;
    @Value("${password}")
    private String password;

    @Value("${url}")
    private String url;

    private static int lastId = 0;
    private final FileService fileService;


    public CostsDao(FileService fileService) {
        this.fileService = fileService;
    }


    public void add(Double cost, String symbol) {
        //здесь кидаем в базу SQL
        try (final Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO binance_courses (datetime, symbol, cost) " +
                             "VALUES (?, ?, ?)")) {
            statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            statement.setString(2, symbol);
            statement.setDouble(3, cost);

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //для автоматической загрузки из файла
    //@PostConstruct
    /*void init() {
        try {
            readFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /*private void saveToFile() {
        try {
            DataFile dataFile = new DataFile(lastId, timeCosts);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            String s = objectMapper.writeValueAsString(dataFile);
            fileService.saveToFile(s);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }*/

    /*private void readFromFile() {
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
    }*/


    public TreeMap<LocalDateTime, Double> getDataForGraph() {
        TreeMap<LocalDateTime, Double> result = new TreeMap<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement("" +
                     "SELECT * FROM binance_courses " +
                     //"WHERE EXTRACT (MONTH FROM datetime) = (?) " +
                     "ORDER BY datetime")
        ) {
            //statement.setInt();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet.getTimestamp(1).toLocalDateTime() + " " + resultSet.getDouble(3));
                result.put(resultSet.getTimestamp(1).toLocalDateTime(), resultSet.getDouble(3));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



        return result;
    }

    public String getMonthCourses(int month) {
        StringBuilder result = new StringBuilder();

        try (Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement statement = connection.prepareStatement("" +
                "SELECT * FROM binance_courses " +
                "WHERE EXTRACT (MONTH FROM datetime) = (?) " +
                "ORDER BY datetime")
        ){
            statement.setInt(1, month);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                result.append(resultSet.getString(1))
                        .append(" | ")
                        .append(resultSet.getString(2))
                        .append(" | ")
                        .append(resultSet.getString(3))
                        .append(" | ")
                        .append(resultSet.getString(4))
                        .append("\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    //переводим всю базу в SQL
    /*public boolean transferToSql(){
        try (final Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO binance_courses (datetime, symbol, cost) " +
                             "VALUES (?, ?, ?)")) {
            for (Map.Entry<Integer, TimeCost> entry : timeCosts.entrySet()) {

            statement.setTimestamp(1, Timestamp.valueOf(entry.getValue().getTime()));
            statement.setString(2, entry.getValue().getSymbol());
            statement.setDouble(3, entry.getValue().getCost());

            statement.execute();
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }*/

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class DataFile {
        private int lastId;
        private Map<Integer, TimeCost> timeCosts;
    }
}
