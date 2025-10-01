package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.booking;

import java.awt.Desktop.Action;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;

import javax.swing.JPasswordField;

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
        addFormField("Updated By:", updatedByField, row, 1);
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
        return booking;
    }

    @Override
    public void setModel(Object model) {
        BookingDto booking = (BookingDto) model;

        if (booking == null) {
            this.setSize(900, 700);

            clearForm();

            return;
        }

        this.setSize(900, 1000);

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
    }
}
