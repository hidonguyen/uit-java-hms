package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.user;

import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

import javax.swing.*;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.UserRole;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.UserStatus;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.User;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.Constants;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.CryptoHelper;

public class CreateOrEditUserModal extends JDialog {
    private final JButton saveBtn = new JButton("Save");
    private final JButton cancelBtn = new JButton("Cancel");

    private final JTextField idField = new JTextField(20);
    private final JTextField usernameField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JComboBox<String> roleCombo = new JComboBox<>(new String[] { UserRole.Manager.name(), UserRole.Receptionist.name() });
    private final JComboBox<String> statusCombo = new JComboBox<>(new String[] { UserStatus.Active.name(), UserStatus.Locked.name() });
    private final JTextField lastLoginField = new JTextField(20);
    private final JTextField createdAtField = new JTextField(20);
    private final JTextField createdByField = new JTextField(20);
    private final JTextField updatedAtField = new JTextField(20);
    private final JTextField updatedByField = new JTextField(20);

    public CreateOrEditUserModal(JFrame parent) {
        super(parent, "Create or Edit User", true);
        setSize(600, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(saveBtn);
        cancelBtn.addActionListener(_ -> {
            dispose();
        });
        top.add(cancelBtn);
        add(top, BorderLayout.NORTH);


        JPanel panel = new JPanel(new GridLayout(10, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("ID:"));
        idField.setEnabled(false);
        panel.add(idField);

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        panel.add(new JLabel("Role:"));
        panel.add(roleCombo);

        panel.add(new JLabel("Status:"));
        panel.add(statusCombo);

        panel.add(new JLabel("Last Login:"));
        lastLoginField.setEnabled(false);
        panel.add(lastLoginField);

        panel.add(new JLabel("Created At:"));
        createdAtField.setEnabled(false);
        panel.add(createdAtField);

        panel.add(new JLabel("Created By:"));
        createdByField.setEnabled(false);
        panel.add(createdByField);

        panel.add(new JLabel("Updated At:"));
        updatedAtField.setEnabled(false);
        panel.add(updatedAtField);

        panel.add(new JLabel("Updated By:"));
        updatedByField.setEnabled(false);
        panel.add(updatedByField);

        add(panel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    public void onSave(ActionListener l) { saveBtn.addActionListener(l); }

    public boolean isValidInput() {
        String idText = idField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty()) {
            showValidationError(Constants.ValidateMessage.USERNAME_CANNOT_BE_EMPTY);
            return false;
        }
        if (idText.isEmpty() && password.isEmpty()) {
            showValidationError(Constants.ValidateMessage.PASSWORD_CANNOT_BE_EMPTY);
            return false;
        }

        return true;
    }

    public User getModel() {
        String idText = idField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String roleText = (String) roleCombo.getSelectedItem();
        String statusText = (String) statusCombo.getSelectedItem();
        String lastLoginText = lastLoginField.getText().trim();
        String createdAtText = createdAtField.getText().trim();
        String createdByText = createdByField.getText().trim();

        User user = new User();
        if (!idText.isEmpty()) {
            user.setId(Long.parseLong(idText));
        }
        user.setUsername(username);
        if (!password.isEmpty()) {
            String passwordHash = CryptoHelper.encrypt(password);
            user.setPasswordHash(passwordHash);
        }
        user.setRole(UserRole.valueOf(roleText));
        user.setStatus(UserStatus.valueOf(statusText));
        if (!lastLoginText.isEmpty()) {
            user.setLastLoginAt(OffsetDateTime.of(LocalDateTime.parse(lastLoginText, DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm)), ZoneOffset.UTC));
        }
        if (!createdAtText.isEmpty()) {
            user.setCreatedAt(OffsetDateTime.of(LocalDateTime.parse(createdAtText, DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm)), ZoneOffset.UTC));
        }
        if (!createdByText.isEmpty()) {
            user.setCreatedBy(Long.parseLong(createdByText));
        }
        return user;
    }

    public void setModel(User user) {
        if (user == null) {
            idField.setText("");
            usernameField.setText("");
            passwordField.setText("");
            roleCombo.setSelectedIndex(0);
            statusCombo.setSelectedIndex(0);
            idField.setEnabled(false);
            passwordField.setEnabled(true);
        } else {
            idField.setText(user.getId().toString());
            usernameField.setText(user.getUsername());
            passwordField.setText(user.getPasswordHash());
            roleCombo.setSelectedItem(user.getRole().name());
            statusCombo.setSelectedItem(user.getStatus().name());
            lastLoginField.setText(user.getLastLoginAt() != null ? user.getLastLoginAt().format(DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm)) : "");
            createdAtField.setText(user.getCreatedAt() != null ? user.getCreatedAt().format(DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm)) : "");
            createdByField.setText(user.getCreatedBy() != null ? user.getCreatedBy().toString() : "");
            updatedAtField.setText(user.getUpdatedAt() != null ? user.getUpdatedAt().format(DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm)) : "");
            updatedByField.setText(user.getUpdatedBy() != null ? user.getUpdatedBy().toString() : "");
            idField.setEnabled(false);
            passwordField.setEnabled(false);
            lastLoginField.setEnabled(false);
            createdAtField.setEnabled(false);
            createdByField.setEnabled(false);
            updatedAtField.setEnabled(false);
            updatedByField.setEnabled(false);
        }
    }

    public void showValidationError(String message) {
        JOptionPane.showMessageDialog(this, message, "Validation Error - " + Constants.ErrorTitle.USER, JOptionPane.ERROR_MESSAGE);
    }
}