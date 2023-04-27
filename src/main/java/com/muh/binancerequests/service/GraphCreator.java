package com.muh.binancerequests.service;

import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;

@Service
public class GraphCreator extends JPanel {
    public void init() {
        JFrame frame = new JFrame("График");
        GraphCreator graph = new GraphCreator();
        frame.add(graph);
        frame.setSize(600, 400);
        frame.setBackground(Color.black);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Нарисовать график
        drawGraph(g);
    }

    private void drawGraph(Graphics g) {
        // Получить размеры панели
        int width = getWidth();
        int height = getHeight();

        //g.setXORMode(Color.BLUE);

        // Нарисовать оси
        g.setColor(Color.BLACK);
        g.drawLine(50, height - 50, 50, 50); // Ось Y
        g.drawLine(50, height - 50, width - 50, height - 50); // Ось X

        // Нарисовать точки на графике
        g.setColor(Color.RED);
        for (int i = 0; i < 10; i++) {
            int x = 50 + i * 50; // Расстояние между точками по оси X
            int y = height - 50 - i * 30; // Высота точки на оси Y
            g.fillOval(x - 5, y - 5, 10, 10); // Нарисовать точку
        }
    }
}
