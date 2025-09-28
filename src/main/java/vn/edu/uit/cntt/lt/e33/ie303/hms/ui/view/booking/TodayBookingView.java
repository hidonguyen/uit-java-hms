package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.booking;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.TodayBooking;

public class TodayBookingView extends JPanel {
    
    public TodayBookingView() {
        super(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JPanel headerPanel = createHeaderPanel();
        
        // Main content with scrollable room cards
        JScrollPane scrollPane = createRoomCardsScrollPane();

        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        
        JButton newBookingBtn = new JButton("+ New Booking");
        newBookingBtn.setBackground(new Color(0, 123, 255));
        newBookingBtn.setForeground(Color.WHITE);
        newBookingBtn.setFocusPainted(false);
        newBookingBtn.setBorderPainted(false);
        newBookingBtn.setPreferredSize(new Dimension(150, 40));
        newBookingBtn.addActionListener(_ -> showBookingDetailDialog(null));
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(newBookingBtn, BorderLayout.EAST);
        
        headerPanel.add(topPanel, BorderLayout.NORTH);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        return headerPanel;
    }

    private JScrollPane createRoomCardsScrollPane() {
        JPanel cardsPanel = new JPanel();
        cardsPanel.setLayout(new GridLayout(0, 3, 15, 15)); // 3 columns, dynamic rows
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Sample data - replace with actual data from your booking service
        addSampleBookings(cardsPanel);
        
        JScrollPane scrollPane = new JScrollPane(cardsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        return scrollPane;
    }

    private void addSampleBookings(JPanel cardsPanel) {
        // Sample bookings - replace with actual data
        BookingInfo[] sampleBookings = {
            new BookingInfo("101", "John Smith", "Premium Suite", "10:30 AM", "$450.00"),
            new BookingInfo("205", "Maria Garcia", "Deluxe Room", "2:15 PM", "$280.00"),
            new BookingInfo("312", "David Johnson", "Standard Room", "11:45 AM", "$180.00"),
            new BookingInfo("208", "Sarah Wilson", "Executive Suite", "9:20 AM", "$520.00"),
            new BookingInfo("156", "Michael Brown", "Deluxe Room", "1:10 PM", "$280.00"),
            new BookingInfo("304", "Emma Davis", "Standard Room", "3:30 PM", "$180.00")
        };
        
        for (BookingInfo booking : sampleBookings) {
            cardsPanel.add(createRoomCard(booking));
        }
    }

    private JPanel createRoomCard(BookingInfo booking) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setBackground(Color.WHITE);
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Room number (header)
        JLabel roomLabel = new JLabel("Room " + booking.roomNumber);
        roomLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        roomLabel.setForeground(new Color(0, 123, 255));
        roomLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Guest name
        JLabel guestLabel = new JLabel(booking.guestName);
        guestLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        guestLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        guestLabel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        
        // Room type
        JLabel roomTypeLabel = new JLabel(booking.roomType);
        roomTypeLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        roomTypeLabel.setForeground(Color.GRAY);
        roomTypeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Check-in time
        JPanel timePanel = createInfoRow("Check-in:", booking.checkInTime);
        
        // Estimated charges
        JPanel chargePanel = createInfoRow("Charges:", booking.estimatedCharges);
        JLabel chargeValue = (JLabel) chargePanel.getComponent(1);
        chargeValue.setForeground(new Color(0, 150, 0));
        chargeValue.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        
        // Status indicator
        JLabel statusLabel = new JLabel("● Occupied");
        statusLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        statusLabel.setForeground(new Color(255, 140, 0));
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        card.add(roomLabel);
        card.add(guestLabel);
        card.add(roomTypeLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(timePanel);
        card.add(chargePanel);
        card.add(Box.createVerticalStrut(8));
        card.add(statusLabel);
        
        // Add click listener
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showBookingDetailDialog(booking);
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0, 123, 255), 2),
                    BorderFactory.createEmptyBorder(14, 14, 14, 14)
                ));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                    BorderFactory.createEmptyBorder(15, 15, 15, 15)
                ));
            }
        });
        
        return card;
    }

    private JPanel createInfoRow(String label, String value) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        
        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        labelComp.setForeground(Color.GRAY);
        
        JLabel valueComp = new JLabel(value);
        valueComp.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        
        panel.add(labelComp, BorderLayout.WEST);
        panel.add(valueComp, BorderLayout.EAST);
        
        return panel;
    }

    private void showBookingDetailDialog(BookingInfo booking) {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), 
                                   booking == null ? "New Booking" : "Booking Details", true);
        dialog.setSize(800, 700);
        dialog.setLocationRelativeTo(this);
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JLabel headerLabel = new JLabel(booking == null ? "Create New Booking" : "Booking Details - Room " + booking.roomNumber);
        headerLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Main content panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // General Information Panel (Top)
        JPanel generalInfoPanel = createGeneralInfoPanel(booking);
        
        // Services Panel (Bottom)
        JPanel servicesPanel = createServicesPanel(booking);
        
        mainPanel.add(generalInfoPanel, BorderLayout.NORTH);
        mainPanel.add(servicesPanel, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        if (booking != null) {
            JButton checkOutBtn = new JButton("Check Out");
            checkOutBtn.setBackground(new Color(220, 53, 69));
            checkOutBtn.setForeground(Color.WHITE);
            checkOutBtn.setFocusPainted(false);
            checkOutBtn.setBorderPainted(false);
            checkOutBtn.addActionListener(e -> {
                int result = JOptionPane.showConfirmDialog(dialog, 
                    "Are you sure you want to check out this guest?", 
                    "Confirm Check Out", 
                    JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(dialog, "Guest checked out successfully!");
                    dialog.dispose();
                }
            });
            buttonPanel.add(checkOutBtn);
        }
        
        JButton saveBtn = new JButton(booking == null ? "Create Booking" : "Update Booking");
        JButton cancelBtn = new JButton("Cancel");
        
        saveBtn.setBackground(new Color(0, 123, 255));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        saveBtn.setBorderPainted(false);
        
        saveBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(dialog, "Booking " + (booking == null ? "created" : "updated") + " successfully!");
            dialog.dispose();
        });
        
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);
        
        contentPanel.add(headerLabel, BorderLayout.NORTH);
        contentPanel.add(mainPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(contentPanel);
        dialog.setVisible(true);
    }

    private JPanel createGeneralInfoPanel(BookingInfo booking) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), 
                                           "General Information", 
                                           0, 0, 
                                           new Font(Font.SANS_SERIF, Font.BOLD, 14)),
            BorderFactory.createEmptyBorder(10, 15, 15, 15)
        ));
        
        // Create two columns for the form
        JPanel leftColumn = new JPanel(new GridBagLayout());
        JPanel rightColumn = new JPanel(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Left column fields
        addFormFieldToColumn(leftColumn, gbc, 0, "Room Number:", booking != null ? booking.roomNumber : "");
        addFormFieldToColumn(leftColumn, gbc, 1, "Room Type:", booking != null ? booking.roomType : "");
        addFormFieldToColumn(leftColumn, gbc, 2, "Guest Name:", booking != null ? booking.guestName : "");
        addFormFieldToColumn(leftColumn, gbc, 3, "Phone Number:", booking != null ? "+1 (555) 123-4567" : "");
        addFormFieldToColumn(leftColumn, gbc, 4, "Email:", booking != null ? "guest@example.com" : "");
        addFormFieldToColumn(leftColumn, gbc, 5, "ID Number:", booking != null ? "ID123456789" : "");
        
        // Right column fields
        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.insets = new Insets(5, 5, 5, 5);
        gbcRight.anchor = GridBagConstraints.WEST;
        
        addFormFieldToColumn(rightColumn, gbcRight, 0, "Check-in Date:", booking != null ? "Sep 28, 2025" : "");
        addFormFieldToColumn(rightColumn, gbcRight, 1, "Check-in Time:", booking != null ? booking.checkInTime : "");
        addFormFieldToColumn(rightColumn, gbcRight, 2, "Check-out Date:", booking != null ? "Sep 30, 2025" : "");
        addFormFieldToColumn(rightColumn, gbcRight, 3, "Number of Guests:", booking != null ? "2" : "");
        addFormFieldToColumn(rightColumn, gbcRight, 4, "Rate per Night:", booking != null ? "$180.00" : "");
        addFormFieldToColumn(rightColumn, gbcRight, 5, "Payment Method:", booking != null ? "Credit Card" : "");
        
        JPanel formPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        formPanel.add(leftColumn);
        formPanel.add(rightColumn);
        
        panel.add(formPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createServicesPanel(BookingInfo booking) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), 
                                           "Services & Add-ons", 
                                           0, 0, 
                                           new Font(Font.SANS_SERIF, Font.BOLD, 14)),
            BorderFactory.createEmptyBorder(10, 15, 15, 15)
        ));
        
        // Services header with Add Service button
        JPanel headerPanel = new JPanel(new BorderLayout());
        
        JButton addServiceBtn = new JButton("+ Add Service");
        addServiceBtn.setBackground(new Color(40, 167, 69));
        addServiceBtn.setForeground(Color.WHITE);
        addServiceBtn.setFocusPainted(false);
        addServiceBtn.setBorderPainted(false);
        addServiceBtn.setPreferredSize(new Dimension(120, 30));
        addServiceBtn.addActionListener(e -> showAddServiceDialog());
        
        headerPanel.add(addServiceBtn, BorderLayout.EAST);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Services table
        String[] columnNames = {"Service", "Description", "Qty", "Unit Price", "Total", "Date", "Action"};
        Object[][] serviceData = {};
        
        if (booking != null) {
            serviceData = new Object[][]{
                {"Room Service", "Breakfast delivery", "1", "$25.00", "$25.00", "Sep 28", "Remove"},
                {"Laundry", "Dry cleaning", "3", "$15.00", "$45.00", "Sep 28", "Remove"},
                {"Spa Service", "Massage therapy", "1", "$80.00", "$80.00", "Sep 29", "Remove"},
                {"Minibar", "Beverages and snacks", "1", "$35.00", "$35.00", "Sep 28", "Remove"}
            };
        }
        
        JTable servicesTable = new JTable(serviceData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only action column is editable
            }
        };
        
        servicesTable.setRowHeight(30);
        servicesTable.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        servicesTable.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        servicesTable.getTableHeader().setBackground(new Color(248, 249, 250));
        servicesTable.setSelectionBackground(new Color(230, 240, 255));
        
        // Set column widths
        servicesTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        servicesTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        servicesTable.getColumnModel().getColumn(2).setPreferredWidth(40);
        servicesTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        servicesTable.getColumnModel().getColumn(4).setPreferredWidth(80);
        servicesTable.getColumnModel().getColumn(5).setPreferredWidth(60);
        servicesTable.getColumnModel().getColumn(6).setPreferredWidth(70);
        
        // Add button renderer for Action column
        servicesTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        servicesTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor());
        
        JScrollPane tableScrollPane = new JScrollPane(servicesTable);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        tableScrollPane.setPreferredSize(new Dimension(0, 150));
        
        // Services summary panel
        JPanel summaryPanel = createServicesSummary();
        
        // Main services content
        JPanel servicesContent = new JPanel(new BorderLayout());
        servicesContent.add(headerPanel, BorderLayout.NORTH);
        servicesContent.add(tableScrollPane, BorderLayout.CENTER);
        servicesContent.add(summaryPanel, BorderLayout.SOUTH);
        
        panel.add(servicesContent, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createServicesSummary() {
        JPanel summaryPanel = new JPanel(new BorderLayout());
        summaryPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(10, 0, 0, 0)
        ));
        
        JPanel rightPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 10, 2, 0);
        gbc.anchor = GridBagConstraints.EAST;
        
        // Summary rows
        addSummaryRow(rightPanel, gbc, 0, "Room Charges:", "$360.00");
        addSummaryRow(rightPanel, gbc, 1, "Services Subtotal:", "$185.00");
        addSummaryRow(rightPanel, gbc, 2, "Tax (10%):", "$54.50");
        
        // Total row with different styling
        gbc.gridy = 3;
        gbc.gridx = 0;
        JLabel totalLabel = new JLabel("Total Amount:");
        totalLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        rightPanel.add(totalLabel, gbc);
        
        gbc.gridx = 1;
        JLabel totalValue = new JLabel("$599.50");
        totalValue.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        totalValue.setForeground(new Color(0, 123, 255));
        rightPanel.add(totalValue, gbc);
        
        summaryPanel.add(rightPanel, BorderLayout.EAST);
        return summaryPanel;
    }

    private void addSummaryRow(JPanel panel, GridBagConstraints gbc, int row, String label, String value) {
        gbc.gridy = row;
        gbc.gridx = 0;
        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        panel.add(labelComp, gbc);
        
        gbc.gridx = 1;
        JLabel valueComp = new JLabel(value);
        valueComp.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        panel.add(valueComp, gbc);
    }

    private void addFormFieldToColumn(JPanel panel, GridBagConstraints gbc, int row, String labelText, String value) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        panel.add(label, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JTextField field = new JTextField(value, 15);
        field.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        field.setPreferredSize(new Dimension(150, 25));
        panel.add(field, gbc);
    }

    private void showAddServiceDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Add Service");
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setModal(true);
        
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        addFormFieldToColumn(contentPanel, gbc, 0, "Service:", "");
        addFormFieldToColumn(contentPanel, gbc, 1, "Description:", "");
        addFormFieldToColumn(contentPanel, gbc, 2, "Quantity:", "");
        addFormFieldToColumn(contentPanel, gbc, 3, "Unit Price:", "");
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("Add Service");
        JButton cancelBtn = new JButton("Cancel");
        
        addBtn.setBackground(new Color(40, 167, 69));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);
        addBtn.setBorderPainted(false);
        
        addBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(dialog, "Service added successfully!");
            dialog.dispose();
        });
        
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(addBtn);
        buttonPanel.add(cancelBtn);
        
        dialog.add(contentPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    // Button renderer and editor classes for the table
    private class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setText("Remove");
            setBackground(new Color(220, 53, 69));
            setForeground(Color.WHITE);
            setFocusPainted(false);
            setBorderPainted(false);
            setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    private class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private boolean isPushed;
        
        public ButtonEditor() {
            super(new JCheckBox());
            button = new JButton();
            button.setOpaque(true);
            button.setText("Remove");
            button.setBackground(new Color(220, 53, 69));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
            button.addActionListener(e -> fireEditingStopped());
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            isPushed = true;
            return button;
        }
        
        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                JOptionPane.showMessageDialog(button, "Service removed!");
            }
            isPushed = false;
            return "Remove";
        }
        
        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }

    // Data class for booking information
    private static class BookingInfo {
        String roomNumber;
        String guestName;
        String roomType;
        String checkInTime;
        String estimatedCharges;
        
        BookingInfo(String roomNumber, String guestName, String roomType, String checkInTime, String estimatedCharges) {
            this.roomNumber = roomNumber;
            this.guestName = guestName;
            this.roomType = roomType;
            this.checkInTime = checkInTime;
            this.estimatedCharges = estimatedCharges;
        }
    }

    public void setBookings(java.util.ArrayList<TodayBooking> bookings) {
        // This method can be used to set actual booking data from the service
        // For now, it does nothing as we are using sample data
    }
}