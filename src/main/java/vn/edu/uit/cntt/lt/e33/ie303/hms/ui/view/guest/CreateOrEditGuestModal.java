package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.guest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.GuestGender;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Guest;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.common.BaseModalView;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.Constants;

public class CreateOrEditGuestModal extends BaseModalView {
    private final JTextField idField = new JTextField();
    private final JTextField nameField = new JTextField();
    private final JComboBox<GuestGender> genderCombo = new JComboBox<>(GuestGender.values());

    private final DatePicker dobPicker;

    private final JTextField nationalityField = new JTextField();
    private final JTextField phoneField = new JTextField();
    private final JTextField emailField = new JTextField();
    private final JTextField addressField = new JTextField();
    private final JTextField descriptionField = new JTextField();

    private final JTextField createdAtField = new JTextField();
    private final JTextField createdByField = new JTextField();
    private final JTextField updatedAtField = new JTextField();
    private final JTextField updatedByField = new JTextField();

    public CreateOrEditGuestModal(JFrame parent) {
        super(parent, "Create or Edit Guest");

        DatePickerSettings dpSettings = new DatePickerSettings();
        dpSettings.setAllowEmptyDates(true);
        dpSettings.setFormatForDatesCommonEra(Constants.DateTimeFormat.ddMMyyyy);
        dobPicker = new DatePicker(dpSettings);

        setupFormFields();
        finalizeModal();
    }

    private void setupFormFields() {
        idField.setEnabled(false);
        createdAtField.setEnabled(false);
        createdByField.setEnabled(false);
        updatedAtField.setEnabled(false);
        updatedByField.setEnabled(false);

        styleDatePicker(dobPicker);

        int row = 0;
        addFormField("ID:", idField, row, 0);
        addFormField("Name:", nameField, row++, 1);

        addFormField("Gender:", genderCombo, row, 0);
        addFormField("Date of Birth (dd/MM/yyyy):", dobPicker, row++, 1);

        addFormField("Nationality:", nationalityField, row, 0);
        addFormField("Phone:", phoneField, row++, 1);

        addFormField("Email:", emailField, row, 0);
        addFormField("Address:", addressField, row++, 1);

        addFormField("Description:", descriptionField, row, 0);
        addFormField("Created At:", createdAtField, row++, 1);

        addFormField("Created By:", createdByField, row, 0);
        addFormField("Updated At:", updatedAtField, row++, 1);

        addFormField("Updated By:", updatedByField, row, 0);
        addFormField("", new JPanel(), row, 1);
    }

    private void styleDatePicker(DatePicker datePicker) {
        datePicker.getComponentDateTextField().setBorder(createFieldBorder());
        datePicker.getComponentDateTextField().setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        datePicker.getComponentDateTextField().setBackground(java.awt.Color.WHITE);
        datePicker.getComponentDateTextField().setForeground(TEXT_COLOR);
        datePicker.setPreferredSize(new java.awt.Dimension(200, 36));

        datePicker.getComponentToggleCalendarButton().setBackground(PRIMARY_COLOR);
        datePicker.getComponentToggleCalendarButton().setForeground(java.awt.Color.WHITE);
        datePicker.getComponentToggleCalendarButton().setBorder(javax.swing.BorderFactory.createEmptyBorder());
        datePicker.getComponentToggleCalendarButton().setFocusPainted(false);
        datePicker.getComponentToggleCalendarButton().setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    private javax.swing.border.Border createFieldBorder() {
        return javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                javax.swing.BorderFactory.createEmptyBorder(8, 12, 8, 12));
    }

    @Override
    public boolean isValidInput() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        if (name.isEmpty()) {
            showStyledError(
                    Constants.ValidateMessage.GUEST_NAME_CANNOT_BE_EMPTY,
                    "Validation Error - " + Constants.ErrorTitle.GUEST);
            return false;
        }

        if (!email.isEmpty() && !isValidEmail(email)) {
            showStyledError(
                    Constants.ValidateMessage.GUEST_EMAIL_INVALID,
                    "Validation Error - " + Constants.ErrorTitle.GUEST);
            return false;
        }

        if (!phone.isEmpty() && !isValidPhone(phone)) {
            showStyledError(
                    Constants.ValidateMessage.GUEST_PHONE_INVALID,
                    "Validation Error - " + Constants.ErrorTitle.GUEST);
            return false;
        }

        LocalDate dob = dobPicker.getDate();
        if (dob != null && dob.isAfter(LocalDate.now())) {
            showStyledError(
                    "Date of birth cannot be in the future.",
                    "Validation Error - " + Constants.ErrorTitle.GUEST);
            return false;
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private boolean isValidPhone(String phone) {
        String phoneRegex = "^[\\d\\s\\-\\(\\)\\+]+$";
        return phone.matches(phoneRegex) && phone.replaceAll("[^\\d]", "").length() >= 10;
    }

    @Override
    public Guest getModel() {
        Guest g = new Guest();

        if (!idField.getText().trim().isEmpty()) {
            g.setId(Long.parseLong(idField.getText().trim()));
        }

        g.setName(nameField.getText().trim());
        g.setGender((GuestGender) genderCombo.getSelectedItem());

        LocalDate dob = dobPicker.getDate();
        g.setDateOfBirth(dob);

        g.setNationality(nationalityField.getText().trim());
        g.setPhone(phoneField.getText().trim());
        g.setEmail(emailField.getText().trim());
        g.setAddress(addressField.getText().trim());
        g.setDescription(descriptionField.getText().trim());

        return g;
    }

    @Override
    public void setModel(Object model) {
        Guest g = (Guest) model;

        if (g == null) {
            clearFields();
        } else {
            idField.setText(g.getId() != null ? g.getId().toString() : "");
            nameField.setText(g.getName() != null ? g.getName() : "");
            genderCombo.setSelectedItem(g.getGender() != null ? g.getGender() : GuestGender.Male);

            if (g.getDateOfBirth() != null) {
                dobPicker.setDate(g.getDateOfBirth());
            } else {
                dobPicker.clear();
            }

            nationalityField.setText(g.getNationality() != null ? g.getNationality() : "");
            phoneField.setText(g.getPhone() != null ? g.getPhone() : "");
            emailField.setText(g.getEmail() != null ? g.getEmail() : "");
            addressField.setText(g.getAddress() != null ? g.getAddress() : "");
            descriptionField.setText(g.getDescription() != null ? g.getDescription() : "");

            DateTimeFormatter fmt = DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm);
            createdAtField.setText(g.getCreatedAt() != null ? g.getCreatedAt().format(fmt) : "");
            createdByField.setText(g.getCreatedBy() != null ? g.getCreatedBy().toString() : "");
            updatedAtField.setText(g.getUpdatedAt() != null ? g.getUpdatedAt().format(fmt) : "");
            updatedByField.setText(g.getUpdatedBy() != null ? g.getUpdatedBy().toString() : "");
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        genderCombo.setSelectedIndex(0);
        dobPicker.clear();
        nationalityField.setText("");
        phoneField.setText("");
        emailField.setText("");
        addressField.setText("");
        descriptionField.setText("");
        createdAtField.setText("");
        createdByField.setText("");
        updatedAtField.setText("");
        updatedByField.setText("");
    }
}