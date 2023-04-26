package com.muh.binancerequests.service;

import org.springframework.stereotype.Service;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//@Service
public class GraphDrawerService extends JFrame {
    public void ChartAppSwing() {
        // Установка заголовка окна
        setTitle("Пример графика Swing");

        // Создание осей X и Y
        DateTimeAxisSwing xAxis = new DateTimeAxisSwing();
        NumberAxisSwing yAxis = new NumberAxisSwing();

        // Установка меток на оси X в формате HH:mm dd-MMM-yyyy
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MMM-yyyy");
        //xAxis.setTickLabelFormatter(new TickLabelFormatterSwing(formatter));

        // Создание графика с осью X типа LocalDateTime
        LineChartSwing lineChart = new LineChartSwing();

        // Добавление данных на график
        lineChart.addData(LocalDateTime.of(2023, 4, 25, 10, 30), 10);
        lineChart.addData(LocalDateTime.of(2023, 4, 26, 12, 15), 20);
        lineChart.addData(LocalDateTime.of(2023, 4, 27, 14, 45), 15);

        // Добавление графика на панель содержимого окна
        getContentPane().add(lineChart);

        // Установка размеров окна
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChartAppSwing());
    }*/
}

class DateTimeAxisSwing extends JComponent {
    // Реализация оси X для дат и времени
    // Ваш код реализации DateTimeAxisSwing
}

class NumberAxisSwing extends JComponent {
    // Реализация оси Y для чисел
    // Ваш код реализации NumberAxisSwing
}

class LineChartSwing extends JComponent {
    // Реализация графика с линиями
    // Ваш код реализации LineChartSwing

    // Метод для добавления данных на график
    public void addData(LocalDateTime xValue, double yValue) {
        // Ваш код для добавления данных на график
    }
}

class TickLabelFormatterSwing extends JComponent {
    // Реализация форматтера для меток на оси X
    // Ваш код реализации TickLabelFormatterSwing

    public TickLabelFormatterSwing(DateTimeFormatter formatter) {
        // Ваш код для инициализации форматтера
    }
}
