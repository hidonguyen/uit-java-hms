package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class LoginView extends JFrame {
    private final JTextField usernameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JButton loginBtn = new JButton("Login");
    private final JButton cancelBtn = new JButton("Cancel");

    public LoginView() {
        setTitle("UIT Hotel Management Pro - Login");

        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        panel.add(loginBtn);
        cancelBtn.addActionListener(_ -> {
            dispose();
            System.exit(0);
        });
        panel.add(cancelBtn);

        add(panel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });
    }

    public String getUsername() { return usernameField.getText(); }
    public String getPassword() { return new String(passwordField.getPassword()); }

    public void onSubmit(ActionListener l) { loginBtn.addActionListener(l); }

    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }
}
