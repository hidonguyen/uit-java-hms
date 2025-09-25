package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.User;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.presenter.UserPresenter;

public class MainView extends JFrame {
    // Main content area
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final JTabbedPane tabbedPane = new JTabbedPane();

    // Status bar components
    private final JPanel statusBar = new JPanel(new BorderLayout());
    private final JLabel userInfoLabel = new JLabel();
    private final JLabel dateTimeLabel = new JLabel();


    // Timer for updating date/time
    private Timer dateTimeTimer;

    // Current user
    private User currentUser;

    public MainView() {
        super("UIT Hotel Management Pro");
        initializeComponents();
        setupLayout();
        setupMenuBar();
        setupToolBar();
        setupStatusBar();
        setupMainPanel();
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

    private void initializeComponents() {
        // Set toolbar button properties
        setupToolBarButton(addButton, "Add new record", "Add");
        setupToolBarButton(editButton, "Edit selected record", "Edit");
        setupToolBarButton(saveButton, "Save changes", "Save");
        setupToolBarButton(deleteButton, "Delete selected record", "Delete");
        setupToolBarButton(refreshButton, "Refresh data", "Refresh");
        setupToolBarButton(printButton, "Print report", "Print");
        setupToolBarButton(exportButton, "Export data", "Export");

        // Set initial button states
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        saveButton.setEnabled(false);

        // Configure tabbed pane
        tabbedPane.setTabPlacement(JTabbedPane.TOP);
        tabbedPane.setBorder(BorderFactory.createEmptyBorder());
    }

    private void setupToolBarButton(JButton button, String tooltip, String actionCommand) {
        button.setToolTipText(tooltip);
        button.setActionCommand(actionCommand);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(80, 32));
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
    }

    private void setupMenuBar() {
        // File menu items
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem saveAsItem = new JMenuItem("Save As...");
        JMenuItem printItem = new JMenuItem("Print");
        JMenuItem exitItem = new JMenuItem("Exit");

        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.META_DOWN_MASK));
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.META_DOWN_MASK));
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.META_DOWN_MASK));
        printItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.META_DOWN_MASK));

        exitItem.addActionListener(_ -> {
            if (dateTimeTimer != null) {
                dateTimeTimer.stop();
            }
            dispose();
            System.exit(0);
        });

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.addSeparator();
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.addSeparator();
        fileMenu.add(printItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Edit menu items
        JMenuItem undoItem = new JMenuItem("Undo");
        JMenuItem redoItem = new JMenuItem("Redo");
        JMenuItem cutItem = new JMenuItem("Cut");
        JMenuItem copyItem = new JMenuItem("Copy");
        JMenuItem pasteItem = new JMenuItem("Paste");
        JMenuItem selectAllItem = new JMenuItem("Select All");
        JMenuItem findItem = new JMenuItem("Find");

        undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.META_DOWN_MASK));
        redoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.META_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        cutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.META_DOWN_MASK));
        copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.META_DOWN_MASK));
        pasteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.META_DOWN_MASK));
        selectAllItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.META_DOWN_MASK));
        findItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.META_DOWN_MASK));

        editMenu.add(undoItem);
        editMenu.add(redoItem);
        editMenu.addSeparator();
        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.addSeparator();
        editMenu.add(selectAllItem);
        editMenu.addSeparator();
        editMenu.add(findItem);

        // View menu items
        JMenuItem dashboardItem = new JMenuItem("Dashboard");
        JMenuItem bookingsItem = new JMenuItem("Bookings");
        JMenuItem roomsItem = new JMenuItem("Rooms");
        JMenuItem guestsItem = new JMenuItem("Guests");
        JMenuItem servicesItem = new JMenuItem("Services");
        JMenuItem paymentsItem = new JMenuItem("Payments");
        JMenuItem reportsItem = new JMenuItem("Reports");

        viewMenu.add(dashboardItem);
        viewMenu.addSeparator();
        viewMenu.add(bookingsItem);
        viewMenu.add(roomsItem);
        viewMenu.add(guestsItem);
        viewMenu.add(servicesItem);
        viewMenu.add(paymentsItem);
        viewMenu.addSeparator();
        viewMenu.add(reportsItem);

        // Window menu items
        JMenuItem minimizeItem = new JMenuItem("Minimize");
        JMenuItem zoomItem = new JMenuItem("Zoom");
        windowMenu.add(minimizeItem);
        windowMenu.add(zoomItem);

        // Help menu items
        JMenuItem aboutItem = new JMenuItem("About UIT Hotel Management Pro");
        helpMenu.add(aboutItem);

        aboutItem.addActionListener(_ -> showAboutDialog());

        // Add menus to menu bar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(windowMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    private void setupToolBar() {
        toolBar.setFloatable(false);
        toolBar.setRollover(true);
        toolBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        toolBar.add(addButton);
        toolBar.add(editButton);
        toolBar.add(saveButton);
        toolBar.add(deleteButton);
        toolBar.addSeparator();
        toolBar.add(refreshButton);
        toolBar.addSeparator();
        toolBar.add(printButton);
        toolBar.add(exportButton);

        // Add some spacing
        toolBar.add(Box.createHorizontalGlue());

        add(toolBar, BorderLayout.NORTH);
    }

    private void setupMainPanel() {
        // Create welcome panel as default
        JPanel welcomePanel = createWelcomePanel();
        tabbedPane.addTab("Dashboard", welcomePanel);

        // Add some sample tabs to demonstrate the interface
        JPanel bookingsPanel = new JPanel(new BorderLayout());
        bookingsPanel.add(new JLabel("Bookings Module - Under Development", SwingConstants.CENTER));
        tabbedPane.addTab("Bookings", bookingsPanel);

        JPanel roomsPanel = new JPanel(new BorderLayout());
        roomsPanel.add(new JLabel("Rooms Module - Under Development", SwingConstants.CENTER));
        tabbedPane.addTab("Rooms", roomsPanel);

        tabbedPane.addTab("Guests", new UserPresenter().getView());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Welcome to UIT Hotel Management Pro", SwingConstants.CENTER);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JPanel contentPanel = new JPanel(new GridLayout(2, 3, 20, 20));

        // Create quick action cards
        contentPanel.add(createQuickActionCard("New Booking", "Create a new room booking"));
        contentPanel.add(createQuickActionCard("Check In", "Check in a guest"));
        contentPanel.add(createQuickActionCard("Check Out", "Check out a guest"));
        contentPanel.add(createQuickActionCard("Room Status", "View room availability"));
        contentPanel.add(createQuickActionCard("Guest Management", "Manage guest information"));
        contentPanel.add(createQuickActionCard("Reports", "Generate reports"));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createQuickActionCard(String title, String description) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));

        JLabel descLabel = new JLabel("<html><div style='text-align: center'>" + description + "</div></html>", SwingConstants.CENTER);
        descLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        descLabel.setForeground(Color.GRAY);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(descLabel, BorderLayout.CENTER);

        // Add hover effect
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(240, 240, 240));
                card.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(Color.WHITE);
                card.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(MainView.this, 
                    "Feature '" + title + "' will be implemented in future versions.", 
                    "Coming Soon", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });

        return card;
    }

    private void setupStatusBar() {
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        statusBar.setBackground(new Color(240, 240, 240));

        // Left panel - User info
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setOpaque(false);
        userInfoLabel.setText("Not logged in");
        userInfoLabel.setIcon(createColorIcon(Color.GRAY, 8));
        leftPanel.add(userInfoLabel);

        // Center panel - Database status
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        centerPanel.setOpaque(false);
        databaseStatusLabel.setText("Database: Connected");
        databaseStatusLabel.setIcon(createColorIcon(Color.GREEN, 8));
        centerPanel.add(databaseStatusLabel);

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

    private Icon createColorIcon(Color color, int size) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                g.setColor(color);
                g.fillOval(x, y, size, size);
            }

            @Override
            public int getIconWidth() {
                return size;
            }

            @Override
            public int getIconHeight() {
                return size;
            }
        };
    }

    private void startDateTimeUpdater() {
        dateTimeTimer = new Timer(1000, _ -> updateDateTime());
        dateTimeTimer.start();
    }

    private void updateDateTime() {
        LocalDateTime now = LocalDateTime.now();
        String formattedDateTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        dateTimeLabel.setText(formattedDateTime);
    }

    private void showAboutDialog() {
        String message = """
            UIT Hotel Management Pro
            Version 1.0.0
            
            A comprehensive hotel management system developed for
            University of Information Technology (UIT)
            
            Course: IE303.E33.LT.CNTT - Công nghệ Java
            
            © 2025 UIT. All rights reserved.
            """;

        JOptionPane.showMessageDialog(this, message, "About", JOptionPane.INFORMATION_MESSAGE);
    }

    public void setLoggedInUser(User user) {
        this.currentUser = user;
        if (user != null) {
            userInfoLabel.setText("User: " + user.getUsername() + " (" + user.getRole() + ")");
            userInfoLabel.setIcon(createColorIcon(Color.GREEN, 8));
        } else {
            userInfoLabel.setText("Not logged in");
            userInfoLabel.setIcon(createColorIcon(Color.GRAY, 8));
        }
    }

    public void setDatabaseStatus(boolean connected) {
        if (connected) {
            databaseStatusLabel.setText("Database: Connected");
            databaseStatusLabel.setIcon(createColorIcon(Color.GREEN, 8));
        } else {
            databaseStatusLabel.setText("Database: Disconnected");
            databaseStatusLabel.setIcon(createColorIcon(Color.RED, 8));
        }
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    // Getters for action listeners
    public JButton getAddButton() { return addButton; }
    public JButton getEditButton() { return editButton; }
    public JButton getSaveButton() { return saveButton; }
    public JButton getDeleteButton() { return deleteButton; }
    public JButton getRefreshButton() { return refreshButton; }
    public JButton getPrintButton() { return printButton; }
    public JButton getExportButton() { return exportButton; }

    public JTabbedPane getTabbedPane() { return tabbedPane; }
    public User getCurrentUser() { return currentUser; }
}