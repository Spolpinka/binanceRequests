package com.muh.binancerequests.controller;

import com.muh.binancerequests.service.GraphCreator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/graph")
public class GraphController {

    private final GraphCreator graphCreator;


    public GraphController(GraphCreator graphCreator) {
        this.graphCreator = graphCreator;
    }


    @GetMapping("/JPanel")
    public ResponseEntity<String> getGraphJPanel () {
        graphCreator.init();
        return ResponseEntity.ok("Нипанятна!");
    }

    @GetMapping("/Chart")
    public void graph() {

    }
}
