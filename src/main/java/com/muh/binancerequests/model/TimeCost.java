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
    private LocalDateTime time;

    public TimeCost(double cost, String symbol) {
        this.cost = cost;
        this.symbol = symbol;
        time = LocalDateTime.now();

    }
}
