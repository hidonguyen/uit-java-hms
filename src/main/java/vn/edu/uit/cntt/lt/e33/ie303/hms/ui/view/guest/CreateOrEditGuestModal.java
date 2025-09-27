package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.guest;

import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.*;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.GuestGender;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Guest;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.Constants;

public class CreateOrEditGuestModal extends JDialog {
    private final JButton saveBtn = new JButton("Save");
    private final JButton cancelBtn = new JButton("Cancel");

    private final JTextField idField = new JTextField(20);
    private final JTextField nameField = new JTextField(20);
    private final JComboBox<GuestGender> genderCombo = new JComboBox<>(GuestGender.values());
    private final JTextField dobField = new JTextField(20); // dd/MM/yyyy
    private final JTextField nationalityField = new JTextField(20);
    private final JTextField phoneField = new JTextField(20);
    private final JTextField emailField = new JTextField(20);
    private final JTextField addressField = new JTextField(20);
    private final JTextField descriptionField = new JTextField(20);

    private final JTextField createdAtField = new JTextField(20);
    private final JTextField createdByField = new JTextField(20);
    private final JTextField updatedAtField = new JTextField(20);
    private final JTextField updatedByField = new JTextField(20);

    public CreateOrEditGuestModal(JFrame parent) {
        super(parent, "Create or Edit Guest", true);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        top.add(saveBtn);
        cancelBtn.addActionListener(_ -> dispose());
        top.add(cancelBtn);
        add(top, BorderLayout.NORTH);

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
        addPair(form, gc, row, 0, "ID:", idField);
        addPair(form, gc, row, 1, "Name:", nameField);
        row++;
        addPair(form, gc, row, 0, "Gender:", genderCombo);
        addPair(form, gc, row, 1, "Date of Birth (dd/MM/yyyy):", dobField);
        row++;
        addPair(form, gc, row, 0, "Nationality:", nationalityField);
        addPair(form, gc, row, 1, "Phone:", phoneField);
        row++;
        addPair(form, gc, row, 0, "Email:", emailField);
        addPair(form, gc, row, 1, "Address:", addressField);
        row++;
        addPair(form, gc, row, 0, "Description:", descriptionField);
        addPair(form, gc, row, 1, "Created At:", createdAtField);
        row++;
        addPair(form, gc, row, 0, "Created By:", createdByField);
        addPair(form, gc, row, 1, "Updated At:", updatedAtField);
        row++;
        addPair(form, gc, row, 0, "Updated By:", updatedByField);
        addPair(form, gc, row, 1, "", new JPanel());

        add(form, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setResizable(false);
        pack();
        setLocationRelativeTo(parent);
    }

    private void addPair(JPanel panel, GridBagConstraints gc, int row, int col, String label, JComponent field) {
        int base = col * 2;
        gc.gridy = row;

        gc.gridx = base;
        gc.weightx = 0;
        panel.add(new JLabel(label), gc);

        gc.gridx = base + 1;
        gc.weightx = 1;
        panel.add(field, gc);
    }

    public void onSave(java.awt.event.ActionListener l) {
        saveBtn.addActionListener(l);
    }

    public boolean isValidInput() {
        if (nameField.getText().trim().isEmpty()) {
            showValidationError("Guest name cannot be empty");
            return false;
        }
        String dob = dobField.getText().trim();
        if (!dob.isEmpty()) {
            try {
                DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyy).parse(dob);
            } catch (DateTimeParseException ex) {
                showValidationError("Invalid date format. Use dd/MM/yyyy");
                return false;
            }
        }
        return true;
    }

    public Guest getModel() {
        Guest g = new Guest();
        if (!idField.getText().trim().isEmpty())
            g.setId(Long.parseLong(idField.getText().trim()));
        g.setName(nameField.getText().trim());
        g.setGender((GuestGender) genderCombo.getSelectedItem());
        // Parse DOB (DATE)
        if (!dobField.getText().trim().isEmpty()) {
            LocalDate d = LocalDate.parse(dobField.getText().trim(),
                    DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyy));
            g.setDateOfBirth(d);
        }
        g.setNationality(nationalityField.getText().trim());
        g.setPhone(phoneField.getText().trim());
        g.setEmail(emailField.getText().trim());
        g.setAddress(addressField.getText().trim());
        g.setDescription(descriptionField.getText().trim());
        return g;
    }

    public void setModel(Guest g) {
        if (g == null) {
            idField.setText("");
            nameField.setText("");
            dobField.setText("");
            nationalityField.setText("");
            phoneField.setText("");
            emailField.setText("");
            addressField.setText("");
            descriptionField.setText("");
            genderCombo.setSelectedIndex(0);
            createdAtField.setText("");
            createdByField.setText("");
            updatedAtField.setText("");
            updatedByField.setText("");
        } else {
            idField.setText(g.getId() != null ? g.getId().toString() : "");
            nameField.setText(g.getName() != null ? g.getName() : "");
            genderCombo.setSelectedItem(g.getGender() != null ? g.getGender() : "Male");
            dobField.setText(g.getDateOfBirth() != null
                    ? g.getDateOfBirth().format(DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyy))
                    : "");
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

    public void showValidationError(String message) {
        JOptionPane.showMessageDialog(this, message, "Validation Error - " + Constants.ErrorTitle.GUEST,
                JOptionPane.ERROR_MESSAGE);
    }
}
