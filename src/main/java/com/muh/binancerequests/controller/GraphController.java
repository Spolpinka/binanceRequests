package com.muh.binancerequests.controller;

import com.muh.binancerequests.service.ChartApplication;
import com.muh.binancerequests.service.GraphCreator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/graph")
public class GraphController {

    private final GraphCreator graphCreator;
    private final ChartApplication chartApplication;

    public GraphController(GraphCreator graphCreator, ChartApplication chartApplication) {
        this.graphCreator = graphCreator;
        this.chartApplication = chartApplication;
    }


    @GetMapping("/JPanel")
    public void getGraphJPanel () {
        graphCreator.init();
    }

    @GetMapping("/Chart")
    public void graph() {
        chartApplication.showChart();
    }
}
