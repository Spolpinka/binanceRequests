package com.muh.binancerequests.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeCost {
    private double cost;

    private String symbol;
    private String time;

    public TimeCost(double cost, String symbol) {
        this.cost = cost;
        this.symbol = symbol;
        LocalDateTime ldt = LocalDateTime.now();
        time = "" + ldt.getHour() + ":" +
        ldt.getMinute() + " " +
        ldt.getDayOfMonth() + "-" +
        ldt.getMonth() + "-" +
        ldt.getYear();
    }
}
