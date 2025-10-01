package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.booking;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.BookingDetailDto;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.BookingDto;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.GuestItem;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.RoomItem;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.ServiceItem;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.TodayBookingDto;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.BookingChargeType;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.BookingDetailType;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.BookingStatus;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.PaymentMethod;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.presenter.BookingPresenter;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.Constants;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.UIUtils;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.ui.WrapLayout;

public class TodayBookingView2 extends JPanel {
    private BookingPresenter presenter;

    private JScrollPane roomCardsScrollPane;
    private JPanel cardsPanel;

    private ArrayList<TodayBookingDto> bookings;

    public TodayBookingView2() {
        super(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UIUtils.TEXT_COLOR);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel titleLabel = new JLabel("Today Bookings");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(UIUtils.TEXT_COLOR);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JPanel searchPanel = createSearchPanel();
        headerPanel.add(searchPanel, BorderLayout.EAST);

        // Cards panel
        cardsPanel = new JPanel();
        cardsPanel.setLayout(new WrapLayout(FlowLayout.LEFT, 20, 20));
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        roomCardsScrollPane = new JScrollPane(cardsPanel);
        roomCardsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        roomCardsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        roomCardsScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(headerPanel, BorderLayout.NORTH);
        add(roomCardsScrollPane, BorderLayout.CENTER);
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        searchPanel.setBackground(UIUtils.TEXT_COLOR);

        JButton newBookingBtn = new JButton("+ New Booking");
        newBookingBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        newBookingBtn.setBackground(UIUtils.SECONDARY_COLOR);
        newBookingBtn.setForeground(UIUtils.TEXT_COLOR);
        newBookingBtn.setFocusPainted(false);
        newBookingBtn.setBorderPainted(false);
        newBookingBtn.setPreferredSize(new Dimension(150, 40));
        newBookingBtn.addActionListener(_ -> showBookingDetailDialog((BookingDto) null));

        JButton refreshBtn = new JButton("⟳ Refresh");
        refreshBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        refreshBtn.setBackground(UIUtils.SECONDARY_COLOR);
        refreshBtn.setForeground(UIUtils.TEXT_COLOR);
        refreshBtn.setFocusPainted(false);
        refreshBtn.setBorderPainted(false);
        refreshBtn.setPreferredSize(new Dimension(150, 40));
        refreshBtn.addActionListener(_ -> refreshData());

        searchPanel.add(newBookingBtn);
        searchPanel.add(refreshBtn);
        return searchPanel;
    }

    public void refreshData() {
    }

    private JPanel createRoomCard(TodayBookingDto booking) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(UIUtils.TEXT_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.setPreferredSize(new Dimension(300, 260));
        card.setMinimumSize(new Dimension(300, 260));
        card.setMaximumSize(new Dimension(300, 260));

        JPanel roomInfo = new JPanel(new BorderLayout());
        roomInfo.setOpaque(false);
        roomInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        roomInfo.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));

        JLabel roomLabel = new JLabel("Room " + booking.getRoomName());
        roomLabel.setForeground(new Color(0, 123, 255));
        roomLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel roomTypeLabel = new JLabel(booking.getRoomTypeName());

        roomInfo.add(roomLabel, BorderLayout.WEST);
        roomInfo.add(roomTypeLabel, BorderLayout.EAST);

        JPanel guestInfoPanel = new JPanel(new BorderLayout());
        guestInfoPanel.setOpaque(false);
        guestInfoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        guestInfoPanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        guestInfoPanel.add(new JLabel(booking.getPrimaryGuestName()), BorderLayout.WEST);
        guestInfoPanel.add(new JLabel(booking.getPrimaryGuestPhone()), BorderLayout.EAST);

        JPanel numberOfGuests = new JPanel(new BorderLayout());
        numberOfGuests.setOpaque(false);
        numberOfGuests.setAlignmentX(Component.LEFT_ALIGNMENT);
        numberOfGuests.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        numberOfGuests.add(new JLabel("Adults: " + booking.getNumAdults()), BorderLayout.WEST);
        numberOfGuests.add(new JLabel("Children: " + booking.getNumChildren()), BorderLayout.EAST);

        JPanel checkInTimePanel = createInfoRow("Check-in:",
                booking.getCheckin().format(DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm)));

        JPanel roomChargePanel = createInfoRow("Room Charges:", String.format("%,.0f", booking.getTotalRoomCharges()));
        JLabel roomChargeValue = (JLabel) roomChargePanel.getComponent(1);
        roomChargeValue.setForeground(new Color(0, 150, 0));

        JPanel serviceChargePanel = createInfoRow("Service Charges:",
                String.format("%,.0f", booking.getTotalServiceCharges()));
        JLabel serviceChargeValue = (JLabel) serviceChargePanel.getComponent(1);
        serviceChargeValue.setForeground(new Color(0, 150, 0));

        JLabel statusLabel = new JLabel("● Checked In");
        switch (booking.getStatus()) {
            case BookingStatus.CheckedIn:
                statusLabel.setText("● Checked In");
                statusLabel.setForeground(new Color(40, 167, 69));
                break;
            case BookingStatus.CheckedOut:
                statusLabel.setText("● Checked Out");
                statusLabel.setForeground(new Color(220, 53, 69));
                break;
            default:
                break;
        }

        card.add(roomInfo);
        card.add(guestInfoPanel);
        card.add(numberOfGuests);
        card.add(checkInTimePanel);
        card.add(roomChargePanel);
        card.add(serviceChargePanel);
        card.add(statusLabel);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (presenter != null) {
                    presenter.onBookingCardClicked(booking.getId());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(0, 123, 255), 2),
                        BorderFactory.createEmptyBorder(14, 14, 14, 14)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)));
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
        JLabel valueComp = new JLabel(value);

        panel.add(labelComp, BorderLayout.WEST);
        panel.add(valueComp, BorderLayout.EAST);

        return panel;
    }

    public void showBookingDetailDialog(BookingDto booking) {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this),
                booking == null ? "New Booking"
                        : "Booking Details" + (booking.getBookingNo() == null ? "" : " - " + booking.getBookingNo()),
                true);
        if (booking != null) {
            dialog.setSize(800, 600);
        } else {
            dialog.setSize(800, 300);
        }
        dialog.setLocationRelativeTo(this);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Main content panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // General Information Panel (Top)
        JPanel generalInfoPanel = createGeneralInfoPanel(booking);

        // Services Panel (Bottom)
        if (booking != null) {
            JPanel servicesPanel = createServicesPanel(booking);
            mainPanel.add(servicesPanel, BorderLayout.CENTER);
        }

        mainPanel.add(generalInfoPanel, BorderLayout.NORTH);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        if (booking != null) {
            JButton checkOutBtn = new JButton("Check Out");
            checkOutBtn.setBackground(new Color(220, 53, 69));
            checkOutBtn.setForeground(UIUtils.TEXT_COLOR);
            checkOutBtn.setFocusPainted(false);
            checkOutBtn.setBorderPainted(false);
            checkOutBtn.addActionListener(e -> {
                int result = JOptionPane.showConfirmDialog(dialog,
                        "Are you sure you want to check out this guest?",
                        "Confirm Check Out",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    int checkOutResult = presenter.checkOutBooking();
                    if (checkOutResult == -1) {
                        JOptionPane.showMessageDialog(dialog, "Error during check-out. Please try again.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    JOptionPane.showMessageDialog(dialog, "Guest checked out successfully!");
                    dialog.dispose();
                }
            });
            buttonPanel.add(checkOutBtn);
        }

        JButton saveBtn = new JButton(booking == null ? "Create Booking" : "Update Booking");
        JButton cancelBtn = new JButton("Cancel");

        saveBtn.setBackground(new Color(0, 123, 255));
        saveBtn.setForeground(UIUtils.TEXT_COLOR);
        saveBtn.setFocusPainted(false);
        saveBtn.setBorderPainted(false);

        saveBtn.addActionListener(e -> {
            int saveResult = presenter.saveBooking();
            if (saveResult == -1) {
                JOptionPane.showMessageDialog(dialog, "Error during saving booking. Please try again.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(dialog,
                    "Booking " + (booking == null ? "created" : "updated") + " successfully!");
            dialog.dispose();
        });

        cancelBtn.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);

        contentPanel.add(mainPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(contentPanel);
        dialog.setVisible(true);
    }

    private JPanel createGeneralInfoPanel(BookingDto booking) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)),
                        "General Information",
                        0, 0, null),
                BorderFactory.createEmptyBorder(10, 15, 15, 15)));

        // Create two columns for the form
        JPanel leftColumn = new JPanel(new GridBagLayout());
        JPanel rightColumn = new JPanel(new GridBagLayout());

        GridBagConstraints gbcLeft = new GridBagConstraints();
        gbcLeft.insets = new Insets(5, 5, 5, 5);
        gbcLeft.anchor = GridBagConstraints.WEST;
        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.insets = new Insets(5, 5, 5, 5);
        gbcRight.anchor = GridBagConstraints.WEST;

        JComboBox<String> chargeTypeField = new JComboBox<>(
                new String[] { BookingChargeType.Hour.name(), BookingChargeType.Night.name() });
        addFormFieldToColumn(leftColumn, gbcLeft, 0, "Charge Type:", chargeTypeField);
        JSpinner checkInSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor checkInEditor = new JSpinner.DateEditor(checkInSpinner,
                Constants.DateTimeFormat.ddMMyyyyHHmm);
        checkInSpinner.setEditor(checkInEditor);
        checkInSpinner.setPreferredSize(new Dimension(150, 25));
        addFormFieldToColumn(rightColumn, gbcRight, 0, "Check-in Time:", checkInSpinner);

        JComboBox<RoomItem> roomComboBox = new JComboBox<>();
        addFormFieldToColumn(leftColumn, gbcLeft, 1, "Room:", roomComboBox);
        JTextField roomTypeField = new JTextField(20);
        roomTypeField.setEditable(false);
        addFormFieldToColumn(rightColumn, gbcRight, 1, "Room Type:", roomTypeField);
        roomComboBox.addActionListener(e -> {
            RoomItem selectedRoom = (RoomItem) roomComboBox.getSelectedItem();
            if (selectedRoom != null) {
                // roomTypeField.setText(selectedRoom.getType());
            }
        });

        JComboBox<GuestItem> guestComboBox = new JComboBox<>();
        addFormFieldToColumn(leftColumn, gbcLeft, 2, "Guest:", guestComboBox);
        JTextField guestPhoneField = new JTextField(20);
        addFormFieldToColumn(rightColumn, gbcRight, 2, "Phone Number:", guestPhoneField);
        guestComboBox.addActionListener(e -> {
            GuestItem selectedGuest = (GuestItem) guestComboBox.getSelectedItem();
            if (selectedGuest != null) {
                guestPhoneField.setText(selectedGuest.getPhone());
            }
        });

        JTextField numberAdultsField = new JTextField(20);
        numberAdultsField.setHorizontalAlignment(JTextField.RIGHT);
        numberAdultsField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });
        addFormFieldToColumn(leftColumn, gbcLeft, 3, "Number of Adults:", numberAdultsField);
        JTextField numberChildrenField = new JTextField(20);
        numberChildrenField.setHorizontalAlignment(JTextField.RIGHT);
        numberChildrenField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });
        addFormFieldToColumn(rightColumn, gbcRight, 3, "Number of Children:", numberChildrenField);

        if (booking != null) {
            chargeTypeField.setSelectedItem(booking.getChargeType().name());
            checkInSpinner.setValue(booking.getCheckin());
            for (int i = 0; i < roomComboBox.getItemCount(); i++) {
                if (roomComboBox.getItemAt(i).getId().equals(booking.getRoomId())) {
                    roomComboBox.setSelectedIndex(i);
                    break;
                }
            }
            for (int i = 0; i < guestComboBox.getItemCount(); i++) {
                if (guestComboBox.getItemAt(i).getId().equals(booking.getPrimaryGuestId())) {
                    guestComboBox.setSelectedIndex(i);
                    break;
                }
            }
            numberAdultsField.setText(String.valueOf(booking.getNumAdults()));
            numberChildrenField.setText(String.valueOf(booking.getNumChildren()));
        }

        JPanel formPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        formPanel.add(leftColumn);
        formPanel.add(rightColumn);

        panel.add(formPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createServicesPanel(BookingDto booking) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)),
                        "Services & Add-ons",
                        0, 0, null),
                BorderFactory.createEmptyBorder(10, 15, 15, 15)));

        // Services header with Add Service button
        JPanel headerPanel = new JPanel(new BorderLayout());

        JButton addServiceBtn = new JButton("+ Add Service");
        addServiceBtn.setBackground(new Color(40, 167, 69));
        addServiceBtn.setForeground(UIUtils.TEXT_COLOR);
        addServiceBtn.setFocusPainted(false);
        addServiceBtn.setBorderPainted(false);
        addServiceBtn.setPreferredSize(new Dimension(120, 30));
        addServiceBtn.addActionListener(e -> showAddServiceDialog());

        headerPanel.add(addServiceBtn, BorderLayout.EAST);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Services table
        String[] columnNames = { "Time", "Type", "Description", "Qty", "Unit Price", "Total", "Action" };
        ArrayList<BookingDetailDto> details = booking != null ? booking.getBookingDetails() : new ArrayList<>();
        Object[][] serviceData = new Object[details.size()][7];
        for (int i = 0; i < details.size(); i++) {
            BookingDetailDto detail = details.get(i);
            serviceData[i][0] = detail.getIssuedAt()
                    .format(DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm));
            serviceData[i][1] = detail.getType().name();
            serviceData[i][2] = detail.getDescription() + " (" + detail.getServiceName() + ")";
            serviceData[i][3] = detail.getQuantity();
            serviceData[i][4] = String.format("%,.0f", detail.getUnitPrice());
            serviceData[i][5] = String.format("%,.0f", detail.getAmount());
            serviceData[i][6] = "Remove";
        }

        JTable servicesTable = new JTable(serviceData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only action column is editable
            }
        };

        servicesTable.setRowHeight(30);
        servicesTable.getTableHeader().setBackground(new Color(248, 249, 250));

        // Set column widths
        servicesTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        servicesTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        servicesTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        servicesTable.getColumnModel().getColumn(3).setPreferredWidth(30);
        servicesTable.getColumnModel().getColumn(4).setPreferredWidth(70);
        servicesTable.getColumnModel().getColumn(5).setPreferredWidth(70);
        servicesTable.getColumnModel().getColumn(6).setPreferredWidth(60);

        // Add button renderer for Action column
        servicesTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        servicesTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor());

        JScrollPane tableScrollPane = new JScrollPane(servicesTable);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        tableScrollPane.setPreferredSize(new Dimension(0, 150));

        // Services summary panel
        JPanel summaryPanel = createServicesSummary(booking);

        // Main services content
        JPanel servicesContent = new JPanel(new BorderLayout());
        servicesContent.add(headerPanel, BorderLayout.NORTH);
        servicesContent.add(tableScrollPane, BorderLayout.CENTER);
        servicesContent.add(summaryPanel, BorderLayout.SOUTH);

        panel.add(servicesContent, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createServicesSummary(BookingDto booking) {
        JPanel summaryPanel = new JPanel(new BorderLayout());
        summaryPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(10, 0, 0, 0)));

        JPanel rightPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 10, 2, 0);
        gbc.anchor = GridBagConstraints.EAST;

        // Summary rows
        Double roomCharges = booking != null ? booking.getTotalRoomCharges() : 0.0;
        Double serviceCharges = booking != null ? booking.getTotalServiceCharges() : 0.0;
        addSummaryRow(rightPanel, gbc, 0, "Room Charges:", String.format("%,.0f", roomCharges));
        addSummaryRow(rightPanel, gbc, 1, "Services Subtotal:", String.format("%,.0f", serviceCharges));

        // Total row with different styling
        gbc.gridy = 3;
        gbc.gridx = 0;
        JLabel totalLabel = new JLabel("Total Amount:");
        rightPanel.add(totalLabel, gbc);

        gbc.gridx = 1;
        JLabel totalValue = new JLabel(String.format("%,.0f", roomCharges + serviceCharges));
        totalValue.setForeground(new Color(0, 123, 255));
        rightPanel.add(totalValue, gbc);

        summaryPanel.add(rightPanel, BorderLayout.EAST);
        return summaryPanel;
    }

    private void addSummaryRow(JPanel panel, GridBagConstraints gbc, int row, String label, String value) {
        gbc.gridy = row;
        gbc.gridx = 0;
        JLabel labelComp = new JLabel(label);
        panel.add(labelComp, gbc);

        gbc.gridx = 1;
        JLabel valueComp = new JLabel(value);
        panel.add(valueComp, gbc);
    }

    private void addFormFieldToColumn(JPanel panel, GridBagConstraints gbc, int row, String labelText,
            Component field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;

        JLabel label = new JLabel(labelText);
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        field.setPreferredSize(new Dimension(150, 25));
        panel.add(field, gbc);
    }

    private void showAddServiceDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Add Service");
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setModal(true);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JSpinner issuedAtSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor issuedAtEditor = new JSpinner.DateEditor(issuedAtSpinner,
                Constants.DateTimeFormat.ddMMyyyyHHmm);
        issuedAtSpinner.setEditor(issuedAtEditor);
        issuedAtSpinner.setPreferredSize(new Dimension(150, 25));
        addFormFieldToColumn(contentPanel, gbc, 0, "Time:", issuedAtSpinner);
        JComboBox<String> detailTypeField = new JComboBox<>(new String[] { BookingDetailType.Room.name(),
                BookingDetailType.Service.name(), BookingDetailType.Fee.name(), BookingDetailType.Adjustment.name() });
        addFormFieldToColumn(contentPanel, gbc, 1, "Type:", detailTypeField);

        JTextField descriptionField = new JTextField(20);
        addFormFieldToColumn(contentPanel, gbc, 2, "Description:", descriptionField);

        JComboBox<ServiceItem> serviceComboBox = presenter.getServiceSelectionComboBox();
        addFormFieldToColumn(contentPanel, gbc, 3, "Service:", serviceComboBox);
        JTextField unitPriceField = new JTextField(20);
        unitPriceField.setHorizontalAlignment(JTextField.RIGHT);
        unitPriceField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });
        unitPriceField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void formatPrice() {
                SwingUtilities.invokeLater(() -> {
                    String text = unitPriceField.getText().replace(",", "").trim();
                    if (!text.isEmpty()) {
                        try {
                            double value = Double.parseDouble(text);
                            String formatted = String.format("%,.0f", value);
                            if (!unitPriceField.getText().equals(formatted)) {
                                unitPriceField.setText(formatted);
                            }
                        } catch (NumberFormatException ex) {
                            // Ignore formatting if not a valid number
                        }
                    }
                });
            }

            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                formatPrice();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                formatPrice();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                formatPrice();
            }
        });
        addFormFieldToColumn(contentPanel, gbc, 4, "Price:", unitPriceField);
        serviceComboBox.addActionListener(e -> {
            ServiceItem selectedService = (ServiceItem) serviceComboBox.getSelectedItem();
            if (selectedService != null) {
                unitPriceField.setText(String.format("%,.0f", selectedService.getUnitPrice()));
            }
        });
        JTextField quantityField = new JTextField(20);
        quantityField.setHorizontalAlignment(JTextField.RIGHT);
        quantityField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });
        addFormFieldToColumn(contentPanel, gbc, 5, "Quantity:", quantityField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("Add Service");
        JButton cancelBtn = new JButton("Cancel");

        addBtn.setBackground(new Color(40, 167, 69));
        addBtn.setForeground(UIUtils.TEXT_COLOR);
        addBtn.setFocusPainted(false);
        addBtn.setBorderPainted(false);

        addBtn.addActionListener(e -> {
            // Validate inputs
            if (BookingDetailType.valueOf(detailTypeField.getSelectedItem().toString()) == BookingDetailType.Service
                    && ((ServiceItem) serviceComboBox.getSelectedItem()).getId() == -1L) {
                JOptionPane.showMessageDialog(dialog, "Please select a valid service.", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (BookingDetailType.valueOf(detailTypeField.getSelectedItem().toString()) != BookingDetailType.Service
                    && descriptionField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please enter a description.", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (quantityField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please enter a quantity.", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int result = presenter.addServiceToBooking(
                    (java.util.Date) issuedAtSpinner.getValue(),
                    (String) detailTypeField.getSelectedItem(),
                    descriptionField.getText().trim(),
                    (ServiceItem) serviceComboBox.getSelectedItem(),
                    quantityField.getText().trim());

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
            setForeground(UIUtils.TEXT_COLOR);
            setFocusPainted(false);
            setBorderPainted(false);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
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
            button.setForeground(UIUtils.TEXT_COLOR);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
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

    public void setPresenter(BookingPresenter presenter) {
        this.presenter = presenter;
    }

    public void setBookings(java.util.ArrayList<TodayBookingDto> bookings) {
        this.bookings = bookings;

        for (TodayBookingDto booking : this.bookings) {
            cardsPanel.add(createRoomCard(booking));
        }
    }

    public BookingDto getCurrentBookingDto() {
        // For simplicity, return a dummy booking. In a real application, this would
        // return the actual booking being viewed/edited.
        BookingDto booking = new BookingDto();
        booking.setId(1L);
        booking.setBookingNo("BKG123456");
        booking.setRoomName("101");
        booking.setRoomTypeName("Deluxe");
        booking.setPrimaryGuestName("John Doe");
        booking.setPrimaryGuestPhone("+1 (555) 123-4567");
        booking.setNumAdults(2);
        booking.setNumChildren(0);
        booking.setCheckin(LocalDateTime.of(2025, 9, 28, 14, 0, 0, 0));
        booking.setCheckout(LocalDateTime.of(2025, 9, 30, 12, 0, 0, 0));
        booking.setTotalRoomCharges(360.00);
        booking.setTotalServiceCharges(185.00);
        booking.setStatus(BookingStatus.CheckedIn);
        return booking;
    }
}