package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.booking;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop.Action;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;

import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.BookingDetailDto;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.BookingDto;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.GuestItem;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.RoomItem;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.RoomTypeItem;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.BookingChargeType;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.BookingStatus;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.PaymentStatus;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.UserRole;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.UserStatus;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Guest;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Room;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.RoomType;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.User;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.common.BaseModalView;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.presenter.BookingPresenter;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.Constants;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.CryptoHelper;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.UIUtils;

public class CreateOrEditBookingModal extends BaseModalView {
    private BookingPresenter presenter;

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


    private final String[] DetailTableColumnNames = { "Time", "Type", "Description", "Qty", "Unit Price", "Total", "Action" };
    private DefaultTableModel bookingDetailTableModel;
    private JPanel bookingDetailTablePanel;
    private JPanel summaryPanel;

    public CreateOrEditBookingModal(JFrame parent, BookingPresenter presenter) {
        super(parent, "Create or Edit Booking");

        this.presenter = presenter;
        setupFormFields();
        finalizeModal();
    }

    private void setupFormFields() {
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
        bookingDetailTableModel = new DefaultTableModel(new Object[0][DetailTableColumnNames.length], DetailTableColumnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only action column is editable
            }
        };

        JTable servicesTable = new JTable(bookingDetailTableModel);
        servicesTable.setRowHeight(36);
        servicesTable.getTableHeader().setBackground(new Color(248, 249, 250));
        servicesTable.getTableHeader().setReorderingAllowed(false);
        servicesTable.setShowGrid(true);

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

        // booking.setBookingDetails(new ArrayList<BookingDetailDto>());
        
        return booking;
    }

    @Override
    public void setModel(Object model) {
        BookingDto booking = (BookingDto) model;

        if (booking == null) {
            this.setSize(900, 550);

            clearForm();

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
        RoomTypeItem selectedRoomType = presenter.getRoomTypeItemById(booking.getRoomTypeId());
        roomTypeCombo.setSelectedItem(selectedRoomType != null ? selectedRoomType : roomTypes.getElementAt(0));
        RoomItem selectedRoom = presenter.getRoomItemById(booking.getRoomId());
        roomCombo.setSelectedItem(selectedRoom != null ? selectedRoom : rooms.getElementAt(0));
        GuestItem selectedGuest = presenter.getGuestItemById(booking.getPrimaryGuestId());
        guestCombo.setSelectedItem(selectedGuest != null ? selectedGuest : guests.getElementAt(0));
        guestPhoneField.setText(selectedGuest != null ? selectedGuest.getPhone() : "");
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

        Object[][] serviceData = new Object[booking.getBookingDetails().size()][7];
        for (int i = 0; i < booking.getBookingDetails().size(); i++) {
            BookingDetailDto detail = booking.getBookingDetails().get(i);
            serviceData[i][0] = detail.getIssuedAt()
                    .format(DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm));
            serviceData[i][1] = detail.getType().name();
            serviceData[i][2] = detail.getDescription() + " (" + detail.getServiceName() + ")";
            serviceData[i][3] = detail.getQuantity();
            serviceData[i][4] = String.format("%,.0f", detail.getUnitPrice());
            serviceData[i][5] = String.format("%,.0f", detail.getAmount());
            serviceData[i][6] = "Remove";
        }
        bookingDetailTableModel.setDataVector(serviceData, DetailTableColumnNames);

        Double roomCharges = booking.getTotalRoomCharges();
        Double serviceCharges = booking.getTotalServiceCharges();
        Double totalAmount = roomCharges + serviceCharges;
        roomChargesLabel.setText(String.format("%,.0f", roomCharges));
        serviceChargesLabel.setText(String.format("%,.0f", serviceCharges));
        totalAmountLabel.setText(String.format("%,.0f", totalAmount));

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

        bookingDetailTableModel.setRowCount(0);

        roomChargesLabel.setText(String.format("%,.0f", 0d));
        serviceChargesLabel.setText(String.format("%,.0f", 0d));
        totalAmountLabel.setText(String.format("%,.0f", 0d));
    }
}
