package com.muh.binancerequests.controller;

import com.muh.binancerequests.service.RequestService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @Operation(summary = "Hello", description = "проверка запуска программы")
    @GetMapping("/")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("The program is working");
    }

    @GetMapping("/RubUSDT")
    public ResponseEntity<String> getRubUSDT() {
        try {
            return ResponseEntity.ok(requestService.getRubUsdt("USDTRUB"));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("BTCUSDT")
    public String getBTCUSDT() {
        try {
            return requestService.getRubUsdt("BTCUSDT");
        } catch (IOException e) {
            return e.toString();
        }
    }

    @GetMapping("PHPUSDT")
    public String getPHPUSDT() {
        try {
            return requestService.getRubUsdt("PHPUSDT");
        } catch (IOException e) {
            return e.toString();
        }
    }

    @GetMapping("/avgUSDTRUB")
    public ResponseEntity<String> avgUSDTRUB() {
        try{
            return ResponseEntity.ok(requestService.getAvrCourse("USDTRUB"));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/timeLapUSDTRUB")
    public ResponseEntity<Void> timeLapsUSDTRUB() {
        try{
            requestService.getTimeLapsRequests("USDTRUB");
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
