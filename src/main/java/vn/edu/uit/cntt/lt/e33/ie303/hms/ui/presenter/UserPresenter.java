package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.presenter;

import vn.edu.uit.cntt.lt.e33.ie303.hms.bootstrap.DIContainer;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.User;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service.IUserService;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.UserView;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class UserPresenter {
    private final UserView view;
    private final CreateOrEditUserModal modal;
    private final IUserService service;

    public UserPresenter() {
        this.view = new UserView();
        this.modal = new CreateOrEditUserModal();
        this.service = DIContainer.getInstance().getUserService();
        this.view.onRefresh(_ -> load());
        this.view.setTableModel(new UserTableModel(new ArrayList<>()));
    }

    public UserView getView() { return view; }

    public void load() {
        new SwingWorker<List<User>, Void>() {
            @Override protected List<User> doInBackground() { return service.findAll(); }
            @Override protected void done() {
                try {
                    view.setTableModel(new UserTableModel(get()));
                } catch (Exception e) {
                    view.showError(e.getMessage());
                }
            }
        }.execute();
    }

    static class UserTableModel extends AbstractTableModel {
        private final String[] cols = {"Id", "Username", "Role", "Status", "Last Login At", "Created At", "Created By", "Updated At", "Updated By"};
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
                case 4 -> o.getLastLoginAt();
                case 5 -> o.getCreatedAt();
                case 6 -> o.getCreatedBy();
                case 7 -> o.getUpdatedAt();
                case 8 -> o.getUpdatedBy();
                default -> "";
            };
        }
    }
}