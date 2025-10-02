package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.user;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.UserRole;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.UserStatus;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.User;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.common.BaseModalView;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.Constants;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.CryptoHelper;

public class CreateOrEditUserModal extends BaseModalView {
    private final JTextField idField = new JTextField();
    private final JTextField usernameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JComboBox<UserRole> roleCombo = new JComboBox<>(UserRole.values());
    private final JComboBox<UserStatus> statusCombo = new JComboBox<>(UserStatus.values());
    private final JTextField lastLoginField = new JTextField();
    private final JTextField createdAtField = new JTextField();
    private final JTextField createdByField = new JTextField();
    private final JTextField updatedAtField = new JTextField();
    private final JTextField updatedByField = new JTextField();

    public CreateOrEditUserModal(JFrame parent) {
        super(parent, "Create or Edit User");
        setupFormFields();
        finalizeModal();
    }

    private void setupFormFields() {
        idField.setEnabled(false);
        lastLoginField.setEnabled(false);
        createdAtField.setEnabled(false);
        createdByField.setEnabled(false);
        updatedAtField.setEnabled(false);
        updatedByField.setEnabled(false);

        int row = 0;
        addFormField("ID:", idField, row, 0);
        addFormField("Username:", usernameField, row++, 1);

        addFormField("Password:", passwordField, row, 0);
        addFormField("Role:", roleCombo, row++, 1);

        addFormField("Status:", statusCombo, row, 0);
        addFormField("Last Login:", lastLoginField, row++, 1);

        addFormField("Created At:", createdAtField, row, 0);
        addFormField("Created By:", createdByField, row++, 1);

        addFormField("Updated At:", updatedAtField, row, 0);
        addFormField("Updated By:", updatedByField, row, 1);
    }

    @Override
    public boolean isValidInput() {
        String idText = idField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        if (username.isEmpty()) {
            showStyledError(Constants.ValidateMessage.USERNAME_CANNOT_BE_EMPTY,
                    "Validation Error - " + Constants.ErrorTitle.USER);
            return false;
        }
        if (idText.isEmpty() && password.isEmpty()) {
            showStyledError(Constants.ValidateMessage.PASSWORD_CANNOT_BE_EMPTY,
                    "Validation Error - " + Constants.ErrorTitle.USER);
            return false;
        }
        return true;
    }

    @Override
    public User getModel() {
        String idText = idField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        User user = new User();
        if (!idText.isEmpty())
            user.setId(Long.parseLong(idText));
        user.setUsername(username);
        if (!password.isEmpty())
            user.setPasswordHash(CryptoHelper.encrypt(password));
        user.setRole((UserRole) roleCombo.getSelectedItem());
        user.setStatus((UserStatus) statusCombo.getSelectedItem());

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm);
        if (!lastLoginField.getText().trim().isEmpty())
            user.setLastLoginAt(
                    LocalDateTime.parse(lastLoginField.getText().trim(), fmt));
        if (!createdAtField.getText().trim().isEmpty())
            user.setCreatedAt(
                    LocalDateTime.parse(createdAtField.getText().trim(), fmt));
        if (!createdByField.getText().trim().isEmpty())
            user.setCreatedBy(Long.parseLong(createdByField.getText().trim()));
        if (!updatedAtField.getText().trim().isEmpty())
            user.setUpdatedAt(
                    LocalDateTime.parse(updatedAtField.getText().trim(), fmt));
        if (!updatedByField.getText().trim().isEmpty())
            user.setUpdatedBy(Long.parseLong(updatedByField.getText().trim()));
        return user;
    }

    @Override
    public void setModel(Object model) {
        User u = (User) model;
        if (u == null) {
            idField.setText("");
            usernameField.setText("");
            passwordField.setText("");
            roleCombo.setSelectedIndex(0);
            statusCombo.setSelectedIndex(0);
            lastLoginField.setText("");
            createdAtField.setText("");
            createdByField.setText("");
            updatedAtField.setText("");
            updatedByField.setText("");
            passwordField.setEnabled(true);
            return;
        }

        idField.setText(u.getId() != null ? u.getId().toString() : "");
        usernameField.setText(u.getUsername() != null ? u.getUsername() : "");
        passwordField.setText("");
        passwordField.setEnabled(false);
        if (u.getRole() != null)
            roleCombo.setSelectedItem(u.getRole());
        else
            roleCombo.setSelectedIndex(0);
        if (u.getStatus() != null)
            statusCombo.setSelectedItem(u.getStatus());
        else
            statusCombo.setSelectedIndex(0);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm);
        lastLoginField.setText(u.getLastLoginAt() != null ? u.getLastLoginAt().format(fmt) : "");
        createdAtField.setText(u.getCreatedAt() != null ? u.getCreatedAt().format(fmt) : "");
        createdByField.setText(u.getCreatedBy() != null ? u.getCreatedBy().toString() : "");
        updatedAtField.setText(u.getUpdatedAt() != null ? u.getUpdatedAt().format(fmt) : "");
        updatedByField.setText(u.getUpdatedBy() != null ? u.getUpdatedBy().toString() : "");
    }
}
