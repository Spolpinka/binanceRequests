package com.muh.binancerequests.repositories;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartData {
    private Double cost;
    private LocalDateTime timestamp;
}
