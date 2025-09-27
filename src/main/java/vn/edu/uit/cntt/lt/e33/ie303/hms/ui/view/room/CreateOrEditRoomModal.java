package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.room;

import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.HousekeepingStatus;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.RoomStatus;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Room;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.Constants;

public class CreateOrEditRoomModal extends JDialog {
    private final JButton saveBtn = new JButton("Save");
    private final JButton cancelBtn = new JButton("Cancel");

    private final JTextField idField = new JTextField(20);
    private final JTextField nameField = new JTextField(20);
    private final JTextField roomTypeIdField = new JTextField(20);
    private final JTextField descriptionField = new JTextField(20);

    // combo cứng theo schema/common practice
    private final JComboBox<HousekeepingStatus> housekeepingCombo = new JComboBox<>(HousekeepingStatus.values());
    private final JComboBox<RoomStatus> statusCombo = new JComboBox<>(RoomStatus.values());

    private final JTextField createdAtField = new JTextField(20);
    private final JTextField createdByField = new JTextField(20);
    private final JTextField updatedAtField = new JTextField(20);
    private final JTextField updatedByField = new JTextField(20);

    public CreateOrEditRoomModal(JFrame parent) {
        super(parent, "Create or Edit Room", true);
        setLayout(new BorderLayout());

        // Top action bar
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        top.add(saveBtn);
        cancelBtn.addActionListener(_ -> dispose());
        top.add(cancelBtn);
        add(top, BorderLayout.NORTH);

        // Center form
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
        addPair(form, gc, row, 0, "Room Type Id:", roomTypeIdField);
        addPair(form, gc, row, 1, "Description:", descriptionField);
        row++;
        addPair(form, gc, row, 0, "Housekeeping:", housekeepingCombo);
        addPair(form, gc, row, 1, "Status:", statusCombo);
        row++;
        addPair(form, gc, row, 0, "Created At:", createdAtField);
        addPair(form, gc, row, 1, "Created By:", createdByField);
        row++;
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
        String name = nameField.getText().trim();
        String roomTypeId = roomTypeIdField.getText().trim();

        if (name.isEmpty()) {
            showValidationError("Room name cannot be empty");
            return false;
        }
        if (roomTypeId.isEmpty()) {
            showValidationError("Room type id cannot be empty");
            return false;
        }
        try {
            Long.parseLong(roomTypeId);
        } catch (NumberFormatException ex) {
            showValidationError("Room type id must be a number");
            return false;
        }
        return true;
    }

    public Room getModel() {
        Room r = new Room();
        if (!idField.getText().trim().isEmpty())
            r.setId(Long.parseLong(idField.getText().trim()));
        r.setName(nameField.getText().trim());
        r.setRoomTypeId(Long.parseLong(roomTypeIdField.getText().trim()));
        r.setDescription(descriptionField.getText().trim());
        r.setHousekeepingStatus((HousekeepingStatus) housekeepingCombo.getSelectedItem());
        r.setStatus((RoomStatus) statusCombo.getSelectedItem());

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm);
        if (!createdAtField.getText().trim().isEmpty()) {
            r.setCreatedAt(
                    OffsetDateTime.of(LocalDateTime.parse(createdAtField.getText().trim(), fmt), ZoneOffset.UTC));
        }
        if (!createdByField.getText().trim().isEmpty()) {
            r.setCreatedBy(Long.parseLong(createdByField.getText().trim()));
        }
        if (!updatedAtField.getText().trim().isEmpty()) {
            r.setUpdatedAt(
                    OffsetDateTime.of(LocalDateTime.parse(updatedAtField.getText().trim(), fmt), ZoneOffset.UTC));
        }
        if (!updatedByField.getText().trim().isEmpty()) {
            r.setUpdatedBy(Long.parseLong(updatedByField.getText().trim()));
        }
        return r;
    }

    public void setModel(Room r) {
        if (r == null) {
            idField.setText("");
            nameField.setText("");
            roomTypeIdField.setText("");
            descriptionField.setText("");
            housekeepingCombo.setSelectedIndex(0);
            statusCombo.setSelectedIndex(0);
        } else {
            idField.setText(r.getId() != null ? r.getId().toString() : "");
            nameField.setText(r.getName() != null ? r.getName() : "");
            roomTypeIdField.setText(r.getRoomTypeId() != null ? r.getRoomTypeId().toString() : "");
            descriptionField.setText(r.getDescription() != null ? r.getDescription() : "");
            housekeepingCombo.setSelectedItem(r.getHousekeepingStatus() != null ? r.getHousekeepingStatus() : "Clean");
            statusCombo.setSelectedItem(r.getStatus() != null ? r.getStatus() : "Available");

            DateTimeFormatter fmt = DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm);
            createdAtField.setText(r.getCreatedAt() != null ? r.getCreatedAt().format(fmt) : "");
            createdByField.setText(r.getCreatedBy() != null ? r.getCreatedBy().toString() : "");
            updatedAtField.setText(r.getUpdatedAt() != null ? r.getUpdatedAt().format(fmt) : "");
            updatedByField.setText(r.getUpdatedBy() != null ? r.getUpdatedBy().toString() : "");
        }
    }

    public void showValidationError(String message) {
        JOptionPane.showMessageDialog(this, message, "Validation Error - " + Constants.ErrorTitle.ROOM,
                JOptionPane.ERROR_MESSAGE);
    }
}
