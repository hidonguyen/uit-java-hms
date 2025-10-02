package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.presenter;

import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import vn.edu.uit.cntt.lt.e33.ie303.hms.bootstrap.DIContainer;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Guest;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service.IGuestService;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.guest.CreateOrEditGuestModal;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.guest.GuestView;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.Constants;

public class GuestPresenter {
    private final GuestView view;
    private final CreateOrEditGuestModal modal;
    private final IGuestService service;

    private List<Guest> guests = new ArrayList<>();

    public GuestPresenter(JFrame parentFrame) {
        this.view = new GuestView();
        this.modal = new CreateOrEditGuestModal(parentFrame);
        this.service = DIContainer.getInstance().getGuestService();

        view.onAdd(_ -> {
            modal.setModel(null);
            modal.setVisible(true);
        });

        view.onEdit(_ -> {
            int row = view.getSelectedRow();
            if (row >= 0 && row < guests.size()) {
                modal.setModel(guests.get(row));
                modal.setVisible(true);
            }
        });

        view.onDelete(_ -> {
            int row = view.getSelectedRow();
            if (row >= 0 && row < guests.size()) {
                Guest g = guests.get(row);
                int confirm = JOptionPane.showConfirmDialog(
                        view,
                        "Are you sure you want to delete guest: " + g.getName() + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    new SwingWorker<Integer, Void>() {
                        @Override
                        protected Integer doInBackground() {
                            return service.delete(g.getId());
                        }

                        @Override
                        protected void done() {
                            try {
                                if (get() > 0)
                                    view.showSuccessMessage(Constants.SuccessMessage.DELETE_GUEST_SUCCESS);
                            } catch (Exception ex) {
                                view.showErrorMessage(ex.getMessage());
                            }
                            loadData();
                        }
                    }.execute();
                }
            }
        });

        view.onSearch(_ -> {
            String q = view.getSearchQuery();
            if (q == null || q.isEmpty()) {
                view.setTableModel(new GuestTableModel(guests));
            } else {
                String qq = q.toLowerCase();
                List<Guest> filtered = guests.stream()
                        .filter(g -> (g.getName() != null && g.getName().toLowerCase().contains(qq)) ||
                                (g.getPhone() != null && g.getPhone().toLowerCase().contains(qq)) ||
                                (g.getEmail() != null && g.getEmail().toLowerCase().contains(qq)))
                        .toList();
                view.setTableModel(new GuestTableModel(filtered));
            }
        });

        modal.onSave(_ -> {
            if (!modal.isValidInput())
                return;
            Guest g = modal.getModel();
            new SwingWorker<Integer, Void>() {
                @Override
                protected Integer doInBackground() {
                    try {
                        return g.getId() == null ? service.create(g) : service.update(g);
                    } catch (Exception ex) {
                        view.showErrorMessage(ex.getMessage());
                        return -1;
                    }
                }

                @Override
                protected void done() {
                    try {
                        if (get() > 0) {
                            view.showSuccessMessage(g.getId() == null
                                    ? Constants.SuccessMessage.CREATE_GUEST_SUCCESS
                                    : Constants.SuccessMessage.UPDATE_GUEST_SUCCESS);
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

    public GuestView getView() {
        view.authorize();
        return view;
    }

    public void loadData() {
        new SwingWorker<List<Guest>, Void>() {
            @Override
            protected List<Guest> doInBackground() {
                return service.findAll();
            }

            @Override
            protected void done() {
                try {
                    guests = get();
                    view.setTableModel(new GuestTableModel(guests));
                } catch (Exception e) {
                    view.showErrorMessage(e.getMessage());
                }
            }
        }.execute();
    }

    static class GuestTableModel extends AbstractTableModel {
        private final String[] cols = {
                "Id", "Name", "Gender", "DOB", "Nationality",
                "Phone", "Email", "Address", "Description",
                "Created At", "Created By", "Updated At", "Updated By"
        };
        private final List<Guest> data;

        GuestTableModel(List<Guest> data) {
            this.data = data;
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return cols.length;
        }

        @Override
        public String getColumnName(int c) {
            return cols[c];
        }

        @Override
        public Object getValueAt(int r, int c) {
            Guest o = data.get(r);
            DateTimeFormatter dt = DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyy);
            DateTimeFormatter dtt = DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm);
            return switch (c) {
                case 0 -> o.getId();
                case 1 -> o.getName();
                case 2 -> o.getGender();
                case 3 -> o.getDateOfBirth() == null ? null : o.getDateOfBirth().format(dt);
                case 4 -> o.getNationality();
                case 5 -> o.getPhone();
                case 6 -> o.getEmail();
                case 7 -> o.getAddress();
                case 8 -> o.getDescription();
                case 9 -> o.getCreatedAt() == null ? null : o.getCreatedAt().format(dtt);
                case 10 -> o.getCreatedBy();
                case 11 -> o.getUpdatedAt() == null ? null : o.getUpdatedAt().format(dtt);
                case 12 -> o.getUpdatedBy();
                default -> "";
            };
        }
    }
}
