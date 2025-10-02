package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.booking;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Locale;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.BookingDetailDto;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.ServiceItem;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.BookingDetailType;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.common.BaseModalView;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.presenter.BookingPresenter;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.Constants;

public class AddServiceToBookingModal extends BaseModalView {
    private BookingPresenter presenter;

    private final NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    private final NumberFormat quantityFormat = NumberFormat.getNumberInstance(new Locale("vi", "VN"));

    private DateTimePicker issuedAtPicker;
    private final JComboBox<BookingDetailType> detailTypeCombo = new JComboBox<>(BookingDetailType.values());
    DefaultComboBoxModel<ServiceItem> services = new DefaultComboBoxModel<>();
    private final JComboBox<ServiceItem> serviceCombo = new JComboBox<ServiceItem>(services);
    private final JTextField descriptionField = new JTextField();
    private final JTextField unitField = new JTextField();
    private final JTextField unitPriceField = new JTextField();
    private final JTextField quantityField = new JTextField();
    private final JTextField discountAmountField = new JTextField();
    private final JTextField amountField = new JTextField();

    public AddServiceToBookingModal(JFrame parent, BookingPresenter presenter) {
        super(parent, "Add Service to Booking");

        this.presenter = presenter;
        setAlwaysOnTop(true);
        setupFormFields();
        finalizeModal();
    }

    private void setupFormFields() {
        setModalityType(ModalityType.APPLICATION_MODAL);

        amountField.setEditable(false);

        int row = 0;

        DatePickerSettings issuedAtDatePickerSettings = new DatePickerSettings();
        issuedAtDatePickerSettings.setFormatForDatesCommonEra(Constants.DateTimeFormat.ddMMyyyy);
        issuedAtDatePickerSettings.setFormatForDatesBeforeCommonEra(Constants.DateTimeFormat.ddMMyyyy);
        TimePickerSettings issuedAtTimePickerSettings = new TimePickerSettings();
        issuedAtTimePickerSettings.use24HourClockFormat();
        issuedAtTimePickerSettings.setFormatForDisplayTime(Constants.DateTimeFormat.HHmm);
        issuedAtTimePickerSettings.setFormatForMenuTimes(Constants.DateTimeFormat.HHmm);
        issuedAtPicker = new DateTimePicker(issuedAtDatePickerSettings, issuedAtTimePickerSettings);
        addFormField("Time:", issuedAtPicker, row++, 0, 2);
        
        addFormField("Type:", detailTypeCombo, row++, 0, 2);
        addFormField("Description:", descriptionField, row++, 0, 2);

        services.removeAllElements();
        presenter.getServiceItems().forEach(s -> services.addElement(s));
        serviceCombo.addActionListener(_ -> {
            ServiceItem selectedService = (ServiceItem) serviceCombo.getSelectedItem();
            if (selectedService != null) {
                unitField.setText(selectedService.getUnit());
                unitPriceField.setText(moneyFormat.format(selectedService.getUnitPrice()));
            } else {
                unitField.setText("");
                unitPriceField.setText(moneyFormat.format(0));
            }
        });
        addFormField("Service:", serviceCombo, row++, 0, 2);

        addFormField("Unit:", unitField, row++, 0, 2);

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
            private void updateAmount() {
                Double amount = calculateAmount();
                amountField.setText(moneyFormat.format(amount));
            }
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateAmount();
            }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateAmount();
            }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateAmount();
            }
        });
        addFormField("Price:", unitPriceField, row++, 0, 2);

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
        quantityField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void updateAmount() {
                Double amount = calculateAmount();
                amountField.setText(moneyFormat.format(amount));
            }
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateAmount();
            }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateAmount();
            }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateAmount();
            }
        });
        addFormField("Quantity:", quantityField, row++, 0, 2);

        discountAmountField.setHorizontalAlignment(JTextField.RIGHT);
        discountAmountField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });
        discountAmountField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void updateAmount() {
                Double amount = calculateAmount();
                amountField.setText(moneyFormat.format(amount));
            }
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateAmount();
            }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateAmount();
            }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateAmount();
            }
        });
        addFormField("Discount:", discountAmountField, row++, 0, 2);

        amountField.setHorizontalAlignment(JTextField.RIGHT);
        addFormField("Amount:", amountField, row++, 0, 2);
    }

    @Override
    public boolean isValidInput() {
        if (BookingDetailType.valueOf(detailTypeCombo.getSelectedItem().toString()) == BookingDetailType.Service
                && ((ServiceItem) serviceCombo.getSelectedItem()).getId() == -1L) {
            JOptionPane.showMessageDialog(this, "Please select a valid service.", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (BookingDetailType.valueOf(detailTypeCombo.getSelectedItem().toString()) != BookingDetailType.Service
                && descriptionField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a description.", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (quantityField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a quantity.", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (unitPriceField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a unit price.", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private Double calculateAmount() {
        String quantityStr = quantityField.getText().trim().replaceAll("[^\\d]", "");
        String unitPriceStr = unitPriceField.getText().trim().replaceAll("[^\\d]", "");
        String discountAmountStr = discountAmountField.getText().trim().replaceAll("[^\\d]", "");

        int quantity = !quantityStr.isEmpty()
                ? Integer.parseInt(quantityStr)
                : 0;
        double unitPrice = !unitPriceStr.isEmpty()
                ? Double.parseDouble(unitPriceStr)
                : 0.0;
        double discountAmount = !discountAmountStr.isEmpty()
                ? Double.parseDouble(discountAmountStr)
                : 0.0;

        return (quantity * unitPrice) - discountAmount;
    }

    @Override
    public BookingDetailDto getModel() {
        BookingDetailDto bookingDetail = new BookingDetailDto();
        
        bookingDetail.setIssuedAt(issuedAtPicker.getDateTimePermissive());
        bookingDetail.setType((BookingDetailType) detailTypeCombo.getSelectedItem());
        bookingDetail.setDescription(!descriptionField.getText().trim().isEmpty() ? descriptionField.getText().trim() : null);
        ServiceItem selectedService = (ServiceItem) serviceCombo.getSelectedItem();
        bookingDetail.setServiceId(selectedService != null && selectedService.getId() != -1L ? selectedService.getId() : null);
        bookingDetail.setServiceName(selectedService != null && selectedService.getId() != -1L ? selectedService.toString() : null);
        bookingDetail.setUnit(!unitField.getText().trim().isEmpty() ? unitField.getText().trim() : null);
        bookingDetail.setQuantity(!quantityField.getText().trim().isEmpty()
                ? Integer.parseInt(quantityField.getText().trim())
                : 0);
        bookingDetail.setUnitPrice(!unitPriceField.getText().trim().isEmpty()
                ? Double.parseDouble(unitPriceField.getText().trim().replaceAll("[^\\d]", ""))
                : 0.0);
        bookingDetail.setDiscountAmount(!discountAmountField.getText().trim().isEmpty()
                ? Double.parseDouble(discountAmountField.getText().trim().replaceAll("[^\\d]", ""))
                : 0.0);
        bookingDetail.setAmount(!amountField.getText().trim().isEmpty()
                ? Double.parseDouble(amountField.getText().trim().replaceAll("[^\\d]", ""))
                : 0.0);
        
        return bookingDetail;
    }

    @Override
    public void setModel(Object model) {
        this.setSize(400, 550);

        clearForm();
    }

    @Override
    public void clearForm() {
        super.clearForm();
        
        issuedAtPicker.setDateTimePermissive(LocalDateTime.now());
        detailTypeCombo.setSelectedIndex(0);
        descriptionField.setText("");
        serviceCombo.setSelectedIndex(0);
        unitField.setText("");
        unitPriceField.setText(moneyFormat.format(0));
        quantityField.setText(quantityFormat.format(1));
        discountAmountField.setText(moneyFormat.format(0));
        amountField.setText(moneyFormat.format(0));
    }
}
