package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.booking;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.BookingDetailDto;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.BookingDto;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.GuestItem;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.RoomItem;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.RoomTypeItem;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.BookingChargeType;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.BookingDetailType;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.BookingStatus;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.PaymentStatus;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.common.BaseModalView;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.presenter.BookingPresenter;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.Constants;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.UIUtils;

public class CreateOrEditBookingModal extends BaseModalView {
    private BookingPresenter presenter;

    private final NumberFormat money = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    private final NumberFormat quantity = NumberFormat.getNumberInstance(new Locale("vi", "VN"));

    private Long bookingId = null;
    private final JTextField bookingNoField = new JTextField();
    private final JComboBox<BookingChargeType> chargeTypeCombo = new JComboBox<>(BookingChargeType.values());
    private DateTimePicker checkInTimePicker;
    private DateTimePicker checkOutTimePicker;
    DefaultComboBoxModel<RoomTypeItem> roomTypes = new DefaultComboBoxModel<>();
    private final JComboBox<RoomTypeItem> roomTypeCombo = new JComboBox<RoomTypeItem>(roomTypes);
    DefaultComboBoxModel<RoomItem> rooms = new DefaultComboBoxModel<>();
    private final JComboBox<RoomItem> roomCombo = new JComboBox<RoomItem>(rooms);
    DefaultComboBoxModel<GuestItem> guests = new DefaultComboBoxModel<>();
    private final JComboBox<GuestItem> guestCombo = new JComboBox<GuestItem>(guests);
    private final JTextField guestPhoneField = new JTextField();
    private final JTextField numAdultsField = new JTextField();
    private final JTextField numChildrenField = new JTextField();
    private final JComboBox<BookingStatus> statusCombo = new JComboBox<>(BookingStatus.values());
    private final JComboBox<PaymentStatus> paymentStatusCombo = new JComboBox<>(PaymentStatus.values());
    private final JTextField notesField = new JTextField();
    private final JTextField createdAtField = new JTextField();
    private final JTextField createdByField = new JTextField();
    private final JTextField updatedAtField = new JTextField();
    private final JTextField updatedByField = new JTextField();

    private JLabel roomChargesLabel;
    private JLabel serviceChargesLabel;
    private JLabel totalAmountLabel;
    
    private JScrollPane tableScrollPane;
    private JTable servicesTable;
    private final String[] DetailTableColumnNames = { "Time", "Type", "Description", "UoM", "Unit Price", "Quantity", "Discount", "Total", "Action", "Id", "ServiceId" };
    private JPanel bookingDetailTablePanel;
    private JPanel summaryPanel;

    private boolean isEditing = false;

    private JButton checkoutBtn;

    public CreateOrEditBookingModal(JFrame parent, BookingPresenter presenter) {
        super(parent, "Create or Edit Booking");

        this.presenter = presenter;
        setupFormFields();
        finalizeModal();
    }

    @Override
    protected void onInitExtraActions(JPanel actionPanel) {
        checkoutBtn = createModernButton("Checkout", UIUtils.ACCENT_COLOR, Color.WHITE);
        actionPanel.add(createSpacer(10));
        actionPanel.add(checkoutBtn);
    }

    private Component createSpacer(int width) {
        return javax.swing.Box.createHorizontalStrut(width);
    }

    protected JButton createModernButton(String text, Color bgColor, Color textColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(textColor);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setBorderPainted(false);
        return button;
    }

    public void onCheckout(java.awt.event.ActionListener l) {
        if (checkoutBtn != null) {
            checkoutBtn.addActionListener(l);
        }
    }

    private void setupFormFields() {
        setModalityType(ModalityType.APPLICATION_MODAL);

        bookingNoField.setEnabled(false);
        createdAtField.setEnabled(false);
        createdByField.setEnabled(false);
        updatedAtField.setEnabled(false);
        updatedByField.setEnabled(false);

        int row = 0;
        addFormField("Booking No:", bookingNoField, row, 0);
        addFormField("Charge Type:", chargeTypeCombo, row++, 1);

        DatePickerSettings checkInDatePickerSettings = new DatePickerSettings();
        checkInDatePickerSettings.setFormatForDatesCommonEra(Constants.DateTimeFormat.ddMMyyyy);
        checkInDatePickerSettings.setFormatForDatesBeforeCommonEra(Constants.DateTimeFormat.ddMMyyyy);
        TimePickerSettings checkInTimePickerSettings = new TimePickerSettings();
        checkInTimePickerSettings.use24HourClockFormat();
        checkInTimePickerSettings.setFormatForDisplayTime(Constants.DateTimeFormat.HHmm);
        checkInTimePickerSettings.setFormatForMenuTimes(Constants.DateTimeFormat.HHmm);
        checkInTimePicker = new DateTimePicker(checkInDatePickerSettings, checkInTimePickerSettings);
        addFormField("Check-In Time:", checkInTimePicker, row, 0);

        DatePickerSettings checkOutDatePickerSettings = new DatePickerSettings();
        checkOutDatePickerSettings.setFormatForDatesCommonEra(Constants.DateTimeFormat.ddMMyyyy);
        checkOutDatePickerSettings.setFormatForDatesBeforeCommonEra(Constants.DateTimeFormat.ddMMyyyy);
        TimePickerSettings checkOutTimePickerSettings = new TimePickerSettings();
        checkOutTimePickerSettings.use24HourClockFormat();
        checkOutTimePickerSettings.setFormatForDisplayTime(Constants.DateTimeFormat.HHmm);
        checkOutTimePickerSettings.setFormatForMenuTimes(Constants.DateTimeFormat.HHmm);
        checkOutTimePicker = new DateTimePicker(checkOutDatePickerSettings, checkOutTimePickerSettings);
        addFormField("Check-Out Time:", checkOutTimePicker, row++, 1);

        roomTypes.removeAllElements();
        presenter.getRoomTypeItems().forEach(rt -> roomTypes.addElement(rt));
        roomTypeCombo.addActionListener(_ -> {
            RoomTypeItem selectedRoomType = (RoomTypeItem) roomTypeCombo.getSelectedItem();
            if (selectedRoomType != null && selectedRoomType.getId() != -1L) {
                rooms.removeAllElements();
                presenter.getRoomItems(selectedRoomType.getId()).forEach(r -> rooms.addElement(r));
            } else {
                rooms.removeAllElements();
                presenter.getRoomItems(-1L).forEach(r -> rooms.addElement(r));
            }
        });
        addFormField("Room Type:", roomTypeCombo, row, 0);
        addFormField("Room:", roomCombo, row++, 1);

        guests.removeAllElements();
        presenter.getGuestItems().forEach(g -> guests.addElement(g));
        guestCombo.addActionListener(_ -> {
            GuestItem selectedGuest = (GuestItem) guestCombo.getSelectedItem();
            if (selectedGuest != null) {
                guestPhoneField.setText(selectedGuest.getPhone());
            } else {
                guestPhoneField.setText("");
            }
        });
        addFormField("Guest:", guestCombo, row, 0);
        addFormField("Guest Phone:", guestPhoneField, row++, 1);

        numAdultsField.setHorizontalAlignment(JTextField.RIGHT);
        numAdultsField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });
        addFormField("Num Adults:", numAdultsField, row, 0);
        numChildrenField.setHorizontalAlignment(JTextField.RIGHT);
        numChildrenField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });
        addFormField("Num Children:", numChildrenField, row++, 1);

        addFormField("Status:", statusCombo, row, 0);
        addFormField("Payment Status:", paymentStatusCombo, row++, 1);

        addFormField("Note:", notesField, row++, 0, 3);

        addFormField("Created At:", createdAtField, row, 0);
        addFormField("Created By:", createdByField, row++, 1);

        addFormField("Updated At:", updatedAtField, row, 0);
        addFormField("Updated By:", updatedByField, row++, 1);
        
        // Services detail table
        bookingDetailTablePanel = createDetailTablePanel();
        addFormField(bookingDetailTablePanel, row++, 0, 4);

        // Services summary panel
        summaryPanel = createSummaryPanel();
        addFormField(summaryPanel, row++, 0, 4);
    }

    private JPanel createDetailTablePanel() {
        JPanel servicesHeaderPanel = new JPanel(new BorderLayout());
        servicesHeaderPanel.setBackground(CARD_COLOR);

        JButton addServiceBtn = new JButton("+ Add Service");
        addServiceBtn.setBackground(UIUtils.ACCENT_COLOR);
        addServiceBtn.setForeground(Color.WHITE);
        addServiceBtn.setFocusPainted(false);
        addServiceBtn.setBorderPainted(false);
        addServiceBtn.setPreferredSize(new Dimension(120, 30));
        addServiceBtn.addActionListener(_ -> presenter.onAddServiceClicked());

        servicesHeaderPanel.add(addServiceBtn, BorderLayout.EAST);
        servicesHeaderPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 5, 0));

        // Services table
        servicesTable = new JTable(new Object[0][DetailTableColumnNames.length], DetailTableColumnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 8; // Only action column is editable
            }
        };

        tableScrollPane = new JScrollPane(servicesTable);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        tableScrollPane.setPreferredSize(new Dimension(0, 200));

        JPanel servicesContent = new JPanel(new BorderLayout());
        servicesContent.add(servicesHeaderPanel, BorderLayout.NORTH);
        servicesContent.add(tableScrollPane, BorderLayout.CENTER);

        return servicesContent;
    }

    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel roomChargesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        roomChargesPanel.setBackground(CARD_COLOR);
        roomChargesLabel = new JLabel(String.format("%,.0f", 0d));
        roomChargesLabel.setHorizontalAlignment(JLabel.RIGHT);
        roomChargesPanel.add(new JLabel("Room Charges: "));
        roomChargesPanel.add(roomChargesLabel);

        JPanel serviceChargesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        serviceChargesPanel.setBackground(CARD_COLOR);
        serviceChargesLabel = new JLabel(String.format("%,.0f", 0d));
        serviceChargesLabel.setHorizontalAlignment(JLabel.RIGHT);
        serviceChargesPanel.add(new JLabel("Service Charges: "));
        serviceChargesPanel.add(serviceChargesLabel);

        JPanel totalAmountPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalAmountPanel.setBackground(CARD_COLOR);
        totalAmountLabel = new JLabel(String.format("%,.0f", 0d));
        totalAmountLabel.setHorizontalAlignment(JLabel.RIGHT);
        totalAmountLabel.setForeground(PRIMARY_COLOR);
        totalAmountPanel.add(new JLabel("Total Amount: "));
        totalAmountPanel.add(totalAmountLabel);

        panel.add(roomChargesPanel, BorderLayout.NORTH);
        panel.add(serviceChargesPanel, BorderLayout.CENTER);
        panel.add(totalAmountPanel, BorderLayout.SOUTH);

        return panel;
    }

    @Override
    public boolean isValidInput() {
        LocalDateTime checkInTime = checkInTimePicker.getDateTimePermissive();
        if (checkInTime == null) {
            showStyledError(Constants.ValidateMessage.CHECKIN_TIME_CANNOT_BE_EMPTY,
                    "Validation Error - " + Constants.ErrorTitle.BOOKING);
            return false;
        }

        LocalDateTime checkOutTime = checkOutTimePicker.getDateTimePermissive();
        if (checkOutTime != null && !checkOutTime.isAfter(checkInTime)) {
            showStyledError(Constants.ValidateMessage.CHECKOUT_TIME_MUST_BE_AFTER_CHECKIN_TIME,
                    "Validation Error - " + Constants.ErrorTitle.BOOKING);
            return false;
        }

        RoomTypeItem selectedRoomType = (RoomTypeItem) roomTypeCombo.getSelectedItem();
        if (selectedRoomType == null || selectedRoomType.getId() == -1L) {
            showStyledError(Constants.ValidateMessage.ROOM_TYPE_MUST_BE_SELECTED,
                    "Validation Error - " + Constants.ErrorTitle.BOOKING);
            return false;
        }

        RoomItem selectedRoom = (RoomItem) roomCombo.getSelectedItem();
        if (selectedRoom == null || selectedRoom.getId() == -1L) {
            showStyledError(Constants.ValidateMessage.ROOM_MUST_BE_SELECTED,
                    "Validation Error - " + Constants.ErrorTitle.BOOKING);
            return false;
        }

        GuestItem selectedGuest = (GuestItem) guestCombo.getSelectedItem();
        if (selectedGuest == null || selectedGuest.getId() == -1L) {
            showStyledError(Constants.ValidateMessage.GUEST_MUST_BE_SELECTED,
                    "Validation Error - " + Constants.ErrorTitle.BOOKING);
            return false;
        }

        return true;
    }

    @Override
    public BookingDto getModel() {
        BookingDto booking = new BookingDto();
        
        booking.setId(bookingId);
        booking.setBookingNo(bookingNoField.getText());
        booking.setChargeType((BookingChargeType) chargeTypeCombo.getSelectedItem());
        booking.setCheckin(checkInTimePicker.getDateTimePermissive());
        booking.setCheckout(checkOutTimePicker.getDateTimePermissive());
        RoomTypeItem selectedRoomType = (RoomTypeItem) roomTypeCombo.getSelectedItem();
        booking.setRoomTypeId(selectedRoomType != null ? selectedRoomType.getId() : null);
        RoomItem selectedRoom = (RoomItem) roomCombo.getSelectedItem();
        booking.setRoomId(selectedRoom != null ? selectedRoom.getId() : null);
        GuestItem selectedGuest = (GuestItem) guestCombo.getSelectedItem();
        booking.setPrimaryGuestId(selectedGuest != null ? selectedGuest.getId() : null);
        booking.setNumAdults(!numAdultsField.getText().trim().isEmpty()
                ? Integer.parseInt(numAdultsField.getText().trim())
                : 0);
        booking.setNumChildren(!numChildrenField.getText().trim().isEmpty()
                ? Integer.parseInt(numChildrenField.getText().trim())
                : 0);
        booking.setStatus((BookingStatus) statusCombo.getSelectedItem());
        booking.setPaymentStatus((PaymentStatus) paymentStatusCombo.getSelectedItem());
        booking.setNotes(!notesField.getText().trim().isEmpty() ? notesField.getText().trim() : null);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm);
        
        if (!createdAtField.getText().trim().isEmpty())
            booking.setCreatedAt(
                    LocalDateTime.parse(createdAtField.getText().trim(), fmt));
        if (!createdByField.getText().trim().isEmpty())
            booking.setCreatedBy(Long.parseLong(createdByField.getText().trim()));
        if (!updatedAtField.getText().trim().isEmpty())
            booking.setUpdatedAt(
                    LocalDateTime.parse(updatedAtField.getText().trim(), fmt));
        if (!updatedByField.getText().trim().isEmpty())
            booking.setUpdatedBy(Long.parseLong(updatedByField.getText().trim()));

        DefaultTableModel model = (DefaultTableModel) servicesTable.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            BookingDetailDto detail = new BookingDetailDto();

            detail.setIssuedAt(model.getValueAt(i, 0) != null
                    ? LocalDateTime.parse((String) model.getValueAt(i, 0), fmt)
                    : LocalDateTime.now());
            detail.setType(BookingDetailType.valueOf((String) model.getValueAt(i, 1)));
            detail.setDescription(model.getValueAt(i, 2) != null ? (String) model.getValueAt(i, 2) : null);
            detail.setUnit(model.getValueAt(i, 3) != null ? (String) model.getValueAt(i, 3) : null);
            detail.setUnitPrice(model.getValueAt(i, 4) != null
                    ? Double.parseDouble(((String) model.getValueAt(i, 4)).replaceAll("[^\\d]", ""))
                    : 0d);
            detail.setQuantity(Integer.parseInt(((String) model.getValueAt(i, 5)).replaceAll("[^\\d]", "")));
            detail.setDiscountAmount(model.getValueAt(i, 6) != null
                    ? Double.parseDouble(((String) model.getValueAt(i, 6)).replaceAll("[^\\d]", ""))
                    : 0d);
            detail.setAmount(model.getValueAt(i, 7) != null
                    ? Double.parseDouble(((String) model.getValueAt(i, 7)).replaceAll("[^\\d]", ""))
                    : 0d);
            detail.setId((Long) model.getValueAt(i, 9));
            detail.setServiceId((Long) model.getValueAt(i, 10));

            booking.getBookingDetails().add(detail);
        }
        return booking;
    }

    @Override
    public void setModel(Object model) {
        BookingDto booking = (BookingDto) model;

        if (booking == null) {
            this.setSize(900, 550);

            clearForm();

            checkoutBtn.setVisible(false);
            bookingDetailTablePanel.setVisible(false);
            summaryPanel.setVisible(false);

            return;
        }

        this.setSize(900, 900);

        this.bookingId = booking.getId();
        bookingNoField.setText(booking.getBookingNo());
        chargeTypeCombo.setSelectedItem(booking.getChargeType());
        checkInTimePicker.setDateTimePermissive(booking.getCheckin());
        checkOutTimePicker.setDateTimePermissive(booking.getCheckout());
        
        for (int i = 0; i < roomTypes.getSize(); i++) {
            RoomTypeItem item = roomTypes.getElementAt(i);
            if (item != null && item.getId() != null && item.getId().equals(booking.getRoomTypeId())) {
                roomTypeCombo.setSelectedIndex(i);
                break;
            }
        }
        
        for (int i = 0; i < rooms.getSize(); i++) {
            RoomItem item = rooms.getElementAt(i);
            if (item != null && item.getId() != null && item.getId().equals(booking.getRoomId())) {
                roomCombo.setSelectedIndex(i);
                break;
            }
        }

        for (int i = 0; i < guests.getSize(); i++) {
            GuestItem item = guests.getElementAt(i);
            if (item != null && item.getId() != null && item.getId().equals(booking.getPrimaryGuestId())) {
                guestCombo.setSelectedIndex(i);
                break;
            }
        }

        numAdultsField.setText(String.valueOf(booking.getNumAdults()));
        numChildrenField.setText(String.valueOf(booking.getNumChildren()));
        statusCombo.setSelectedItem(booking.getStatus());
        paymentStatusCombo.setSelectedItem(booking.getPaymentStatus());
        notesField.setText(booking.getNotes() != null ? booking.getNotes() : "");

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm);
        
        createdAtField.setText(booking.getCreatedAt() != null ? booking.getCreatedAt().format(fmt) : "");
        createdByField.setText(booking.getCreatedBy() != null ? booking.getCreatedBy().toString() : "");
        updatedAtField.setText(booking.getUpdatedAt() != null ? booking.getUpdatedAt().format(fmt) : "");
        updatedByField.setText(booking.getUpdatedBy() != null ? booking.getUpdatedBy().toString() : "");

        Object[][] serviceData = new Object[booking.getBookingDetails().size()][DetailTableColumnNames.length];
        for (int i = 0; i < booking.getBookingDetails().size(); i++) {
            BookingDetailDto detail = booking.getBookingDetails().get(i);
            serviceData[i][0] = detail.getIssuedAt().format(DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm));
            serviceData[i][1] = detail.getType().name();
            serviceData[i][2] = detail.getDescription() + (detail.getServiceId() == null ? "" : " (" + detail.getServiceName() + ")");
            serviceData[i][3] = detail.getUnit();
            serviceData[i][4] = money.format(detail.getUnitPrice());
            serviceData[i][5] = quantity.format(detail.getQuantity());
            serviceData[i][6] = money.format(detail.getDiscountAmount());
            serviceData[i][7] = money.format(detail.getAmount());
            serviceData[i][8] = null;
            serviceData[i][9] = detail.getId();
            serviceData[i][10] = detail.getServiceId();
        }
        
        DefaultTableModel serviceDataModel = new DefaultTableModel(serviceData, DetailTableColumnNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 8; // Only action column is editable
            }
        };
        servicesTable = new JTable(serviceDataModel);
        servicesTable.setRowHeight(36);
        servicesTable.getTableHeader().setBackground(CARD_COLOR);
        servicesTable.getTableHeader().setReorderingAllowed(false);
        servicesTable.setShowGrid(true);

        // Set column widths
        servicesTable.getColumn("Time").setPreferredWidth(100);
        servicesTable.getColumn("Type").setPreferredWidth(30);
        servicesTable.getColumn("Description").setPreferredWidth(200);
        servicesTable.getColumn("UoM").setPreferredWidth(20);
        servicesTable.getColumn("Unit Price").setPreferredWidth(70);
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        servicesTable.getColumn("Unit Price").setCellRenderer(rightRenderer);
        servicesTable.getColumn("Quantity").setPreferredWidth(20);
        servicesTable.getColumn("Quantity").setCellRenderer(rightRenderer);
        servicesTable.getColumn("Discount").setPreferredWidth(70);
        servicesTable.getColumn("Discount").setCellRenderer(rightRenderer);
        servicesTable.getColumn("Total").setPreferredWidth(70);
        servicesTable.getColumn("Total").setCellRenderer(rightRenderer);
        servicesTable.getColumn("Action").setPreferredWidth(10);
        servicesTable.getColumn("Id").setMinWidth(0);
        servicesTable.getColumn("Id").setMaxWidth(0);
        servicesTable.getColumn("ServiceId").setMinWidth(0);
        servicesTable.getColumn("ServiceId").setMaxWidth(0);

        // Add button renderer for Action column
        servicesTable.getColumn("Action").setCellRenderer(new IconButtonRenderer());
        servicesTable.getColumn("Action").setCellEditor(new IconButtonEditor(new JCheckBox(), serviceDataModel));

        tableScrollPane.setViewportView(servicesTable);
        tableScrollPane.revalidate();
        tableScrollPane.repaint();

        Double roomCharges = booking.getTotalRoomCharges();
        Double serviceCharges = booking.getTotalServiceCharges();
        Double totalAmount = roomCharges + serviceCharges;
        roomChargesLabel.setText(money.format(roomCharges));
        serviceChargesLabel.setText(money.format(serviceCharges));
        totalAmountLabel.setText(money.format(totalAmount));

        bookingDetailTablePanel.setVisible(true);
        summaryPanel.setVisible(true);
    }

    @Override
    public void clearForm() {
        super.clearForm();
        
        this.bookingId = null;
        bookingNoField.setText("Auto-generated upon creation");
        chargeTypeCombo.setSelectedIndex(0);
        checkInTimePicker.setDateTimePermissive(LocalDateTime.now());
        checkOutTimePicker.setDateTimePermissive(null);

        roomTypeCombo.setSelectedIndex(0);
        roomCombo.setSelectedIndex(0);
        guestCombo.setSelectedIndex(0);
        guestPhoneField.setText("");
        numAdultsField.setText("1");
        numChildrenField.setText("0");

        statusCombo.setSelectedItem(BookingStatus.CheckedIn);
        paymentStatusCombo.setSelectedItem(PaymentStatus.Unpaid);

        notesField.setText("");

        createdAtField.setText("");
        createdByField.setText("");
        updatedAtField.setText("");
        updatedByField.setText("");

        servicesTable.setModel(new DefaultTableModel(new Object[0][DetailTableColumnNames.length], DetailTableColumnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 8; // Only action column is editable
            }
        });

        roomChargesLabel.setText(money.format(0d));
        serviceChargesLabel.setText(money.format(0d));
        totalAmountLabel.setText(money.format(0d));
    }

    public void addBookingDetail(BookingDetailDto detail) {
        DefaultTableModel model = (DefaultTableModel) servicesTable.getModel();
        Object[] rowData = new Object[DetailTableColumnNames.length];
        rowData[0] = detail.getIssuedAt().format(DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm));
        rowData[1] = detail.getType().name();
        rowData[2] = detail.getDescription() + (detail.getServiceId() == null ? "" : " (" + detail.getServiceName() + ")");
        rowData[3] = detail.getUnit();
        rowData[4] = money.format(detail.getUnitPrice());
        rowData[5] = quantity.format(detail.getQuantity());
        rowData[6] = money.format(detail.getDiscountAmount());
        rowData[7] = money.format(detail.getAmount());
        rowData[8] = null;
        rowData[9] = detail.getId();
        rowData[10] = detail.getServiceId();
        model.addRow(rowData);
        model.fireTableDataChanged();

        // Cập nhật lại tổng tiền
        Double roomCharges = 0d;
        Double serviceCharges = 0d;
        for (int i = 0; i < model.getRowCount(); i++) {
            BookingDetailType type = BookingDetailType.valueOf((String) model.getValueAt(i, 1));
            String amountStr = (String) model.getValueAt(i, 7);
            Double amount = 0d;
            try {
                amount = money.parse(amountStr).doubleValue();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (type == BookingDetailType.Room) {
                roomCharges += amount;
            } else {
                serviceCharges += amount;
            }
        }

        Double totalAmount = roomCharges + serviceCharges;
        roomChargesLabel.setText(money.format(roomCharges));
        serviceChargesLabel.setText(money.format(serviceCharges));
        totalAmountLabel.setText(money.format(totalAmount));

        isEditing = true;
    }

    /// Renderer: hiển thị nút icon
    class IconButtonRenderer extends JButton implements TableCellRenderer {
        public IconButtonRenderer() {
            setOpaque(true);
            // set icon trash can
            setText("X");
            setForeground(Color.RED);
            setToolTipText("Delete");
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    // Editor: xử lý khi click icon
    class IconButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private DefaultTableModel table;
        private boolean clicked;
        private int row;

        public IconButtonEditor(JCheckBox checkBox, DefaultTableModel table) {
            super(checkBox);
            this.table = table;

            button = new JButton();
            button.setOpaque(true);
            button.setText("X");
            button.setForeground(Color.RED);
            button.setToolTipText("Delete");

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped(); // bắt buộc để gọi getCellEditorValue()
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            this.row = row;
            clicked = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                int option = JOptionPane.showConfirmDialog(button,
                        "Delete row " + row + "?", "Confirm Delete",
                        JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    table.removeRow(row);
                    table.fireTableDataChanged();
                    // Cập nhật lại tổng tiền
                    Double roomCharges = 0d;
                    Double serviceCharges = 0d;
                    for (int i = 0; i < table.getRowCount(); i++) {
                        BookingDetailType type = BookingDetailType.valueOf((String) table.getValueAt(i, 1));
                        String amountStr = (String) table.getValueAt(i, 7);
                        Double amount = 0d;
                        try {
                            amount = money.parse(amountStr).doubleValue();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (type == BookingDetailType.Room) {
                            roomCharges += amount;
                        } else {
                            serviceCharges += amount;
                        }
                    }
                    Double totalAmount = roomCharges + serviceCharges;
                    roomChargesLabel.setText(money.format(roomCharges));
                    serviceChargesLabel.setText(money.format(serviceCharges));
                    totalAmountLabel.setText(money.format(totalAmount));

                    isEditing = true;
                }
            }
            clicked = false;
            return null;
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }
    }
}
