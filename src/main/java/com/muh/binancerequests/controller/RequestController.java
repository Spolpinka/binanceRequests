package com.muh.binancerequests.controller;

import com.muh.binancerequests.service.ChartApplication;
import com.muh.binancerequests.service.RequestService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class RequestController {

    private final RequestService requestService;
    private final ChartApplication chartApplication;

    public RequestController(RequestService requestService, ChartApplication chartApplication) {
        this.requestService = requestService;
        this.chartApplication = chartApplication;
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
            return requestService.getRubUsdt("USDT/PHP");
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

    @GetMapping("/avgBTCUSDT")
    public ResponseEntity<String> avgBTCUSDT(){
        try{
            return ResponseEntity.ok(requestService.getAvrCourse("BTCUSDT"));
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

    @GetMapping("/ratioRUBPHP")
    public ResponseEntity<String> ratioUSDTRUB() {
        try{
            return ResponseEntity.ok(requestService.getRatio("USDTRUB", "PHPUSDT"));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/exchangeInfo")
    public ResponseEntity<String> exchangeInfo() {
        try {
            return ResponseEntity.ok(requestService.exchangeInfo());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
