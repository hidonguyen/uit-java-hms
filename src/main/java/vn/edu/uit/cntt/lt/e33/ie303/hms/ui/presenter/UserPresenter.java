package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.presenter;

import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import vn.edu.uit.cntt.lt.e33.ie303.hms.bootstrap.DIContainer;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.User;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service.IUserService;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.user.CreateOrEditUserModal;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.user.UserView;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.Constants;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.CryptoHelper;

public class UserPresenter {
    private final UserView view;
    private final CreateOrEditUserModal modal;
    private final IUserService service;

    private List<User> users = new ArrayList<>();

    public UserPresenter(JFrame parentFrame) {
        this.view = new UserView();
        this.modal = new CreateOrEditUserModal(parentFrame);
        this.service = DIContainer.getInstance().getUserService();

        this.view.onAdd(_ -> {
            modal.setModel(null);
            modal.setVisible(true);
        });
        this.view.onEdit(_ -> {
            int selectedRow = view.getSelectedRow();
            if (selectedRow >= 0 && selectedRow < users.size()) {
                User user = users.get(selectedRow);
                modal.setModel(user);
                modal.setVisible(true);
            }
        });
        this.view.onDelete(_ -> {
            int selectedRow = view.getSelectedRow();
            if (selectedRow >= 0 && selectedRow < users.size()) {
                User user = users.get(selectedRow);
                int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete user: " + user.getUsername() + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    new SwingWorker<Integer, Void>() {
                        @Override protected Integer doInBackground() {
                            return service.delete(user.getId());
                        }
                        @Override protected void done() {
                            try {
                                if (get() > 0) {
                                    view.showSuccessMessage(Constants.SuccessMessage.DELETE_USER_SUCCESS);
                                }
                            } catch (Exception ex) {
                                view.showErrorMessage(ex.getMessage());
                            }
                            loadData();
                        }
                    }.execute();
                }
            }
        });
        this.view.onResetPassword(_ -> {
            int selectedRow = view.getSelectedRow();
            if (selectedRow >= 0 && selectedRow < users.size()) {
                User user = users.get(selectedRow);
                JPasswordField passwordField = new JPasswordField(20);
                int option = JOptionPane.showConfirmDialog(view, passwordField, "Enter new password for: " + user.getUsername(), JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String newPassword = new String(passwordField.getPassword());
                    if (newPassword != null && !newPassword.trim().isEmpty()) {
                        user.setPasswordHash(CryptoHelper.encrypt(newPassword.trim()));
                        new SwingWorker<Integer, Void>() {
                            @Override protected Integer doInBackground() {
                                return service.update(user);
                            }
                            @Override protected void done() {
                                try {
                                    if (get() > 0) {
                                        view.showSuccessMessage(Constants.SuccessMessage.UPDATE_USER_SUCCESS);
                                    }
                                } catch (Exception ex) {
                                    view.showErrorMessage(ex.getMessage());
                                }
                                loadData();
                            }
                        }.execute();
                    } else {
                        view.showErrorMessage(Constants.ValidateMessage.PASSWORD_CANNOT_BE_EMPTY);
                    }
                }
            }
        });
        this.view.onSearch(_ -> {
            String query = view.getSearchQuery();
            if (query == null || query.isEmpty()) {
                view.setTableModel(new UserTableModel(users));
            } else {
                List<User> filtered = users.stream()
                    .filter(u -> u.getUsername().toLowerCase().contains(query.toLowerCase()))
                    .toList();
                view.setTableModel(new UserTableModel(filtered));
            }
        });

        this.modal.onSave(_ -> {
            if (!modal.isValidInput()) {
                return;
            }

            User user = modal.getModel();
            new SwingWorker<Integer, Void>() {
                @Override protected Integer doInBackground() {
                    try {
                        User existingUser = service.findByUsername(user.getUsername());
                        if (existingUser != null && (user.getId() == null || !existingUser.getId().equals(user.getId()))) {
                            modal.showValidationError(Constants.ValidateMessage.USERNAME_IS_ALREADY_TAKEN);
                            return -1;
                        }

                        return user.getId() == null ? service.create(user) : service.update(user);
                    } catch (Exception ex) {
                        view.showErrorMessage(ex.getMessage());
                        return -1;
                    }
                }
                @Override protected void done() {
                    try {
                        if (get() > 0) {
                            if (user.getId() == null) {
                                view.showSuccessMessage(Constants.SuccessMessage.CREATE_USER_SUCCESS);
                            } else {
                                view.showSuccessMessage(Constants.SuccessMessage.UPDATE_USER_SUCCESS);
                            }
                        }
                    } catch (Exception ex) {
                        view.showErrorMessage(ex.getMessage());
                    }
                    modal.setVisible(false);
                    loadData();
                }
            }.execute();
        });
        
        loadData();
    }

    public UserView getView() { return view; }

    public void loadData() {
        new SwingWorker<List<User>, Void>() {
            @Override protected List<User> doInBackground() { return service.findAll(); }
            @Override protected void done() {
                try {
                    users = get();
                    view.setTableModel(new UserTableModel(users));
                } catch (Exception e) {
                    view.showErrorMessage(e.getMessage());
                }
            }
        }.execute();
    }

    static class UserTableModel extends AbstractTableModel {
        private final String[] cols = { "Id", "Username", "Role", "Status", "Last Login At", "Created At", "Created By", "Updated At", "Updated By" };
        private final List<User> data;
        UserTableModel(List<User> data) { this.data = data; }
        @Override public int getRowCount() { return data.size(); }
        @Override public int getColumnCount() { return cols.length; }
        @Override public String getColumnName(int c) { return cols[c]; }
        @Override public Object getValueAt(int r, int c) {
            User o = data.get(r);
            return switch (c) {
                case 0 -> o.getId();
                case 1 -> o.getUsername();
                case 2 -> o.getRole();
                case 3 -> o.getStatus();
                case 4 -> o.getLastLoginAt() == null ? null : o.getLastLoginAt().format(DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm));
                case 5 -> o.getCreatedAt() == null ? null : o.getCreatedAt().format(DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm));
                case 6 -> o.getCreatedBy();
                case 7 -> o.getUpdatedAt() == null ? null : o.getUpdatedAt().format(DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm));
                case 8 -> o.getUpdatedBy();
                default -> "";
            };
        }
    }
}