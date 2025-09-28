package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.service;

import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.ServiceStatus;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Service;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.Constants;

public class CreateOrEditServiceModal extends JDialog {
    private final JButton saveBtn = new JButton("Save");
    private final JButton cancelBtn = new JButton("Cancel");

    private final JTextField idField = new JTextField(20);
    private final JTextField nameField = new JTextField(20);
    private final JTextField unitField = new JTextField(20);
    private final JTextField priceField = new JTextField(20);
    private final JTextField descriptionField = new JTextField(20);
    private final JComboBox<String> statusCombo = new JComboBox<>(
            new String[] { ServiceStatus.Active.name(), ServiceStatus.Inactive.name() });
    private final JTextField createdAtField = new JTextField(20);
    private final JTextField createdByField = new JTextField(20);
    private final JTextField updatedAtField = new JTextField(20);
    private final JTextField updatedByField = new JTextField(20);

    public CreateOrEditServiceModal(JFrame parent) {
        super(parent, "Create or Edit Service", true);
        setLayout(new BorderLayout());

        // Top action bar
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        top.add(saveBtn);
        cancelBtn.addActionListener(_ -> dispose());
        top.add(cancelBtn);
        add(top, BorderLayout.NORTH);

        // Center form with GridBagLayout (2 columns of pairs)
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 6, 6, 6);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;

        idField.setEnabled(false);
        createdAtField.setEnabled(false);
        createdByField.setEnabled(false);
        updatedAtField.setEnabled(false);
        updatedByField.setEnabled(false);

        int row = 0;
        // Row 0: ID | Name
        addPair(form, gc, row, 0, "ID:", idField);
        addPair(form, gc, row, 1, "Name:", nameField);
        row++;

        // Row 1: Unit | Description
        addPair(form, gc, row, 0, "Unit:", unitField);
        addPair(form, gc, row, 1, "Description:", descriptionField);
        row++;

        // Row 2: Price | Status
        addPair(form, gc, row, 0, "Price:", priceField);
        addPair(form, gc, row, 1, "Status:", statusCombo);
        row++;

        // Row 3: Created At | Created By
        addPair(form, gc, row, 0, "Created At:", createdAtField);
        addPair(form, gc, row, 1, "Created By:", createdByField);
        row++;

        // Row 4: Updated At | Updated By
        addPair(form, gc, row, 0, "Updated At:", updatedAtField);
        addPair(form, gc, row, 1, "Updated By:", updatedByField);

        add(form, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setResizable(false);
        pack(); // <-- để layout tự fit kích thước đẹp
        setLocationRelativeTo(parent);
    }

    private void addPair(JPanel panel, GridBagConstraints gc, int row, int col, String label, JComponent field) {
        // colPair: 0 = left pair, 1 = right pair
        int colBase = col * 2;
        gc.gridy = row;

        // Label
        gc.gridx = colBase;
        gc.weightx = 0;
        panel.add(new JLabel(label), gc);

        // Field
        gc.gridx = colBase + 1;
        gc.weightx = 1;
        panel.add(field, gc);
    }

    public void onSave(java.awt.event.ActionListener l) {
        saveBtn.addActionListener(l);
    }

    public boolean isValidInput() {
        String name = nameField.getText().trim();
        String unit = unitField.getText().trim();
        String price = priceField.getText().trim();

        if (name.isEmpty()) {
            showValidationError("Service name cannot be empty");
            return false;
        }
        if (unit.isEmpty()) {
            showValidationError("Unit cannot be empty");
            return false;
        }
        if (price.isEmpty()) {
            showValidationError("Price cannot be empty");
            return false;
        }
        try {
            Double.parseDouble(price);
        } catch (NumberFormatException ex) {
            showValidationError("Price must be a number");
            return false;
        }
        return true;
    }

    public Service getModel() {
        Service s = new Service();
        if (!idField.getText().trim().isEmpty())
            s.setId(Long.parseLong(idField.getText().trim()));
        s.setName(nameField.getText().trim());
        s.setUnit(unitField.getText().trim());
        s.setPrice(Double.parseDouble(priceField.getText().trim()));
        s.setDescription(descriptionField.getText().trim());
        s.setStatus(ServiceStatus.valueOf((String) statusCombo.getSelectedItem()));

        if (!createdAtField.getText().trim().isEmpty()) {
            s.setCreatedAt(OffsetDateTime.of(
                    LocalDateTime.parse(createdAtField.getText().trim(),
                            DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm)),
                    ZoneOffset.UTC));
        }
        if (!createdByField.getText().trim().isEmpty()) {
            s.setCreatedBy(Long.parseLong(createdByField.getText().trim()));
        }
        if (!updatedAtField.getText().trim().isEmpty()) {
            s.setUpdatedAt(OffsetDateTime.of(
                    LocalDateTime.parse(updatedAtField.getText().trim(),
                            DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm)),
                    ZoneOffset.UTC));
        }
        if (!updatedByField.getText().trim().isEmpty()) {
            s.setUpdatedBy(Long.parseLong(updatedByField.getText().trim()));
        }
        return s;
    }

    public void setModel(Service s) {
        if (s == null) {
            idField.setText("");
            nameField.setText("");
            unitField.setText("");
            priceField.setText("");
            descriptionField.setText("");
            statusCombo.setSelectedIndex(0);
        } else {
            idField.setText(s.getId() != null ? s.getId().toString() : "");
            nameField.setText(s.getName() != null ? s.getName() : "");
            unitField.setText(s.getUnit() != null ? s.getUnit() : "");
            priceField.setText(String.valueOf(s.getPrice()));
            descriptionField.setText(s.getDescription() != null ? s.getDescription() : "");
            statusCombo.setSelectedItem(s.getStatus().name());
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm);
            createdAtField.setText(s.getCreatedAt() != null ? s.getCreatedAt().format(fmt) : "");
            createdByField.setText(s.getCreatedBy() != null ? s.getCreatedBy().toString() : "");
            updatedAtField.setText(s.getUpdatedAt() != null ? s.getUpdatedAt().format(fmt) : "");
            updatedByField.setText(s.getUpdatedBy() != null ? s.getUpdatedBy().toString() : "");
        }
    }

    public void showValidationError(String message) {
        JOptionPane.showMessageDialog(this, message,
                "Validation Error - " + Constants.ErrorTitle.SERVICE, JOptionPane.ERROR_MESSAGE);
    }
}
