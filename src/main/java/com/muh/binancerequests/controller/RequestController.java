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
    public String getRubUSDT() {
        try {
            return requestService.getRubUsdt();
        } catch (IOException e) {
            return e.toString();
        }
    }
}
