package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.User;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.presenter.BookingPresenter;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.presenter.UserPresenter;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.Constants;

public class MainView extends JFrame {
    // Main content area
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final JTabbedPane tabbedPane = new JTabbedPane();

    // Status bar components
    private final JPanel statusBar = new JPanel(new BorderLayout());
    private final JLabel userInfoLabel = new JLabel();
    private final JLabel appVersionLabel = new JLabel();
    private final JLabel dateTimeLabel = new JLabel();


    // Timer for updating date/time
    private Timer dateTimeTimer;

    // Current user
    private User currentUser;

    private final String appVersion = "1.0.0";

    private final BookingPresenter bookingPresenter;
    private final UserPresenter userPresenter;

    public MainView() {
        super("UIT Hotel Management Pro");

        JFrame parentFrame = (JFrame)SwingUtilities.getWindowAncestor(this);

        bookingPresenter = new BookingPresenter(parentFrame);
        userPresenter = new UserPresenter(parentFrame);

        // Configure tabbed pane
        tabbedPane.setTabPlacement(JTabbedPane.TOP);
        tabbedPane.setBorder(BorderFactory.createEmptyBorder());
        
        setLayout(new BorderLayout());

        setupMainPanel();
        setupStatusBar();
        startDateTimeUpdater();

        setSize(1366, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (dateTimeTimer != null) {
                    dateTimeTimer.stop();
                }
                dispose();
                System.exit(0);
            }
        });
    }

    private void setupMainPanel() {
        tabbedPane.addTab("Today Bookings", bookingPresenter.getTodayBookingView());
        tabbedPane.addTab("Users", userPresenter.getView());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
    }

    private void setupStatusBar() {
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        statusBar.setBackground(new Color(240, 240, 240));

        // Left panel - User info
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setOpaque(false);
        userInfoLabel.setText("Not logged in");
        leftPanel.add(userInfoLabel);

        // Center panel - App version
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        centerPanel.setOpaque(false);
        appVersionLabel.setText("App version: %s".formatted(appVersion));
        appVersionLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showAboutDialog();
            }
        });
        centerPanel.add(appVersionLabel);

        // Right panel - Date and time
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightPanel.setOpaque(false);
        updateDateTime();
        rightPanel.add(dateTimeLabel);

        statusBar.add(leftPanel, BorderLayout.WEST);
        statusBar.add(centerPanel, BorderLayout.CENTER);
        statusBar.add(rightPanel, BorderLayout.EAST);

        add(statusBar, BorderLayout.SOUTH);
    }

    private void startDateTimeUpdater() {
        dateTimeTimer = new Timer(1000, _ -> updateDateTime());
        dateTimeTimer.start();
    }

    private void updateDateTime() {
        LocalDateTime now = LocalDateTime.now();
        String formattedDateTime = now.format(DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm));
        dateTimeLabel.setText(formattedDateTime);
    }

    private void showAboutDialog() {
        String message = """
            UIT Hotel Management Pro
            Version %s

            A comprehensive hotel management system developed for
            University of Information Technology (UIT)
            
            Course: IE303.E33.LT.CNTT - Công nghệ Java
            
            © 2025 UIT. All rights reserved.
            """.formatted(appVersion);

        JOptionPane.showMessageDialog(this, message, "About", JOptionPane.INFORMATION_MESSAGE);
    }

    public void setLoggedInUser(User user) {
        this.currentUser = user;
        if (user != null) {
            userInfoLabel.setText("User: " + user.getUsername() + " (" + user.getRole() + ")");
        } else {
            userInfoLabel.setText("Not logged in");
        }
    }

    public JTabbedPane getTabbedPane() { return tabbedPane; }
    public User getCurrentUser() { return currentUser; }
}