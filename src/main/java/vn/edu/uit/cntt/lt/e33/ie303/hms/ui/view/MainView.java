package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    public MainView() {
        super("Hotel Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800, 500);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    public void showWindow() { setVisible(true); }
}