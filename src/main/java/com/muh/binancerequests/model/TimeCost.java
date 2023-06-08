package com.muh.binancerequests.model;

import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Override
    public String toString() {
        return "Время : " + time.getDayOfMonth() + "." + time.getMonth() + "." + time.getYear() + //в принципе можно использовать Long
                " " + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond() + "\n"+
                "стоимость = " + cost + "\n" +
                "символ = " + symbol + "\n"
                ;
    }
}
