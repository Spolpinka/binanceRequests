package com.muh.binancerequests.controller;

import com.muh.binancerequests.service.RequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class RequestController {

    private RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

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
}
