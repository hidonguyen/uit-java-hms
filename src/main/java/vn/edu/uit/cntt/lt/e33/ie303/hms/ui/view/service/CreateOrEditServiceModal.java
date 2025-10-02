package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.ServiceStatus;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Service;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.common.BaseModalView;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.Constants;

public class CreateOrEditServiceModal extends BaseModalView {
    private final JTextField idField = new JTextField();
    private final JTextField nameField = new JTextField();
    private final JTextField unitField = new JTextField();
    private final JTextField priceField = new JTextField();
    private final JTextField descriptionField = new JTextField();
    private final JComboBox<ServiceStatus> statusCombo = new JComboBox<>(ServiceStatus.values());
    private final JTextField createdAtField = new JTextField();
    private final JTextField createdByField = new JTextField();
    private final JTextField updatedAtField = new JTextField();
    private final JTextField updatedByField = new JTextField();

    public CreateOrEditServiceModal(JFrame parent) {
        super(parent, "Create or Edit Service");
        setupFormFields();
        finalizeModal();
    }

    private void setupFormFields() {
        idField.setEnabled(false);
        createdAtField.setEnabled(false);
        createdByField.setEnabled(false);
        updatedAtField.setEnabled(false);
        updatedByField.setEnabled(false);

        int row = 0;
        addFormField("ID:", idField, row, 0);
        addFormField("Name:", nameField, row++, 1);

        addFormField("Unit:", unitField, row, 0);
        addFormField("Description:", descriptionField, row++, 1);

        addFormField("Price:", priceField, row, 0);
        addFormField("Status:", statusCombo, row++, 1);

        addFormField("Created At:", createdAtField, row, 0);
        addFormField("Created By:", createdByField, row++, 1);

        addFormField("Updated At:", updatedAtField, row, 0);
        addFormField("Updated By:", updatedByField, row, 1);
    }

    @Override
    public boolean isValidInput() {
        String name = nameField.getText().trim();
        String unit = unitField.getText().trim();
        String price = priceField.getText().trim();

        if (name.isEmpty()) {
            showStyledError(Constants.ValidateMessage.SERVICE_NAME_CANNOT_BE_EMPTY,
                    "Validation Error - " + Constants.ErrorTitle.SERVICE);
            return false;
        }
        if (unit.isEmpty()) {
            showStyledError(Constants.ValidateMessage.SERVICE_UNIT_CANNOT_BE_EMPTY,
                    "Validation Error - " + Constants.ErrorTitle.SERVICE);
            return false;
        }
        if (price.isEmpty()) {
            showStyledError(Constants.ValidateMessage.SERVICE_PRICE_MUST_BE_POSITIVE,
                    "Validation Error - " + Constants.ErrorTitle.SERVICE);
            return false;
        }
        try {
            double p = Double.parseDouble(price);
            if (p <= 0) {
                showStyledError(Constants.ValidateMessage.SERVICE_PRICE_MUST_BE_POSITIVE,
                        "Validation Error - " + Constants.ErrorTitle.SERVICE);
                return false;
            }
        } catch (NumberFormatException ex) {
            showStyledError(Constants.ValidateMessage.SERVICE_PRICE_MUST_BE_POSITIVE,
                    "Validation Error - " + Constants.ErrorTitle.SERVICE);
            return false;
        }
        return true;
    }

    @Override
    public Service getModel() {
        Service s = new Service();
        if (!idField.getText().trim().isEmpty())
            s.setId(Long.parseLong(idField.getText().trim()));
        s.setName(nameField.getText().trim());
        s.setUnit(unitField.getText().trim());
        s.setPrice(Double.parseDouble(priceField.getText().trim()));
        s.setDescription(descriptionField.getText().trim());
        s.setStatus((ServiceStatus) statusCombo.getSelectedItem());

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm);
        if (!createdAtField.getText().trim().isEmpty()) {
            s.setCreatedAt(
                    LocalDateTime.parse(createdAtField.getText().trim(), fmt));
        }
        if (!createdByField.getText().trim().isEmpty()) {
            s.setCreatedBy(Long.parseLong(createdByField.getText().trim()));
        }
        if (!updatedAtField.getText().trim().isEmpty()) {
            s.setUpdatedAt(
                    LocalDateTime.parse(updatedAtField.getText().trim(), fmt));
        }
        if (!updatedByField.getText().trim().isEmpty()) {
            s.setUpdatedBy(Long.parseLong(updatedByField.getText().trim()));
        }
        return s;
    }

    @Override
    public void setModel(Object model) {
        Service s = (Service) model;
        if (s == null) {
            idField.setText("");
            nameField.setText("");
            unitField.setText("");
            priceField.setText("");
            descriptionField.setText("");
            statusCombo.setSelectedIndex(0);
            createdAtField.setText("");
            createdByField.setText("");
            updatedAtField.setText("");
            updatedByField.setText("");
            return;
        }

        idField.setText(s.getId() != null ? s.getId().toString() : "");
        nameField.setText(s.getName() != null ? s.getName() : "");
        unitField.setText(s.getUnit() != null ? s.getUnit() : "");
        priceField.setText(String.valueOf(s.getPrice()));
        descriptionField.setText(s.getDescription() != null ? s.getDescription() : "");
        if (s.getStatus() != null)
            statusCombo.setSelectedItem(s.getStatus());
        else
            statusCombo.setSelectedIndex(0);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm);
        createdAtField.setText(s.getCreatedAt() != null ? s.getCreatedAt().format(fmt) : "");
        createdByField.setText(s.getCreatedBy() != null ? s.getCreatedBy().toString() : "");
        updatedAtField.setText(s.getUpdatedAt() != null ? s.getUpdatedAt().format(fmt) : "");
        updatedByField.setText(s.getUpdatedBy() != null ? s.getUpdatedBy().toString() : "");
    }
}
