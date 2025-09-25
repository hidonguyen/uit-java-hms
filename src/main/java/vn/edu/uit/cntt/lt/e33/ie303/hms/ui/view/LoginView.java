package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginView extends JFrame {
    private final JTextField usernameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JButton loginBtn = new JButton("Login");
    private final JButton cancelBtn = new JButton("Cancel");

    public LoginView() {
        setTitle("Login");

        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

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
        cancelBtn.addActionListener(_ -> dispose());
        panel.add(cancelBtn);

        add(panel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    public String getUsername() { return usernameField.getText(); }
    public String getPassword() { return new String(passwordField.getPassword()); }

    public void onSubmit(ActionListener l) { loginBtn.addActionListener(l); }
    
    public void showMainView() { /* Show the main application window */
        MainView mainView = new MainView();
        mainView.showWindow();
        setVisible(false);
    }
}
