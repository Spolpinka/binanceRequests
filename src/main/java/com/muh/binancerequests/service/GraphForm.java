package com.muh.binancerequests.service;

import org.springframework.stereotype.Service;

import javax.swing.*;

public class GraphForm extends JFrame{
    private JButton button1;
    private JPanel rootPanel;

    public GraphForm(JPanel rootPanel) {
        setVisible(true);
        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


}
