package com.muh.binancerequests.service;

import com.muh.binancerequests.repositories.ChartData;
import com.muh.binancerequests.repositories.CostsDao;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;

@Service
public class ChartApplication {

    private final ChartData chartData;
    private final CostsDao costsDao;

    public ChartApplication(ChartData chartData, CostsDao costsDao) {
        this.chartData = chartData;
        this.costsDao = costsDao;
    }

    public void showChart() {
        TreeMap<String, Double> dataTreeMap = costsDao.getMapForChart();
        // Создание датасета для графика
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Преобразование данных и заполнение датасета
        for (Map.Entry<String, Double> entry : dataTreeMap.entrySet()) {
            String timestamp = entry.getKey();
            Double cost = entry.getValue();
            ChartData chartData = new ChartData();
            chartData.setCost(cost);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MMM-yyyy");
            try {
                System.out.println(timestamp);

                chartData.setTimestamp(LocalDateTime.parse(timestamp, formatter));
                System.out.println(chartData.getTimestamp().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            dataset.addValue(chartData.getCost(), "Cost", new SimpleDateFormat("HH:mm dd-MMM-yyyy").format(chartData.getTimestamp()));
        }

        // Создание графика
        JFreeChart chart = ChartFactory.createLineChart(
                "График стоимости",
                "Дата",
                "Стоимость",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Создание панели с графиком
        ChartPanel chartPanel = new ChartPanel(chart);

        // Создание окна
        JFrame frame = new JFrame("График стоимости");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Добавление панели с графиком на окно
        frame.getContentPane().add(chartPanel, BorderLayout.CENTER);

        // Отображение окна
        frame.setVisible(true);
    }
}

