package com.muh.binancerequests.controller;

import com.muh.binancerequests.service.ChartApplication;
import com.muh.binancerequests.service.FirstGUI;
import com.muh.binancerequests.service.GraphCreator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/graph")
public class GraphController {

    private final GraphCreator graphCreator;
    private final ChartApplication chartApplication;

    //private final FirstGUI firstGUI;

    public GraphController(GraphCreator graphCreator, ChartApplication chartApplication
            //, FirstGUI firstGUI
    ) {
        this.graphCreator = graphCreator;
        this.chartApplication = chartApplication;
        //this.firstGUI = firstGUI;
    }


    @GetMapping("/JPanel")
    public ResponseEntity<String> getGraphJPanel () {
        graphCreator.init();
        return ResponseEntity.ok("Нипанятна!");
    }

    @GetMapping("/Chart")
    public void graph() {
        chartApplication.showChart();
    }
}
