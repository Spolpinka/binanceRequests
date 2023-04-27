package com.muh.binancerequests.service;

import com.muh.binancerequests.repositories.ChartData;
import com.muh.binancerequests.repositories.CostsDao;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;

public class FirstGUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel panel1;

//пытаемся реализовать график
    private final ChartData chartData;
    private final CostsDao costsDao;

    private static TreeMap<LocalDateTime, Double> dataTreeMap;

    public FirstGUI(ChartData chartData, CostsDao costsDao) {
        this.chartData = chartData;
        this.costsDao = costsDao;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private static void init() {
        CostsDao costsDao1 = new CostsDao(new FileService());
        System.out.println("1");
        TreeMap<LocalDateTime, Double> dataTreeMap = costsDao1.getMapForChart();
        for (Map.Entry<LocalDateTime, Double> entry : dataTreeMap.entrySet()) {
            System.out.println(entry.getKey().toString() + "-" + entry.getValue());
        }
        // Создание датасета для графика
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Преобразование данных и заполнение датасета
        for (Map.Entry<LocalDateTime, Double> entry : dataTreeMap.entrySet()) {
            LocalDateTime timestamp = entry.getKey();
            Double cost = entry.getValue();
            ChartData chartData = new ChartData();
            chartData.setCost(cost);
            String timestampString = "" + timestamp.getDayOfYear() + timestamp.getMonth() + timestamp.getYear();
            System.out.println(timestampString);
            chartData.setTimestamp(timestamp);
            System.out.println(chartData.getTimestamp().getDayOfYear());
            dataset.addValue(chartData.getCost(), "Cost", timestampString);
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

    public static void main(String[] args) {
        FirstGUI dialog = new FirstGUI(new ChartData(), new CostsDao(new FileService()));
        dialog.pack();
        dialog.setVisible(true);
        init();
        System.exit(0);
    }
}
